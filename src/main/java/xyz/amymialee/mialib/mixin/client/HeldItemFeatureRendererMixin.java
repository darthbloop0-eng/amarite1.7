package xyz.amymialee.mialib.mixin.client;

import net.minecraft.class_1268;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_989;
import net.minecraft.class_809.class_811;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({class_989.class})
public abstract class HeldItemFeatureRendererMixin {
   @Inject(
      method = {"renderItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void mialib$hideItem(
      class_1309 entity,
      @NotNull class_1799 stack,
      class_811 transformationMode,
      class_1306 arm,
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      CallbackInfo ci
   ) {
      if (stack.method_7909().mialib$shouldHideInHand(entity, entity.method_6068() == arm ? class_1268.field_5808 : class_1268.field_5810, stack)) {
         ci.cancel();
      }
   }
}
