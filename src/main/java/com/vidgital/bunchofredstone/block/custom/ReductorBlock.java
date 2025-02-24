package com.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DiodeBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

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
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder)
    {
        pBuilder.add(FACING, DELAY, POWERED);
    }

    public ReductorBlock(BlockBehaviour.Properties properties)
    {
        super(properties);
    }
}
