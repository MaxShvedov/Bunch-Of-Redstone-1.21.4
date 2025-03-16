package com.vidgital.bunchofredstone.item;

import com.vidgital.bunchofredstone.BunchOfRedstone;
import com.vidgital.bunchofredstone.block.ModBlocks;
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
//                        output.accept(ModItems.WRENCH.get());
//                        output.accept(ModItems.EXPOSED_WRENCH.get());
//                        output.accept(ModItems.WEATHERED_WRENCH.get());
//                        output.accept(ModItems.OXIDIZED_WRENCH.get());

                        output.accept(ModBlocks.REDSTONE_LANTERN.get());
                        output.accept(ModBlocks.REDSTONE_ROD.get());
                        output.accept(ModBlocks.RAIN_DETECTOR.get());
                        output.accept(ModBlocks.COPPER_ROD.get());

                        output.accept(ModBlocks.REDUCTOR.get());
                        output.accept(ModBlocks.INTERSECTION.get());

                        output.accept(ModBlocks.GOLDEN_BUTTON.get());
                        output.accept(ModBlocks.IRON_BUTTON.get());

                        output.accept(ModBlocks.COPPER_BUTTON.get());
                        output.accept(ModBlocks.EXPOSED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.WEATHERED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.OXIDIZED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.WAXED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get());
                        output.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get());

                        output.accept(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.GRANITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.DIORITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.ANDESITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.TUFF_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get());
                        output.accept(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get());

                        output.accept(ModBlocks.COBBLESTONE_BUTTON.get());
                        output.accept(ModBlocks.GRANITE_BUTTON.get());
                        output.accept(ModBlocks.DIORITE_BUTTON.get());
                        output.accept(ModBlocks.ANDESITE_BUTTON.get());
                        output.accept(ModBlocks.TUFF_BUTTON.get());
                        output.accept(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get());
                        output.accept(ModBlocks.BLACKSTONE_BUTTON.get());
                        output.accept(ModBlocks.POLISHED_GRANITE_BUTTON.get());
                        output.accept(ModBlocks.POLISHED_DIORITE_BUTTON.get());
                        output.accept(ModBlocks.POLISHED_ANDESITE_BUTTON.get());
                        output.accept(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get());
                        output.accept(ModBlocks.POLISHED_TUFF_BUTTON.get());

//                        output.accept(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());

//                        output.accept(ModItems.COPPER_NUGGET.get());
//                        output.accept(ModItems.COPPER_DUST.get());


                    }).build());


    public static void Register(IEventBus eventBus)
    {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
