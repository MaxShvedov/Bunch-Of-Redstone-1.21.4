package com.vidgital.bunchofredstone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CopperRodBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    protected static final VoxelShape Y_AXIS_AABB = Block.box(7.0f, 0.0f, 7.0f, 9.0f, 16.0f, 9.0f);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(7.0f, 7.0f, 0.0f, 9.0f, 9.0f, 16.0f);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0f, 7.0f, 7.0f, 16.0f, 9.0f, 9.0f);

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch ((Direction.Axis) pState.getValue(AXIS))
        {
            case X:
            default:
                return X_AXIS_AABB;
            case Z:
                return Z_AXIS_AABB;
            case Y:
                return Y_AXIS_AABB;
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
    pBuilder.add(WATERLOGGED).add(AXIS);
    }

    @Override
    protected FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        FluidState fluidState = pContext.getLevel().getFluidState(pContext.getClickedPos());
        boolean flag = fluidState.getType() == Fluids.WATER;
        return super.getStateForPlacement(pContext).setValue(WATERLOGGED, flag);
    }

    @Override
    protected BlockState updateShape(
            BlockState pState,
            LevelReader pLevel,
            ScheduledTickAccess pScheduledTickAccess,
            BlockPos pPos,
            Direction pDirection,
            BlockPos pNeighborPos,
            BlockState pNeighborState,
            RandomSource pRandom)
    {
        if(pState.getValue(WATERLOGGED))
        {
            pScheduledTickAccess.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pLevel, pScheduledTickAccess, pPos, pDirection, pNeighborPos, pNeighborState, pRandom);
    }

    public CopperRodBlock(Properties properties) {
        super(properties);
    }
}
