package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements DataProvider
{
    protected RecipeOutput pOutput;

    @Override
    protected void buildRecipes()
    {
        this.output.includeRootAdvancement();
        this.generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));

        this.shaped(RecipeCategory.MISC, ModItems.WRENCH.get())
                .pattern(" - ")
                .pattern(" --")
                .pattern("-  ")
                .define('-', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(this.output);


    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput)
    {
        return null;
    }

    @Override
    public String getName()
    {
        return "";
    }

    public static class Runner extends RecipeProvider.Runner
    {

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput)
        {
            return new ModRecipeProvider(pRegistries, pOutput);
        }

        @Override
        public String getName()
        {
            return "Recipes";
        }


        //Creates an object of Runner class.
        protected Runner(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pRegistries)
        {
            super(pPackOutput, pRegistries);
        }
    }

    //Creates an object of ModRecipeProvider class.
    protected ModRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput)
    {
        super(pRegistries, pOutput);
    }
}
