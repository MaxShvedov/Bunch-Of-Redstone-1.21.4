package net.vidgital.bunchofredstone.datagen;

import com.google.common.collect.Maps;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.world.level.block.Block;

import java.util.Map;
import java.util.stream.Stream;

public class ModBlockFamilies extends BlockFamilies
{
    private static final Map<Block, BlockFamily> _MAP = Maps.newHashMap();



    public static Stream<BlockFamily> getAllFamilies()
    {
        return _MAP.values().stream();
    }

    private static BlockFamily.Builder familyBuilder(Block pBaseBlock)
    {
        BlockFamily.Builder blockFamilyBuilder = new BlockFamily.Builder(pBaseBlock);
        BlockFamily blockFamily = _MAP.put(pBaseBlock, blockFamilyBuilder.getFamily());
        if(blockFamily != null)
        {
            throw new IllegalStateException("Duplicate family definition for " + BuiltInRegistries.BLOCK.getKey(pBaseBlock));
        }
        else
        {
            return blockFamilyBuilder;
        }
    }

}
