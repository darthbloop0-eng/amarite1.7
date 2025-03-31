package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2428;
import net.minecraft.class_2680;
import net.minecraft.class_2766;
import net.minecraft.class_3414;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

@Mixin({class_2428.class})
public class NoteBlockMixin {
   @WrapOperation(
      method = {"onSyncedBlockEvent"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/block/enums/Instrument;getSound()Lnet/minecraft/sound/SoundEvent;"
      )}
   )
   @Nullable
   private class_3414 amarite$squeak(class_2766 instance, Operation<class_3414> original, class_2680 state, @NotNull class_1937 world, @NotNull class_2338 pos) {
      return world.method_8320(pos.method_10074()).method_27852(AmariteBlocks.WIN_PLUSHIE)
         ? AmariteSoundEvents.WIN_PLUSHIE
         : (class_3414)original.call(new Object[]{instance});
   }
}
