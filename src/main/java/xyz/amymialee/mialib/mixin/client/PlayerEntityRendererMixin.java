package xyz.amymialee.mialib.mixin.client;

import net.minecraft.class_1007;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_742;
import net.minecraft.class_572.class_573;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1007.class})
public class PlayerEntityRendererMixin {
   @Inject(
      method = {"getArmPose"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void mialib$hideOrPose(@NotNull class_742 player, class_1268 hand, CallbackInfoReturnable<class_573> cir) {
      class_1799 stack = player.method_5998(hand);
      if (stack.method_7909().mialib$shouldHideInHand(player, hand, stack)) {
         cir.setReturnValue(class_573.field_3409);
      } else {
         class_573 pose = stack.method_7909().mialib$pose(player, hand, stack);
         if (pose != null) {
            cir.setReturnValue(pose);
         }
      }
   }
}
