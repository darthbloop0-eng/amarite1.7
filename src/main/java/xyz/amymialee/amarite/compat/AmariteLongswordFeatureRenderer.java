package xyz.amymialee.amarite.compat;

import doctor4t.arsenal.common.components.BackWeaponComponent;
import net.minecraft.class_1160;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_3883;
import net.minecraft.class_3887;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_583;
import net.minecraft.class_809.class_811;
import xyz.amymialee.amarite.registry.AmariteItems;

public class AmariteLongswordFeatureRenderer<T extends class_1657, M extends class_583<T>> extends class_3887<T, M> {
   public AmariteLongswordFeatureRenderer(class_3883<T, M> context) {
      super(context);
   }

   public void render(
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      T entity,
      float limbAngle,
      float limbDistance,
      float tickDelta,
      float animationProgress,
      float headYaw,
      float headPitch
   ) {
      if (!BackWeaponComponent.isHoldingBackWeapon(entity)) {
         class_1799 stack = BackWeaponComponent.getBackWeapon(entity);
         if (!stack.method_7960()) {
            if (stack.method_7909() == AmariteItems.AMARITE_LONGSWORD) {
               matrices.method_22903();
               matrices.method_22904(-0.1, 0.25, 0.275);
               matrices.method_22907(class_1160.field_20707.method_23214(0.0F));
               matrices.method_22905(1.85F, 1.85F, 1.0F);
               class_310.method_1551()
                  .method_1480()
                  .method_23177(entity, stack, class_811.field_4319, false, matrices, vertexConsumers, entity.field_6002, light, class_4608.field_21444, 0);
               matrices.method_22909();
            }
         }
      }
   }
}
