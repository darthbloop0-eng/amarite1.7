package xyz.amymialee.amarite.mixin.client;

import net.minecraft.class_1160;
import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3881;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_583;
import net.minecraft.class_989;
import net.minecraft.class_809.class_811;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_989.class})
public abstract class HeldItemFeatureRendererMixin<T extends class_1309, M extends class_583<T> & class_3881> extends class_3887<T, M> {
   public HeldItemFeatureRendererMixin(class_3883<T, M> context) {
      super(context);
   }

   @Inject(
      method = {"renderItem"},
      at = {@At("HEAD")},
      cancellable = true
   )
   protected void amarite$twoHanded(
      @NotNull class_1309 entity,
      class_1799 stack,
      class_811 transformationMode,
      class_1306 arm,
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      CallbackInfo ci
   ) {
      if (entity.method_6047().method_31574(AmariteItems.AMARITE_LONGSWORD) && entity.method_6068() != arm) {
         matrices.method_22903();
         ((class_3881)this.method_17165()).method_2803(arm, matrices);
         matrices.method_22909();
         ci.cancel();
      }

      if (stack.method_31574(AmariteItems.AMARITE_DISC) && entity instanceof class_1657 player) {
         DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(player);
         boolean leftArm = arm == class_1306.field_6182;

         for (int i = 0; i < (player.method_7337() ? 3 : 1) * 3; i++) {
            if (discComponent.getDiscEntity(i) == null && discComponent.getDiscDurability(i) > 0) {
               matrices.method_22903();
               ((class_3881)this.method_17165()).method_2803(arm, matrices);
               matrices.method_22905(0.7F, 0.7F, 0.7F);
               matrices.method_22907(class_1160.field_20703.method_23214(-90.0F));
               matrices.method_22907(class_1160.field_20705.method_23214(180.0F));
               matrices.method_22904(i * (leftArm ? -1.0F : 1.0F) / 12.0F, 0.135, -0.625);
               class_310.method_1551()
                  .method_1480()
                  .method_23177(
                     entity,
                     stack,
                     transformationMode,
                     leftArm,
                     matrices,
                     vertexConsumers,
                     entity.field_6002,
                     light,
                     class_4608.field_21444,
                     entity.method_5628() + transformationMode.ordinal()
                  );
               matrices.method_22909();
            }
         }

         ci.cancel();
      }
   }
}
