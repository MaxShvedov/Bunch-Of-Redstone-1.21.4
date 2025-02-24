package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider
{
    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.SMOOTH_CALCITE.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_TILES.get());

        tag(BlockTags.NEEDS_STONE_TOOL);
    }

    //Creates an object of ModBlockTagProvider class.
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, BunchOfRedstone.MOD_ID, existingFileHelper);
    }
}
