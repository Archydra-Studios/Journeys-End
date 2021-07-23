package azzy.fabric.journeysend.biome;

import azzy.fabric.journeysend.JourneysEnd;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class JourneysEndBiomes {

    public static final RegistryKey<Biome> CINDER_PIPES_KEY = RegistryKey.of(Registry.BIOME_KEY, JourneysEnd.id("cinder_pipes"));

    public static final RegistryKey<Biome> VIBRANT_VEIL_KEY = RegistryKey.of(Registry.BIOME_KEY, JourneysEnd.id("vibrant_veil"));

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, CINDER_PIPES_KEY.getValue(), CinderPipesBiome.CINDER_PIPES);
        Registry.register(BuiltinRegistries.BIOME, VIBRANT_VEIL_KEY.getValue(), VibrantVeilBiome.VIBRANT_VEIL);
    }
}
