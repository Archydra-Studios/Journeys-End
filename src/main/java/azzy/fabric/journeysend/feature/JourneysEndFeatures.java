package azzy.fabric.journeysend.feature;

import azzy.fabric.journeysend.JourneysEnd;
import azzy.fabric.journeysend.block.JourneysEndBlocks;
import azzy.fabric.journeysend.feature.config.JourneyShroomFeatureConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.MushroomBlock;
import net.minecraft.class_6005;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.VerticalSurfaceType;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.decorator.CaveSurfaceDecoratorConfig;
import net.minecraft.world.gen.decorator.Decorator;
import net.minecraft.world.gen.decorator.RangeDecoratorConfig;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.placer.SimpleBlockPlacer;
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JourneysEndFeatures {

    public static Feature<JourneyShroomFeatureConfig> CAMPANULATE_MUSHROOM_CONFIG;
    public static RegistryFeature<?, ?> BLACK_MUD_VEINS;
    public static RegistryFeature<?, ?> LUCENT_CAP, LUCENT_CAP_TINY, LUCENT_CAP_TREE, LUCENT_CAP_GIANT, LUCENT_VEGETATION, SHIMMERGRASS_PATCH, SHIMMERFERN_PATCH, GLIMMEROOT_PATCH, SHIRO_PATCH;
    public static RegistryFeature<?, ?> DARK_ASH_FLOOR, ASH_VEGETATION;

    public static void init() {
        CAMPANULATE_MUSHROOM_CONFIG = register("campanulate_mushroom", new CampanulateMushroomFeature(JourneyShroomFeatureConfig.CODEC));

        BLACK_MUD_VEINS = register("black_mud_veins", Feature.ORE.configure(new OreFeatureConfig(OreFeatureConfig.Rules.BASE_STONE_OVERWORLD, JourneysEndBlocks.BLACK_MUD.getDefaultState(), 64)).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeat(14));

        //Vibrant Veil
        LUCENT_CAP = register("lucent_cap", CAMPANULATE_MUSHROOM_CONFIG.configure(new JourneyShroomFeatureConfig(States.BLACK_MUSHROOM_STEM, UniformIntProvider.create(2, 5), UniformIntProvider.create(2, 5), 3, Collections.emptyList(), Arrays.asList(States.LUCENT_CAP, States.LUCENT_CAP_GLOWING, States.LUCENT_CAP_ARTERY), Arrays.asList(1F, 0.15F, 0.003F))).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeat(2));
        LUCENT_CAP_TINY = register("lucent_cap_tiny", CAMPANULATE_MUSHROOM_CONFIG.configure(new JourneyShroomFeatureConfig(States.BLACK_MUSHROOM_STEM, UniformIntProvider.create(1, 4), UniformIntProvider.create(1, 2), 3, Collections.emptyList(), Arrays.asList(States.LUCENT_CAP, States.LUCENT_CAP_GLOWING, States.LUCENT_CAP_ARTERY), Arrays.asList(1F, 0.15F, 0.004F))).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().applyChance(10).repeatRandomly(12));
        LUCENT_CAP_TREE = register("lucent_cap_tree", CAMPANULATE_MUSHROOM_CONFIG.configure(new JourneyShroomFeatureConfig(States.BLACK_MUSHROOM_STEM, UniformIntProvider.create(1, 4), UniformIntProvider.create(2, 4), 3, Collections.emptyList(), Arrays.asList(States.LUCENT_CAP, States.LUCENT_CAP_GLOWING, States.LUCENT_CAP_ARTERY), Arrays.asList(1F, 0.15F, 0.003F))).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0));
        LUCENT_CAP_GIANT = register("lucent_cap_giant", CAMPANULATE_MUSHROOM_CONFIG.configure(new JourneyShroomFeatureConfig(States.BLACK_MUSHROOM_STEM, UniformIntProvider.create(5, 9), UniformIntProvider.create(4, 8), 12, Collections.emptyList(), Arrays.asList(States.LUCENT_CAP, States.LUCENT_CAP_GLOWING, States.LUCENT_CAP_ARTERY), Arrays.asList(1F, 0.15F, 0.01F))).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeat(4));

        LUCENT_VEGETATION = register("lucent_vegetation", Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig((new WeightedBlockStateProvider(new class_6005.class_6006<BlockState>().method_34975(Blocks.AIR.getDefaultState(), 24).method_34975(JourneysEndBlocks.SHIMMERGRASS.getDefaultState(), 70).method_34975(JourneysEndBlocks.SHIMMERFERN.getDefaultState(), 28).method_34975(JourneysEndBlocks.LUCENT_MUSHROOM.getDefaultState(), 15).method_34975(Blocks.BROWN_MUSHROOM.getDefaultState(), 1).method_34974())))));
        SHIMMERGRASS_PATCH = register("shimmergrass_patch", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(JourneysEndBlocks.SHIMMERGRASS.getDefaultState()), SimpleBlockPlacer.INSTANCE).cannotProject().tries(40).build()).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(ConfiguredFeatures.Decorators.BOTTOM_TO_60).spreadHorizontally().repeat(50));
        SHIMMERFERN_PATCH = register("shimmerfern_patch", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(JourneysEndBlocks.SHIMMERFERN.getDefaultState()), SimpleBlockPlacer.INSTANCE).cannotProject().tries(30).build()).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(ConfiguredFeatures.Decorators.BOTTOM_TO_60).spreadHorizontally().repeat(20));
        GLIMMEROOT_PATCH = register("glimmeroot_patch", Feature.RANDOM_PATCH.configure(new RandomPatchFeatureConfig.Builder(new SimpleBlockStateProvider(JourneysEndBlocks.GLIMMEROOT.getDefaultState()), SimpleBlockPlacer.INSTANCE).cannotProject().tries(12).build()).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.CEILING, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeatRandomly(5));
        SHIRO_PATCH = register("shiro_patch", Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(BlockTags.LUSH_PLANTS_REPLACEABLE.getId(), new SimpleBlockStateProvider(JourneysEndBlocks.SHIRO.getDefaultState()), () -> LUCENT_VEGETATION.getFeature(), VerticalSurfaceType.FLOOR, UniformIntProvider.create(1, 1), 0.0F, 6, 0.65F, UniformIntProvider.create(0, 3), 0.3F)).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeat(28));

        //Cinder Pipes
        ASH_VEGETATION = register("ash_vegetation", Feature.SIMPLE_BLOCK.configure(new SimpleBlockFeatureConfig(new WeightedBlockStateProvider(new class_6005.class_6006<BlockState>().method_34975(Blocks.AIR.getDefaultState(), 1)))));
        DARK_ASH_FLOOR = register("dark_ash_floor", Feature.VEGETATION_PATCH.configure(new VegetationPatchFeatureConfig(BlockTags.LUSH_PLANTS_REPLACEABLE.getId(), new SimpleBlockStateProvider(JourneysEndBlocks.DARK_ASH.getDefaultState()), () -> ASH_VEGETATION.getFeature(), VerticalSurfaceType.FLOOR, UniformIntProvider.create(2, 5), 0.3F, 9, 0.65F, UniformIntProvider.create(1, 4), 0.35F)).decorate(Decorator.CAVE_SURFACE.configure(new CaveSurfaceDecoratorConfig(VerticalSurfaceType.FLOOR, 12))).range(Decorators.BOTTOM_TO_0).spreadHorizontally().repeat(256));
    }

    private static <C extends FeatureConfig, F extends Feature<C>> F register(String name, F feature) {
        return Registry.register(Registry.FEATURE, JourneysEnd.id(name), feature);
    }

    private static <FC extends FeatureConfig> RegistryFeature<FC, ?> register(String id, ConfiguredFeature<FC, ?> configuredFeature) {
        Identifier registeredID = JourneysEnd.id(id);
        return new RegistryFeature<>(Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, registeredID, configuredFeature), RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, registeredID));
    }

    public static class RegistryFeature<T extends FeatureConfig, V extends Feature<T>> {
        private final ConfiguredFeature<T, V> feature;
        private final RegistryKey<ConfiguredFeature<?, ?>> registryKey;

        public RegistryFeature(ConfiguredFeature<T, V> feature, RegistryKey<ConfiguredFeature<?, ?>> key) {
            this.feature = feature;
            this.registryKey = key;
        }

        public ConfiguredFeature<T, V> getFeature() {
            return feature;
        }

        public RegistryKey<ConfiguredFeature<?, ?>> getRegistryKey() {
            return registryKey;
        }

        public Identifier getID() {
            return registryKey.getValue();
        }
    }

    public static class States {

        public static final BlockState BLACK_MUSHROOM_STEM = JourneysEndBlocks.BLACK_MUSHROOM_STEM.getDefaultState().with(MushroomBlock.UP, false).with(MushroomBlock.DOWN, false);

        public static final BlockState LUCENT_CAP = JourneysEndBlocks.LUCENT_CAP.getDefaultState();
        public static final BlockState LUCENT_CAP_GLOWING = JourneysEndBlocks.LUCENT_CAP_GLOWING.getDefaultState();
        public static final BlockState LUCENT_CAP_ARTERY = JourneysEndBlocks.LUCENT_CAP_ARTERY.getDefaultState();
    }

    public static class Decorators {
        public static final RangeDecoratorConfig BOTTOM_TO_0 = new RangeDecoratorConfig(YOffset.getBottom(), YOffset.fixed(0));
    }
}
