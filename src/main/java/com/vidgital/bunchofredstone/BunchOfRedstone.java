package com.vidgital.bunchofredstone;

import com.mojang.logging.LogUtils;
import com.vidgital.bunchofredstone.block.ModBlocks;
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
            event.accept(ModItems.WRENCH);
            event.accept(ModItems.EXPOSED_WRENCH);
            event.accept(ModItems.WEATHERED_WRENCH);
            event.accept(ModItems.OXIDIZED_WRENCH);
        }

        if(event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS)
        {
            event.accept(ModBlocks.COBBLED_DEEPSLATE_TILES);
            event.accept(ModBlocks.SMOOTH_CALCITE);
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
