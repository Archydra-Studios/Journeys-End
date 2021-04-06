package azzy.fabric.journeysend.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FernBlock;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;

public class UndergroundFernBlock extends FernBlock {

    protected UndergroundFernBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return BlockTags.MUSHROOM_GROW_BLOCK.contains(floor.getBlock()) || floor.isOf(JourneysEndBlocks.BLACK_MUD) || floor.isOf(JourneysEndBlocks.RED_MUD) || floor.isOf(Blocks.STONE) || floor.isOf(Blocks.DEEPSLATE) || floor.isOf(Blocks.COBBLED_DEEPSLATE) || floor.isOf(Blocks.GRAVEL) || floor.isOf(Blocks.DIORITE) || floor.isOf(Blocks.ANDESITE) || floor.isOf(Blocks.GRANITE) || floor.isOf(Blocks.TUFF) || floor.isOf(Blocks.BLACKSTONE);
    }

    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }
}
