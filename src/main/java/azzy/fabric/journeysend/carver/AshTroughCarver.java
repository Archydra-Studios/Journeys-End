package azzy.fabric.journeysend.carver;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.class_6108;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverContext;
import org.apache.commons.lang3.mutable.MutableBoolean;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class AshTroughCarver extends Carver<class_6108> {

    public AshTroughCarver(Codec<class_6108> configCodec) {
        super(configCodec);
    }

    @Override
    public boolean carve(CarverContext context, class_6108 config, Chunk chunk, Function<BlockPos, Biome> posToBiome, Random random, int seaLevel, ChunkPos pos, BitSet carvingMask) {

        int mainChunkX = chunk.getPos().x;
        int mainChunkZ = chunk.getPos().z;

        double x = pos.getOffsetX(16);
        double y = config.field_31488.method_35391(random, context);
        double z = pos.getOffsetZ(16);

        int branchCount = (4 * 2 - 1) * 24; // The maximum size of a cave tunnel system, 112 by default

        // The amount of cave systems to generate in total, uses 3 randoms to bias the count downwards.
        // On average, 2-3 cave systems will spawn, but up to 15 can spawn rarely.
        int caveCount = random.nextInt(random.nextInt(random.nextInt(8) + 1) + 1);

        // Generate each cave system
        for(int i = 0; i < caveCount; ++i) {
            // Get the position to spawn

            // Each system generates 1 tunnel by default, more has a chance to be added later
            int tunnelCount = 1;

            // Each system has a 1/4 chance to generate a circular room
            //if (random.nextInt(4) == 0) {
            //    // The room has a variable size, from 1 to 7 units
            //    float size = 1.0F + random.nextFloat() * 6.0F;
            //    carveCaveRoom(chunk, random, mainChunkX, mainChunkZ, x, y, z, size);
            //    // Add 0 to 3 more tunnels coming out from the cave room, to make it more connected and variable
            //    tunnelCount += random.nextInt(4);
            //}

            // Generate each tunnel
            for (int j = 0; j < tunnelCount; j++) {
                // Calculate both the yaw and pitch.
                // The yaw represents the horizontal movement of the cave, as well as the horizontal size.
                // The pitch represents the vertical movement of the cave, as well as the vertical size.

                // Yaw takes up the entire unit circle, so the cave can move in all directions horizontally
                float yaw = random.nextFloat() * 6.2831855F;
                // Pitch goes from -0.125 to 0.125, so the cave can have a slight vertical shift up or down.
                float pitch = (random.nextFloat() - 0.5F) / 14.0F;
                // The width of the cave goes from 1 to 3
                float width = random.nextFloat() * random.nextFloat() * 8.0F + 1 + (random.nextFloat() * 2.5F);
                // 1/10 chance to drastically increase the size of the cave
                if (random.nextInt(10) == 0) {
                    width *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
                }

                // Reduce the branch count by 0% to 25%
                int maxBranches = branchCount - random.nextInt(branchCount / 4);

                // Start the recursive tunnel carving
               this.carveTunnels(context, config, chunk, posToBiome, carvingMask, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, width, yaw, pitch, 0, maxBranches);
                //carveRegion(context, config, chunk, posToBiome, random.nextLong(), seaLevel, x, y, z, 2, 0.5, carvingMask, ((context1, scaledRelativeX, scaledRelativeY, scaledRelativeZ, y1) -> false));
            }
        }
        return true;
    }

    protected void carveTunnels(CarverContext context, class_6108 config, Chunk chunk, Function<BlockPos, Biome> posToBiome, BitSet carvingMask, long seed, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float width, float yaw, float pitch, int branchStartIndex, int branchCount) {
        // Get the position for starting the next branch, from 25% of the total length to 75% to ensure it doesn't branch near the ends

        Random random = new Random(seed);

        int nextBranch = random.nextInt(branchCount / 2) + branchCount / 4;

        // 16% of tunnels are more steep than normal
        boolean steeperCave = random.nextInt(6) == 0;
        float yawChange = 0.0F;
        float pitchChange = 0.0F;

        for(int i = branchStartIndex; i < branchCount; ++i) {
            // Make the yaw the sin of the cave from [0, pi], making it larger in the middle and smaller at the edges.
            // The horizontal size of the cave ranges from [1.5, 1.5 + width].
            double scaledYaw = 1.5 + (double)(MathHelper.sin(3.1415927F * (float)i / (float)branchCount) * width);
            // Scale the pitch by 1.0. The nether carver uses 2.0.
            double scaledPitch = scaledYaw * 0.6;

            // Cosine of pitch is the delta of the horizontal movement.
            // the steeperCave boolean makes this much smaller through reducing the pitch, making the delta smaller, producing steep caves.
            float delta = MathHelper.cos(pitch);

            // Use trig to increment the position of the tunnel
            x += MathHelper.cos(yaw) * delta;
            y += MathHelper.sin(pitch);
            z += MathHelper.sin(yaw) * delta;

            // Modify the pitch based on the steeperCave value, and add the pitch change
            pitch *= steeperCave ? 0.98F : 0.55F;
            pitch += pitchChange * 0.1F;
            // Add the yaw change
            yaw += yawChange * 0.2F;
            // Scale the change values downwards for the next addition
            pitchChange *= 0.4F;
            yawChange *= 0.75F;
            // Add some more values onto the change values, to modify it for the next iteration
            pitchChange += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 1.5F;
            yawChange += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;

            // Split the cave off into 2 more tunnels and end this tunnel.
            // The width > 1.0 check makes it so only the first tunnel can split.
            if (i == nextBranch && width > 1.0F) {
                // Change the yaw by pi/2 with different sign for both tunnels, to split them off at a fork and make them go opposite ways.
                // Reduce the pitch by a factor of 3 to flatten out cave tunnel forks.
                this.carveTunnels(context, config, chunk, posToBiome, carvingMask, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, random.nextFloat() + random.nextInt(3), yaw - 1.5707964F, pitch / 3.0F, i, branchCount);
                this.carveTunnels(context, config, chunk, posToBiome, carvingMask, random.nextLong(), seaLevel, mainChunkX, mainChunkZ, x, y, z, random.nextFloat() + random.nextInt(3), yaw + 1.5707964F, pitch / 3.0F, i, branchCount);
                return;
            }

            // 25% of generation is skipped for a more random feeling
            if (random.nextInt(4) != 0) {
                // Carve the region at this position
                carveRegion(context, config, chunk, posToBiome, random.nextLong(), seaLevel, x, y, z, scaledYaw, scaledPitch, carvingMask, ((context1, scaledRelativeX, scaledRelativeY, scaledRelativeZ, y1) -> false));
            }
        }
    }


    //protected void carveCaveRoom(Chunk chunk, Random random, class_6108 config, int seaLevel, int mainChunkX, int mainChunkZ, double x, double y, double z, float yaw) {
    //    // Increase the size of the horizontal
    //    double scaledYaw = 1.5D + yaw;
    //    // Scale the vertical size to be 1/2 the horizontal size
    //    double scaledPitch = scaledYaw * 0.5;
//
    //    // Call the internal carveRegion function with the given yaw and pitch, as well as a slight x offset
    //    this.carveRegion(chunk, config, random, seaLevel, mainChunkX, mainChunkZ, x + 1.0, y, z, scaledYaw, scaledPitch);
    //}

    @Override
    public boolean shouldCarve(class_6108 config, Random random) {
        return random.nextFloat() <= config.probability;
    }
}
