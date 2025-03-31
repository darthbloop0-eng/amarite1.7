package xyz.amymialee.amarite.client;

import net.minecraft.class_1160;
import net.minecraft.class_1799;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4608;
import net.minecraft.class_4618;
import net.minecraft.class_897;
import net.minecraft.class_918;
import net.minecraft.class_5617.class_5618;
import net.minecraft.class_809.class_811;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.entities.MalignancyEntity;
import xyz.amymialee.amarite.registry.AmariteItems;

public class MalignancyEntityRenderer extends class_897<MalignancyEntity> {
   private static final class_1799 stack = AmariteItems.AMARITE_LONGSWORD.method_7854();
   private final class_918 itemRenderer;

   public MalignancyEntityRenderer(class_5618 context) {
      super(context);
      this.itemRenderer = context.method_32168();
   }

   public void render(@NotNull MalignancyEntity malignancy, float f, float tickDelta, @NotNull class_4587 matrixStack, class_4597 vertexConsumerProvider, int i) {
      matrixStack.method_22903();
      matrixStack.method_22904(0.0, 0.8, 0.0);
      matrixStack.method_22905(1.7F, 1.7F, 1.7F);
      class_4618 consumers = class_310.method_1551().method_22940().method_23003();
      if (malignancy.field_6012 > 80) {
         malignancy.rotation += 8.0F * tickDelta;
         stack.method_7948().method_10569("malignant", -10639338);
         int r = 93;
         int g = 168;
         int b = 22;
         consumers.method_23286(r, g, b, 255);
         if (malignancy.firing) {
            malignancy.rotation += 8.0F * tickDelta;
         }
      } else {
         malignancy.rotation += 4.0F * tickDelta;
         int shade = (int)(160.0 + 95.0 * Math.pow(malignancy.field_6012 / 80.0F, 8.0));
         int r = shade >> 16 & 0xFF;
         int g = shade >> 8 & 0xFF;
         int b = shade & 0xFF;
         int rgb = r << 16 | g << 8 | b;
         stack.method_7948().method_10569("malignant", rgb);
         consumers.method_23286(shade, shade, shade / 2, 255);
      }

      matrixStack.method_22907(class_1160.field_20705.method_23214(malignancy.rotation));
      matrixStack.method_22907(class_1160.field_20707.method_23214(-45.0F));
      this.itemRenderer.method_23178(stack, class_811.field_4319, 15728640, class_4608.field_21444, matrixStack, consumers, 0);
      matrixStack.method_22909();
   }

   public class_2960 getTexture(MalignancyEntity malignancy) {
      return null;
   }
}
