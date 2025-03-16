package com.vidgital.bunchofredstone.block.custom;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

public class RedstoneLanternBlock extends LanternBlock
{
    public static final BooleanProperty LIT = BlockStateProperties.LIT;

    public static final BooleanProperty HANGING = LanternBlock.HANGING;
    public static final BooleanProperty WATERLOGGED = LanternBlock.WATERLOGGED;
    public static final Map<BlockGetter, List<RedstoneLanternBlock.Toggle>> RECENT_TOGGLES = new WeakHashMap<>();
    public static final int RECENT_TOGGLE_TIMER = 60;
    public static final int MAX_RECENT_TOGGLE_TIMER = 8;
    public static final int RESTART_DELAY = 160;
    public static final int TOGGLE_DELAY = 2;

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston)
    {
        this.notifyNeighbors(pLevel, pPos, pState);
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston)
    {
        if(!pMovedByPiston)
        {
            this.notifyNeighbors(pLevel, pPos, pState);
        }
    }

    private void notifyNeighbors(Level pLevel, BlockPos pPos, BlockState pState)
    {
        Orientation orientation = this.randomOrientation(pLevel, pState);

        for (Direction direction : Direction.values())
        {
            pLevel.updateNeighborsAt(pPos.relative(direction), this, ExperimentalRedstoneUtils.withFront(orientation, direction));
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder)
    {
        builder.add(LIT);
        builder.add(HANGING);
        builder.add(WATERLOGGED);
    }

    @Override
    protected boolean isSignalSource(BlockState pState)
    {
        return true;
    }

    @Override
    protected int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pState.getValue(LIT) && Direction.DOWN != pDirection ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pDirection == Direction.UP ? pState.getSignal(pLevel, pPos, pDirection) : 0;
    }

    protected boolean hasNeighborSignal(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return pState.getValue(HANGING) && pLevel.hasSignal(pPos.above(), Direction.DOWN);
    }


    @Override
    protected void neighborChanged(BlockState pState, Level pLevel, BlockPos pPos, Block pNeighborBlock, @Nullable Orientation pOrientation, boolean pMovedByPiston)
    {
        if (pState.getValue(LIT) == this.hasNeighborSignal(pLevel, pPos, pState) && !pLevel.getBlockTicks().willTickThisTick(pPos, this))
        {
            pLevel.scheduleTick(pPos, this, 2);
        }
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pSource)
    {
        boolean flag = this.hasNeighborSignal(pLevel, pPos, pState);
        List<RedstoneLanternBlock.Toggle> list = RECENT_TOGGLES.get(pLevel);

        while (list != null && !list.isEmpty() && pLevel.getGameTime() - list.get(0).when > 60L)
        {
            list.remove(0);
        }

        if (pState.getValue(LIT))
        {
            if (flag)
            {
                pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.valueOf(false)), 3);
                if (isToggledTooFrequently(pLevel, pPos, true))
                {
                    pLevel.levelEvent(1502, pPos, 0);
                    pLevel.scheduleTick(pPos, pLevel.getBlockState(pPos).getBlock(), 160);
                }
            }
        }
        else if (!flag && !isToggledTooFrequently(pLevel, pPos, false))
        {
            pLevel.setBlock(pPos, pState.setValue(LIT, Boolean.valueOf(true)), 3);
        }
    }

    private static boolean isToggledTooFrequently(Level pLevel, BlockPos pPos, boolean pLogToggle)
    {
        List<RedstoneLanternBlock.Toggle> list = RECENT_TOGGLES.computeIfAbsent(pLevel, blockGetter -> Lists.newArrayList());
        if (pLogToggle)
        {
            list.add(new RedstoneLanternBlock.Toggle(pPos.immutable(), pLevel.getGameTime()));
        }

        int i = 0;
        for (RedstoneLanternBlock.Toggle redstonelanternblock$toggle : list)
        {
            if (redstonelanternblock$toggle.pos.equals(pPos))
            {
                if (++i >= 8) {
                    return true;
                }
            }
        }

        return false;
    }

    @Nullable
    protected Orientation randomOrientation(Level pLevel, BlockState pState)
    {
        return ExperimentalRedstoneUtils.initialOrientation(pLevel, null, Direction.UP);
    }

    public RedstoneLanternBlock(Properties properties)
    {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(LIT, true)
                .setValue(HANGING, false)
                .setValue(WATERLOGGED, false));
    }

    public static class Toggle
    {
        final BlockPos pos;
        final long when;

        public Toggle(BlockPos pPos, long pWhen)
        {
            this.pos = pPos;
            this.when = pWhen;
        }
    }
}
