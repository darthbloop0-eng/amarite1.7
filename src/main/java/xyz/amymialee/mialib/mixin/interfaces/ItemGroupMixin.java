package xyz.amymialee.mialib.mixin.interfaces;

import java.util.function.Supplier;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.mialib.interfaces.MItemGroup;

@Mixin({class_1761.class})
public class ItemGroupMixin implements MItemGroup {
   @Unique
   private Supplier<class_1799> iconSupplier;

   @Inject(
      method = {"getIcon"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void mialib$constantIcon(CallbackInfoReturnable<class_1799> cir) {
      if (this.iconSupplier != null) {
         cir.setReturnValue(this.iconSupplier.get());
      }
   }

   @Override
   public Supplier<class_1799> mialib$hasConstantIcon() {
      return this.iconSupplier;
   }

   @Override
   public class_1761 mialib$setConstantIcon(Supplier<class_1799> constantIcon) {
      this.iconSupplier = constantIcon;
      return (class_1761)this;
   }
}
