package net.vidgital.bunchofredstone.block.entity;

import net.vidgital.bunchofredstone.BunchOfRedstone;
import net.vidgital.bunchofredstone.block.ModBlocks;
import net.vidgital.bunchofredstone.block.entity.custom.RainDetectorBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockEntities
{
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BunchOfRedstone.MOD_ID);

    public static final RegistryObject<BlockEntityType<RainDetectorBlockEntity>> RAIN_DETECTOR_BE =
            BLOCK_ENTITIES.register("rain_detector_be", () -> new BlockEntityType<>(
            RainDetectorBlockEntity::new, Set.of(ModBlocks.RAIN_DETECTOR.get())));

    public static void Register(IEventBus eventBus)
    {
        BLOCK_ENTITIES.register(eventBus);
    }
}
