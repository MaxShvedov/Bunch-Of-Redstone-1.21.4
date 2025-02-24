package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder
{
    @Override
    protected void buildRecipes()
    {
        shaped(RecipeCategory.TOOLS, ModItems.WRENCH.get())
                .pattern(" - ")
                .pattern(" --")
                .pattern("-  ")
                .define('-', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(this.output);
    }

    //Creates an object of ModRecipeProvider class.
    protected ModRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput)
    {
        super(pRegistries, pOutput);
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
}
