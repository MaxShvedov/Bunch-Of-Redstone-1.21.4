package net.vidgital.bunchofredstone.datagen;

import net.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.model.ModelInstance;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;

import java.util.function.BiConsumer;

public class ModItemModelGenerators extends ItemModelGenerators
{
    @Override
    public void run()
    {
        generateFlatItem(ModItems.WRENCH.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.EXPOSED_WRENCH.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.WEATHERED_WRENCH.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.OXIDIZED_WRENCH.get(), ModelTemplates.FLAT_ITEM);

        generateFlatItem(ModItems.COPPER_NUGGET.get(), ModelTemplates.FLAT_ITEM);
        generateFlatItem(ModItems.COPPER_DUST.get(), ModelTemplates.FLAT_ITEM);

        if(this.itemModelOutput instanceof ModModelProvider.ModItemInfoCollector collector)
            collector.generateDefaultBlockModels();
    }

    //Creates an object of ModItemModelGenerators class.
    public ModItemModelGenerators(ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput) {
        super(pItemModelOutput, pModelOutput);
    }
}
