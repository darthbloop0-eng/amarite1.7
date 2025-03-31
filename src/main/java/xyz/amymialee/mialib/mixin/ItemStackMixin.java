package xyz.amymialee.mialib.mixin;

import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1799.class})
public abstract class ItemStackMixin {
   @Shadow
   public abstract class_1792 method_7909();

   @Inject(
      method = {"getName"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void getName(CallbackInfoReturnable<class_2561> cir) {
      int color = this.method_7909().mialib$getNameColor((class_1799)this);
      if (color != -1) {
         class_2561 text = (class_2561)cir.getReturnValue();
         if (text.method_10866().method_10973() == null) {
            cir.setReturnValue(text.mialib$withColor(color));
         }
      }
   }
}
