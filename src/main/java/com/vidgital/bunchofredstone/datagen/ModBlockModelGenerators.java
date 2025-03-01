package com.vidgital.bunchofredstone.datagen;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.vidgital.bunchofredstone.block.ModBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelOutput;
import net.minecraft.client.data.models.blockstates.*;
import net.minecraft.client.data.models.model.*;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;


public class ModBlockModelGenerators extends BlockModelGenerators
{
    final List<Block> nonOrientableTrapdoor = ImmutableList.of(Blocks.OAK_TRAPDOOR, Blocks.DARK_OAK_TRAPDOOR, Blocks.IRON_TRAPDOOR);
    final Map<Block, TexturedModel> texturedModels = ImmutableMap.<Block, TexturedModel>builder()
            .build();

    static final ImmutableMap<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>> SHAPE_CONSUMERS =
            ImmutableMap.<BlockFamily.Variant, BiConsumer<ModBlockFamilyProvider, Block>>builder()
                    .put(BlockFamily.Variant.BUTTON, ModBlockFamilyProvider::button)
                    .put(BlockFamily.Variant.PRESSURE_PLATE, ModBlockFamilyProvider::pressurePlate)
                    .put(BlockFamily.Variant.DOOR, ModBlockFamilyProvider::door)
                    .put(BlockFamily.Variant.TRAPDOOR, ModBlockFamilyProvider::trapdoor)
                    .build();

    protected void createDoor(Block pDoorBlock)
    {
        TextureMapping texturemapping = TextureMapping.door(pDoorBlock);
        ResourceLocation locationDoorBottomLeft = ModModelTemplates.DOOR_BOTTOM_LEFT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorBottomLeftOpen = ModModelTemplates.DOOR_BOTTOM_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorBottomRight = ModModelTemplates.DOOR_BOTTOM_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorBottomRightOpen = ModModelTemplates.DOOR_BOTTOM_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorTopLeft = ModModelTemplates.DOOR_TOP_LEFT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorTopLeftOpen = ModModelTemplates.DOOR_TOP_LEFT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorTopRight = ModModelTemplates.DOOR_TOP_RIGHT.create(pDoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationDoorTopRightOpen = ModModelTemplates.DOOR_TOP_RIGHT_OPEN.create(pDoorBlock, texturemapping, this.modelOutput);
        this.registerSimpleFlatItemModel(pDoorBlock.asItem());
        this.blockStateOutput
                .accept(
                        createDoor(
                                pDoorBlock,
                                locationDoorBottomLeft,
                                locationDoorBottomLeftOpen,
                                locationDoorBottomRight,
                                locationDoorBottomRightOpen,
                                locationDoorTopLeft,
                                locationDoorTopLeftOpen,
                                locationDoorTopRight,
                                locationDoorTopRightOpen
                        )
                );
    }

    protected static BlockStateGenerator createTrapdoor(Block pTrapdoorBlock, ResourceLocation pTopModelLocation, ResourceLocation pBottomModelLocation, ResourceLocation pOpenModelLocation) {
        return MultiVariantGenerator.multiVariant(pTrapdoorBlock)
                .with(
                        PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.HALF, BlockStateProperties.OPEN)
                                .select(Direction.NORTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.SOUTH, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.EAST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.WEST, Half.BOTTOM, false, Variant.variant().with(VariantProperties.MODEL, pBottomModelLocation))
                                .select(Direction.NORTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.SOUTH, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.EAST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.WEST, Half.TOP, false, Variant.variant().with(VariantProperties.MODEL, pTopModelLocation))
                                .select(Direction.NORTH, Half.BOTTOM, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation))
                                .select(
                                        Direction.SOUTH,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .select(
                                        Direction.EAST,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .select(
                                        Direction.WEST,
                                        Half.BOTTOM,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                                .select(Direction.NORTH, Half.TOP, true, Variant.variant().with(VariantProperties.MODEL, pOpenModelLocation))
                                .select(
                                        Direction.SOUTH,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180)
                                )
                                .select(
                                        Direction.EAST,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90)
                                )
                                .select(
                                        Direction.WEST,
                                        Half.TOP,
                                        true,
                                        Variant.variant()
                                                .with(VariantProperties.MODEL, pOpenModelLocation)
                                                .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270)
                                )
                );
    }

    //Type needed blocks here.
    public void run()
    {
        ModBlockFamilies.getAllFamilies().filter(BlockFamily::shouldGenerateModel).forEach(
                blockFamily -> this.family(blockFamily.getBaseBlock()).generateFor(blockFamily)
        );

        createTrivialCube(ModBlocks.SMOOTH_CALCITE.get());
        createTrivialCube(ModBlocks.COBBLED_DEEPSLATE_TILES.get());

        createPressurePlate(ModBlocks.COBBLESTONE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.GRANITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.POLISHED_GRANITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.DIORITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.POLISHED_DIORITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.ANDESITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.POLISHED_ANDESITE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.COBBLED_DEEPSLATE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.POLISHED_DEEPSLATE_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.TUFF_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.POLISHED_TUFF_PRESSURE_PLATE.get());
        createPressurePlate(ModBlocks.BLACKSTONE_PRESSURE_PLATE.get());

        createButton(ModBlocks.COBBLESTONE_BUTTON.get());
        createButton(ModBlocks.GRANITE_BUTTON.get());
        createButton(ModBlocks.POLISHED_GRANITE_BUTTON.get());
        createButton(ModBlocks.DIORITE_BUTTON.get());
        createButton(ModBlocks.POLISHED_DIORITE_BUTTON.get());
        createButton(ModBlocks.ANDESITE_BUTTON.get());
        createButton(ModBlocks.POLISHED_ANDESITE_BUTTON.get());
        createButton(ModBlocks.COBBLED_DEEPSLATE_BUTTON.get());
        createButton(ModBlocks.POLISHED_DEEPSLATE_BUTTON.get());
        createButton(ModBlocks.TUFF_BUTTON.get());
        createButton(ModBlocks.POLISHED_TUFF_BUTTON.get());
        createButton(ModBlocks.BLACKSTONE_BUTTON.get());

        createPressurePlate(ModBlocks.MEASURING_WEIGHTED_PRESSURE_PLATE.get());

        createButton(ModBlocks.COPPER_BUTTON.get());
        createButton(ModBlocks.GOLDEN_BUTTON.get());
        createButton(ModBlocks.IRON_BUTTON.get());

        createLantern(ModBlocks.REDSTONE_LANTERN.get());
        createRotatableColumn(ModBlocks.REDSTONE_ROD.get());

    }

