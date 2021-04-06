package azzy.fabric.journeysend.biome;

import azzy.fabric.journeysend.feature.JourneysEndFeatures;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredStructureFeatures;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilders;

public class VibrantVeilBiome {

    public static final Biome VIBRANT_VEIL = createBiome();

    private static Biome createBiome() {
        Biome.Builder biome = new Biome.Builder();
        GenerationSettings.Builder generation = new GenerationSettings.Builder();
        SpawnSettings.Builder spawns = new SpawnSettings.Builder();
        BiomeEffects.Builder effects = new BiomeEffects.Builder();

        generation.surfaceBuilder(ConfiguredSurfaceBuilders.SWAMP);
        generation.structureFeature(ConfiguredStructureFeatures.RUINED_PORTAL);

        generation.feature(GenerationStep.Feature.UNDERGROUND_ORES, JourneysEndFeatures.BLACK_MUD_VEINS.getFeature());

        DefaultBiomeFeatures.addLandCarvers(generation);
        DefaultBiomeFeatures.addMineables(generation);
        DefaultBiomeFeatures.addAmethystGeodes(generation);
        DefaultBiomeFeatures.addDefaultOres(generation);
        DefaultBiomeFeatures.addDefaultDisks(generation);
        DefaultBiomeFeatures.addDefaultUndergroundStructures(generation);


        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.LUCENT_CAP_GIANT.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.SHIRO_PATCH.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.SHIMMERGRASS_PATCH.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.SHIMMERFERN_PATCH.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.GLIMMEROOT_PATCH.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.LUCENT_CAP.getFeature());
        generation.feature(GenerationStep.Feature.VEGETAL_DECORATION, JourneysEndFeatures.LUCENT_CAP_TINY.getFeature());

        DefaultBiomeFeatures.addBatsAndMonsters(spawns);

        effects.fogColor(0xe10112e);
        effects.foliageColor(0x2affb6);
        effects.waterColor(0x10112e);
        effects.waterFogColor(0x3e7fd5);
        effects.skyColor(0x3e7fd5);

        biome.spawnSettings(spawns.build());
        biome.generationSettings(generation.build());
        biome.precipitation(Biome.Precipitation.RAIN);
        biome.category(Biome.Category.UNDERGROUND);
        biome.downfall(1.0F);
        biome.temperature(0.8F);
        biome.scale(0.1F);
        biome.depth(0.2F);
        biome.effects(effects.build());
        return biome.build();
    }
}
