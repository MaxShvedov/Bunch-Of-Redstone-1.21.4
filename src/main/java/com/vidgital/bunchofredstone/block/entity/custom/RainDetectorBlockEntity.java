package com.vidgital.bunchofredstone.block.entity.custom;

import com.vidgital.bunchofredstone.block.entity.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RainDetectorBlockEntity extends BlockEntity
{


//    public static <T extends BlockEntity> void tick(Level level, BlockPos pos, BlockState state, T t)
//    {
//        int power = 0;
//        if (level.getBiome(pos).value().getPrecipitationAt(pos) != Biome.Precipitation.NONE) {
//            // Block outputs a redstone signal of strength 1-8 if it's raining...
//            if (level.getRainLevel(1.0F) > 0.2) {
//                power = (int) (level.getRainLevel(1.0F) * 8.0F);
//            }
//            // ...and a redstone signal of strength 9-15 if it's thundering.
//            if (level.getThunderLevel(1.0F) > 0.2) {
//                power = 8 + (int) (level.getThunderLevel(1.0F) * 7.0F);
//            }
//        }
//        level.setBlockAndUpdate(pos, state.setValue(BlockStateProperties.POWER, power));
//    }



    public RainDetectorBlockEntity(BlockPos pPos, BlockState pBlockState)
    {
        super(ModBlockEntities.RAIN_DETECTOR_BE.get(), pPos, pBlockState);
    }
}
