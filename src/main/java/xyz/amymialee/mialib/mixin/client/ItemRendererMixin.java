package xyz.amymialee.mialib.mixin.client;

import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_746;
import net.minecraft.class_918;
import net.minecraft.class_809.class_811;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.mialib.MiaLibClient;

@Mixin({class_918.class})
public class ItemRendererMixin {
   @Inject(
      method = {"renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/world/World;III)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"
      )}
   )
   private void mialib$storeEntity(
      class_1309 entity,
      class_1799 item,
      class_811 renderMode,
      boolean leftHanded,
      class_4587 matrices,
      class_4597 vertexConsumers,
      class_1937 world,
      int light,
      int overlay,
      int seed,
      CallbackInfo ci
   ) {
      MiaLibClient.renderingEntityWithItem = entity;
      MiaLibClient.currentMode = renderMode;
   }

   @Inject(
      method = {"innerRenderInGui(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;IIII)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"
      )}
   )
   private void mialib$storeEntity(class_1309 entity, class_1799 itemStack, int x, int y, int seed, int depth, CallbackInfo ci) {
      MiaLibClient.renderingEntityWithItem = entity;
      MiaLibClient.currentMode = class_811.field_4317;
   }

   @Inject(
      method = {"renderGuiItemIcon"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/ItemRenderer;getModel(Lnet/minecraft/item/ItemStack;Lnet/minecraft/world/World;Lnet/minecraft/entity/LivingEntity;I)Lnet/minecraft/client/render/model/BakedModel;"
      )}
   )
   private void mialib$storeEntity(class_1799 stack, int x, int y, CallbackInfo ci) {
      MiaLibClient.renderingEntityWithItem = null;
      MiaLibClient.currentMode = class_811.field_4317;
   }

   @Inject(
      method = {"renderGuiItemOverlay(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/item/ItemStack;IILjava/lang/String;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/item/ItemStack;isItemBarVisible()Z",
         shift = Shift.BEFORE
      )}
   )
   public void mialib$customBar(class_327 renderer, class_1799 stack, int x, int y, String countLabel, CallbackInfo ci) {
      class_746 clientPlayerEntity = class_310.method_1551().field_1724;
      if (clientPlayerEntity != null) {
         stack.method_7909().mialib$renderCustomBar((class_918)this, renderer, stack, x, y, countLabel);
      }
   }
}
