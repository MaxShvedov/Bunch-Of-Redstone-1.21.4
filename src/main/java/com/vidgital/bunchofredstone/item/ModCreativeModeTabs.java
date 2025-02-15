package com.vidgital.bunchofredstone.item;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs
{
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BunchOfRedstone.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BUNCH_OF_REDSTONE_TAB = CREATIVE_MODE_TABS.register("bunch_of_redstone_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WRENCH.get()))
                    .title(Component.translatable("creativetab.bunchofredstone.bunch_of_redstone_tab"))
                    .displayItems((ItemDisplayParameters, output) -> {
                        output.accept(ModItems.WRENCH.get());
                        output.accept(ModItems.EXPOSED_WRENCH.get());
                        output.accept(ModItems.WEATHERED_WRENCH.get());
                        output.accept(ModItems.OXIDIZED_WRENCH.get());
                        output.accept(ModItems.MAGIC_WRENCH.get());
                    }).build());


    public static void Register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
