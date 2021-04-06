package azzy.fabric.journeysend.block;

import net.minecraft.block.*;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.chunk.light.ChunkLightProvider;

import java.util.Random;
import java.util.Set;

public class DynamicSpreadableBlock extends SpreadableBlock implements Fertilizable {

    private final Set<Block> replaceables;
    private final BlockState base;
    private final int minLight, maxLight;
    private final boolean fertilizeOnly;

    public DynamicSpreadableBlock(Settings settings, BlockState base, Set<Block> replaceables, int minLight, int maxLight, boolean fertilizeOnly) {
        super(settings);
        this.replaceables = replaceables;
        this.base = base;
        this.minLight = minLight;
        this.maxLight = maxLight;
        this.fertilizeOnly = fertilizeOnly;
    }

    protected boolean canSurvive(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.isOf(Blocks.SNOW)) {
            return true;
        } else if (blockState.getFluidState().getLevel() == 8) {
            return false;
        } else {
            int i = ChunkLightProvider.getRealisticOpacity(world, state, pos, blockState, blockPos, Direction.UP, blockState.getOpacity(world, blockPos));
            return i < world.getMaxLightLevel();
        }
    }

    protected boolean canSpread(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return canSurvive(state, world, pos) && !world.getFluidState(blockPos).isIn(FluidTags.WATER);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!canSurvive(state, world, pos)) {
            world.setBlockState(pos, base);
        } else if (!fertilizeOnly && canGrow(world, random, pos, state)) {
            tryGrow(world, pos, random);
        }
    }

    public void tryGrow(ServerWorld world, BlockPos pos, Random random) {
        BlockState blockState = this.getDefaultState();
        for(int i = 0; i < 4; ++i) {
            BlockPos blockPos = pos.add(random.nextInt(3) - 1, random.nextInt(5) - 3, random.nextInt(3) - 1);
            if (replaceables.contains(world.getBlockState(blockPos).getBlock()) && canSpread(blockState, world, blockPos)) {
                world.setBlockState(blockPos, blockState.with(SNOWY, world.getBlockState(blockPos.up()).isOf(Blocks.SNOW)));
            }
        }
    }

    @Override
    public boolean isFertilizable(BlockView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        int light = world.getLightLevel(pos.up());
        return light >= minLight && light <= maxLight;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if(canGrow(world, random, pos, state)) {
            tryGrow(world, pos, random);
        }
    }
}
