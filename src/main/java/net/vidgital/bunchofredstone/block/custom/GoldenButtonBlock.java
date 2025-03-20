package net.vidgital.bunchofredstone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class GoldenButtonBlock extends ButtonBlock
{
    public static final IntegerProperty POWER = BlockStateProperties.POWER;
    public static final BooleanProperty PRESSED = BooleanProperty.create("pressed");
    private final int ticksToStayPressed;
    private final BlockSetType type;

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext)
    {
        Direction direction = pState.getValue(FACING);
        boolean flag = pState.getValue(POWER) > 0;
        switch ((AttachFace)pState.getValue(FACE)) {
            case FLOOR:
                if (direction.getAxis() == Direction.Axis.X) {
                    return flag ? PRESSED_FLOOR_AABB_X : FLOOR_AABB_X;
                }

                return flag ? PRESSED_FLOOR_AABB_Z : FLOOR_AABB_Z;
            case WALL:
                return switch (direction) {
                    case EAST -> flag ? PRESSED_EAST_AABB : EAST_AABB;
                    case WEST -> flag ? PRESSED_WEST_AABB : WEST_AABB;
                    case SOUTH -> flag ? PRESSED_SOUTH_AABB : SOUTH_AABB;
                    case NORTH, UP, DOWN -> flag ? PRESSED_NORTH_AABB : NORTH_AABB;
                };
            case CEILING:
            default:
                if (direction.getAxis() == Direction.Axis.X) {
                    return flag ? PRESSED_CEILING_AABB_X : CEILING_AABB_X;
                } else {
                    return flag ? PRESSED_CEILING_AABB_Z : CEILING_AABB_Z;
                }
        }
    }

    @Override
    protected InteractionResult useWithoutItem(
            BlockState pState,
            Level pLevel,
            BlockPos pPos,
            Player pPlayer,
            BlockHitResult pResult
    )
    {
        if (pState.getValue(PRESSED))
        {
            return InteractionResult.SUCCESS;
        }
        else
        {
            this.press(pState, pLevel, pPos, pPlayer);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    public void press(BlockState pState, Level pLevel, BlockPos pPos, @Nullable Player pPlayer)
    {
        if(!pState.getValue(POWERED))
            this.playSound(pPlayer, pLevel, pPos, true);

        pState = pState.setValue(POWERED, true);
        pState = pState.setValue(POWER, Math.min(pState.getValue(POWER) + 1, 15));
        pState = pState.setValue(PRESSED, true);
        pLevel.setBlock(pPos, pState, 3);
        this.updateNeighbours(pState, pLevel, pPos);
        pLevel.scheduleTick(pPos, this, this.ticksToStayPressed);
        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_ACTIVATE, pPos);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel level, BlockPos blockPos, RandomSource randomSource)
    {
        if (blockState.getValue(POWERED))
        {
            this.checkPressed(blockState, level, blockPos);
        }
    }

    protected void checkPressed(BlockState pState, Level pLevel, BlockPos pPos)
    {
        AbstractArrow abstractarrow = this.type.canButtonBeActivatedByArrows()
                ? pLevel.getEntitiesOfClass(AbstractArrow.class, pState.getShape(pLevel, pPos).bounds().move(pPos)).stream().findFirst().orElse(null)
                : null;
        boolean flag = abstractarrow != null;
        boolean flag1 = pState.getValue(POWERED);
        boolean flag2 = pState.getValue(PRESSED);
        if (flag != flag2)
        {
            pState = pState.setValue(PRESSED, flag);
            pState = pState.setValue(POWERED, true);
            pLevel.setBlock(pPos, pState, 3);
            pLevel.scheduleTick(pPos, this, this.ticksToStayPressed);
        }
        else if(flag1 != flag2)
        {
            pState = pState.setValue(POWERED, false);
            pState = pState.setValue(POWER, flag2 ? pState.getValue(POWER) : 0);
            pLevel.setBlock(pPos, pState, 3);
            this.updateNeighbours(pState, pLevel, pPos);
            this.playSound(null, pLevel, pPos, flag2);
            pLevel.gameEvent(abstractarrow, flag2 ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pPos);
        }

        if(flag)
        {
            pState = pState.setValue(POWER, Math.min(pState.getValue(POWER) + 1, 15));
            pLevel.setBlock(pPos, pState, 3);
            pLevel.scheduleTick(new BlockPos(pPos), this, this.ticksToStayPressed);
            this.updateNeighbours(pState, pLevel, pPos);
        }
    }

    private void updateNeighbours(BlockState pState, Level pLevel, BlockPos pPos)
    {
        Direction direction = getConnectedDirection(pState).getOpposite();
        Orientation orientation = ExperimentalRedstoneUtils.initialOrientation(
                pLevel, direction, direction.getAxis().isHorizontal() ? Direction.UP : pState.getValue(FACING)
        );
        pLevel.updateNeighborsAt(pPos, this, orientation);
        pLevel.updateNeighborsAt(pPos.relative(direction), this, orientation);
    }

    @Override
    protected int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return pBlockState.getValue(POWER);
    }

    @Override
    protected int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return getConnectedDirection(pBlockState) == pSide ? pBlockState.getValue(POWER) : 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING, POWERED, POWER, FACE, PRESSED);
    }

    public GoldenButtonBlock(BlockSetType pType, int pTicksToStayPressed, Properties pProperties)
    {
        super(pType, pTicksToStayPressed, pProperties);
        this.ticksToStayPressed = pTicksToStayPressed;
        this.type = pType;
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
                        .setValue(PRESSED, false)
                        .setValue(POWER, 0)
                        .setValue(FACE, AttachFace.WALL)
        );
    }
}
