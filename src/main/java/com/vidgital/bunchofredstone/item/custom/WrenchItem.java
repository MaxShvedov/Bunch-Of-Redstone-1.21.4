package com.vidgital.bunchofredstone.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class WrenchItem extends Item
{

    private static final Map<Direction.Axis, Direction.Axis> _AXIS_MAP =
            Map.of(
                    Direction.Axis.X, Direction.Axis.Y,
                    Direction.Axis.Y, Direction.Axis.Z,
                    Direction.Axis.Z, Direction.Axis.X
            );

    private static final Map<Direction, Direction> _FACING_6_MAP =
            Map.of(
                    Direction.NORTH, Direction.EAST,
                    Direction.EAST, Direction.SOUTH,
                    Direction.SOUTH, Direction.WEST,
                    Direction.WEST, Direction.UP,
                    Direction.UP, Direction.DOWN,
                    Direction.DOWN, Direction.NORTH
            );

    private static final Map<Direction, Direction> _FACING_4_MAP =
            Map.of(
                    Direction.NORTH, Direction.EAST,
                    Direction.EAST, Direction.SOUTH,
                    Direction.SOUTH, Direction.WEST,
                    Direction.WEST, Direction.NORTH
            );

    private static final Map<SlabType, SlabType> _SLAB_MAP =
            Map.of(
                    SlabType.BOTTOM, SlabType.TOP,
                    SlabType.TOP, SlabType.BOTTOM
            );

    private static final Map<Half, Half> _HALF_MAP  =
    Map.of(
            Half.BOTTOM, Half.TOP,
            Half.TOP, Half.BOTTOM
        );

    private static final List<Block> _IGNORE_BLOCKS =
            List.of();

    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer) {
        if(!pLevel.isClientSide())
        {

            if(this.HandleInteraction(pPlayer, pState, pLevel, pPos, false, null))
                pLevel.playSound(null, pPos ,SoundEvents.SPYGLASS_USE, SoundSource.BLOCKS);
        }
        return false;
    }

    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext pContext)
    {
        Player player = pContext.getPlayer();
        Level level = pContext.getLevel();
        Direction.Axis axis = pContext.getClickedFace().getAxis();
        if(!level.isClientSide())
        {
            BlockPos blockPos = pContext.getClickedPos();
            if(!this.HandleInteraction(player, level.getBlockState(blockPos), level, blockPos, true, axis))
            {
                return InteractionResult.FAIL;
            }
            level.playSound(null, pContext.getClickedPos(),SoundEvents.SPYGLASS_USE, SoundSource.BLOCKS);
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, Player player) {
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    private boolean HandleInteraction(Player pPlayer, BlockState pStateClicked, LevelAccessor pAccessor, BlockPos pPos, boolean rightMouseClicked, Direction.Axis pAxis )
    {
        StateDefinition<Block, BlockState> stateDefinition = pStateClicked.getBlock().getStateDefinition();
        Collection<Property<?>> collection = stateDefinition.getProperties();


        if(collection.contains(BlockStateProperties.AXIS))
        {
            if(rightMouseClicked)
            {
                if (pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.AXIS, getKeyByValue(_AXIS_MAP, pStateClicked.getValue(BlockStateProperties.AXIS))), 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.AXIS, _AXIS_MAP.get(pStateClicked.getValue(BlockStateProperties.AXIS))), 11);
            }
            return true;
        }
        if(collection.contains(BlockStateProperties.FACING))
        {
            if(rightMouseClicked)
            {
                if (pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.FACING, pStateClicked.getValue(BlockStateProperties.FACING).getCounterClockWise(pAxis)), 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.FACING, pStateClicked.getValue(BlockStateProperties.FACING).getClockWise(pAxis)), 11);
            }
            else
            {
                if (pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.FACING, getKeyByValue(_FACING_6_MAP, pStateClicked.getValue(BlockStateProperties.FACING))), 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.FACING, _FACING_6_MAP.get(pStateClicked.getValue(BlockStateProperties.FACING))), 11);
            }
            return true;
        }
        if(collection.contains(BlockStateProperties.HORIZONTAL_FACING))
        {
            if(rightMouseClicked)
            {
                if (pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.HORIZONTAL_FACING, pStateClicked.getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise(Direction.Axis.Y)), 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.HORIZONTAL_FACING, pStateClicked.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise(Direction.Axis.Y)), 11);
            }
            return true;
        }
        if(collection.contains(BlockStateProperties.ROTATION_16))
        {
            if(rightMouseClicked)
            {
                if(pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.ROTATION_16,
                            pStateClicked.getValue(BlockStateProperties.ROTATION_16) - 1 < 0 ? 15 : pStateClicked.getValue(BlockStateProperties.ROTATION_16) - 1) , 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.ROTATION_16, (pStateClicked.getValue(BlockStateProperties.ROTATION_16) + 1) % 16), 11);
            }
            else
            {
                if(pPlayer.isCrouching())
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.ROTATION_16,
                            pStateClicked.getValue(BlockStateProperties.ROTATION_16) - 4 < 0 ? 16 + pStateClicked.getValue(BlockStateProperties.ROTATION_16) - 4 : pStateClicked.getValue(BlockStateProperties.ROTATION_16) - 4) , 11);
                else
                    pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.ROTATION_16, (pStateClicked.getValue(BlockStateProperties.ROTATION_16) + 4) % 16), 11);
            }
            return true;
        }
        if(collection.contains(BlockStateProperties.SLAB_TYPE))
        {
            if(rightMouseClicked)
                pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.SLAB_TYPE, _SLAB_MAP.get(pStateClicked.getValue(BlockStateProperties.SLAB_TYPE))), 11);
            return true;
        }
        if(collection.contains(BlockStateProperties.HALF))
        {
            if(!rightMouseClicked)
                pAccessor.setBlock(pPos, pStateClicked.setValue(BlockStateProperties.HALF, _HALF_MAP.get(pStateClicked.getValue(BlockStateProperties.HALF))), 11);
        }

        return false;
    }

    //Заготовка под метод. Вызывается при нажатии ПКМ по блоку с ключом в руках.
    private boolean InteractionRightClick()
    {
        return false;
    }

    //Заготовка под метод. Вызывается при нажатии ЛКМ по блоку с ключом в руках.
    private boolean InteractionLeftClick()
    {
        return false;
    }



    //Returns key from key-value pair from map.
    private static <T, E> T getKeyByValue(Map<T, E> map, E value)
    {
        T pKey = null;
        for (Map.Entry<T, E> entry : map.entrySet())
        {
            if (Objects.equals(value, entry.getValue()))
            {
                pKey = entry.getKey();
                break;
            }
        }
        return pKey;
    }



    //Заготовки под будущие методы. Не забудь убрать static при необходимости.
    private static void RotateAxis(){}
    private static void RotateHorizontalFacing(){}
    private static void RotateTrapdoor(){}
    private static void ChangeDoorHinge(){}
    private static void RotateFacing(){}
    private static void RotateAngular(){}
    private static void RotateSlab(){}
    private static void RotateStairs(){}
    private static void ChangeStairsShape(){}
    private static void RotateRails(){}
    private static void ChangeRailsType(){}
    private static void RotateHopper(){}
    private static void RotateCrafter(){}
    private static void RotateMushroom(){}
    private static void AddFenceSide(){}
    private static void AddWallSite(){}
    private static void RotateFence(){}
    private static void RotateWallPlants(){}
    private static void ConnectMinecarts(){}
    private static void DisconnectMinecarts(){}
    private static void ArmorStandInteraction(){}


    //Creates an object of WrenchItem class.
    public WrenchItem(Properties pProperties)
    {
        super(pProperties);
    }

}
