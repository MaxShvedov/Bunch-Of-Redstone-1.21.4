package net.vidgital.bunchofredstone.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;

import java.util.concurrent.CompletableFuture;

public class ModRecipeDataProvider extends ModRecipeProvider.Runner
{
    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput) {
        return new ModRecipeProvider(pRegistries, pOutput);
    }

    //Creates an object of ModRecipeDataProvider class.
    protected ModRecipeDataProvider(PackOutput pPackOutput, CompletableFuture<HolderLookup.Provider> pRegistries)
    {
        super(pPackOutput, pRegistries);
    }
}
