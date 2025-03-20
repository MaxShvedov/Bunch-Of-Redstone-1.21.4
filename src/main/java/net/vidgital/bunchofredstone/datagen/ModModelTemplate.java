package net.vidgital.bunchofredstone.datagen;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Streams;
import com.google.gson.JsonObject;
import net.minecraft.client.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.TrapDoorBlock;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class ModModelTemplate extends ModelTemplate
{
    private final Optional<ResourceLocation> model;
    private final Set<TextureSlot> requiredSlots;
    private final Optional<String> suffix;

    public ResourceLocation create(Block pBlock, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput)
    {
        if (pBlock instanceof DoorBlock || pBlock instanceof TrapDoorBlock)
            return this.createDoor(ModelLocationUtils.getModelLocation(pBlock, this.suffix.orElse("")), pTextureMapping, pOutput);
        return this.create(ModelLocationUtils.getModelLocation(pBlock, this.suffix.orElse("")), pTextureMapping, pOutput);
    }

    public ResourceLocation create(ResourceLocation pModelLocation, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput)
    {
        Map<TextureSlot, ResourceLocation> map = this.createMap(pTextureMapping);
        pOutput.accept(pModelLocation, () -> {
            JsonObject jsonObject1 = new JsonObject();
            this.model.ifPresent(resourceLocation
                    -> jsonObject1.addProperty("parent", resourceLocation.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonObject2 = new JsonObject();
                map.forEach((textureSlot, resourceLocation)
                        -> jsonObject2.addProperty(textureSlot.getId(), resourceLocation.toString()));
                jsonObject1.add("textures", jsonObject2);
            }
            return jsonObject1;
        });
        return pModelLocation;
    }

    public ResourceLocation createDoor(ResourceLocation pModelLocation, TextureMapping pTextureMapping, BiConsumer<ResourceLocation, ModelInstance> pOutput)
    {
        Map<TextureSlot, ResourceLocation> map = this.createMap(pTextureMapping);
        pOutput.accept(pModelLocation, () -> {
            JsonObject jsonObject1 = new JsonObject();
            this.model.ifPresent(resourceLocation
                    -> jsonObject1.addProperty("parent", resourceLocation.toString()));
            if (!map.isEmpty()) {
                JsonObject jsonObject2 = new JsonObject();
                map.forEach((textureSlot, resourceLocation)
                        -> jsonObject2.addProperty(textureSlot.getId(), resourceLocation.toString()));
                jsonObject1.add("textures", jsonObject2);
            }
            jsonObject1.addProperty("render_type", "minecraft:cutout");
            return jsonObject1;
        });
        return pModelLocation;
    }

    private Map<TextureSlot, ResourceLocation> createMap(TextureMapping pTextureMapping)
    {
        return Streams.concat(this.requiredSlots.stream(), pTextureMapping.getForced())
                .collect(ImmutableMap.toImmutableMap(Function.identity(), pTextureMapping::get));
    }


    //Creates an object of ModModelTemplate class.
    public ModModelTemplate(Optional<ResourceLocation> pModel, Optional<String> pSuffix, TextureSlot... pRequiredSlots)
    {
        super(pModel, pSuffix, pRequiredSlots);
        this.model = pModel;
        this.suffix = pSuffix;
        this.requiredSlots = ImmutableSet.copyOf(pRequiredSlots);
    }
}
