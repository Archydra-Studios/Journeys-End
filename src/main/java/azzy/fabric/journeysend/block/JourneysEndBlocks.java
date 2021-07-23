package azzy.fabric.journeysend.block;

import azzy.fabric.incubus_core.datagen.BSJsonGen;
import azzy.fabric.incubus_core.datagen.LootGen;
import azzy.fabric.incubus_core.datagen.ModelJsonGen;
import azzy.fabric.incubus_core.datagen.RecipeJsonGen;
import azzy.fabric.journeysend.JourneysEndSounds;
import azzy.fabric.journeysend.feature.JourneysEndFeatures;
import com.google.common.collect.ImmutableSet;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static azzy.fabric.journeysend.JourneysEnd.*;

public class JourneysEndBlocks {

    public static FabricItemSettings genericSettings(Rarity rarity) {
        return new FabricItemSettings().group(NATURAL_RESOURCES).rarity(rarity);
    }

    public static final Block BLACK_MUSHROOM_STEM = registerBlock("black_mushroom_stem", new MushroomBlock(FabricBlockSettings.of(Material.NETHER_WOOD, MapColor.BLACK)), genericSettings(Rarity.COMMON), false);

    //Vibrant Veil
    public static final Block LUCENT_CAP = registerBlock("lucent_cap", new Block(FabricBlockSettings.copyOf(BLACK_MUSHROOM_STEM).mapColor(MapColor.LAPIS_BLUE).sounds(BlockSoundGroup.NETHER_SPROUTS)), genericSettings(Rarity.COMMON), false);
    public static final Block LUCENT_CAP_GLOWING = registerBlock("lucent_cap_glowing", new Block(FabricBlockSettings.copyOf(LUCENT_CAP).luminance(6)), genericSettings(Rarity.COMMON), false);
    public static final Block LUCENT_CAP_ARTERY = registerBlock("lucent_cap_artery", new Block(FabricBlockSettings.copyOf(LUCENT_CAP).luminance(8)), genericSettings(Rarity.COMMON), false);

