package com.vidgital.bunchofredstone.block;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.client.resources.sounds.Sound;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks
{
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BunchOfRedstone.MOD_ID);

    //Temp block
    public static final RegistryObject<Block> COBBLED_DEEPSLATE_TILES = RegisterBlock("cobbled_deepslate_tiles",
            () -> new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "cobbled_deepslate_tiles")))
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE_TILES)));

    //Temp block
    public static final RegistryObject<Block> SMOOTH_CALCITE = RegisterBlock("smooth_calcite",
            () -> new Block(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "smooth_calcite")))
                    .strength(2f).requiresCorrectToolForDrops().sound(SoundType.CALCITE)));


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
                setId(ResourceKey.create(Registries.ITEM, ResourceLocation.parse("bunchofredstone:" + name)))));
    }

    public static void Register(IEventBus eventBus)
    {
        BLOCKS.register(eventBus);
    }
}
