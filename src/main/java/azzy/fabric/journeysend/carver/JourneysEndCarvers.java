package azzy.fabric.journeysend.carver;

import azzy.fabric.journeysend.JourneysEnd;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.Blocks;
import net.minecraft.class_6108;
import net.minecraft.class_6122;
import net.minecraft.class_6124;
import net.minecraft.util.math.floatprovider.FloatProvider;
import net.minecraft.util.math.floatprovider.UniformFloatProvider;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.carver.Carver;
import net.minecraft.world.gen.carver.CarverConfig;
import net.minecraft.world.gen.carver.CarverDebugConfig;
import net.minecraft.world.gen.carver.ConfiguredCarver;

public class JourneysEndCarvers {

    public static final Codec<CarverConfig> STANDARD_CARVER_CODED = RecordCodecBuilder.create((carverConfigInstance -> {
        return carverConfigInstance.group(
                Codec.FLOAT.fieldOf("probability").forGetter(carver -> carver.probability),
                class_6122.field_31540.fieldOf("y").forGetter(carver -> carver.field_31488),
                FloatProvider.VALUE_CODEC.fieldOf("yScale").forGetter(carver -> carver.field_31489),
                YOffset.OFFSET_CODEC.fieldOf("lava_level").forGetter(carver -> carver.field_31490),
                CarverDebugConfig.CODEC.optionalFieldOf("debug_settings", CarverDebugConfig.DEFAULT).forGetter(carver -> carver.debugConfig)
        ).apply(carverConfigInstance, CarverConfig::new);
    }));

    public static final Carver<CarverConfig> TUBE_CAVE_CARVER = register("tube_cave_carver", new AshTroughCarver(class_6108.field_31491));

    public static final ConfiguredCarver<?> ASH_TROUGH_CARVER = register("ash_trough_carver", TUBE_CAVE_CARVER.configure(new class_6108(0.2132334F, class_6124.method_35396(YOffset.aboveBottom(3), YOffset.fixed(-8)), UniformFloatProvider.create(0.1F, 0.9F), YOffset.aboveBottom(11), CarverDebugConfig.create(false, Blocks.CRIMSON_BUTTON.getDefaultState()), UniformFloatProvider.create(0.3F, 1.8F), UniformFloatProvider.create(0.5F, 1.8F), UniformFloatProvider.create(-1.0F, 0.0F))));

    public static void init() {
    }

    @SuppressWarnings("unchecked")
    public static <T extends CarverConfig> Carver<T> register(String name, Carver<?> carver) {
        return (Carver<T>) Registry.register(Registry.CARVER, JourneysEnd.id(name), carver);
    }

    public static ConfiguredCarver<?> register(String name, ConfiguredCarver<?> carver) {
        return BuiltinRegistries.add(BuiltinRegistries.CONFIGURED_CARVER, JourneysEnd.id(name), carver);
    }
}
