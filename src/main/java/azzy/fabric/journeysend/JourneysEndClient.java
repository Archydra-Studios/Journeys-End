package azzy.fabric.journeysend;

import azzy.fabric.journeysend.block.JourneysEndBlocks;
import net.fabricmc.api.ClientModInitializer;

public class JourneysEndClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        JourneysEndBlocks.clientInit();
    }
}
