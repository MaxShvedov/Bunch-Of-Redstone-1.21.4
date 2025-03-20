package net.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import net.vidgital.bunchofredstone.block.entity.ModBlockEntities;
import net.vidgital.bunchofredstone.block.entity.custom.RainDetectorBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.*;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;


public class RainDetectorBlock extends BaseEntityBlock implements SimpleWaterloggedBlock
{
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty MOISTURE = IntegerProperty.create("moisture", 0, 15);
    public static final IntegerProperty POWER = BlockStateProperties.POWER;

    public static final VoxelShape SHAPE = Block.box(0.0f, 0.0f, 0.0f,
            16.0f, 6.f, 16.0f);
    public static final MapCodec<RainDetectorBlock> CODEC = simpleCodec(RainDetectorBlock::new);
    ;

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new RainDetectorBlockEntity(pPos, pState);
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    protected boolean useShapeForLightOcclusion(BlockState pState)
    {
        return true;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder
                .add(WATERLOGGED)
                .add(MOISTURE)
                .add(POWER);
    }

    @Override
    protected FluidState getFluidState(BlockState pState)
    {
        return pState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(pState);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext pContext)
    {
        BlockPos blockPos = pContext.getClickedPos();
        FluidState fluidState = pContext.getLevel().getFluidState(blockPos);
        boolean flag = pContext.getLevel().getFluidState(blockPos).getType() == Fluids.WATER;
        return this.defaultBlockState()
                .setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER)
                .setValue(MOISTURE, flag ? 15 : 0)
                .setValue(POWER, 0);
    }

    @Override
    protected InteractionResult useItemOn(
            ItemStack pStack,
            BlockState pState,
            Level pLevel,
            BlockPos pPos,
            Player pPlayer,
            InteractionHand pHand,
            BlockHitResult pHitResult)
    {
        if(pLevel.getBlockEntity(pPos) instanceof RainDetectorBlockEntity rainDetectorBlockEntity)
        {
            ItemStack itemStack = pPlayer.getItemInHand(pHand);
            if(itemStack.is(Items.SPONGE)
                    && pState.getValue(MOISTURE) != 0
                    && !pState.getValue(WATERLOGGED))
            {
                pLevel.setBlock(pPos, pState.setValue(MOISTURE, 0), 11);
                if(!pPlayer.isCreative())
                {
                    itemStack.shrink(1);
                    pPlayer.addItem(new ItemStack(Items.WET_SPONGE, 1));
                }
                return InteractionResult.SUCCESS;
            }
            if(itemStack.is(Items.POTION))
            {
                PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
                if(potionContents.is(Potions.WATER) && pState.getValue(MOISTURE) < 15) {
                    pLevel.setBlock(pPos, pState.setValue(MOISTURE, pState.getValue(MOISTURE) + 1), 11);
                    ItemStack itemsStack1 = ItemUtils.createFilledResult(itemStack, pPlayer, Items.GLASS_BOTTLE.getDefaultInstance());
                    pPlayer.playSound(SoundEvents.BOTTLE_EMPTY, 0.5f, 1.2f);
                    pPlayer.setItemInHand(pHand, itemsStack1);
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.FAIL;
    }

    @Override
    public boolean placeLiquid(LevelAccessor pLevel, BlockPos pPos, BlockState pState, FluidState pFluidState) {
        if(!pState.getValue(BlockStateProperties.WATERLOGGED) && pFluidState.getType() == Fluids.WATER)
        {
            pLevel.setBlock(pPos, pState.setValue(WATERLOGGED, true).setValue(MOISTURE, 15), 3);
            pLevel.scheduleTick(pPos, pFluidState.getType(), pFluidState.getType().getTickDelay(pLevel));
            return true;
        }
        return false;
    }

    @Override
    protected void onProjectileHit(Level pLevel, BlockState pState, BlockHitResult pHit, Projectile pProjectile)
    {
        BlockPos blockPos = pHit.getBlockPos();
        if(pProjectile instanceof ThrownPotion thrownPotion)
        {
            ItemStack itemStack = thrownPotion.getItem();
            PotionContents potionContents = itemStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

            if (pLevel instanceof ServerLevel serverLevel
                    && potionContents.is(Potions.WATER))
            {
                    pLevel.setBlock(blockPos, pState.setValue(MOISTURE, Math.min(pState.getValue(MOISTURE) + 1, 15)), 11);
            }
        }
    }

    @Override
    protected boolean isSignalSource(BlockState pState) {
        return true;
    }

    @Override
    protected int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pState.getValue(POWER);
    }

    private static void updateSignalStrength(BlockState pState, Level pLevel, BlockPos pPos)
    {
        int rain = pLevel.isRaining() ? 2 : 0;
        int thunder = pLevel.isThundering() ? 2 : 0;
        int moisture = pState.getValue(MOISTURE);
        float downfall = pLevel.getBiome(pPos).get().modifiableBiomeInfo().get().climateSettings().downfall();
        int power;


        if (pLevel.isRaining() && pLevel.canSeeSky(pPos))
            power = rain + thunder + (int)(downfall * 10) + moisture;
        else
            power = moisture;

        pLevel.setBlock(pPos, pState.setValue(POWER, Math.min(power, 15)), 3);
    }

    @Override
    protected void tick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        pLevel.setBlock(pPos, pState.setValue(MOISTURE, Math.max(pState.getValue(MOISTURE) - 1, 0)), 3);
    }

    protected BlockState updateShape(
            BlockState pState,
            LevelReader pLevel,
            ScheduledTickAccess pScheduledTickAccess,
            BlockPos pPos,
            Direction pDirection,
            BlockPos pNeighborPos,
            BlockState pNeighborState,
            RandomSource pRandom)
    {
        if (pState.getValue(WATERLOGGED))
        {
            pScheduledTickAccess.scheduleTick(pPos, Fluids.WATER, Fluids.WATER.getTickDelay(pLevel));
        }

        return super.updateShape(pState, pLevel, pScheduledTickAccess, pPos, pDirection, pNeighborPos, pNeighborState, pRandom);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        return !pLevel.isClientSide
                ? createTickerHelper(pBlockEntityType, ModBlockEntities.RAIN_DETECTOR_BE.get(), RainDetectorBlock::tickEntity)
                : null;
    }

    private static void tickEntity(Level pLevel, BlockPos pPos, BlockState pState, RainDetectorBlockEntity pBlockEntity)
    {
        if(pState.getValue(MOISTURE) > 0 && !pState.getValue(WATERLOGGED))
        {
            pLevel.scheduleTick(pPos, pState.getBlock(), 200);
        }
        updateSignalStrength(pState, pLevel, pPos);
    }

    public RainDetectorBlock(Properties p_49224_)
    {
        super(p_49224_);
        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(WATERLOGGED, false)
                        .setValue(MOISTURE, 0)
                        .setValue(POWER, 0)
        );
    }
}
