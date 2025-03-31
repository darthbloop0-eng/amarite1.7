package xyz.amymialee.amarite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.class_746;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_746.class})
public class ClientPlayerEntityMixin {
   @WrapOperation(
      method = {"tickMovement"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
      )}
   )
   private boolean amarite$noSwordSlowdown(class_746 player, @NotNull Operation<Boolean> original) {
      return (Boolean)original.call(new Object[]{player}) && !player.method_6030().method_31574(AmariteItems.AMARITE_LONGSWORD);
   }
}
