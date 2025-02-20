package com.vidgital.bunchofredstone.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.block.state.properties.SlabType;

import java.util.Collection;

public class NewWrenchItem extends Item
{

    //Method to prevent block destruction in Creative Mode.
    @Override
    public boolean canAttackBlock(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer)
    {
        return !pPlayer.isCreative();
    }


    //Method to interact with blocks without opening BlockEntities GUI, changing redstone blocks settings, etc.
    @Override
    public InteractionResult onItemUseFirst(ItemStack stack, UseOnContext context)
    {
        Player player = context.getPlayer();
        Level level = context.getLevel();
        if(!level.isClientSide())
        {
            BlockPos blockPos = context.getClickedPos();
            Direction face = context.getClickedFace();
            assert player != null;
            if(!this.InteractionRightClick(player, level.getBlockState(blockPos), level, blockPos, face, player.isCrouching()))
            {
                return InteractionResult.FAIL;
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.SUCCESS;
    }


    //Right click interaction handling method. Calls the proper method for different block properties.
    private boolean InteractionRightClick(Player pPlayer, BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        StateDefinition<Block, BlockState> stateDefinition = pState.getBlock().getStateDefinition();
        Collection<Property<?>> stateProperties = stateDefinition.getProperties();

        if(pState.getBlock() instanceof StairBlock stairBlock)
            return RotateHorizontalFacing(pState, pLevel, pPos, isPlayerCrouching);

        if(pState.getBlock() instanceof GrindstoneBlock grindstoneBlock)
            return RotateHorizontalFacing(pState, pLevel, pPos, isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.HORIZONTAL_FACING))
            return RotateHorizontalFacing(pState, pLevel, pPos, isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.FACING))
            return RotateFacing(pState, pLevel, pPos, pFace.getAxis() , isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.AXIS))
            return  RotateAxis(pState, pLevel, pPos, pFace.getAxis());

        if(stateProperties.contains(BlockStateProperties.ROTATION_16))
            return RotateAngular(pState, pLevel, pPos, isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.SLAB_TYPE))
            return RotateSlab(pState, pLevel, pPos);

        return false;
    }

    private boolean RotateStairs(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateGrindstone(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateHorizontalFacing(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        if(isPlayerCrouching)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise(Direction.Axis.Y)), 11);
        else
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise(Direction.Axis.Y)), 11);

        return true;
    }

    private boolean RotateFacing(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        if(isPlayerCrouching)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING, pState.getValue(BlockStateProperties.FACING).getCounterClockWise(interactedAxis)), 11);
        else
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING, pState.getValue(BlockStateProperties.FACING).getClockWise(interactedAxis)), 11);

        return true;
    }

    private boolean RotateAxis(BlockState pState, LevelAccessor pLevel, BlockPos pPos,Direction.Axis interactedAxis)
    {
        var stateAxis = pState.getValue(BlockStateProperties.AXIS);

        if(stateAxis == interactedAxis)
            return false;
        if(stateAxis.isVertical())
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, interactedAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X), 11);
            return true;
        }
        if(stateAxis.isHorizontal())
        {
            if(interactedAxis.isVertical())
                pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, stateAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X), 11);
            else
                pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y), 11);
            return true;
        }

        return true;
    }

    private boolean RotateAngular(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        int rotationAngle = pState.getValue(BlockStateProperties.ROTATION_16);

        if(isPlayerCrouching)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.ROTATION_16, rotationAngle - 1 < 0 ? 15 : rotationAngle - 1) , 11);
        else
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.ROTATION_16, (rotationAngle + 1) % 16), 11);

        return true;
    }

    private boolean RotateSlab(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        if(pState.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP), 11);
        else if(pState.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.TOP)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM), 11);
        else
            return false;

        return true;
    }


    //Creates an object of WrenchItem class.
    public NewWrenchItem(Properties pProperties)
    {
        super(pProperties);
    }
}
