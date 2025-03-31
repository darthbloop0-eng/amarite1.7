package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import java.util.Objects;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_3417;
import net.minecraft.class_3419;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_1309.class})
public abstract class LivingEntityMixin extends class_1297 {
   @Shadow
   public abstract float method_6032();

   public LivingEntityMixin(class_1299<?> type, class_1937 world) {
      super(type, world);
   }

   @WrapOperation(
      method = {"damage"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;takeKnockback(DDD)V"
      )}
   )
   private void amarite$pull(class_1309 instance, double strength, double x, double z, Operation<Void> original, @NotNull class_1282 source, float amount) {
      if (!Objects.equals(source.field_5841, "amarite.accumulate") && !Objects.equals(source.field_5841, "amarite.malignancy")) {
         original.call(new Object[]{instance, strength, x, z});
      }
   }

   @Inject(
      method = {"setHealth"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$setHealth(float health, CallbackInfo ci) {
      class_1309 entity = (class_1309)this;
      if (health > this.method_6032() && entity.method_6059(AmariteEntities.BUDDING)) {
         ci.cancel();
      }
   }

   @Inject(
      method = {"dropEquipment"},
      at = {@At("TAIL")}
   )
   private void amarite$cookie(@NotNull class_1282 source, int lootingMultiplier, boolean allowDrops, CallbackInfo ci) {
      if (source.method_5529() instanceof class_1657 && "d93dde4b-7b15-4e7f-a860-03a760f2aad7".equals(this.method_5845())) {
         this.method_5775(AmariteItems.WIN_COOKIE.method_7854());
      }

      class_1309 entity = (class_1309)this;
      if (entity.method_6059(AmariteEntities.BUDDING)) {
         for (int i = 0; i < 3; i++) {
            this.method_5775(new class_1799(AmariteItems.AMARITE_SHARD));
         }

         this.field_6002.method_43129(null, this, class_3417.field_26942, class_3419.field_15248, 1.0F, 0.75F);
      }
   }
}
