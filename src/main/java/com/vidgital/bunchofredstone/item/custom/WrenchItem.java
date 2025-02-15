package com.vidgital.bunchofredstone.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;

import java.util.Map;

public class WrenchItem extends Item
{
    private static final Map<Direction.Axis, Direction.Axis> _AXIS_MAP =
            Map.of(
                    Direction.Axis.X, Direction.Axis.Y,
                    Direction.Axis.Y, Direction.Axis.Z,
                    Direction.Axis.Z, Direction.Axis.X
            );


//    @Override
//    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
//    {
//        Level level = context.getLevel();
//        BlockPos blockPos = context.getClickedPos();
//        BlockState blockState = level.getBlockState(blockPos);
//        Block targetBlock = blockState.getBlock();
//        if(!level.isClientSide && _AXIS_LIST.contains(targetBlock))
//        {
//            blockState.rotate(level, blockPos, Rotation.CLOCKWISE_90);
//            level.playSound(null,context.getClickedPos(), SoundEvents.SPYGLASS_USE, SoundSource.BLOCKS);
//        }
//        return InteractionResult.SUCCESS;
//    }

    @Override
    public InteractionResult useOn(UseOnContext pContext)
    {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();

        if(!level.isClientSide())
        {
            BlockPos blockPos = pContext.getClickedPos();
            Block selectedBlock = level.getBlockState(blockPos).getBlock();
            if(!this.HandleInteraction(selectedBlock, level.getBlockState(blockPos), level, blockPos, true, pContext.getItemInHand()))
            {
                return InteractionResult.FAIL;
            }
            level.playSound(null, pContext.getClickedPos(),SoundEvents.SPYGLASS_USE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.SUCCESS;
    }

    private boolean HandleInteraction(Block pSelectedBlock, BlockState pStateClicked, LevelAccessor pAccessor, BlockPos pPos, boolean pShouldRotate, ItemStack pWrenchStack )
    {

        if(pSelectedBlock instanceof RotatedPillarBlock)
        {
            pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.AXIS, _AXIS_MAP.get(pStateClicked.getValue(BlockStateProperties.AXIS))), 11);
            return true;
        }
        return false;
    }

    //Creates an object of WrenchItem class.
    public WrenchItem(Properties pProperties)
    {
        super(pProperties);
    }

}
