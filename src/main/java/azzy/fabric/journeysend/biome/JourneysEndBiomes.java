package azzy.fabric.journeysend.biome;

import azzy.fabric.journeysend.JourneysEnd;
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes;
import net.fabricmc.fabric.api.biome.v1.OverworldClimate;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.biome.Biome;

public class JourneysEndBiomes {

    public static final RegistryKey<Biome> ASH_TROUGH_KEY = RegistryKey.of(Registry.BIOME_KEY, JourneysEnd.id("ash_trough"));

    public static final RegistryKey<Biome> VIBRANT_VEIL_KEY = RegistryKey.of(Registry.BIOME_KEY, JourneysEnd.id("vibrant_veil"));

    public static void init() {
        Registry.register(BuiltinRegistries.BIOME, ASH_TROUGH_KEY.getValue(), AshTroughBiome.ASH_TROUGH);
        Registry.register(BuiltinRegistries.BIOME, VIBRANT_VEIL_KEY.getValue(), VibrantVeilBiome.VIBRANT_VEIL);
    }
}
