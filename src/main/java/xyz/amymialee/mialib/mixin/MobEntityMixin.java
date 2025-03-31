package xyz.amymialee.mialib.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1508;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({class_1308.class})
public abstract class MobEntityMixin extends class_1309 {
   protected MobEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
      super(entityType, world);
   }

   @Inject(
      method = {"tryAttack"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void mialib$customAttacks(@NotNull class_1297 target, CallbackInfoReturnable<Boolean> cir) {
      if (target.method_5732()) {
         if (target instanceof class_1508) {
            target = ((class_1508)target).field_7007;
         }

         class_1799 main = this.method_6047();
         if (main.method_7909().mialib$attack(this.method_37908(), main, this, target)) {
            cir.setReturnValue(true);
         }
      }
   }
}
