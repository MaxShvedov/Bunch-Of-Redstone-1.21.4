package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{
    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
//        tag(ModTags.Items.WEATHERING_ITEMS);
    }

    //Creates an object of ModBlockTagProvider class.
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(pOutput, pLookupProvider, pBlockTags, BunchOfRedstone.MOD_ID, existingFileHelper);
    }
}
