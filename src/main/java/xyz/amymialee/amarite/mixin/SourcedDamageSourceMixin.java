package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1284;
import net.minecraft.class_1285;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.amarite.registry.AmariteEnchantments;

@Mixin({class_1285.class, class_1284.class})
public class SourcedDamageSourceMixin extends DamageSourceMixin {
   @WrapOperation(
      method = {"getDeathMessage"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/Entity;getDisplayName()Lnet/minecraft/text/Text;"
      )}
   )
   private class_2561 nameless$removeName(class_1297 instance, Operation<class_2561> original) {
      if (instance instanceof class_1309 living) {
         class_1799 mask = MaskItem.getWornMask(living);
         if (class_1890.method_8225(AmariteEnchantments.ANONYMITY, mask) > 0) {
            return class_2561.method_43470("Somebody");
         }
      }

      return (class_2561)original.call(new Object[]{instance});
   }
}
