package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider
{
    @Override
    protected void generate()
    {
        dropSelf(ModBlocks.COBBLED_DEEPSLATE_TILES.get());
        dropSelf(ModBlocks.SMOOTH_CALCITE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }

    //Creates an object of ModBlockLootTableProvider class.
    protected ModBlockLootTableProvider(HolderLookup.Provider pRegistries)
    {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags(), pRegistries);
    }
}
