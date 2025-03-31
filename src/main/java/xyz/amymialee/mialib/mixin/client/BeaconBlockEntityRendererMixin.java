package xyz.amymialee.mialib.mixin.client;

import java.util.List;
import net.minecraft.class_2580;
import net.minecraft.class_4587;
import net.minecraft.class_4597;
import net.minecraft.class_822;
import net.minecraft.class_2580.class_2581;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import xyz.amymialee.mialib.interfaces.MBeaconBeamSegment;

@Mixin({class_822.class})
public class BeaconBlockEntityRendererMixin {
   @Inject(
      method = {"render(Lnet/minecraft/block/entity/BeaconBlockEntity;FLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;II)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/block/entity/BeaconBlockEntityRenderer;renderBeam(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;FJII[F)V"
      )},
      cancellable = true,
      locals = LocalCapture.CAPTURE_FAILSOFT
   )
   private void amarite$consumedBeacon(
      class_2580 beaconBlockEntity,
      float f,
      class_4587 matrixStack,
      class_4597 vertexConsumerProvider,
      int i,
      int j,
      CallbackInfo ci,
      long l,
      List<class_2581> list,
      int k,
      int m,
      @NotNull class_2581 beamSegment
   ) {
      if (((MBeaconBeamSegment)beamSegment).mialib$isHidden()) {
         ci.cancel();
      }
   }
}
