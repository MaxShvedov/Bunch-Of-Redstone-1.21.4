package com.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected int getDelay(BlockState pState)
    {
        return pState.getValue(DELAY) * 2;
    }


    @Override
    protected boolean sideInputDiodesOnly()
    {
        return true;
    }

    @Override
    protected int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return this.getSignal(pBlockState, pBlockAccess, pPos, pSide);
    }

    @Override
    protected int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        if(!pBlockState.getValue(POWERED))
        {
            return 0;
        }
        else
        {
            return pBlockState.getValue(FACING) == pSide ? this.getOutputSignal(pBlockAccess, pPos, pBlockState) : 0;
        }
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
