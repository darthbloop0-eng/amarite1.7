package xyz.amymialee.amarite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_976;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.amymialee.amarite.items.MaskItem;

@Mixin({class_976.class})
public class HeadFeatureRendererMixin<T extends class_1309> {
   @WrapOperation(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/entity/LivingEntity;getEquippedStack(Lnet/minecraft/entity/EquipmentSlot;)Lnet/minecraft/item/ItemStack;"
      )}
   )
   private class_1799 amarite$hideHeadFeature(class_1309 entity, class_1304 slot, Operation<class_1799> original) {
      class_1799 mask = MaskItem.getWornMask(entity);
      return !mask.method_7960() ? mask : (class_1799)original.call(new Object[]{entity, slot});
   }

   @Inject(
      method = {"render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderItem(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformation$Mode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
      )},
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void amarite$maskOffset(
      class_4587 matrixStack,
      class_4597 vertexConsumerProvider,
      int i,
      T livingEntity,
      float f,
      float g,
      float h,
      float j,
      float k,
      float l,
      CallbackInfo ci,
      @NotNull class_1799 stack
   ) {
      if (stack.method_7909() instanceof MaskItem) {
         matrixStack.method_22904(0.0, MaskItem.getOffset(stack) * 0.1, 0.0);
      }
   }
}
