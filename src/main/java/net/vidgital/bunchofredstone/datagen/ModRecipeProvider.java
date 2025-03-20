package net.vidgital.bunchofredstone.datagen;

import net.vidgital.bunchofredstone.BunchOfRedstone;
import net.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements DataProvider
{
    protected RecipeOutput pOutput;

    @Override
    protected void buildRecipes() {
        this.output.includeRootAdvancement();
        this.generateForEnabledBlockFamilies(FeatureFlagSet.of(FeatureFlags.VANILLA));

//        shaped(RecipeCategory.TOOLS, ModItems.WRENCH.get())
//                .pattern(" - ")
//                .pattern(" --")
//                .pattern("-  ")
//                .define('-', Items.COPPER_INGOT)
//                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(this.output);
//
//        shaped(RecipeCategory.MISC, Items.COPPER_INGOT)
//                .pattern("***")
//                .pattern("***")
//                .pattern("***")
//                .define('*', ModItems.COPPER_NUGGET.get())
//                .unlockedBy(getHasName(ModItems.COPPER_NUGGET.get()), has(ModItems.COPPER_NUGGET.get())).save(this.output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.REDSTONE_LANTERN.get())
                .pattern("***")
                .pattern("*/*")
                .pattern("***")
                .define('*', Items.IRON_NUGGET)
                .define('/', Items.REDSTONE_TORCH)
                .unlockedBy(getHasName(Items.REDSTONE_TORCH), has(Items.REDSTONE_TORCH)).save(this.output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.REDSTONE_ROD.get())
                .pattern(" r ")
                .pattern("r/r")
                .pattern(" g ")
                .define('r', Items.REDSTONE)
                .define('/', Items.END_ROD)
                .define('g', Items.GOLD_INGOT)
                .unlockedBy(getHasName(Items.END_ROD), has(Items.END_ROD)).save(this.output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.COPPER_ROD.get())
                .pattern("-")
                .pattern("-")
                .define('-', Items.COPPER_INGOT)
                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(this.output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.RAIN_DETECTOR.get())
                .pattern("###")
                .pattern("MMM")
                .pattern("___")
                .define('#', Items.IRON_BARS)
                .define('M', Items.MOSS_BLOCK)
                .define('_', Items.COBBLED_DEEPSLATE_SLAB)
                .unlockedBy(getHasName(Items.MOSS_BLOCK), has(Items.MOSS_BLOCK)).save(this.output);
        
        shaped(RecipeCategory.REDSTONE, ModBlocks.REDUCTOR.get())
                .pattern(" - ")
                .pattern("/r/")
                .pattern("DDD")
                .define('-', Items.BRICK)
                .define('/', Items.REDSTONE_TORCH)
                .define('r', Items.REDSTONE)
                .define('D', Items.DEEPSLATE)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.DEEPSLATE)).save(this.output);

        shaped(RecipeCategory.REDSTONE, ModBlocks.INTERSECTION.get())
                .pattern(" ^ ")
                .pattern("r/r")
                .pattern("DDD")
                .define('^', Items.RESIN_CLUMP)
                .define('/', Items.REDSTONE_TORCH)
                .define('r', Items.REDSTONE)
                .define('D', Items.DEEPSLATE)
                .unlockedBy(getHasName(Items.DEEPSLATE), has(Items.DEEPSLATE)).save(this.output);

//        shapeless(RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 9)
//                .requires(Items.COPPER_INGOT)
//                .unlockedBy(getHasName(Items.COPPER_INGOT), has(Items.COPPER_INGOT)).save(this.output);
//
//        shapeless(RecipeCategory.MISC, ModItems.COPPER_DUST.get())
//                .requires(ModItems.COPPER_NUGGET.get())
//                .unlockedBy(getHasName(ModItems.COPPER_NUGGET.get()), has(ModItems.COPPER_NUGGET.get())).save(this.output);

