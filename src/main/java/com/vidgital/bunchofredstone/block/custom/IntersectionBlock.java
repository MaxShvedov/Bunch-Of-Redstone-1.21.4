package com.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import com.vidgital.bunchofredstone.block.IntersectionMode;
import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.SignalGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.RedStoneWireBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.ticks.TickPriority;

public class IntersectionBlock extends DiodeBlock
{
    public static final MapCodec<IntersectionBlock> CODEC = simpleCodec(IntersectionBlock::new);
    public static final BooleanProperty PRIME_POWERED = BooleanProperty.create("prime_power");
    public static final BooleanProperty SECOND_POWERED = BooleanProperty.create("second_power");
    public static final EnumProperty<IntersectionMode> MODE = EnumProperty.create("mode", IntersectionMode.class);
    @Override
    protected MapCodec<? extends DiodeBlock> codec()
    {
        return CODEC;
    }

    @Override
    protected int getDelay(BlockState pState)
    {
        return 0;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        if(!pPlayer.mayBuild())
            return InteractionResult.PASS;
        else
        {
            pLevel.playSound(pPlayer, pPos, SoundEvents.ITEM_FRAME_ROTATE_ITEM, SoundSource.BLOCKS, 1.0f, 1.0f);
            pLevel.setBlock(pPos, pState.cycle(MODE), 3);
            pLevel.scheduleTick(pPos, this, 0);
            return InteractionResult.SUCCESS;
        }
    }

    @Override
    protected boolean isSignalSource(BlockState pState)
    {
        return true;
    }

