package azzy.fabric.journeysend.mixin;

import net.minecraft.entity.FallingBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FallingBlockEntity.class)
public interface FallingBlockDestroyAccessor {

    @Accessor
    void setDestroyedOnLanding(boolean destroy);
}
