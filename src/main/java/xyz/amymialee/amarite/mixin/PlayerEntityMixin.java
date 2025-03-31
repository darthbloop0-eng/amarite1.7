package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_243;
import net.minecraft.class_3419;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.BuddedComponent;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.cca.LongswordAccumulateComponent;
import xyz.amymialee.amarite.cca.LongswordDashComponent;
import xyz.amymialee.amarite.cca.LongswordDoubleDashComponent;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

@Mixin({class_1657.class})
public abstract class PlayerEntityMixin extends class_1309 {
   protected PlayerEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
      super(entityType, world);
   }

   @Inject(
      method = {"damage"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$noDamage(class_1282 source, float amount, CallbackInfoReturnable<Boolean> cir) {
      if (((LongswordDashComponent)Amarite.DASH.get(this)).isDashing() || ((LongswordDoubleDashComponent)Amarite.DOUBLE_DASH.get(this)).isDashing()) {
         cir.setReturnValue(false);
      }

      ((DiscComponent)Amarite.DISC.get(this)).chargeOrbit(amount);
   }

   @WrapOperation(
      method = {"applyDamage"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;modifyAppliedDamage(Lnet/minecraft/entity/damage/DamageSource;F)F"
      )}
   )
   private float amarite$swordBlock(class_1657 player, class_1282 source, float amount, @NotNull Operation<Float> original) {
      float base = (Float)original.call(new Object[]{player, source, amount});
      class_1799 stack = player.method_6047();
      if (!this.field_6002.method_8608()
         && !source.method_5504()
         && stack.method_31574(AmariteItems.AMARITE_LONGSWORD)
         && player.mialib$isUsing()
         && !((LongswordAccumulateComponent)Amarite.ACCUMULATE.get(player)).accumulateActive) {
         class_243 damagePos = source.method_5510();
         if (damagePos != null) {
            class_243 rotVec = this.method_5828(1.0F);
            class_243 difference = damagePos.method_1035(this.method_33571()).method_1029();
            double angle = difference.method_1026(rotVec);
            if (!(angle < -1.0) && angle < -0.35) {
               this.field_6002
                  .method_43129(
                     null, this, AmariteSoundEvents.SWORD_BLOCK, class_3419.field_15251, 1.0F, 1.0F + this.field_6002.field_9229.method_43057() * 0.4F
                  );
               AmariteLongswordItem.getMode(player, stack).absorbDamage(base);
               return base / 2.0F;
            }
         } else if (source == class_1282.field_5868) {
            class_243 rotVec = this.method_5828(1.0F);
            class_243 difference = new class_243(0.0, 1.0, 0.0);
            double angle = difference.method_1026(rotVec);
            if (angle < -0.35) {
               this.field_6002
                  .method_43129(
                     null, this, AmariteSoundEvents.SWORD_BLOCK, class_3419.field_15251, 1.0F, 1.0F + this.field_6002.field_9229.method_43057() * 0.4F
                  );
               AmariteLongswordItem.getMode(player, stack).absorbDamage(base / 2.0F);
               return base / 4.0F;
            }
         }
      }

      return base;
   }

   @Inject(
      method = {"eatFood"},
      at = {@At("HEAD")}
   )
   private void amarite$eatedItAll(class_1937 world, @NotNull class_1799 stack, CallbackInfoReturnable<class_1799> cir) {
      BuddedComponent budding = (BuddedComponent)Amarite.BUDDED.get(this);
      if (stack.method_7969() != null) {
         if (stack.method_7969().method_10577("Budded")) {
            budding.setBudTime(12000);
         }

         if (stack.method_7969().method_10577("Curative")) {
            budding.setBudTime(0);
         }
      }

      if (stack.method_31574(class_1802.field_20417)) {
         budding.setBudTime(0);
      }
   }

   @Inject(
      method = {"attack"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;onAttacking(Lnet/minecraft/entity/Entity;)V"
      )}
   )
   private void amarite$chargeRebound(class_1297 target, CallbackInfo ci) {
      ((DiscComponent)Amarite.DISC.get(this)).chargeRebound(1);
   }
}
