package xyz.amymialee.mialib.mixin;

import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1508;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.mialib.MiaLib;
import xyz.amymialee.mialib.cca.IdCooldownComponent;
import xyz.amymialee.mialib.interfaces.MPlayerEntity;

@Mixin({class_1657.class})
public abstract class PlayerEntityMixin extends class_1309 implements MPlayerEntity {
   protected PlayerEntityMixin(class_1299<? extends class_1309> entityType, class_1937 world) {
      super(entityType, world);
   }

   @Inject(
      method = {"attack"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/player/PlayerEntity;getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D"
      )},
      cancellable = true
   )
   public void mialib$customAttacks(@NotNull class_1297 target, CallbackInfo ci) {
      if (target.method_5732()) {
         if (target instanceof class_1508) {
            target = ((class_1508)target).field_7007;
         }

         class_1799 main = this.method_6047();
         if (main.method_7909().mialib$attack(this.method_37908(), main, this, target)) {
            ci.cancel();
         }
      }
   }

   @Override
   public boolean mialib$isCoolingDown(class_2960 id) {
      return ((IdCooldownComponent)MiaLib.ID_COOLDOWN_COMPONENT.get(this)).isCoolingDown(id);
   }

   @Override
   public void mialib$setCooldown(class_2960 id, int ticks) {
      ((IdCooldownComponent)MiaLib.ID_COOLDOWN_COMPONENT.get(this)).setCooldown(id, ticks);
   }

   @Override
   public int mialib$getCooldown(class_2960 id) {
      return ((IdCooldownComponent)MiaLib.ID_COOLDOWN_COMPONENT.get(this)).getCooldown(id);
   }

   @Override
   public float mialib$getCooldown(class_2960 id, float tickDelta) {
      return ((IdCooldownComponent)MiaLib.ID_COOLDOWN_COMPONENT.get(this)).getCooldown(id, tickDelta);
   }
}
