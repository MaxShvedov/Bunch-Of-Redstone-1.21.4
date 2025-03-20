package net.vidgital.bunchofredstone.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.vidgital.bunchofredstone.BunchOfRedstone;

public class ModTags
{
    public static class Blocks
    {
        private static TagKey<Block> createTag(String name)
        {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, name));
        }
    }

    public static class Items
    {
        public static final TagKey<Item> STONE_BUTTON_CRAFTING_MATERIALS = createTag("stone_button_crafting_materials");


        private static TagKey<Item> createTag(String name)
        {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, name));
        }
    }
}