//        oreSmelting(List.of(ModItems.COPPER_DUST.get()), RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 0f, 200, "nugget");
//        oreBlasting(List.of(ModItems.COPPER_DUST.get()), RecipeCategory.MISC, ModItems.COPPER_NUGGET.get(), 0f, 100, "nugget");

        //Provide recipes using different stone buttons.
        shapeless(RecipeCategory.REDSTONE, ModBlocks.GOLDEN_BUTTON.get(), 1)
                .requires(Items.GOLD_INGOT)
                .requires(Items.STONE_BUTTON)
                .unlockedBy(getHasName(Items.STONE_BUTTON), has(Items.STONE_BUTTON)).save(this.output);

        //Provide recipes using different stone buttons.
        shapeless(RecipeCategory.REDSTONE, ModBlocks.IRON_BUTTON.get(), 1)
                .requires(Items.IRON_INGOT)
                .requires(Items.STONE_BUTTON)
                .unlockedBy(getHasName(Items.STONE_BUTTON), has(Items.STONE_BUTTON)).save(this.output);

        //Provide recipes using different stone buttons.
        shapeless(RecipeCategory.REDSTONE, ModBlocks.COPPER_BUTTON.get(), 1)
                .requires(Items.COPPER_INGOT)
                .requires(Items.STONE_BUTTON)
                .unlockedBy(getHasName(Items.STONE_BUTTON), has(Items.STONE_BUTTON)).save(this.output);

        //Waxing copper buttons.
        shapeless(RecipeCategory.REDSTONE, ModBlocks.WAXED_COPPER_BUTTON.get(), 1)
                .requires(ModBlocks.COPPER_BUTTON.get())
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(ModBlocks.COPPER_BUTTON.get()), has(ModBlocks.COPPER_BUTTON.get())).save(this.output);

        shapeless(RecipeCategory.REDSTONE, ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get(), 1)
                .requires(ModBlocks.EXPOSED_COPPER_BUTTON.get())
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(ModBlocks.COPPER_BUTTON.get()), has(ModBlocks.COPPER_BUTTON.get())).save(this.output);

        shapeless(RecipeCategory.REDSTONE, ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get(), 1)
                .requires(ModBlocks.WEATHERED_COPPER_BUTTON.get())
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(ModBlocks.COPPER_BUTTON.get()), has(ModBlocks.COPPER_BUTTON.get())).save(this.output);

        shapeless(RecipeCategory.REDSTONE, ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get(), 1)
                .requires(ModBlocks.OXIDIZED_COPPER_BUTTON.get())
                .requires(Items.HONEYCOMB)
                .unlockedBy(getHasName(ModBlocks.COPPER_BUTTON.get()), has(ModBlocks.COPPER_BUTTON.get())).save(this.output);

        pressurePlate(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get(), Items.COBBLESTONE);
        pressurePlate(ModBlocks.GRANITE_PRESSURE_PLATE.get(), Items.GRANITE);
        pressurePlate(ModBlocks.DIORITE_PRESSURE_PLATE.get(), Items.DIORITE);
        pressurePlate(ModBlocks.ANDESITE_PRESSURE_PLATE.get(), Items.ANDESITE);
        pressurePlate(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get(), Items.COBBLED_DEEPSLATE);
        pressurePlate(ModBlocks.TUFF_PRESSURE_PLATE.get(), Items.TUFF);
        pressurePlate(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get(), Items.BLACKSTONE);
        pressurePlate(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get(), Items.POLISHED_GRANITE);
        pressurePlate(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get(), Items.POLISHED_DIORITE);
        pressurePlate(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get(), Items.POLISHED_ANDESITE);
        pressurePlate(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get(), Items.POLISHED_DEEPSLATE);
        pressurePlate(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get(), Items.POLISHED_TUFF);
