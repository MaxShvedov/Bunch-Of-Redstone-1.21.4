package net.vidgital.bunchofredstone.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EndRodBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class RedstoneRodBlock extends EndRodBlock
{
    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource)
    {
        Direction direction = blockState.getValue(FACING);
        double d0 = (double)blockPos.getX() + 0.55 - (double)(randomSource.nextFloat() * 0.1F);
        double d1 = (double)blockPos.getY() + 0.55 - (double)(randomSource.nextFloat() * 0.1F);
        double d2 = (double)blockPos.getZ() + 0.55 - (double)(randomSource.nextFloat() * 0.1F);
        double d3 = (double)(0.4F - (randomSource.nextFloat() + randomSource.nextFloat()) * 0.4F);
        if (randomSource.nextInt(5) == 0)
        {
            level.addParticle(
                    DustParticleOptions.REDSTONE,
                    d0 + (double)direction.getStepX() * d3,
                    d1 + (double)direction.getStepY() * d3,
                    d2 + (double)direction.getStepZ() * d3,
                    randomSource.nextGaussian() * 0.035,
                    randomSource.nextGaussian() * 0.035,
                    randomSource.nextGaussian() * 0.035
            );
        }
    }

    @Override
    protected boolean isSignalSource(BlockState pState)
    {
        return true;
    }

    @Override
    protected int getSignal(BlockState pState, BlockGetter pLevel, BlockPos pPos, Direction pDirection)
    {
        return pState.getValue(FACING).getOpposite() == pDirection ? 15 : 0;
    }

    @Override
    public boolean canConnectRedstone(BlockState state, BlockGetter level, BlockPos pos, @Nullable Direction direction)
    {
        return direction != null && direction == state.getValue(FACING).getOpposite();
    }

    public RedstoneRodBlock(Properties properties)
    {
        super(properties);
    }
}
