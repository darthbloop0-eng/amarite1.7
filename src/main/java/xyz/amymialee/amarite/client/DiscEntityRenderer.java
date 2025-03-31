package xyz.amymialee.amarite.client;

import net.minecraft.class_1059;
import net.minecraft.class_1160;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_897;
import net.minecraft.class_918;
import net.minecraft.class_5617.class_5618;
import net.minecraft.class_809.class_811;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.entities.DiscEntity;
import xyz.amymialee.amarite.registry.AmariteItems;

public class DiscEntityRenderer extends class_897<DiscEntity> {
   private static final class_1799 stack = AmariteItems.AMARITE_DISC.method_7854();
   private final class_918 itemRenderer;

   public DiscEntityRenderer(class_5618 ctx) {
      super(ctx);
      this.itemRenderer = ctx.method_32168();
   }

   public void render(@NotNull DiscEntity disc, float yaw, float tickDelta, @NotNull class_4587 matrices, class_4597 vertexConsumers, int light) {
      matrices.method_22903();
      matrices.method_22904(
         (disc.field_6038 - disc.method_23317()) * tickDelta + disc.method_18798().field_1352 * tickDelta,
         (disc.field_5971 - disc.method_23318()) * tickDelta + disc.method_18798().field_1351 * tickDelta,
         (disc.field_5989 - disc.method_23321()) * tickDelta + disc.method_18798().field_1350 * tickDelta
      );
      matrices.method_22907(class_1160.field_20705.method_23214(180.0F + class_3532.method_16439(tickDelta, disc.field_5982, disc.method_36454())));
      matrices.method_22907(class_1160.field_20703.method_23214(90.0F + class_3532.method_16439(tickDelta, disc.field_6004, disc.method_36455())));
      matrices.method_22907(class_1160.field_20707.method_23214((disc.field_6012 + tickDelta) * -75.0F));
      matrices.method_22905(1.0F, 1.0F, 1.0F);
      this.itemRenderer.method_23178(stack, class_811.field_4319, light, class_4608.field_21444, matrices, vertexConsumers, disc.method_5628());
      matrices.method_22909();
      super.method_3936(disc, yaw, tickDelta, matrices, vertexConsumers, light);
   }

   public class_2960 getTexture(DiscEntity entity) {
      return class_1059.field_5275;
   }
}
