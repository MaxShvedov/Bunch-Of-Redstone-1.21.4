package com.vidgital.bunchofredstone.item;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.item.custom.WrenchItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems
{
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BunchOfRedstone.MOD_ID);

    public static final RegistryObject<Item> COPPER_NUGGET = ITEMS.register(
            "copper_nugget", () -> new Item(new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "copper_nugget")))));

    public static final RegistryObject<Item> COPPER_DUST = ITEMS.register(
            "copper_dust", () -> new Item(new Item.Properties().useItemDescriptionPrefix()
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "copper_dust")))));

    public static final RegistryObject<Item> WRENCH = ITEMS.register(
            "wrench", () -> new WrenchItem(new Item.Properties().useItemDescriptionPrefix().stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "wrench")))));

    public static final RegistryObject<Item> EXPOSED_WRENCH = ITEMS.register(
            "exposed_wrench", () -> new WrenchItem(new Item.Properties().useItemDescriptionPrefix().stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "exposed_wrench")))));

    public static final RegistryObject<Item> WEATHERED_WRENCH = ITEMS.register(
            "weathered_wrench", () -> new WrenchItem(new Item.Properties().useItemDescriptionPrefix().stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "weathered_wrench")))));

    public static final RegistryObject<Item> OXIDIZED_WRENCH = ITEMS.register(
            "oxidized_wrench", () -> new WrenchItem(new Item.Properties().useItemDescriptionPrefix().stacksTo(1)
                    .setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(BunchOfRedstone.MOD_ID, "oxidized_wrench")))));

    public static void Register(IEventBus eventBus)
    {
        ITEMS.register(eventBus);
    }
}
