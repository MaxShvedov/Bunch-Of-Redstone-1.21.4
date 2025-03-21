package com.vidgital.bunchofredstone;

import com.mojang.logging.LogUtils;
import com.vidgital.bunchofredstone.block.ModBlocks;
import com.vidgital.bunchofredstone.block.entity.ModBlockEntities;
import com.vidgital.bunchofredstone.event.WrenchEvents;
import com.vidgital.bunchofredstone.item.ModCreativeModeTabs;
import com.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BunchOfRedstone.MOD_ID)
public class BunchOfRedstone
{
    // Define mod id in a common place for everything to reference
    public static final String MOD_ID = "bunchofredstone";
    // Directly reference a slf4j logger
    private static final Logger _LOGGER = LogUtils.getLogger();

    //Creates an object of BunchOfRedstone class
    public BunchOfRedstone(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        // Register ourselves for server and other game events we are interested in

        MinecraftForge.EVENT_BUS.register(this);

        //Register mod creative mode tabs
        ModCreativeModeTabs.Register(modEventBus);

        // Register mod items and blocks
        ModItems.Register(modEventBus);
        ModBlocks.Register(modEventBus);
        ModBlockEntities.Register(modEventBus);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {

    }

    // Add items and block items to the creative menu tabs
    private void addCreative(BuildCreativeModeTabContentsEvent event)
    {
        if(event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES)
        {
//            event.accept(ModItems.WRENCH);
//            event.accept(ModItems.EXPOSED_WRENCH);
//            event.accept(ModItems.WEATHERED_WRENCH);
//            event.accept(ModItems.OXIDIZED_WRENCH);
        }

        if(event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS)
        {
            event.accept(ModBlocks.REDSTONE_LANTERN.get());
            event.accept(ModBlocks.REDSTONE_ROD.get());
            event.accept(ModBlocks.COPPER_ROD.get());
        }
        
        if(event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS)
        {
//            event.accept(ModItems.COPPER_DUST.get());
//            event.accept(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());
            event.accept(ModBlocks.WAXED_COPPER_BUTTON.get());
            event.accept(ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get());
            event.accept(ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get());
            event.accept(ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get());
            event.accept(ModBlocks.GOLDEN_BUTTON.get());
            event.accept(ModBlocks.IRON_BUTTON.get());
            event.accept(ModBlocks.REDSTONE_LANTERN.get());
            event.accept(ModBlocks.REDSTONE_ROD.get());
            event.accept(ModBlocks.COPPER_ROD.get());
            event.accept(ModBlocks.RAIN_DETECTOR.get());
            event.accept(ModBlocks.REDUCTOR.get());
            event.accept(ModBlocks.INTERSECTION.get());
        }

        if(event.getTabKey() == CreativeModeTabs.INGREDIENTS)
        {
//            event.accept(ModItems.COPPER_NUGGET.get());
//            event.accept(ModItems.COPPER_DUST.get());
        }
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