    @Override
    protected int getSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        if(pBlockState.getValue(MODE) == IntersectionMode.FORWARD && pSide == pBlockState.getValue(FACING))
            return Math.max(this.getInputSignal((Level) pBlockAccess, pPos, pBlockState) - 2, 0);
        else return 0;
    }

    @Override
    protected int getDirectSignal(BlockState pBlockState, BlockGetter pBlockAccess, BlockPos pPos, Direction pSide)
    {
        return this.getSignal(pBlockState, pBlockAccess, pPos, pSide);
    }

    @Override
    protected int getInputSignal(Level pLevel, BlockPos pPos, BlockState pState)
    {
        Direction front = pState.getValue(FACING);
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        Direction back = front.getOpposite();
        BlockState blockState = pLevel.getBlockState(pPos.relative(front));
        int i = pLevel.getSignal(pPos.relative(front), front);
        int j = 0;

        switch (pState.getValue(MODE))
        {
            case FORWARD:
            {
                j = pLevel.getDirectSignal(pPos.relative(back), back);
                if(j >= i)
                    blockState = pLevel.getBlockState(pPos.relative(back));
                break;
            }
            case LEFT:
            {
                j = pLevel.getSignal(pPos.relative(left), left);
                if(j >= i)
                    blockState = pLevel.getBlockState(pPos.relative(left));
                break;
            }
            case RIGHT:
            {
                j = pLevel.getSignal(pPos.relative(right), right);
                if(j >= i)
                    blockState = pLevel.getBlockState(pPos.relative(right));
                break;
            }
        }
        return Math.max(Math.max(i, j), blockState.getBlock() instanceof RedStoneWireBlock ? blockState.getValue(RedStoneWireBlock.POWER) : 0);
    }

    @Override
    protected int getAlternateSignal(SignalGetter pLevel, BlockPos pPos, BlockState pState)
    {
        Direction front = pState.getValue(FACING);
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        Direction back = front.getOpposite();
        BlockState blockState = pLevel.getBlockState(pPos.relative(back));
        int i = 0;
        int j = 0;

        switch (pState.getValue(MODE))
        {
            case FORWARD:
            {
                i = Math.min(pLevel.getSignal(pPos.relative(left), left), 15);
                j = Math.min(pLevel.getSignal(pPos.relative(right), right), 15);
                if(j > i)
                    blockState = pLevel.getBlockState(pPos.relative(right));
                else
                    blockState = pLevel.getBlockState(pPos.relative(left));
                break;
            }
            case LEFT:
            {
                i = Math.min(pLevel.getSignal(pPos.relative(back), back), 15);
                j = Math.min(pLevel.getSignal(pPos.relative(right), right), 15);
                if(j > i)
                    blockState = pLevel.getBlockState(pPos.relative(right));
                break;
            }
            case RIGHT:
            {
                i = Math.min(pLevel.getSignal(pPos.relative(back), back), 15);
                j = Math.min(pLevel.getSignal(pPos.relative(left), left), 15);
                if(j > i)
                    blockState = pLevel.getBlockState(pPos.relative(left));
                break;
            }
        }
        return Math.max(Math.max(i, j), blockState.hasAnalogOutputSignal() ? blockState.getValue(RedStoneWireBlock.POWER) : 0);
    }

    protected int getOutputSignal(BlockGetter pLevel, BlockPos pPos, BlockState pState, Direction pSide)
    {
        Direction front = pState.getValue(FACING);
        Direction left = front.getClockWise();
        Direction right = front.getCounterClockWise();
        Direction back = front.getOpposite();

        if(pSide == back)
            return Math.max(this.getInputSignal((Level) pLevel, pPos, pState), 0);

        return 0;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING, PRIME_POWERED, SECOND_POWERED, MODE);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        boolean prime = pState.getValue(PRIME_POWERED);
        boolean second = pState.getValue(SECOND_POWERED);
        boolean flagPrime = this.shouldTurnOn(pLevel, pPos, pState);
        boolean flagSecond = this.shouldAlternativeTurnOn(pLevel, pPos, pState);
        boolean tickScheduleFlag = false;
            if (prime && !flagPrime)
                pState = pState.setValue(PRIME_POWERED, false);
            else if (!prime && flagPrime)
                pState = pState.setValue(PRIME_POWERED, true);
            else if (!flagPrime)
                    tickScheduleFlag = true;

            if (second && !flagSecond)
                pState = pState.setValue(SECOND_POWERED, false);
            else if(!second && flagSecond)
                pState = pState.setValue(SECOND_POWERED, true);
            else if (!flagSecond)
                    tickScheduleFlag = true;

            pLevel.setBlock(pPos, pState, 3);
            if (tickScheduleFlag)
                pLevel.scheduleTick(pPos, this, 0, TickPriority.VERY_HIGH);
    }

    @Override
    protected void checkTickOnNeighbor(Level pLevel, BlockPos pPos, BlockState pState)
    {
        boolean prime = pState.getValue(PRIME_POWERED);
        boolean second = pState.getValue(SECOND_POWERED);
        boolean primeFlag = this.shouldTurnOn(pLevel, pPos, pState);
        boolean secondFlag = this.shouldAlternativeTurnOn(pLevel, pPos, pState);
        if (prime != primeFlag && !pLevel.getBlockTicks().willTickThisTick(pPos, this))
        {
            TickPriority tickpriority = TickPriority.HIGH;
            if (this.shouldPrioritize(pLevel, pPos, pState))
            {
                tickpriority = TickPriority.EXTREMELY_HIGH;
            }
            else if (prime)
            {
                tickpriority = TickPriority.VERY_HIGH;
            }

            pLevel.scheduleTick(pPos, this, 0, tickpriority);
        }
        if (second != secondFlag && !pLevel.getBlockTicks().willTickThisTick(pPos, this))
        {
            TickPriority tickpriority = TickPriority.HIGH;
            if (this.shouldPrioritize(pLevel, pPos, pState))
            {
                tickpriority = TickPriority.EXTREMELY_HIGH;
            }
            else if (second)
            {
                tickpriority = TickPriority.VERY_HIGH;
            }

            pLevel.scheduleTick(pPos, this, 0, tickpriority);
        }
    }

    @Override
    protected boolean shouldTurnOn(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return this.getInputSignal(pLevel, pPos, pState) > 0;
    }

    protected boolean shouldAlternativeTurnOn(Level pLevel, BlockPos pPos, BlockState pState)
    {
        return this.getAlternateSignal(pLevel, pPos, pState) > 0;
    }

    public IntersectionBlock(Properties pProperties)
    {
        super(pProperties);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
                        .setValue(PRIME_POWERED, false)
                        .setValue(SECOND_POWERED, false)
                        .setValue(MODE,  IntersectionMode.FORWARD)
        );
    }
}
