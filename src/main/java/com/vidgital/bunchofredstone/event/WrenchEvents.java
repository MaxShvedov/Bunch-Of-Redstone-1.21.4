package com.vidgital.bunchofredstone.event;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.item.custom.NewWrenchItem;
import com.vidgital.bunchofredstone.item.custom.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Interaction;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;


@Mod.EventBusSubscriber(modid = BunchOfRedstone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WrenchEvents
{


    @SubscribeEvent
    public static void onBlockHit(PlayerInteractEvent.LeftClickBlock event)
    {
        Player player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        if(player != null && mainHandItem.getItem() instanceof NewWrenchItem)
        {
            event.setCanceled(true);
        }
        if(mainHandItem.getItem() instanceof NewWrenchItem && player instanceof ServerPlayer serverPlayer)
        {

            LevelAccessor level = event.getLevel();
            BlockPos pos = event.getPos();
            BlockState blockState = level.getBlockState(pos);
            Direction face = event.getFace();

            if(InteractLeftClick(player, blockState, level, pos, face, player.isCrouching()))
            {

            }

        }
    }

    private static boolean InteractLeftClick(Player pPlayer, BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        StateDefinition<Block, BlockState> stateDefinition = pState.getBlock().getStateDefinition();
        Collection<Property<?>> stateProperties = stateDefinition.getProperties();

        if(pState.getBlock() instanceof StairBlock stairBlock)
            return true;

        if(pState.getBlock() instanceof GrindstoneBlock grindstoneBlock)
            return true;

        if(pState.getBlock() instanceof DoorBlock doorBlock)
            return ChangeDoorHinges(pState, pLevel, pPos);

        if(stateProperties.contains(BlockStateProperties.FACING))
            return false;

        if(stateProperties.contains(BlockStateProperties.AXIS))
            return RotateAxis(pState, pLevel, pPos, pFace.getAxis());

        if(stateProperties.contains(BlockStateProperties.ROTATION_16))
            return false;

        if(stateProperties.contains(BlockStateProperties.SLAB_TYPE))
            return false;


        return false;
    }

    private static boolean ChangeStairsShape(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean ChangeDoorHinges(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.DOOR_HINGE, pState.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT), 11);
        return true;
    }

    private static boolean RotateGrindstone(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean RotateFacing(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean RotateAxis(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis)
    {
        var stateAxis = pState.getValue(BlockStateProperties.AXIS);

        if(interactedAxis.isHorizontal() && stateAxis.isHorizontal() && interactedAxis != stateAxis)
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, stateAxis), 11);
            return true;
        }
        if(stateAxis.isVertical())
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, interactedAxis == Direction.Axis.X ? Direction.Axis.X : Direction.Axis.Z), 11);
            return true;
        }
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.AXIS, Direction.Axis.Y), 11);

        return true;
    }

    private static boolean RotateAngular(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis)
    {
        return false;
    }



    private static Direction.Axis calculateAxis(Direction.Axis hitAxis, Direction.Axis stateAxis)
    {
        if(hitAxis.isHorizontal() && stateAxis.isHorizontal() && hitAxis != stateAxis)
            return stateAxis;
        if(stateAxis.isVertical() && hitAxis != Direction.Axis.Z)
            return Direction.Axis.X;
        if (stateAxis.isVertical() && hitAxis == Direction.Axis.Z)
            return Direction.Axis.Z;

        return
                Direction.Axis.Y;
    }

}
