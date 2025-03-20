package net.vidgital.bunchofredstone.block.custom;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import net.vidgital.bunchofredstone.block.ModBlocks;
import net.vidgital.bunchofredstone.block.ModWeatheringCopper;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Optional;

public class WeatheringCopperButtonBlock extends CopperButtonBlock implements ModWeatheringCopper
{
    public static final Supplier<BiMap<Block, Block>> WAX_ON = Suppliers.memoize(
            () -> ImmutableBiMap.<Block, Block>builder()
                    .put(ModBlocks.COPPER_BUTTON.get(), ModBlocks.WAXED_COPPER_BUTTON.get())
                    .put(ModBlocks.EXPOSED_COPPER_BUTTON.get(), ModBlocks.WAXED_EXPOSED_COPPER_BUTTON.get())
                    .put(ModBlocks.WEATHERED_COPPER_BUTTON.get(), ModBlocks.WAXED_WEATHERED_COPPER_BUTTON.get())
                    .put(ModBlocks.OXIDIZED_COPPER_BUTTON.get(), ModBlocks.WAXED_OXIDIZED_COPPER_BUTTON.get())
                    .build()
    );

    private final ModWeatheringCopper.WeatherState weatherState;

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack,
            BlockState pState,
            Level pLevel,
            BlockPos pPos,
            Player pPlayer,
            InteractionHand pHand,
            BlockHitResult pHitResult
    )
    {
        ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if (itemStack.is(Items.HONEYCOMB))
            {
                return getWaxed(pState).<InteractionResult>map(blockState ->
                        {
                            if(!pPlayer.isCreative())
                                itemStack.shrink(1);
                            pLevel.setBlock(pPos, blockState, 11);
                            pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockState));
                            pLevel.levelEvent(pPlayer, 3003, pPos, 0);
                            return InteractionResult.SUCCESS;
                        }
                ).orElse(InteractionResult.PASS);
            }
            if (itemStack.getItem() instanceof AxeItem)
            {
                return ModWeatheringCopper.getPrevious(pState).<InteractionResult>map(blockState ->
                    {
                        if(!pPlayer.isCreative())
                            itemStack.hurtAndBreak(1, pPlayer, LivingEntity.getSlotForHand(pHand));
                        pLevel.setBlock(pPos, blockState, 11);
                        pLevel.gameEvent(GameEvent.BLOCK_CHANGE, pPos, GameEvent.Context.of(pPlayer, blockState));
                        pLevel.playSound(pPlayer, pPos, SoundEvents.AXE_SCRAPE, SoundSource.BLOCKS, 1.0f, 1.0f);
                        pLevel.levelEvent(pPlayer, 3005, pPos, 0);
                        return InteractionResult.SUCCESS;
                    }
                ).orElse(InteractionResult.PASS);
            }
            super.useWithoutItem(pState, pLevel, pPos, pPlayer, pHitResult);
            if(!pState.getValue(POWERED))
                return InteractionResult.SUCCESS;
        return InteractionResult.PASS;
    }

    public static Optional<BlockState> getWaxed(BlockState pState)
    {
        return Optional.ofNullable(WAX_ON.get().get(pState.getBlock())).map(block -> block.withPropertiesOf(pState));
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        this.changeOverTime(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState)
    {
        return ModWeatheringCopper.getNext(pState.getBlock()).isPresent();
    }

    @Override
    public ModWeatheringCopper.WeatherState getAge()
    {
        return this.weatherState;
    }

    public WeatheringCopperButtonBlock(BlockSetType pType, int pTicksToStayPressed, ModWeatheringCopper.WeatherState weatherState, Properties pProperties)
    {
        super(pType, pTicksToStayPressed, pProperties);
        this.weatherState = weatherState;
    }
}