//        pressurePlate(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get(), Items.COPPER_INGOT);

        buttonBuilder(ModBlocks.COBBLESTONE_BUTTON.get(), Ingredient.of (Items.COBBLESTONE)).group("cobblestone")
                .unlockedBy(getHasName(Items.COBBLESTONE), has(Items.COBBLESTONE)).save(this.output);
        buttonBuilder(ModBlocks.GRANITE_BUTTON.get(), Ingredient.of (Items.GRANITE)).group("granite")
                .unlockedBy(getHasName(Items.GRANITE), has(Items.GRANITE)).save(this.output);
        buttonBuilder(ModBlocks.DIORITE_BUTTON.get(), Ingredient.of (Items.DIORITE)).group("diorite")
                .unlockedBy(getHasName(Items.DIORITE), has(Items.DIORITE)).save(this.output);
        buttonBuilder(ModBlocks.ANDESITE_BUTTON.get(), Ingredient.of (Items.ANDESITE)).group("andesite")
                .unlockedBy(getHasName(Items.ANDESITE), has(Items.ANDESITE)).save(this.output);
        buttonBuilder(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get(), Ingredient.of (Items.COBBLED_DEEPSLATE)).group("cobbled_deepslate")
                .unlockedBy(getHasName(Items.COBBLED_DEEPSLATE), has(Items.COBBLED_DEEPSLATE)).save(this.output);
        buttonBuilder(ModBlocks.TUFF_BUTTON.get(), Ingredient.of (Items.TUFF)).group("tuff")
                .unlockedBy(getHasName(Items.TUFF), has(Items.TUFF)).save(this.output);
        buttonBuilder(ModBlocks.BLACKSTONE_BUTTON.get(), Ingredient.of (Items.BLACKSTONE)).group("blackstone")
                .unlockedBy(getHasName(Items.BLACKSTONE), has(Items.BLACKSTONE)).save(this.output);
        buttonBuilder(ModBlocks.POLISHED_GRANITE_BUTTON.get(), Ingredient.of (Items.POLISHED_GRANITE)).group("polished_granite")
                .unlockedBy(getHasName(Items.POLISHED_GRANITE), has(Items.POLISHED_GRANITE)).save(this.output);
        buttonBuilder(ModBlocks.POLISHED_DIORITE_BUTTON.get(), Ingredient.of (Items.POLISHED_DIORITE)).group("polished_diorite")
                .unlockedBy(getHasName(Items.POLISHED_DIORITE), has(Items.POLISHED_DIORITE)).save(this.output);
        buttonBuilder(ModBlocks.POLISHED_ANDESITE_BUTTON.get(), Ingredient.of (Items.POLISHED_ANDESITE)).group("polished_andesite")
                .unlockedBy(getHasName(Items.POLISHED_ANDESITE), has(Items.POLISHED_ANDESITE)).save(this.output);
        buttonBuilder(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get(), Ingredient.of (Items.POLISHED_DEEPSLATE)).group("polished_deepslate")
                .unlockedBy(getHasName(Items.POLISHED_DEEPSLATE), has(Items.POLISHED_DEEPSLATE)).save(this.output);
        buttonBuilder(ModBlocks.POLISHED_TUFF_BUTTON.get(), Ingredient.of (Items.POLISHED_TUFF)).group("polished_tuff")
                .unlockedBy(getHasName(Items.POLISHED_TUFF), has(Items.POLISHED_TUFF)).save(this.output);

        //Provide alternative crafting recipes for existing blocks made of cobblestone
        shaped(RecipeCategory.REDSTONE, Items.DISPENSER)
                .pattern("***")
                .pattern("*b*")
                .pattern("*r*")
                .define('*', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('b', Items.BOW)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_bow", has(Items.BOW))
                .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(Items.DISPENSER));

        shaped(RecipeCategory.REDSTONE, Items.DROPPER)
                .pattern("***")
                .pattern("* *")
                .pattern("*r*")
                .define('*', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(Items.DROPPER));

        shaped(RecipeCategory.REDSTONE, Items.OBSERVER)
                .pattern("***")
                .pattern("rrq")
                .pattern("***")
                .define('*', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('q', Items.QUARTZ)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_quartz", this.has(Items.QUARTZ))
                .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(Items.OBSERVER));

        shaped(RecipeCategory.REDSTONE, Items.PISTON)
                .pattern("ppp")
                .pattern("*-*")
                .pattern("*r*")
                .define('*', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('p', ItemTags.PLANKS)
                .define('-', Items.IRON_INGOT)
                .define('r', Items.REDSTONE)
                .unlockedBy("has_redstone", has(Items.REDSTONE))
                .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(Items.PISTON));

        shaped(RecipeCategory.REDSTONE, Items.LEVER)
                .pattern("/")
                .pattern("*")
                .define('*', ItemTags.STONE_CRAFTING_MATERIALS)
                .define('/', Items.STICK)
                .unlockedBy("has_cobblestone", has(ItemTags.STONE_CRAFTING_MATERIALS))
                .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(Items.LEVER));
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

    protected void oreSmelting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        this.oreCooking(RecipeSerializer.SMELTING_RECIPE, SmeltingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_smelting");
    }

    protected void oreBlasting(List<ItemLike> pIngredients, RecipeCategory pCategory, ItemLike pResult, float pExperience, int pCookingTime, String pGroup) {
        this.oreCooking(RecipeSerializer.BLASTING_RECIPE, BlastingRecipe::new, pIngredients, pCategory, pResult, pExperience, pCookingTime, pGroup, "_from_blasting");
    }

    private <T extends AbstractCookingRecipe> void oreCooking(
            RecipeSerializer<T> pSerializer,
            AbstractCookingRecipe.Factory<T> pRecipeFactory,
            List<ItemLike> pIngredients,
            RecipeCategory pCategory,
            ItemLike pResult,
            float pExperience,
            int pCookingTime,
            String pGroup,
            String pSuffix
    ) {
        for (ItemLike itemlike : pIngredients) {
            SimpleCookingRecipeBuilder.generic(Ingredient.of(itemlike), pCategory, pResult, pExperience, pCookingTime, pSerializer, pRecipeFactory)
                    .group(pGroup)
                    .unlockedBy(getHasName(itemlike), this.has(itemlike))
                    .save(this.output, BunchOfRedstone.MOD_ID + ":" + getItemName(pResult) + pSuffix + "_" + getItemName(itemlike));
        }
    }

    //Creates an object of ModRecipeProvider class.
    protected ModRecipeProvider(HolderLookup.Provider pRegistries, RecipeOutput pOutput)
    {
        super(pRegistries, pOutput);
    }
}
