package azzy.fabric.journeysend;

import azzy.fabric.incubus_core.datagen.Metadata;
import azzy.fabric.journeysend.biome.JourneysEndBiomes;
import azzy.fabric.journeysend.block.JourneysEndBlocks;
import azzy.fabric.journeysend.feature.JourneysEndFeatures;
import azzy.fabric.journeysend.item.JourneysEndItems;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

public class JourneysEnd implements ModInitializer {

    public static final String MODID = "journeysend";
    public static final Metadata METADATA = new Metadata(MODID);

    public static final ItemGroup NATURAL_RESOURCES = FabricItemGroupBuilder.build(id("natural_resources"), () -> new ItemStack(JourneysEndBlocks.LUCENT_CAP_ARTERY));

    public static final boolean DEV_ENV = FabricLoader.getInstance().isDevelopmentEnvironment();
    public static final boolean REGEN_RECIPES = true, REGEN_ITEMS = false, REGEN_BLOCKS = true, REGEN_LOOT = true;

    @Override
    public void onInitialize() {
        JourneysEndBlocks.init();
        JourneysEndItems.init();
        JourneysEndFeatures.init();
        JourneysEndBiomes.init();
    }

    public static Identifier id(String path) {
        return new Identifier(MODID, path);
    }

}
