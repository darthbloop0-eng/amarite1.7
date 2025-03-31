package xyz.amymialee.amarite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_310;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_742;
import net.minecraft.class_759;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_759.class})
public abstract class HeldItemRendererMixin {
   @WrapOperation(
      method = {"renderItem(FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider$Immediate;Lnet/minecraft/client/network/ClientPlayerEntity;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/item/HeldItemRenderer;renderFirstPersonItem(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/util/Hand;FLnet/minecraft/item/ItemStack;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V"
      )}
   )
   public void amarite$renderItems(
      class_759 renderer,
      @NotNull class_742 player,
      float tickDelta,
      float pitch,
      class_1268 hand,
      float swingProgress,
      class_1799 item,
      float equipProgress,
      class_4587 matrices,
      class_4597 vertexConsumers,
      int light,
      Operation<Void> operation
   ) {
      class_1799 stack = player.method_5998(hand);
      if (stack.method_31574(AmariteItems.AMARITE_DISC)) {
         DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(player);

         for (int i = 0; i < (player.method_7337() ? 3 : 1) * 3; i++) {
            if (discComponent.getDiscEntity(i) == null && discComponent.getDiscDurability(i) > 0) {
               matrices.method_22903();
               matrices.method_22904(0.12 * (i - 1), 0.1, 0.0);
               operation.call(new Object[]{renderer, player, tickDelta, pitch, hand, swingProgress, stack, equipProgress, matrices, vertexConsumers, light});
               matrices.method_22909();
            }
         }
      } else {
         operation.call(new Object[]{renderer, player, tickDelta, pitch, hand, swingProgress, item, equipProgress, matrices, vertexConsumers, light});
      }
   }

   @Inject(
      method = {"resetEquipProgress"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$longswordPickup(class_1268 hand, CallbackInfo ci) {
      if (class_310.method_1551().field_1724 != null) {
         class_1799 stack = class_310.method_1551().field_1724.method_5998(hand);
         if (stack.method_31574(AmariteItems.AMARITE_LONGSWORD)) {
            ci.cancel();
         }
      }
   }
}
