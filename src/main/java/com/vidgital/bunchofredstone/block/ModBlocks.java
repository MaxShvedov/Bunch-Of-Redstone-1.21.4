package com.vidgital.bunchofredstone.block;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.block.custom.*;
import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.List;
import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BunchOfRedstone.MOD_ID);

    //Temp block
    public static final RegistryObject<Block> COBBLED_DEEPSLATE_TILES = RegisterBlock("cobbled_deepslate_tiles",
            () -> new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobbled_deepslate_tiles")))
                    .strength(3.5f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_TILES)));

    //Temp block
    public static final RegistryObject<Block> SMOOTH_CALCITE = RegisterBlock("smooth_calcite",
            () -> new MagicBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "smooth_calcite")))
                    .strength(0.75f).requiresCorrectToolForDrops().sound(SoundType.CALCITE)));


    /*FUNCTIONAL REDSTONE BLOCKS*/
    public static final RegistryObject<RedstoneLanternBlock> REDSTONE_LANTERN = RegisterBlock("redstone_lantern",
            () -> new RedstoneLanternBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "redstone_lantern")))
                    .mapColor(MapColor.METAL)
                    .forceSolidOn()
                    .strength(3.5f)
                    .sound(SoundType.LANTERN)
                    .lightLevel(blockState -> blockState.getValue(RedstoneLanternBlock.LIT) ? 7 : 0)
                    .noOcclusion()
                    .pushReaction(PushReaction.DESTROY)));

    public static final RegistryObject<RedstoneRodBlock> REDSTONE_ROD = RegisterBlock("redstone_rod",
            () -> new RedstoneRodBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "redstone_rod")))
                    .forceSolidOn()
                    .instabreak()
                    .sound(SoundType.WOOD)
                    .lightLevel(blockState -> 7)
                    .noOcclusion()));

    public static final RegistryObject<CopperRodBlock> COPPER_ROD = RegisterBlock("copper_rod",
            () -> new CopperRodBlock(Blocks.LIGHTNING_ROD, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "copper_rod")))
                    .mapColor(MapColor.COLOR_ORANGE)
                    .forceSolidOn()
                    .sound(SoundType.COPPER)
                    .noOcclusion()
                    .strength(3.0f, 6.0f)
                    .requiresCorrectToolForDrops()));

    public static final RegistryObject<RainDetectorBlock> RAIN_DETECTOR = RegisterBlock("rain_detector",
            () -> new RainDetectorBlock(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "rain_detector")))
                    .mapColor(MapColor.DEEPSLATE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .strength(0.8f)
                    .requiresCorrectToolForDrops()
                    .sound(SoundType.DEEPSLATE_TILES)));

    /*PRESSURE PLATE BLOCKS*/
    public static final RegistryObject<PressurePlateBlock> POLISHED_GRANITE_PRESSURE_PLATE = RegisterBlock("polished_granite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_granite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> POLISHED_DIORITE_PRESSURE_PLATE = RegisterBlock("polished_diorite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_diorite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> POLISHED_ANDESITE_PRESSURE_PLATE = RegisterBlock("polished_andesite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_andesite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> POLISHED_TUFF_PRESSURE_PLATE = RegisterBlock("polished_tuff_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_tuff_pressure_plate")))
                    .strength(0.5f).sound(SoundType.POLISHED_TUFF).noCollission()));

    public static final RegistryObject<PressurePlateBlock> POLISHED_DEEPSLATE_PRESSURE_PLATE = RegisterBlock("polished_deepslate_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_deepslate_pressure_plate")))
                    .strength(0.5f).sound(SoundType.POLISHED_DEEPSLATE).noCollission()));

    public static final RegistryObject<PressurePlateBlock> GRANITE_PRESSURE_PLATE = RegisterBlock("granite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "granite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> DIORITE_PRESSURE_PLATE = RegisterBlock("diorite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "diorite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> ANDESITE_PRESSURE_PLATE = RegisterBlock("andesite_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "andesite_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> COBBLESTONE_PRESSURE_PLATE = RegisterBlock("cobblestone_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobblestone_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> BLACKSTONE_PRESSURE_PLATE = RegisterBlock("blackstone_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "blackstone_pressure_plate")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<PressurePlateBlock> TUFF_PRESSURE_PLATE = RegisterBlock("tuff_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "tuff_pressure_plate")))
                    .strength(0.5f).sound(SoundType.TUFF).noCollission()));

    public static final RegistryObject<PressurePlateBlock> COBBLED_DEEPSLATE_PRESSURE_PLATE = RegisterBlock("cobbled_deepslate_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.STONE, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobbled_deepslate_pressure_plate")))
                    .strength(0.5f).sound(SoundType.DEEPSLATE).noCollission()));

    public static final RegistryObject<PressurePlateBlock> MEASURING_WEIGHTED_PRESSURE_PLATE = RegisterBlock("measuring_weighted_pressure_plate",
            () -> new PressurePlateBlock(BlockSetType.GOLD, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "measuring_weighted_pressure_plate")))
                    .strength(0.5f).sound(SoundType.COPPER).noCollission()));

    /*BUTTON BLOCKS*/
    public static final RegistryObject<ButtonBlock> POLISHED_GRANITE_BUTTON = RegisterBlock("polished_granite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_granite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> POLISHED_DIORITE_BUTTON = RegisterBlock("polished_diorite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_diorite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> POLISHED_ANDESITE_BUTTON = RegisterBlock("polished_andesite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_andesite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> POLISHED_TUFF_BUTTON = RegisterBlock("polished_tuff_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_tuff_button")))
                    .strength(0.5f).sound(SoundType.POLISHED_TUFF).noCollission()));

    public static final RegistryObject<ButtonBlock> POLISHED_DEEPSLATE_BUTTON = RegisterBlock("polished_deepslate_button",
            () -> new ButtonBlock(BlockSetType.STONE, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "polished_deepslate_button")))
                    .strength(0.5f).sound(SoundType.POLISHED_DEEPSLATE).noCollission()));

    public static final RegistryObject<ButtonBlock> GRANITE_BUTTON = RegisterBlock("granite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "granite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> DIORITE_BUTTON = RegisterBlock("diorite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "diorite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> ANDESITE_BUTTON = RegisterBlock("andesite_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "andesite_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> COBBLESTONE_BUTTON = RegisterBlock("cobblestone_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobblestone_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> BLACKSTONE_BUTTON = RegisterBlock("blackstone_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "blackstone_button")))
                    .strength(0.5f).noCollission()));

    public static final RegistryObject<ButtonBlock> TUFF_BUTTON = RegisterBlock("tuff_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "tuff_button")))
                    .strength(0.5f).sound(SoundType.TUFF).noCollission()));

    public static final RegistryObject<ButtonBlock> COBBLED_DEEPSLATE_BUTTON = RegisterBlock("cobbled_deepslate_button",
            () -> new ButtonBlock(BlockSetType.STONE, 30, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobbled_deepslate_button")))
                    .strength(0.5f).sound(SoundType.DEEPSLATE).noCollission()));

    public static final RegistryObject<ButtonBlock> IRON_BUTTON = RegisterBlock("iron_button",
            () -> new ButtonBlock(BlockSetType.IRON, 2, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "iron_button")))
                    .strength(0.5f).sound(SoundType.METAL).noCollission())
            {
                @Override
                public void appendHoverText(ItemStack pStack, Item.TooltipContext pContext, List<Component> pTooltipComponents, TooltipFlag pTooltipFlag)
                {
                    pTooltipComponents.add(Component.translatable("tooltip.bunchofredstone.iron_button"));
                    super.appendHoverText(pStack, pContext, pTooltipComponents, pTooltipFlag);
                }
            });

    public static final RegistryObject<ButtonBlock> GOLDEN_BUTTON = RegisterBlock("golden_button",
            () -> new ButtonBlock(BlockSetType.GOLD, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "golden_button")))
                    .strength(0.5f).sound(SoundType.METAL).noCollission()));

    public static final RegistryObject<ButtonBlock> COPPER_BUTTON = RegisterBlock("copper_button",
            () -> new ButtonBlock(BlockSetType.GOLD, 20, BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "copper_button")))
                    .strength(0.5f).sound(SoundType.COPPER).noCollission()));





    //Helping method, returns block registration object
    private static <T extends Block> RegistryObject<T> RegisterBlock(String name, Supplier<T> block)
    {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        RegisterBlockItem(name, toReturn);
        return toReturn;
    }

    //Helping method, creates and registers a new block item object
    private static <T extends Block> void RegisterBlockItem(String name, RegistryObject<T> block)
    {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().useItemDescriptionPrefix().
                setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, name)))));
    }

    public static void Register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
