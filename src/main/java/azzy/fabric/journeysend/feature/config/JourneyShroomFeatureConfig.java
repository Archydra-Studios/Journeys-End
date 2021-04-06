package azzy.fabric.journeysend.feature.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.feature.FeatureConfig;

import java.util.List;

public class JourneyShroomFeatureConfig implements FeatureConfig {

    public static final Codec<JourneyShroomFeatureConfig> CODEC = RecordCodecBuilder.create(configInstance -> configInstance.group(
            BlockState.CODEC.fieldOf("stem").forGetter(config -> config.stem),
            UniformIntProvider.CODEC.fieldOf("height").forGetter(config -> config.height),
            UniformIntProvider.CODEC.fieldOf("radius").forGetter(config -> config.radius),
            Codec.INT.fieldOf("modifier").forGetter(config -> config.modifier),
            BlockState.CODEC.listOf().fieldOf("cap").forGetter(config -> config.validGround),
            BlockState.CODEC.listOf().fieldOf("ground").forGetter(config -> config.capStates),
            Codec.floatRange(0, 1).listOf().fieldOf("terChance").forGetter(config -> config.capChances)
    ).apply(configInstance, JourneyShroomFeatureConfig::new));

    public final BlockState stem;
    public final UniformIntProvider height, radius;
    public final int modifier;
    public final List<BlockState> validGround, capStates;
    public final List<Float> capChances;


    public JourneyShroomFeatureConfig(BlockState stem, UniformIntProvider height, UniformIntProvider radius, int modifier, List<BlockState> validGround, List<BlockState> capStates, List<Float> capChances) {
        this.stem = stem;
        this.height = height;
        this.radius = radius;
        this.modifier = modifier;
        this.validGround = validGround;
        this.capStates = capStates;
        this.capChances = capChances;
    }
}
