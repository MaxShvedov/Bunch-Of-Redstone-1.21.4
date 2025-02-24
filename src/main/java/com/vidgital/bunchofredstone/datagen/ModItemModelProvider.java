package com.vidgital.bunchofredstone.datagen;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemModelProvider extends ItemModelProvider
{
    @Override
    protected void registerModels()
    {
        basicItem(ModItems.MAGIC_WRENCH.get());

        basicItem(ModItems.WRENCH.get());
        basicItem(ModItems.EXPOSED_WRENCH.get());
        basicItem(ModItems.WEATHERED_WRENCH.get());
        basicItem(ModItems.OXIDIZED_WRENCH.get());
    }

    //Creates an object of ModItemModelProvider class.
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper)
    {
        super(output, BunchOfRedstone.MOD_ID, existingFileHelper);
    }
}
