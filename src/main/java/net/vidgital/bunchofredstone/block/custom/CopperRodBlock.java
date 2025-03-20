package net.vidgital.bunchofredstone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ParticleUtils;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class CopperRodBlock extends RotatedPillarBlock implements SimpleWaterloggedBlock
{
    protected static final VoxelShape Y_AXIS_AABB = Block.box(7.0f, 0.0f, 7.0f, 9.0f, 16.0f, 9.0f);
    protected static final VoxelShape Z_AXIS_AABB = Block.box(7.0f, 7.0f, 0.0f, 9.0f, 9.0f, 16.0f);
    protected static final VoxelShape X_AXIS_AABB = Block.box(0.0f, 7.0f, 7.0f, 16.0f, 9.0f, 9.0f);

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final Block pLightningRod;

    private static final int ACTIVATION_TICKS = 8;
    private static final int SPARK_CYCLE = 200;

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        switch (pState.getValue(AXIS))
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
    pBuilder
            .add(AXIS)
            .add(POWERED)
            .add(WATERLOGGED);
    }

    @Override
    protected boolean isSignalSource(BlockState pState)
    {
        return true;
    }

    @Override
    protected int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pState.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pState.getValue(POWERED) && pState.getValue(AXIS) == pDirection.getAxis() ? 15 : 0;
    }

    @Override
    protected void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston)
    {
        if (!pState.is(pNewState.getBlock())) {
            if (pState.getValue(POWERED)) {
                this.updateNeighbours(pState, pLevel, pPos);
            }

            super.onRemove(pState, pLevel, pPos, pNewState, pMovedByPiston);
        }
    }

    @Override
    protected void onPlace(BlockState pState, Level pLevel, BlockPos pPos, BlockState pOldState, boolean pMovedByPiston)
    {
        if (!pState.is(pOldState.getBlock())) {
            if (pState.getValue(POWERED) && !pLevel.getBlockTicks().hasScheduledTick(pPos, this)) {
                pLevel.setBlock(pPos, pState.setValue(POWERED, Boolean.valueOf(false)), 18);
            }
        }
    }

    //rewrite
    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos)
    {
        for(Direction direction : pState.getValue(AXIS).getDirections())
            pLevel.updateNeighborsAt(pPos.relative(direction), this, ExperimentalRedstoneUtils.initialOrientation(pLevel, direction, null));
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        for(Direction direction : pState.getValue(AXIS).getDirections())
        {
            BlockPos blockPos = pPos.relative(direction);
            BlockState blockState = pLevel.getBlockState(blockPos);
            if(blockState.is(pLightningRod))
            {
                if(blockState.getValue(LightningRodBlock.FACING) == direction
                        && blockState.getValue(LightningRodBlock.POWERED))
                {
                    onLightningStrike(pState, pLevel, pPos, direction);
                    break;
                }
            }
            pLevel.setBlock(pPos, pState.setValue(POWERED, false), 3);
            updateNeighbours(pState, pLevel, pPos);
        }
    }

    private void onLightningStrike(BlockState pState, Level pLevel, BlockPos pPos, Direction pDirection)
    {
        for(int i = 0; i < 101; i++)
        {
            BlockPos blockPos = pPos.relative(pDirection.getOpposite(), i);
            BlockState blockState = pLevel.getBlockState(blockPos);
            pLevel.scheduleTick(pPos, this, 8);

            if(!blockState.is(this))
                break;

            pLevel.setBlock(blockPos, blockState.setValue(POWERED, true), 3);
            updateNeighbours(blockState, pLevel, blockPos);
            pLevel.scheduleTick(blockPos, this, 8);
        }
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom)
    {
        for(Direction direction : pState.getValue(AXIS).getDirections())
        {
            for(int i = 0; i < 10; i++)
            {
                BlockPos blockPos = pPos.relative(direction, i);
                BlockState blockState = pLevel.getBlockState(blockPos);
                if (blockState.is(pLightningRod))
                {
                    if (pLevel.isThundering()
                            && (long) pLevel.random.nextInt(200) <= pLevel.getGameTime() % 200L
                            && blockPos.getY() == pLevel.getHeight(Heightmap.Types.WORLD_SURFACE, blockPos.getX(), blockPos.getZ()) - 1)
                    {
                        ParticleUtils.spawnParticlesAlongAxis(pState.getValue(AXIS), pLevel, pPos, 0.125, ParticleTypes.ELECTRIC_SPARK, UniformInt.of(1, 2));
                        break;
                    }
                }
                if(!blockState.is(this))
                    break;
            }
        }
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
        return this.defaultBlockState().setValue(AXIS, pContext.getClickedFace().getAxis()).setValue(WATERLOGGED, flag);
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

        if (!pLevel.isClientSide() && !pScheduledTickAccess.getBlockTicks().hasScheduledTick(pPos, this))
        {
            for(Direction direction : pState.getValue(AXIS).getDirections())
            {
                BlockPos blockPos = pPos.relative(direction);
                BlockState blockState = pLevel.getBlockState(blockPos);
                if(blockState.is(pLightningRod)
                        && blockState.getValue(LightningRodBlock.FACING) == direction
                        && blockState.getValue(LightningRodBlock.POWERED))
                {
                    pScheduledTickAccess.scheduleTick(pPos, this, 0);
                    break;
                }
            }
        }
        return super.updateShape(pState, pLevel, pScheduledTickAccess, pPos, pDirection, pNeighborPos, pNeighborState, pRandom);
    }

    public CopperRodBlock(Block pRod, Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AXIS, Direction.Axis.Y)
                        .setValue(WATERLOGGED, false)
                        .setValue(POWERED, false)
        );

        this.pLightningRod = pRod;
    }
}
