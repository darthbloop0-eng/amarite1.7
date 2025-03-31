package xyz.amymialee.amarite.mixin.client;

import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_2561;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_897;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.amarite.registry.AmariteEnchantments;

@Mixin({class_897.class})
public class EntityRendererMixin<T extends class_1297> {
   @Inject(
      method = {"renderLabelIfPresent"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$hideNames(T entity, class_2561 text, class_4587 matrices, class_4597 vertexConsumers, int light, CallbackInfo ci) {
      if (entity instanceof class_1309 living) {
         class_1799 mask = MaskItem.getWornMask(living);
         if (class_1890.method_8225(AmariteEnchantments.CONCEALMENT, mask) > 0) {
            ci.cancel();
         }
      }
   }
}
