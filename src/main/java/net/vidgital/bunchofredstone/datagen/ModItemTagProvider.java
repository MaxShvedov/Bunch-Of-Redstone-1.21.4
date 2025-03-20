package net.vidgital.bunchofredstone.datagen;

import net.minecraft.world.item.Items;
import net.vidgital.bunchofredstone.BunchOfRedstone;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.vidgital.bunchofredstone.block.ModBlocks;
import net.vidgital.bunchofredstone.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends ItemTagsProvider
{


    @Override
    protected void addTags(HolderLookup.Provider pProvider)
    {
        this.tag(ModTags.Items.STONE_BUTTON_CRAFTING_MATERIALS)
                .add(Items.STONE_BUTTON)
                .add(Items.POLISHED_BLACKSTONE_BUTTON)
                .add(ModBlocks.POLISHED_GRANITE_BUTTON.get().asItem())
                .add(ModBlocks.POLISHED_DIORITE_BUTTON.get().asItem())
                .add(ModBlocks.POLISHED_ANDESITE_BUTTON.get().asItem())
                .add(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get().asItem())
                .add(ModBlocks.POLISHED_TUFF_BUTTON.get().asItem());
    }

    //Creates an object of ModItemTagProvider class.
    public ModItemTagProvider(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                              CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(pOutput, pLookupProvider, pBlockTags, BunchOfRedstone.MOD_ID, existingFileHelper);
    }
}
