package net.vidgital.bunchofredstone.block;

import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;
import java.util.function.Supplier;

public interface ModWeatheringCopper extends ChangeOverTimeBlock<ModWeatheringCopper.WeatherState>
{
    Supplier<BiMap<Block, Block>> NEXT_BY_BLOCK = Suppliers.memoize(
            () -> ImmutableBiMap.<Block, Block>builder()
                    .put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.EXPOSED_COPPER_BUTTON.get())
                    .put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WEATHERED_COPPER_BUTTON.get())
                    .put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.OXIDIZED_COPPER_BUTTON.get())
                    .build()
    );
    Supplier<BiMap<Block, Block>> PREVIOUS_BY_BLOCK = Suppliers.memoize(() -> NEXT_BY_BLOCK.get().inverse());

    static Optional<Block> getPrevious(Block pBlock) {
        return Optional.ofNullable(PREVIOUS_BY_BLOCK.get().get(pBlock));
    }

    static Block getFirst(Block pBlock) {
        Block block = pBlock;

        for (Block block1 = PREVIOUS_BY_BLOCK.get().get(pBlock); block1 != null; block1 = PREVIOUS_BY_BLOCK.get().get(block1)) {
            block = block1;
        }

        return block;
    }

    static Optional<BlockState> getPrevious(BlockState pState) {
        return getPrevious(pState.getBlock()).map(p_154903_ -> p_154903_.withPropertiesOf(pState));
    }

    static Optional<Block> getNext(Block pBlock) {
        return Optional.ofNullable(NEXT_BY_BLOCK.get().get(pBlock));
    }

    static BlockState getFirst(BlockState pState) {
        return getFirst(pState.getBlock()).withPropertiesOf(pState);
    }

    @Override
    default Optional<BlockState> getNext(BlockState p_154893_) {
        return getNext(p_154893_.getBlock()).map(p_154896_ -> p_154896_.withPropertiesOf(p_154893_));
    }

    @Override
    default float getChanceModifier() {
        return this.getAge() == ModWeatheringCopper.WeatherState.UNAFFECTED ? 0.75F : 1.0F;
    }

    public static enum WeatherState implements StringRepresentable
    {
        UNAFFECTED("unaffected"),
        EXPOSED("exposed"),
        WEATHERED("weathered"),
        OXIDIZED("oxidized");

        public static final Codec<WeatheringCopper.WeatherState> CODEC = StringRepresentable.fromEnum(WeatheringCopper.WeatherState::values);
        private final String name;

        private WeatherState(final String pName) {
            this.name = pName;
        }

        @Override
        public String getSerializedName() {
            return this.name;
        }
    }
}
