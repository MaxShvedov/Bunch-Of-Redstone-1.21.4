package com.vidgital.bunchofredstone.block.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;

public class WeatheringCopperButtonBlock extends ButtonBlock implements WeatheringCopper
{
    private final WeatheringCopper.WeatherState weatherState;

    public WeatheringCopperButtonBlock(BlockSetType pType, int pTicksToStayPressed, Properties pProperties, WeatherState weatherState)
    {
        super(pType, pTicksToStayPressed, pProperties);
        this.weatherState = weatherState;
    }

    @Override
    protected void randomTick(BlockState pState, ServerLevel pLevel, BlockPos pPos, RandomSource pRandom)
    {
        this.changeOverTime(pState, pLevel, pPos, pRandom);
    }

    @Override
    protected boolean isRandomlyTicking(BlockState pState)
    {
        return WeatheringCopper.getNext(pState.getBlock()).isPresent();
    }

    @Override
    public WeatherState getAge()
    {
        return this.weatherState;
    }
}
