package com.vidgital.bunchofredstone.item.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GrindstoneBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* TODO:   * Усовершенствовать вращение ступеней и блоков с 6 сторонами
           * Добавить проверку на изменение состояния блока
           * Создать механику сцепления и расцепления вагонеток (Как в моде RailCraft)
           * Добавить звуки при взаимодействии с блоком:
                * Попытка поворота - успех
                * Попытка поворота - неудача
                * Изменение формы - успех
                * Изменение формы - неудача
                * Сцепление вагонеток - успех
                * Сцепление вагонеток - неудача
                * Расцепление вагонеток - успех
           * Перенести мапы и методы работы с ними в отдельный класс
           * Добавить недостающие методы
           * Оптимизировать существующие методы
           * Разработать механику обновления блоков: вращение не должно менять состояния соседних блоков, но должно быть замечено блоком наблюдателя
           * Реализовать механику траты прочности
           * Создать механику окисления ключа (Ключ покрывается ржавчиной пока существует. Чем сильнее окисление, тем больше тратится прочности за использование: 0, -1, -2, -4)
           * Создать механику воска для ключа (Ключ можно покрыть воском в меню крафта. По сути работает как вторая прочность, только не позволяет ключу окислится)
           * Реализовать использование ключа в раздатчике. Каждое состояние ключа находясь в раздатчике отвечает за разный вид взаимодействия с целевым блоком, тратя 1 единицу прочности для любого ключа:
                * Чистый гаечный ключ аналогичен взаимодействию через ПКМ
                * Потемневший гаечный ключ аналогичен взаимодействию через ПКМ + shift
                * Состаренный гаечный ключ аналогичен взаимодействию через ЛКМ
                * Окисленный гаечный ключ аналогичен взаимодействию через ЛКМ + shift
 */

public class WrenchItem extends Item
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

    @Override
    public void appendHoverText(ItemStack pStack, TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag)
    {
        if(Screen.hasShiftDown())
        {
            pTooltipComponents.add(Component.translatable("tooltip.bunchofredstone.wrench.shift_down1"));
            pTooltipComponents.add(Component.translatable("tooltip.bunchofredstone.wrench.shift_down2"));
            pTooltipComponents.add(Component.translatable("tooltip.bunchofredstone.wrench.shift_down3"));
        }
        else
        {
            pTooltipComponents.add(Component.translatable("tooltip.bunchofredstone.wrench"));
        }
        super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
    }

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
            return StairsInteraction(pPlayer, pState, pLevel, pPos, pFace.getAxis(), isPlayerCrouching);

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

        if(stateProperties.contains(BlockStateProperties.FACING_HOPPER))
            return RotateHopperOutput(pState, pLevel, pPos, isPlayerCrouching);

        return false;
    }

    private boolean StairsInteraction(Player player, BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction.Axis interactedAxis, boolean isPlayerCrouching)
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

    private boolean RotateHopperOutput(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        if(isPlayerCrouching)
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING_HOPPER, pState.getValue(BlockStateProperties.FACING_HOPPER).getCounterClockWise(Direction.Axis.Y)), 11);
        }
        else
        {
            pLevel.setBlock(pPos, pState.setValue(BlockStateProperties.FACING_HOPPER, pState.getValue(BlockStateProperties.FACING_HOPPER).getClockWise(Direction.Axis.Y)), 11);
        }
        return true;
    }

    private boolean RotateCrafter(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateRails(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateFence(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateMushroomBlock(BlockState pState, LevelAccessor pLevel, BlockPos pPos, Direction pFace, boolean isPlayerCrouching)
    {
        return false;
    }

    private boolean RotateWallPlant(BlockState pState, LevelAccessor pLevel, BlockPos pPos, boolean isPlayerCrouching)
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


    //Creates an object of WrenchItem class.
    public WrenchItem(Properties pProperties)
    {
        super(pProperties);
    }
}