//    protected void createRodWithBase(Block pRodBlock)
//    {
//        TextureMapping textureMapping = TextureMapping.defaultTexture(pRodBlock);
//        ResourceLocation locationRod = ModModelTemplates.
//    }

    protected void createOrientableTrapdoor(Block pOrientableTrapdoorBlock)
    {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pOrientableTrapdoorBlock);
        ResourceLocation locationTrapdoorTop = ModModelTemplates.ORIENTABLE_TRAPDOOR_TOP.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationTrapdoorBottom = ModModelTemplates.ORIENTABLE_TRAPDOOR_BOTTOM.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationTrapdoorOpen = ModModelTemplates.ORIENTABLE_TRAPDOOR_OPEN.create(pOrientableTrapdoorBlock, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createOrientableTrapdoor(pOrientableTrapdoorBlock, locationTrapdoorTop, locationTrapdoorBottom, locationTrapdoorOpen));
        this.registerSimpleItemModel(pOrientableTrapdoorBlock, locationTrapdoorBottom);
    }

    protected void createTrapdoor(Block pTrapdoorBlock)
    {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pTrapdoorBlock);
        ResourceLocation locationTrapdoorTop = ModModelTemplates.TRAPDOOR_TOP.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationTrapdoorBottom = ModModelTemplates.TRAPDOOR_BOTTOM.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        ResourceLocation locationTrapdoorOpen = ModModelTemplates.TRAPDOOR_OPEN.create(pTrapdoorBlock, texturemapping, this.modelOutput);
        this.blockStateOutput.accept(createTrapdoor(pTrapdoorBlock, locationTrapdoorTop, locationTrapdoorBottom, locationTrapdoorOpen));
        this.registerSimpleItemModel(pTrapdoorBlock, locationTrapdoorBottom);
    }

    public void createPressurePlate(Block pPressurePlateBlock)
    {
        TextureMapping texturemapping = TextureMapping.defaultTexture(pPressurePlateBlock);
        ResourceLocation locationPlateUp = ModModelTemplates.PRESSURE_PLATE_UP.create(pPressurePlateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
        ResourceLocation locationPlateDown = ModModelTemplates.PRESSURE_PLATE_DOWN.create(pPressurePlateBlock, texturemapping, ModBlockModelGenerators.this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pPressurePlateBlock, locationPlateUp, locationPlateDown));
        this.registerSimpleItemModel(pPressurePlateBlock, locationPlateUp);
    }

    public void createButton(Block pButtonBlock)
    {
        TextureMapping textureMapping = TextureMapping.defaultTexture(pButtonBlock);
        ResourceLocation locationButton = ModModelTemplates.BUTTON.create(pButtonBlock, textureMapping, ModBlockModelGenerators.this.modelOutput);
        ResourceLocation locationButtonPressed = ModModelTemplates.BUTTON_PRESSED.create(pButtonBlock, textureMapping, ModBlockModelGenerators.this.modelOutput);
        this.blockStateOutput.accept(BlockModelGenerators.createButton(pButtonBlock, locationButton, locationButtonPressed));
        ResourceLocation locationInventory = ModModelTemplates.BUTTON_INVENTORY.create(pButtonBlock, textureMapping, ModBlockModelGenerators.this.modelOutput);
        this.registerSimpleItemModel(pButtonBlock, locationInventory);
    }


    //Creates an object of ModBlockModelGenerators.
    public ModBlockModelGenerators(Consumer<BlockStateGenerator> pBlockStateOutput, ItemModelOutput pItemModelOutput, BiConsumer<ResourceLocation, ModelInstance> pModelOutput)
    {
        super(pBlockStateOutput, pItemModelOutput, pModelOutput);
    }

    public class ModBlockFamilyProvider extends BlockFamilyProvider
    {
        private final TextureMapping mapping;
        private final Map<ModelTemplate, ResourceLocation> models = Maps.newHashMap();
        @Nullable
        private BlockFamily family;
        @Nullable
        private ResourceLocation fullBlock;
        private final Set<Block> skipGeneratingModelsFor = new HashSet<>();

        @Override
        public BlockFamilyProvider generateFor(BlockFamily pFamily) {
            this.family = pFamily;
            pFamily.getVariants().forEach((variant, block) -> {
                if (!this.skipGeneratingModelsFor.contains(block)) {
                    BiConsumer<ModBlockFamilyProvider, Block> biconsumer = ModBlockModelGenerators.SHAPE_CONSUMERS.get(variant);
                    if (biconsumer != null) {
                        biconsumer.accept(this, block);
                    }
                }
            });

            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider button(Block pButtonBlock)
                {
            ResourceLocation locationButton = ModModelTemplates.BUTTON.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation locationButtonPressed = ModModelTemplates.BUTTON_PRESSED.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createButton(pButtonBlock, locationButton, locationButtonPressed));
            ResourceLocation locationInventory = ModModelTemplates.BUTTON_INVENTORY.create(pButtonBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.registerSimpleItemModel(pButtonBlock, locationInventory);

            return this;
        }

        public BlockModelGenerators.BlockFamilyProvider pressurePlate(Block pPressurePlateBlock)
        {
            ResourceLocation locationPlateUp = ModModelTemplates.PRESSURE_PLATE_UP.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ResourceLocation locationPlateDown = ModModelTemplates.PRESSURE_PLATE_DOWN.create(pPressurePlateBlock, this.mapping, ModBlockModelGenerators.this.modelOutput);
            ModBlockModelGenerators.this.blockStateOutput.accept(BlockModelGenerators.createPressurePlate(pPressurePlateBlock, locationPlateUp, locationPlateDown));

            return this;
        }

        protected BlockModelGenerators.BlockFamilyProvider door(Block pDoorBlock)
        {
            ModBlockModelGenerators.this.createDoor(pDoorBlock);
            return this;
        }

        protected void trapdoor(Block pTrapdoorBlock)
        {
            if (ModBlockModelGenerators.this.nonOrientableTrapdoor.contains(pTrapdoorBlock))
            {
                ModBlockModelGenerators.this.createTrapdoor(pTrapdoorBlock);
            }
            else
            {
                ModBlockModelGenerators.this.createOrientableTrapdoor(pTrapdoorBlock);
            }
        }

        //Creates an object of ModBlockFamilyProvider class.
        public ModBlockFamilyProvider(TextureMapping pMapping) {
            super(pMapping);
            this.mapping = pMapping;
        }
    }
}
