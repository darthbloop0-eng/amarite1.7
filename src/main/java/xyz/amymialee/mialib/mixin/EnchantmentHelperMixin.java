package xyz.amymialee.mialib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.class_1269;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1890.class})
public class EnchantmentHelperMixin {
   @Inject(
      method = {"getLevel"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private static void mialib$enchantAdditions(class_1887 enchantment, @NotNull class_1799 stack, @NotNull CallbackInfoReturnable<Integer> cir) {
      Integer level = (Integer)cir.getReturnValue();
      level = stack.method_7909().mialib$enchantLevel(enchantment, stack, level);
      cir.setReturnValue(level);
   }

   @WrapOperation(
      method = {"getPossibleEntries"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/enchantment/Enchantment;isAvailableForRandomSelection()Z"
      )}
   )
   private static boolean mialib$storeEnchants(
      class_1887 enchantment, @NotNull Operation<Boolean> original, @Share("storedEnchantment") @NotNull LocalRef<class_1887> storedEnchantment
   ) {
      storedEnchantment.set(enchantment);
      return (Boolean)original.call(new Object[]{enchantment});
   }

   @WrapOperation(
      method = {"getPossibleEntries"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/enchantment/EnchantmentTarget;isAcceptableItem(Lnet/minecraft/item/Item;)Z"
      )}
   )
   private static boolean mialib$itemAllows(
      class_1886 enchantmentTarget,
      @NotNull class_1792 item,
      Operation<Boolean> original,
      @Share("storedEnchantment") @NotNull LocalRef<class_1887> storedEnchantment
   ) {
      class_1887 stored = (class_1887)storedEnchantment.get();
      class_1269 result = item.mialib$checkEnchantment(enchantmentTarget, stored);
      return result != null && result != class_1269.field_5811
         ? result == class_1269.field_5812
         : (Boolean)original.call(new Object[]{enchantmentTarget, item});
   }
}
