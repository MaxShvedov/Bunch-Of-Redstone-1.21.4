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
                .add(ModBlocks.COBBLED_DEEPSLATE_TILES.get())
                .add(ModBlocks.SMOOTH_CALCITE.get())
                
                .add(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get())

                .add(ModBlocks.COBBLESTONE_BUTTON.get())
                .add(ModBlocks.GRANITE_BUTTON.get())
                .add(ModBlocks.POLISHED_GRANITE_BUTTON.get())
                .add(ModBlocks.DIORITE_BUTTON.get())
                .add(ModBlocks.POLISHED_DIORITE_BUTTON.get())
                .add(ModBlocks.ANDESITE_BUTTON.get())
                .add(ModBlocks.POLISHED_ANDESITE_BUTTON.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.TUFF_BUTTON.get())
                .add(ModBlocks.POLISHED_TUFF_BUTTON.get())
                .add(ModBlocks.BLACKSTONE_BUTTON.get())

                .add(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get())

                .add(ModBlocks.COPPER_BUTTON.get())
                .add(ModBlocks.GOLDEN_BUTTON.get())
                .add(ModBlocks.IRON_BUTTON.get());


        tag(BlockTags.WALL_POST_OVERRIDE)
                .add(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());


        tag(BlockTags.BUTTONS)
                .add(ModBlocks.COBBLESTONE_BUTTON.get())
                .add(ModBlocks.GRANITE_BUTTON.get())
                .add(ModBlocks.POLISHED_GRANITE_BUTTON.get())
                .add(ModBlocks.DIORITE_BUTTON.get())
                .add(ModBlocks.POLISHED_DIORITE_BUTTON.get())
                .add(ModBlocks.ANDESITE_BUTTON.get())
                .add(ModBlocks.POLISHED_ANDESITE_BUTTON.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.TUFF_BUTTON.get())
                .add(ModBlocks.POLISHED_TUFF_BUTTON.get())
                .add(ModBlocks.BLACKSTONE_BUTTON.get())
                .add(ModBlocks.COPPER_BUTTON.get())
                .add(ModBlocks.GOLDEN_BUTTON.get())
                .add(ModBlocks.IRON_BUTTON.get());

        tag(BlockTags.WOODEN_BUTTONS)
                .add(ModBlocks.COBBLESTONE_BUTTON.get())
                .add(ModBlocks.GRANITE_BUTTON.get())
                .add(ModBlocks.DIORITE_BUTTON.get())
                .add(ModBlocks.ANDESITE_BUTTON.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.TUFF_BUTTON.get())
                .add(ModBlocks.BLACKSTONE_BUTTON.get());

        tag(BlockTags.STONE_BUTTONS)
                .add(ModBlocks.POLISHED_GRANITE_BUTTON.get())
                .add(ModBlocks.POLISHED_DIORITE_BUTTON.get())
                .add(ModBlocks.POLISHED_ANDESITE_BUTTON.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get())
                .add(ModBlocks.POLISHED_TUFF_BUTTON.get())
                .add(ModBlocks.COPPER_BUTTON.get()) //temp
                .add(ModBlocks.GOLDEN_BUTTON.get()) //temp
                .add(ModBlocks.IRON_BUTTON.get());  //temp


        tag(BlockTags.PRESSURE_PLATES)
                .add(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());

        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get())
                .add(ModBlocks.GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.TUFF_PRESSURE_PLATE.get())
                .add(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get());

        tag(BlockTags.STONE_PRESSURE_PLATES)
                .add(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get())
                .add(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get());

        //Add tags for stone tools, doors and trapdoors
    }

    //Creates an object of ModBlockTagProvider
    //class.
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper)
    {
        super(output, lookupProvider, BunchOfRedstone.MOD_ID, existingFileHelper);
    }
}
