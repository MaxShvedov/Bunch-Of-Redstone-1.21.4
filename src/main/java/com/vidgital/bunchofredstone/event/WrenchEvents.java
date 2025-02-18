package com.vidgital.bunchofredstone.event;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.item.custom.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
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
        if(mainHandItem.getItem() instanceof WrenchItem && player instanceof ServerPlayer serverPlayer)
        {
            LevelAccessor level = event.getLevel();
            BlockPos pPos = event.getPos();
            BlockState blockState = level.getBlockState(pPos);
            Direction face = event.getFace();
            StateDefinition<Block, BlockState> stateDefinition = blockState.getBlock().getStateDefinition();
            Collection<Property<?>> collection = stateDefinition.getProperties();

            if (collection.contains(BlockStateProperties.AXIS))
            {
                level.setBlock(pPos, blockState.setValue(BlockStateProperties.AXIS, calculateAxis(face.getAxis(), blockState.getValue(BlockStateProperties.AXIS))), 11);
            }
        }
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
