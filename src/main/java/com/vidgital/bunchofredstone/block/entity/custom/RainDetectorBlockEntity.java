package com.vidgital.bunchofredstone.block.entity.custom;

import com.vidgital.bunchofredstone.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RainDetectorBlockEntity extends BlockEntity
{



    public RainDetectorBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.RAIN_DETECTOR_BE.get(), pPos, pBlockState);
    }
}
