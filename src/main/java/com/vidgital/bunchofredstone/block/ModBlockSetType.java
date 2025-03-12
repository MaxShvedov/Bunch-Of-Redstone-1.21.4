package com.vidgital.bunchofredstone.block;

import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

import java.util.Map;
import java.util.stream.Stream;

public record ModBlockSetType(
        String name,
        boolean canOpenByHand,
        boolean canOpenByWindCharge,
        boolean canButtonBeActivatedByArrows,
        BlockSetType.PressurePlateSensitivity pressurePlateSensitivity,
        SoundType soundType,
        SoundEvent doorClose,
        SoundEvent doorOpen,
        SoundEvent trapdoorClose,
        SoundEvent trapdoorOpen,
        SoundEvent pressurePlateClickOff,
        SoundEvent pressurePlateClickOn,
        SoundEvent buttonClickOff,
        SoundEvent buttonClickOn
)
{
    private static final Map<String, BlockSetType> TYPES = new Object2ObjectArrayMap<>();

    public static final BlockSetType HEAVY_COPPER = BlockSetType.register(
      new BlockSetType(
              "heavy_copper",
              false,
              false,
              false,
              BlockSetType.PressurePlateSensitivity.EVERYTHING,
              SoundType.COPPER,
              SoundEvents.COPPER_DOOR_CLOSE,
              SoundEvents.COPPER_DOOR_OPEN,
              SoundEvents.COPPER_TRAPDOOR_CLOSE,
              SoundEvents.COPPER_TRAPDOOR_OPEN,
              SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
              SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
              SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
              SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON
      )
    );

    public static final BlockSetType BR_IRON = BlockSetType.register(
            new BlockSetType(
                    "br_iron",
                    false,
                    false,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.METAL,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON
            )
    );

    public static final BlockSetType BR_GOLD = BlockSetType.register(
            new BlockSetType(
                    "br_gold",
                    false,
                    false,
                    true,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.METAL,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.METAL_PRESSURE_PLATE_CLICK_ON
            )
    );

    public static final BlockSetType GRANITE = BlockSetType.register(
            new BlockSetType(
                    "granite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType DIORITE = BlockSetType.register(
            new BlockSetType(
                    "diorite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType ANDESITE = BlockSetType.register(
            new BlockSetType(
                    "andesite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType TUFF = BlockSetType.register(
            new BlockSetType(
                    "tuff",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.TUFF,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType COBBLED_DEEPSLATE = BlockSetType.register(
            new BlockSetType(
                    "cobbled_deepslate",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.DEEPSLATE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType COBBLESTONE = BlockSetType.register(
            new BlockSetType(
                    "cobblestone",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType BLACKSTONE = BlockSetType.register(
            new BlockSetType(
                    "blackstone",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.EVERYTHING,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType POLISHED_GRANITE = BlockSetType.register(
            new BlockSetType(
                    "polished_granite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.MOBS,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType POLISHED_DIORITE = BlockSetType.register(
            new BlockSetType(
                    "polished_diorite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.MOBS,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType POLISHED_ANDESITE = BlockSetType.register(
            new BlockSetType(
                    "polished_andesite",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.MOBS,
                    SoundType.STONE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType POLISHED_TUFF = BlockSetType.register(
            new BlockSetType(
                    "polished_tuff",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.MOBS,
                    SoundType.POLISHED_TUFF,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static final BlockSetType POLISHED_DEEPSLATE = BlockSetType.register(
            new BlockSetType(
                    "polished_deepslate",
                    true,
                    true,
                    false,
                    BlockSetType.PressurePlateSensitivity.MOBS,
                    SoundType.POLISHED_DEEPSLATE,
                    SoundEvents.IRON_DOOR_CLOSE,
                    SoundEvents.IRON_DOOR_OPEN,
                    SoundEvents.IRON_TRAPDOOR_CLOSE,
                    SoundEvents.IRON_TRAPDOOR_OPEN,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF,
                    SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON,
                    SoundEvents.STONE_BUTTON_CLICK_OFF,
                    SoundEvents.STONE_BUTTON_CLICK_ON
            )
    );

    public static Stream<BlockSetType> values()
    {
        return TYPES.values().stream();
    }
}
