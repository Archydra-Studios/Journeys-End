package azzy.fabric.journeysend;

import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class JourneysEndSounds {

    public static final SoundEvent WET_STEP = register("wet_step");
    public static final SoundEvent WET_PLACE = register("wet_place");
    public static final SoundEvent WET_BREAK = register("wet_break");

    public static final BlockSoundGroup MUD = new BlockSoundGroup(1F, 0.8F, WET_BREAK, WET_STEP, WET_PLACE, WET_BREAK, WET_STEP);

    private static SoundEvent register(String name) {
        Identifier id = JourneysEnd.id(name);
        return Registry.register(Registry.SOUND_EVENT, id, new SoundEvent(id));
    }
}
