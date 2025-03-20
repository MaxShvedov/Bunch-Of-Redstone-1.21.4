package net.vidgital.bunchofredstone.event;

import net.vidgital.bunchofredstone.BunchOfRedstone;
import net.vidgital.bunchofredstone.item.custom.WrenchItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/* TODO:   * Усовершенствовать вращение ступеней и блоков с 6 сторонами
           * Добавить проверку на изменение состояния блока
           * Добавить звуки при взаимодействии с блоком:
                * Попытка поворота - успех
                * Попытка поворота - неудача
                * Изменение формы - успех
                * Изменение формы - неудача
           * Перенести мапы и методы работы с ними в отдельный класс
           * Добавить недостающие методы
           * Оптимизировать существующие методы
           * Реализовать механику траты прочности
 */

@Mod.EventBusSubscriber(modid = BunchOfRedstone.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class WrenchEvents
{
    private static final Map<List<?>, List<?>> _STAIRS_ROTATION_MAP =
            Map.of(
                    List.of(Direction.SOUTH, Half.BOTTOM), List.of(Direction.NORTH, Half.BOTTOM),
                    List.of(Direction.NORTH, Half.BOTTOM), List.of(Direction.NORTH, Half.TOP),
                    List.of(Direction.NORTH, Half.TOP),  List.of(Direction.SOUTH, Half.TOP),
                    List.of(Direction.SOUTH, Half.TOP), List.of(Direction.SOUTH, Half.BOTTOM),

                    List.of(Direction.EAST, Half.BOTTOM), List.of(Direction.WEST, Half.BOTTOM),
                    List.of(Direction.WEST, Half.BOTTOM), List.of(Direction.WEST, Half.TOP),
                    List.of(Direction.WEST, Half.TOP),  List.of(Direction.EAST, Half.TOP),
                    List.of(Direction.EAST, Half.TOP), List.of(Direction.EAST, Half.BOTTOM)
            );

    private static final Map<StairsShape, StairsShape> _STAIRS_SHAPE_MAP =
            Map.of(
                    StairsShape.INNER_LEFT, StairsShape.OUTER_LEFT,
                    StairsShape.OUTER_LEFT, StairsShape.STRAIGHT,
                    StairsShape.STRAIGHT, StairsShape.OUTER_RIGHT,
                    StairsShape.OUTER_RIGHT, StairsShape.INNER_RIGHT,
                    StairsShape.INNER_RIGHT, StairsShape.INNER_LEFT
            );

    @SubscribeEvent
    public static void OnBlockHit(PlayerInteractEvent.LeftClickBlock event)
    {
        Player player = event.getEntity();
        ItemStack mainHandItem = player.getMainHandItem();
        if(event.getAction() == PlayerInteractEvent.LeftClickBlock.Action.START)
        {
            if (mainHandItem.getItem() instanceof WrenchItem && player instanceof ServerPlayer serverPlayer)
            {

                LevelAccessor level = event.getLevel();
                BlockPos pos = event.getPos();
                BlockState blockState = level.getBlockState(pos);
                Direction face = event.getFace();

                if (InteractLeftClick(player, blockState, level, pos, face, player.isCrouching())) {

                }
            }
        }
    }

    private static boolean InteractLeftClick(Player pPlayer, BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        StateDefinition<Block, BlockState> stateDefinition = pState.getBlock().getStateDefinition();
        Collection<Property<?>> stateProperties = stateDefinition.getProperties();

        if(pState.getBlock() instanceof StairBlock stairBlock)
            return StairsInteraction(pState, pLevel, pPos, pFace.getAxis()  == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X, isPlayerCrouching);

        if(pState.getBlock() instanceof GrindstoneBlock grindstoneBlock)
            return true;

        if(pState.getBlock() instanceof DoorBlock doorBlock)
            return ChangeDoorHinges(pState, pLevel, pPos);

        if(pState.getBlock() instanceof TrapDoorBlock trapdoorBlock)
            return ChangeTrapdoorHalf(pState, pLevel, pPos);

        if(stateProperties.contains(BlockStateProperties.FACING))
            return RotateFacing(pState, pLevel, pPos, pFace.getAxis(), isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.AXIS))
            return RotateAxis(pState, pLevel, pPos, pFace.getAxis());

        if(stateProperties.contains(BlockStateProperties.ROTATION_16))
            return RotateAngular(pState, pLevel, pPos, isPlayerCrouching);

        if(stateProperties.contains(BlockStateProperties.SLAB_TYPE))
            return RotateSlab(pState, pLevel, pPos);

        if(stateProperties.contains(BlockStateProperties.FACING_HOPPER))
            return ChangeHopperOutput(pState, pLevel, pPos, pFace, isPlayerCrouching);


        return false;
    }

    //Finish this method 02.21.25. Don't forget! Also fix direction for FACING property blocks - it has to depends on interacted face, not interacted axis.
    private static boolean StairsInteraction(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        Direction.Axis stateAxis = pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis();
        List<?> stairsState = List.of(pState.getValue(BlockStateProperties.HORIZONTAL_FACING), pState.getValue(BlockStateProperties.HALF));

        if (isPlayerCrouching)
        {
            if (interactedAxis == stateAxis)
            {
                pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.STAIRS_SHAPE, getKeyByValue(_STAIRS_SHAPE_MAP, pState.getValue(BlockStateProperties.STAIRS_SHAPE))), 11);
                return true;
            }
            else
            {
                if(interactedAxis.isVertical())
                {
                    pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getCounterClockWise(interactedAxis)), 11);
                    return true;
                }
                else
                {
                    stairsState = getKeyByValue(_STAIRS_ROTATION_MAP, stairsState);
                }
            }
        }
        else
        {
            if(interactedAxis == stateAxis)
            {
                pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.STAIRS_SHAPE, _STAIRS_SHAPE_MAP.get(pState.getValue(BlockStateProperties.STAIRS_SHAPE))), 11);
                return true;
            }
            else
            {
                if(interactedAxis.isVertical())
                {
                    pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, pState.getValue(BlockStateProperties.HORIZONTAL_FACING).getClockWise(interactedAxis)), 11);
                    return true;
                }
                else
                {
                    stairsState = _STAIRS_ROTATION_MAP.get(stairsState);
                }
            }
        }
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HORIZONTAL_FACING, (Direction) stairsState.get(0)), 11);
        pState = pLevel.getBlockState(pPos);
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HALF, (Half) stairsState.get(1)), 11);

        return true;
    }

    private static boolean ChangeDoorHinges(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.DOOR_HINGE, pState.getValue(BlockStateProperties.DOOR_HINGE) == DoorHingeSide.LEFT ? DoorHingeSide.RIGHT : DoorHingeSide.LEFT), 11);
        return true;
    }

    private static boolean ChangeTrapdoorHalf(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.HALF, pState.getValue(BlockStateProperties.HALF) == Half.BOTTOM ? Half.TOP : Half.BOTTOM), 11);
        return true;
    }

    private static boolean RotateGrindstone(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean RotateFacing(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
    {
        if(isPlayerCrouching)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING, pState.getValue(BlockStateProperties.FACING).getCounterClockWise(
                    interactedAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X)), 11);
        else
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING, pState.getValue(BlockStateProperties.FACING).getClockWise(
                    interactedAxis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X)), 11);

        return true;
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

    private static boolean RotateAngular(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        int rotationAngle = pState.getValue(BlockStateProperties.ROTATION_16);
        
        if(isPlayerCrouching)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.ROTATION_16,
                    rotationAngle - 4 < 0 ? 16 + rotationAngle - 4 : rotationAngle - 4) , 11);
        else
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.ROTATION_16, (rotationAngle + 4) % 16), 11);

        return true;
    }

    private static boolean RotateSlab(BlockState pState, LevelAccessor pLevel, BlockPos pPos)
    {
        if(pState.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.BOTTOM)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.TOP), 11);
        else if(pState.getValue(BlockStateProperties.SLAB_TYPE) == SlabType.TOP)
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.SLAB_TYPE, SlabType.BOTTOM), 11);
        else
            return false;

        return true;
    }

    private static boolean ChangeHopperOutput(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        Direction stateFace = pState.getValue(BlockStateProperties.FACING_HOPPER);

        if(isPlayerCrouching)
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING_HOPPER,  pFace == Direction.UP ? Direction.NORTH : stateFace == pFace.getOpposite() ? Direction.DOWN : pFace), 11 );
        }
        else
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING_HOPPER, stateFace == pFace ? Direction.DOWN : pFace.getOpposite()), 11 );
        }
        return true;
    }

    private static boolean RotateCrafter(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean ChangeRailsType(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean ChangeFenceShape(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean RotateMushroomBlock(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        return false;
    }

    private static boolean RotateWallPlant(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }


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

}
