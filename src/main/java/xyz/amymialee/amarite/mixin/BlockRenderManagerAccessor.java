package xyz.amymialee.amarite.mixin;

import net.minecraft.class_776;
import net.minecraft.class_778;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_776.class})
public interface BlockRenderManagerAccessor {
   @Accessor("blockModelRenderer")
   class_778 getModelRenderer();
}
