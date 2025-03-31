package xyz.amymialee.mialib.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_109;
import net.minecraft.class_1269;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({class_109.class})
public class EnchantRandomlyLootFunctionMixin {
   @WrapOperation(
      method = {"method_26267"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/enchantment/Enchantment;isAcceptableItem(Lnet/minecraft/item/ItemStack;)Z"
      )}
   )
   private static boolean mialib$itemAllows(class_1887 enchantment, @NotNull class_1799 stack, Operation<Boolean> original) {
      class_1269 result = stack.method_7909().mialib$checkEnchantment(enchantment.field_9083, enchantment);
      return result != null && result != class_1269.field_5811 ? result == class_1269.field_5812 : (Boolean)original.call(new Object[]{enchantment, stack});
   }
}
