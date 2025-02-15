package com.vidgital.bunchofredstone.item.custom;

import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class MagicWrenchItem extends Item
{
    private static final Map<Block, Block> _CHISEL_MAP =
            Map.of(
                    Blocks.STONE, Blocks.STONE_BRICKS,
                    Blocks.END_STONE, Blocks.END_STONE_BRICKS,
                    Blocks.COBBLED_DEEPSLATE, ModBlocks.COBBLED_DEEPSLATE_TILES.get(),
                    Blocks.STONE_BRICKS, Blocks.CRACKED_STONE_BRICKS
            );

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        Level level = pContext.getLevel();
        Block selectedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(_CHISEL_MAP.containsKey(selectedBlock))
        {
            if(!level.isClientSide())
            {
                level.setBlockAndUpdate(pContext.getClickedPos(), _CHISEL_MAP.get(selectedBlock).defaultBlockState());
                pContext.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) pContext.getPlayer()),
                        item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND));

                level.playSound(null, pContext.getClickedPos(), SoundEvents.SPYGLASS_USE, SoundSource.BLOCKS);
            }
        }

        return InteractionResult.SUCCESS;
    }

    //Creates an object of WrenchItem class
    public MagicWrenchItem(Properties pProperties)
    {
        super(pProperties);
    }
}
