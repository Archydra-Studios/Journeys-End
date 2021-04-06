package azzy.fabric.journeysend.feature;

import azzy.fabric.journeysend.feature.config.JourneyShroomFeatureConfig;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.util.FeatureContext;

import java.util.List;
import java.util.Random;

public class CampanulateMushroomFeature extends Feature<JourneyShroomFeatureConfig> {

    public CampanulateMushroomFeature(Codec<JourneyShroomFeatureConfig> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean generate(FeatureContext<JourneyShroomFeatureConfig> context) {
        JourneyShroomFeatureConfig config = context.getConfig();
        BlockPos pos = context.getOrigin();
        StructureWorldAccess world = context.getWorld();
        Random random = context.getRandom();

        List<BlockState> validGround = context.getConfig().validGround;

        if(validGround.isEmpty() || validGround.contains(world.getBlockState(pos.down()))) {


            int height = config.height.get(random);

            for (int i = 0; i < height + 2; i++) {
                if(!world.isAir(pos.up(i)))
                    return false;
            }

            for (int i = 0; i < height; i++) {
                world.setBlockState(pos, config.stem, 2);
                pos = pos.up();
            }

            int capHeight = config.radius.get(random);

            if(capHeight < 2)
                capHeight = 2;

            double steepness = 0.16  / config.modifier;

            for (int i = 0; i < capHeight; i++) {
                BlockPos capCenter = pos.down(i);
                int yDif = pos.getY() - capCenter.getY();

                int radius = (int) Math.round((double) capHeight / (1 + Math.pow(Math.E,  -yDif + (steepness / 1.25))));

                for (int x = -radius; x < radius; x++) {
                    for (int z = -radius; z < radius; z++) {
                        BlockPos capSection = new BlockPos(capCenter.getX() + x, capCenter.getY(), capCenter.getZ() + z);


                        if(world.getBlockState(capSection).getMaterial().isReplaceable() && testSphere(radius, capSection, capCenter, yDif == 0)) {
                            setCapState(capSection, random, world, config);
                        }
                    }
                }
            }

            return true;
        }

        return false;
    }

    private void setCapState(BlockPos pos, Random random, StructureWorldAccess world, JourneyShroomFeatureConfig config) {
        BlockState state = null;
        for (int i = config.capStates.size() - 1; i >= 0; i--) {
            if(i == 0) {
                state = config.capStates.get(0);
            }
            if(random.nextFloat() < config.capChances.get(i)) {
                state = config.capStates.get(i);
                break;
            }
        }
        world.setBlockState(pos, state, 2);
    }

    public boolean testSphere(int r, BlockPos test, BlockPos center, boolean whole) {
        return whole || center.getManhattanDistance(test) == r || center.getManhattanDistance(test) == r - 2  || center.getManhattanDistance(test) == r - 1 || center.getManhattanDistance(test) == r + 1 || center.getManhattanDistance(test) == r + 2;
    }
}
