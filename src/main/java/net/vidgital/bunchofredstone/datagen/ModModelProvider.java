package net.vidgital.bunchofredstone.datagen;

import net.vidgital.bunchofredstone.block.ModBlocks;
import net.vidgital.bunchofredstone.item.ModItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.renderer.item.ClientItem;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ModModelProvider extends ModelProvider
{
    private final PackOutput.PathProvider blockStatePathProvider;
    private final PackOutput.PathProvider itemInfoPathProvider;
    private final PackOutput.PathProvider modelPathProvider;

    @Override
    protected Stream<Block> getKnownBlocks()
    {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    protected Stream<Item> getKnownItems()
    {
        return ModItems.ITEMS.getEntries().stream().map(RegistryObject::get);
    }

    @Override
    protected BlockModelGenerators getBlockModelGenerators(BlockStateGeneratorCollector blocks, ItemInfoCollector items, SimpleModelCollector models)
    {
        return new ModBlockModelGenerators(blocks, items, models);
    }

    @Override
    protected ItemModelGenerators getItemModelGenerators(ItemInfoCollector items, SimpleModelCollector models)
    {
        return new ModItemModelGenerators(items, models);
    }
    @Override
    public CompletableFuture<?> run(CachedOutput cachedOutput)
    {
        ModItemInfoCollector modItemInfoCollector = new ModItemInfoCollector(this::getKnownItems);
        ModelProvider.BlockStateGeneratorCollector blockStateGeneratorCollector = new ModelProvider.BlockStateGeneratorCollector(this::getKnownBlocks);
        ModelProvider.SimpleModelCollector simpleModelCollector = new ModelProvider.SimpleModelCollector();

        getBlockModelGenerators(blockStateGeneratorCollector, modItemInfoCollector, simpleModelCollector).run();
        getItemModelGenerators(modItemInfoCollector, simpleModelCollector).run();

        blockStateGeneratorCollector.validate();
        modItemInfoCollector.finalizeAndValidate();

        return CompletableFuture.allOf(
                blockStateGeneratorCollector.save(cachedOutput, this.blockStatePathProvider),
                simpleModelCollector.save(cachedOutput, this.modelPathProvider),
                modItemInfoCollector.save(cachedOutput, this.itemInfoPathProvider)
        );
    }
    public static class ModItemInfoCollector extends ItemInfoCollector
    {
        private final Map<Item, ClientItem> itemInfos = new HashMap<>();
        private final Map<Item, Item> copies = new HashMap<>();
        private final Supplier<Stream<Item>> known;

        public ModItemInfoCollector(Supplier<Stream<Item>> known)
        {
            this.known = known;
        }

        @Override
        public void accept(Item item, ItemModel.Unbaked unbaked)
        {
            this.register(item, new ClientItem(unbaked, ClientItem.Properties.DEFAULT));
        }

        private void register(Item pItem, ClientItem pClientItem)
        {
            ClientItem clientitem = this.itemInfos.put(pItem, pClientItem);
            if (clientitem != null) {
                throw new IllegalStateException("Duplicate item model definition for " + pItem);
            }
        }

        @Override
        public void copy(Item valueItem, Item keyItem)
        {
            this.copies.put(keyItem, valueItem);
        }

        public void generateDefaultBlockModels()
        {
            ModItems.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(item -> {
                if (!this.copies.containsKey(item)) {
                    if (item instanceof BlockItem blockitem && !this.itemInfos.containsKey(blockitem)) {
                        ResourceLocation resourcelocation = ModelLocationUtils.getModelLocation(blockitem.getBlock());
                        this.accept(blockitem, ItemModelUtils.plainModel(resourcelocation));
                    }
                }
            });
        }
        public void finalizeAndValidate()
        {
            this.copies.forEach((keyItem, valueItem) -> {
                ClientItem clientitem = this.itemInfos.get(valueItem);
                if (clientitem == null) {
                    throw new IllegalStateException("Missing donor: " + valueItem + " -> " + keyItem);
                } else {
                    this.register(keyItem, clientitem);
                }
            });
            List<ResourceLocation> list = known.get()
                    .map(item -> item.builtInRegistryHolder())
                    .filter(itemReference -> !this.itemInfos.containsKey(itemReference.value()))
                    .map(itemReference -> itemReference.key().location())
                    .toList();
            if (!list.isEmpty()) {
                throw new IllegalStateException("Missing item model definitions for: " + list);
            }
        }

        public CompletableFuture<?> save(CachedOutput pOutput, PackOutput.PathProvider pPathProvider)
        {
            return DataProvider.saveAll(
                    pOutput, ClientItem.CODEC, item -> pPathProvider.json(item.builtInRegistryHolder().key().location()), this.itemInfos
            );
        }

        //Creates an object of ModItemInfoCollector class.
        public ModItemInfoCollector()
        {
            this(() -> BuiltInRegistries.ITEM.stream().filter(item -> "bean_machine".equals(item.builtInRegistryHolder().key().location().getNamespace())));
        }

    }


    //Creates an object of ModModelProvider class.
    public ModModelProvider(PackOutput pOutput)
    {
        super(pOutput);
        this.blockStatePathProvider = pOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "blockstates");
        this.itemInfoPathProvider = pOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "items");
        this.modelPathProvider = pOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
    }
}
