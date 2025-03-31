package xyz.amymialee.amarite.mixin;

import net.minecraft.class_2846;
import net.minecraft.class_3222;
import net.minecraft.class_3244;
import net.minecraft.class_2846.class_2847;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_3244.class})
public class ServerPlayNetworkHandlerMixin {
   @Shadow
   public class_3222 field_14140;

   @Inject(
      method = {"onPlayerAction"},
      at = {@At("HEAD")}
   )
   private void arsenal$swapHands(@NotNull class_2846 packet, CallbackInfo ci) {
      if (packet.method_12363() == class_2847.field_12968) {
      }
   }
}
