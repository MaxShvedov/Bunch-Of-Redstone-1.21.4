package com.vidgital.bunchofredstone.block.custom;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.redstone.ExperimentalRedstoneUtils;
import net.minecraft.world.level.redstone.Orientation;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class CopperButtonBlock extends ButtonBlock
{
    public static final BooleanProperty POWER = BooleanProperty.create("power");
    public static final Supplier<BiMap<Block, Block>> WAX_OFF = Suppliers.memoize(() -> WeatheringCopperButtonBlock.WAX_ON.get().inverse());
    private final int ticksToStayPressed;

    @Override
    protected InteractionResult useWithoutItem(
            BlockState p_329418_,
            Level p_334611_,
            BlockPos p_332004_,
            Player p_330636_,
            BlockHitResult p_327724_
    )
    {
        if (p_329418_.getValue(POWERED))
        {
            return InteractionResult.CONSUME;
        } else
        {
            this.press(p_329418_, p_334611_, p_332004_, p_330636_);
            return InteractionResult.SUCCESS;
        }
    }


    @Override
    public void press(BlockState pState, Level pLevel, BlockPos pPos, @Nullable Player pPlayer)
    {
        pLevel.setBlock(pPos, pState.setValue(POWERED, true), 3);
        pState = pLevel.getBlockState(pPos);
        pLevel.setBlock(pPos, pState.setValue(POWER, true), 3);
        this.updateNeighbours(pState, pLevel, pPos);
        pLevel.scheduleTick(pPos, this, 10);
        this.playSound(pPlayer, pLevel, pPos, true);
        pLevel.gameEvent(pPlayer, GameEvent.BLOCK_ACTIVATE, pPos);
    }

    @Override
    protected InteractionResult useItemOn
            (ItemStack pStack,
             BlockState pState,
             Level pLevel,
             BlockPos pPos,
             Player pPlayer,
             InteractionHand pHand,
             BlockHitResult pHitResult
            )
    {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
        if (itemStack.getItem() instanceof AxeItem)
        {
            return getAxed(pState).<InteractionResult>map(blockState ->
                    {
                        if(!pPlayer.isCreative())
                            itemStack.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pHand));
                        pLevel.setBlock(pPos, blockState, 11);
                        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockState));
                        pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_WAX_OFF, SoundSource.BLOCKS, 1.0f, 1.0f);
                        pLevel.levelEvent(pPlayer, 3004, pPos, 0);
                        return InteractionResult.SUCCESS;
                    }
            ).orElse(InteractionResult.PASS);
        }
        this.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
        if(!pState.getValue(POWERED))
            return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    @Override
    protected int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return pBlockState.getValue(POWER) ? 15 : 0;
    }

    @Override
    protected int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return pBlockState.getValue(POWER) && getConnectedDirection(pBlockState) == pSide ? 15 : 0;
    }

    public static Optional<BlockState> getAxed(BlockState pState)
    {
        return Optional.ofNullable(WAX_OFF.get().get(pState.getBlock())).map(block -> block.withPropertiesOf(pState));
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource)
    {
        if(blockState.getValue(POWER))
        {
            serverLevel.setBlock(blockPos, blockState.setValue(POWER, false), 3);
            this.updateNeighbours(blockState, serverLevel, blockPos);
            serverLevel.scheduleTick(blockPos, this, ticksToStayPressed - 10);
        }
        else if (blockState.getValue(POWERED))
        {
            this.checkPressed(blockState, serverLevel, blockPos);
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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING, POWERED, POWER, FACE);
    }

    public CopperButtonBlock(BlockSetType pType, int pTicksToStayPressed, Properties pProperties)
    {
        super(pType, Math.max(pTicksToStayPressed, 10), pProperties);
        this.ticksToStayPressed = pTicksToStayPressed;
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(POWERED, false)
                        .setValue(POWER, false)
                        .setValue(FACE, AttachFace.WALL)
        );
    }
}
