package xyz.amymialee.mialib.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.class_2540;
import net.minecraft.class_310;
import net.minecraft.class_315;
import net.minecraft.class_746;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import xyz.amymialee.mialib.MiaLib;

@Mixin({class_310.class})
public class MinecraftClientMixin {
   @Shadow
   @Final
   public class_315 field_1690;
   @Shadow
   @Nullable
   public class_746 field_1724;
   @Unique
   private boolean mialib$holding = false;
   @Unique
   private boolean mialib$using = false;

   @WrapOperation(
      method = {"handleInputEvents"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/MinecraftClient;handleBlockBreaking(Z)V"
      )}
   )
   private void mialib$holding(class_310 instance, boolean bl, Operation<Void> original) {
      boolean holding = this.field_1690.field_1886.method_1434();
      if (holding != this.mialib$holding) {
         this.mialib$holding = holding;
         class_2540 buf = PacketByteBufs.create();
         buf.writeBoolean(holding);
         ClientPlayNetworking.send(MiaLib.id("attacking"), buf);
      }

      boolean using = this.field_1690.field_1904.method_1434();
      if (using != this.mialib$using) {
         this.mialib$using = using;
         class_2540 buf = PacketByteBufs.create();
         buf.writeBoolean(using);
         ClientPlayNetworking.send(MiaLib.id("using"), buf);
      }

      original.call(new Object[]{instance, bl});
   }
}