    public static final Block SHIRO = registerBlock("shiro", new DynamicSpreadableBlock(FabricBlockSettings.copyOf(Blocks.DEEPSLATE).luminance(7).sounds(BlockSoundGroup.NYLIUM).ticksRandomly(), Blocks.DEEPSLATE.getDefaultState(), ImmutableSet.of(Blocks.DEEPSLATE, Blocks.COBBLED_DEEPSLATE), 0, 9, true), genericSettings(Rarity.COMMON), false);
    public static final Block LUCENT_MUSHROOM = registerBlock("lucent_mushroom", new MushroomPlantBlock(FabricBlockSettings.copyOf(Blocks.RED_MUSHROOM_BLOCK).mapColor(MapColor.BRIGHT_TEAL).nonOpaque().noCollision().luminance(10), () -> JourneysEndFeatures.LUCENT_CAP_TREE.getFeature()), genericSettings(Rarity.COMMON));
    public static final Block SHIMMERGRASS = registerBlock("shimmergrass", new UndergroundFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS).luminance(8).nonOpaque().noCollision()), genericSettings(Rarity.COMMON), false);
    public static final Block SHIMMERFERN = registerBlock("shimmerfern", new UndergroundFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS).luminance(8).nonOpaque().noCollision()), genericSettings(Rarity.COMMON), false);
    public static final Block GLIMMEROOT = registerBlock("glimmeroot", new HangingFernBlock(FabricBlockSettings.copyOf(Blocks.GRASS).luminance(8).nonOpaque().noCollision()), genericSettings(Rarity.COMMON), false);
    public static final Block RED_MUD = registerCubeBlock("red_mud", new SoulSandBlock(FabricBlockSettings.copyOf(Blocks.DIRT).sounds(JourneysEndSounds.MUD).velocityMultiplier(0.55F).jumpVelocityMultiplier(0.8F).breakByTool(FabricToolTags.SHOVELS, 1).breakByHand(false).allowsSpawning((state, world, pos, type) -> true).blockVision((state, world, pos) -> true).strength(2F)), genericSettings(Rarity.COMMON), true);
    public static final Block BLACK_MUD = registerCubeBlock("black_mud", new DynamicFallingBlock(0x251b17, true, true, false, 0f, 0f, null, FabricBlockSettings.copyOf(Blocks.DIRT).velocityMultiplier(0.9F).sounds(JourneysEndSounds.MUD).strength(4.75F).breakByTool(FabricToolTags.SHOVELS, 2).breakByHand(false)), genericSettings(Rarity.COMMON), true);

    //Ash biomes
    public static final Block DARK_ASH = registerBlock("dark_ash", new SandBlock(0x3e393f, FabricBlockSettings.copyOf(Blocks.SAND).breakByHand(false).breakByTool(FabricToolTags.SHOVELS, 2).strength(6F).sounds(BlockSoundGroup.SAND)), genericSettings(Rarity.COMMON));
    public static final Block WHITE_ASH = registerBlock("white_ash", new SandBlock(0xcdcac3, FabricBlockSettings.copyOf(Blocks.SAND).breakByHand(false).breakByTool(FabricToolTags.SHOVELS, 3).strength(11.5F).sounds(BlockSoundGroup.SAND)), genericSettings(Rarity.COMMON));
    public static final Block CINDER = registerCubeBlock("cinder", new DynamicFallingBlock(0xf4a222, true, true, false, 0.1F, 1f, () -> DamageSource.HOT_FLOOR, FabricBlockSettings.copyOf(DARK_ASH).luminance(3).postProcess((state, world, pos) -> true)), genericSettings(Rarity.COMMON), true);
    public static final Block SINTERED_DRIPSTONE = registerBlock("sintered_dripstone", new SinteredDripstoneBlock(FabricBlockSettings.copyOf(DARK_ASH).nonOpaque()), genericSettings(Rarity.COMMON));

    public static void init() {
    }

    @Environment(EnvType.CLIENT)
    public static void clientInit() {
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), LUCENT_MUSHROOM, SHIMMERGRASS, GLIMMEROOT, SHIMMERFERN);
        BlockRenderLayerMap.INSTANCE.putBlocks(RenderLayer.getCutoutMipped(), SINTERED_DRIPSTONE);
    }

    public static Block registerBlock(String name, Block item, Item.Settings settings, boolean genLoot) {
        Identifier id = new Identifier(MODID, name);
        Block block = Registry.register(Registry.BLOCK, id, item);
        Registry.register(Registry.ITEM, id, new BlockItem(block, settings));

        if (genLoot && DEV_ENV && REGEN_LOOT)
            LootGen.genSimpleBlockDropTable(METADATA, block);

        return block;
    }

    public static Block registerBlock(String name, Block item, Item.Settings settings) {
        return registerBlock(name, item, settings, true);
    }

    public static Block registerCubeBlock(String name, Block item, Item.Settings settings, boolean genLoot) {
        return registerGeneratedBlock(name, item, null, null, settings, SingletType.BLOCK, genLoot);
    }

    public static Block registerGeneratedBlock(String name, Block item, @Nullable Identifier parent, @Nullable Identifier texture, Item.Settings settings, SingletType type, boolean genLoot) {
        Identifier id = new Identifier(MODID, name);
        Block block = Registry.register(Registry.BLOCK, id, item);
        Registry.register(Registry.ITEM, id, new BlockItem(block, settings));

        if (DEV_ENV) {
            if (REGEN_BLOCKS) {
                Identifier texId = texture == null ? new Identifier(MODID, "block/" + name) : texture;

                switch (type) {
                    case BLOCK:
                        BSJsonGen.genBlockBS(METADATA, id, "block/");
                        ModelJsonGen.genBlockJson(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case SLAB:
                        BSJsonGen.genSlabBS(METADATA, id, Objects.requireNonNull(parent), "block/");
                        ModelJsonGen.genSlabJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case STAIRS:
                        BSJsonGen.genStairsBS(METADATA, id, "block/");
                        ModelJsonGen.genStairJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case PILLAR:
                        //BSJsonGen.genStairsBS(METADATA, id, "block/");
                        //ModelJsonGen.genStairJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case WALL:
                        BSJsonGen.genWallBS(METADATA, id, "block/");
                        ModelJsonGen.genWallJsons(METADATA, texId, new Identifier(MODID, name), "");
                        break;
                    case FENCE:
                        break;
                    default:
                }
            }
            if (genLoot && REGEN_LOOT) {
                LootGen.genSimpleBlockDropTable(METADATA, block);
            }
        }

        return block;
    }

    public static Block[] registerBuildingBlocks(String baseName, FabricBlockSettings blockSettings, Item.Settings itemSettings, Item baseIngredient, boolean nines) {

        Block parent = registerBlock(baseName, new Block(blockSettings), itemSettings);

        Block[] blocks = new Block[]{
                parent,
                registerBlock(baseName + "_slab", new SlabBlock(blockSettings), itemSettings),
                registerBlock(baseName + "_stairs", new AltStairsBlock(parent, blockSettings), itemSettings),
                registerBlock(baseName + "_wall", new WallBlock(blockSettings), itemSettings)
        };

        if (DEV_ENV) {
            Identifier texId = new Identifier(MODID, "block/" + baseName);
            Identifier parentId = new Identifier(MODID, baseName);

            BSJsonGen.genBlockBS(METADATA, parentId, "block/");
            BSJsonGen.genSlabBS(METADATA, new Identifier(MODID, baseName + "_slab"), parentId, "block/");
            BSJsonGen.genStairsBS(METADATA, new Identifier(MODID, baseName + "_stairs"), "block/");
            BSJsonGen.genWallBS(METADATA, new Identifier(MODID, baseName + "_wall"), "block/");
            ModelJsonGen.genBlockJson(METADATA, texId, new Identifier(MODID, baseName), "");
            ModelJsonGen.genSlabJsons(METADATA, texId, new Identifier(MODID, baseName + "_slab"), "");
            ModelJsonGen.genStairJsons(METADATA, texId, new Identifier(MODID, baseName + "_stairs"), "");
            ModelJsonGen.genWallJsons(METADATA, texId, new Identifier(MODID, baseName + "_wall"), "");
        }

        if (baseIngredient != null && REGEN_RECIPES)
            registerBuildingRecipes(baseName, blocks, baseIngredient, nines);

        return blocks;
    }

    public static void registerBuildingRecipes(String baseName, Block[] blocks, Item baseIngredient, boolean nines) {
        if (baseIngredient != Items.AIR) {
            if (nines) {
                RecipeJsonGen.gen3x3Recipe(METADATA, baseName, baseIngredient, blocks[0].asItem(), 9);
            } else {
                RecipeJsonGen.gen2x2Recipe(METADATA, baseName, baseIngredient, blocks[0].asItem(), 4);
            }
        }
        RecipeJsonGen.genSlabRecipe(METADATA, baseName + "_slabs", blocks[0].asItem(), blocks[1].asItem(), 6);
        RecipeJsonGen.genStairsRecipe(METADATA, baseName + "_stairs", blocks[0].asItem(), blocks[2].asItem(), 8);
        RecipeJsonGen.genWallRecipe(METADATA, baseName + "_walls", blocks[0].asItem(), blocks[3].asItem(), 6);
    }

    private static <T extends BlockEntity> BlockEntityType<T> registerEntity(String name, FabricBlockEntityTypeBuilder.Factory<T> item, Block block) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(MODID, name), FabricBlockEntityTypeBuilder.create(item, block).build(null));
    }

    enum SingletType {
        BLOCK,
        SLAB,
        STAIRS,
        PILLAR,
        WALL,
        FENCE,
        NONE
    }

    private enum WallType {
        WALL,
        FENCE,
        NONE
    }
}