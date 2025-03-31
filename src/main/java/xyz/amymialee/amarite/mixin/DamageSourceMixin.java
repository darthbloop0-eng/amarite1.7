package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1282;
import net.minecraft.class_1284;
import net.minecraft.class_1285;
import net.minecraft.class_1286;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1890;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.amarite.registry.AmariteEnchantments;

@Mixin({class_1282.class, class_1285.class, class_1284.class, class_1286.class})
public class DamageSourceMixin {
   @WrapOperation(
      method = {"getDeathMessage"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getDisplayName()Lnet/minecraft/text/Text;"
      )}
   )
   private class_2561 nameless$removeName(class_1309 instance, Operation<class_2561> original) {
      class_1799 mask = MaskItem.getWornMask(instance);
      return (class_2561)(class_1890.method_8225(AmariteEnchantments.ANONYMITY, mask) > 0
         ? class_2561.method_43470("Somebody")
         : (class_2561)original.call(new Object[]{instance}));
   }
}
