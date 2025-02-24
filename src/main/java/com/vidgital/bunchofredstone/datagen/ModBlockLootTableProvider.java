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
        //Blocks that drops itself
        dropSelf(ModBlocks.COBBLED_DEEPSLATE_TILES.get());
        dropSelf(ModBlocks.SMOOTH_CALCITE.get());

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
