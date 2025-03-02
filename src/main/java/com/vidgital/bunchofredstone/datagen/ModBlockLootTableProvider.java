package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTableProvider extends BlockLootSubProvider
{
    @Override
    protected void generate()
    {
        //Blocks that drops itself
        dropSelf(ModBlocks.COBBLED_DEEPSLATE_TILES.get());
        dropSelf(ModBlocks.SMOOTH_CALCITE.get());

        dropSelf(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.GRANITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.DIORITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.ANDESITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.TUFF_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get());
        dropSelf(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get());

        dropSelf(ModBlocks.COBBLESTONE_BUTTON.get());
        dropSelf(ModBlocks.GRANITE_BUTTON.get());
        dropSelf(ModBlocks.POLISHED_GRANITE_BUTTON.get());
        dropSelf(ModBlocks.DIORITE_BUTTON.get());
        dropSelf(ModBlocks.POLISHED_DIORITE_BUTTON.get());
        dropSelf(ModBlocks.ANDESITE_BUTTON.get());
        dropSelf(ModBlocks.POLISHED_ANDESITE_BUTTON.get());
        dropSelf(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get());
        dropSelf(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get());
        dropSelf(ModBlocks.TUFF_BUTTON.get());
        dropSelf(ModBlocks.POLISHED_TUFF_BUTTON.get());
        dropSelf(ModBlocks.BLACKSTONE_BUTTON.get());

        dropSelf(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());

        dropSelf(ModBlocks.COPPER_BUTTON.get());
        dropSelf(ModBlocks.GOLDEN_BUTTON.get());
        dropSelf(ModBlocks.IRON_BUTTON.get());

        dropSelf(ModBlocks.REDSTONE_LANTERN.get());
        dropSelf(ModBlocks.REDSTONE_ROD.get());
        dropSelf(ModBlocks.COPPER_ROD.get());
        dropSelf(ModBlocks.RAIN_DETECTOR.get());
        
        //Blocks that also drops something additional

        //Blocks that drops other items

    }

    // Assistance method. Uncomment later.
//    protected LootTable.Builder createMultipleOreDrops(Block pBlock, Item pItem, float min, float max) {
//        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
//        return this.createSilkTouchDispatchTable(
//                pBlock,
//                (LootPoolEntryContainer.Builder<?>)this.applyExplosionDecay(
//                        pBlock,
//                        LootItem.lootTableItem(pItem)
//                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max)))
//                                .apply(ApplyBonusCount.addOreBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
//                )
//        );
//    }

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
