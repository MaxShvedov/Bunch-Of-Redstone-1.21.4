package net.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;
import org.jetbrains.annotations.Nullable;

public class ReductorBlock extends DiodeBlock
{
    public static final MapCodec<ReductorBlock> CODEC = simpleCodec(ReductorBlock::new);
    public static final IntegerProperty DELAY = BlockStateProperties.DELAY;

    @Override
    public MapCodec<ReductorBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if(!pPlayer.getAbilities().mayBuild)
        {
            return InteractionResult.PASS;
        }
        else
        {
            pLevel.setBlock(pPos, pState.cycle(DELAY), 3);
            this.updateNeighborsInFront(pLevel, pPos, pState);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected int getDelay(BlockState pState)
    {
        return pState.getValue(DELAY) * 2;
    }

    @Override
    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState)
    {
        return Math.max(getInputSignal((Level) pLevel, pPos, pState) - pState.getValue(DELAY), 0);
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
         if(direction == null)
             return false;
         return direction.getAxis() == state.getValue(FACING).getAxis();
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource)
    {
        boolean flag = blockState.getValue(POWERED);
        boolean flag1 = this.shouldTurnOn(serverLevel, blockPos, blockState);
        if (flag && !flag1)
        {
            serverLevel.setBlock(blockPos, blockState.setValue(POWERED, false), 2);
        }
        else if (!flag)
            serverLevel.setBlock(blockPos, blockState.setValue(POWERED, true), 2);
        if (flag1)
        {
            serverLevel.scheduleTick(blockPos, this, this.getDelay(blockState), TickPriority.VERY_HIGH);
        }
        this.updateNeighborsInFront(serverLevel, blockPos, blockState);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING, DELAY, POWERED);
    }

    public ReductorBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(DELAY, 1)
                        .setValue(POWERED, false)
        );
    }
}
