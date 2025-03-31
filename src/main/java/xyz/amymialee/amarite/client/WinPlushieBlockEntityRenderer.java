package xyz.amymialee.amarite.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1087;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_3532;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_4696;
import net.minecraft.class_776;
import net.minecraft.class_827;
import net.minecraft.class_5614.class_5615;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.blocks.WinPlushieBlockEntity;
import xyz.amymialee.amarite.mixin.BlockRenderManagerAccessor;

@Environment(EnvType.CLIENT)
public class WinPlushieBlockEntityRenderer<T extends class_2586> implements class_827<T> {
   private final class_776 renderManager;

   public WinPlushieBlockEntityRenderer(@NotNull class_5615 ctx) {
      this.renderManager = ctx.method_32141();
   }

   public void method_3569(@NotNull T entity, float tickDelta, @NotNull class_4587 matrices, @NotNull class_4597 vertexConsumers, int light, int overlay) {
      matrices.method_22903();
      double squish = entity instanceof WinPlushieBlockEntity plushie ? plushie.squash : 0.0;
      double lastSquish = squish * 3.0;
      float squash = (float)Math.pow(1.0 - 1.0 / (1.0 + class_3532.method_16436(tickDelta, lastSquish, squish)), 2.0);
      matrices.method_22905(1.0F, 1.0F - squash, 1.0F);
      matrices.method_22904(0.5, 0.0, 0.5);
      matrices.method_22905(1.0F + squash / 2.0F, 1.0F, 1.0F + squash / 2.0F);
      matrices.method_22904(-0.5, 0.0, -0.5);
      class_2680 state = entity.method_11010();
      class_1087 bakedModel = this.renderManager.method_3349(state);
      ((BlockRenderManagerAccessor)this.renderManager)
         .getModelRenderer()
         .method_3367(
            matrices.method_23760(),
            vertexConsumers.getBuffer(class_4696.method_23683(state, false)),
            state,
            bakedModel,
            255.0F,
            255.0F,
            255.0F,
            light,
            overlay
         );
      matrices.method_22909();
   }
}
