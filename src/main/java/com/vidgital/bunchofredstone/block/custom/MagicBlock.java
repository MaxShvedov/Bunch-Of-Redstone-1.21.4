package com.vidgital.bunchofredstone.block.custom;

import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class MagicBlock extends Block
{

    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult)
    {
        pLevel.playSound(pPlayer, pPos, SoundEvents.AMETHYST_CLUSTER_PLACE, SoundSource.BLOCKS, 1f, 1f);
        return InteractionResult.SUCCESS;
    }

    @Override
    public void stepOn(Level pLevel, BlockPos pPos, BlockState pState, Entity pEntity)
    {
        if(pEntity instanceof ItemEntity itemEntity)
        {
            if(itemEntity.getItem().getItem() == ModItems.WRENCH.get())
            {
                itemEntity.setItem(new ItemStack (Items.COPPER_INGOT, 4));
            }
        }

        super.stepOn(pLevel, pPos, pState, pEntity);
    }

    //Creates an object of MagicBlock class
    public MagicBlock(Properties properties)
    {
        super(properties);
    }
}
