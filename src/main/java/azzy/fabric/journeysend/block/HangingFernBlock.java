package azzy.fabric.journeysend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FernBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.WorldView;

public class HangingFernBlock extends FernBlock {

    protected HangingFernBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        BlockPos blockPos = pos.up();
        return this.canPlantOnTop(world.getBlockState(blockPos), world, blockPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isOf(Blocks.STONE) || floor.isOf(Blocks.DEEPSLATE) || floor.isOf(Blocks.COBBLED_DEEPSLATE) || floor.isOf(Blocks.GRAVEL) || floor.isOf(Blocks.DIORITE) || floor.isOf(Blocks.ANDESITE) || floor.isOf(Blocks.GRANITE) || floor.isOf(Blocks.TUFF) || floor.isOf(Blocks.BLACKSTONE) || super.canPlantOnTop(floor, world, pos);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
