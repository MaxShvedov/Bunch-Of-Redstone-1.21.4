package com.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class RainDetectorBlock extends BaseEntityBlock
{
    protected static final VoxelShape SHAPE = Block.box(0.0f, 0.0f, 0.0f,
                                                        16.0f, 6.0f, 16.0f);


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec()
    {
        return null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pPos, BlockState pState)
    {
        return null;
    }

    @Override
    protected VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    public RainDetectorBlock(Properties p_49224_)
    {
        super(p_49224_);
    }
}
