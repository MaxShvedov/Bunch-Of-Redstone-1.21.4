package com.vidgital.bunchofredstone.datagen;


import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

//Parental class is marked as deprecated in 1.21.4 MC version and can be removed in future Forge releases. Keep it in mind when you're going to port your mod to future versions.
public class ModBlockStateProvider extends BlockStateProvider
{
    @Override
    protected void registerStatesAndModels()
    {
        blockWithItem(ModBlocks.COBBLED_DEEPSLATE_TILES);
        blockWithItem(ModBlocks.SMOOTH_CALCITE);
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject)
    {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    //Creates an object of ModBlockStateProvider class.
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper)
    {
        super(output, BunchOfRedstone.MOD_ID, exFileHelper);
    }
}
