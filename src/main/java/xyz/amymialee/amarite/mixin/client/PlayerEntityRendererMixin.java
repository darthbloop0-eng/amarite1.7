package xyz.amymialee.amarite.mixin.client;

import net.minecraft.class_1007;
import net.minecraft.class_1268;
import net.minecraft.class_1799;
import net.minecraft.class_591;
import net.minecraft.class_742;
import net.minecraft.class_922;
import net.minecraft.class_5617.class_5618;
import net.minecraft.class_572.class_573;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.QuiltLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.cca.LongswordAccumulateComponent;
import xyz.amymialee.amarite.compat.AmariteLongswordFeatureRenderer;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_1007.class})
public abstract class PlayerEntityRendererMixin extends class_922<class_742, class_591<class_742>> {
   public PlayerEntityRendererMixin(class_5618 ctx, class_591<class_742> model, float shadowRadius) {
      super(ctx, model, shadowRadius);
   }

   @Inject(
      method = {"getArmPose"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void amarite$swordPoses(@NotNull class_742 player, class_1268 hand, CallbackInfoReturnable<class_573> cir) {
      class_1799 main = player.method_6047();
      if (main.method_31574(AmariteItems.AMARITE_LONGSWORD)) {
         boolean blocking = player.mialib$isUsing() || ((LongswordAccumulateComponent)Amarite.ACCUMULATE.get(player)).accumulateActive;
         if (hand != class_1268.field_5808) {
            cir.setReturnValue(blocking ? class_573.field_3403 : class_573.field_3405);
         } else {
            cir.setReturnValue(blocking ? class_573.field_3403 : class_573.field_3406);
         }

         if (blocking) {
            player.field_6283 = player.field_6241;
            player.field_6220 = player.field_6259;
         }
      }

      if (player.method_5998(hand).method_31574(AmariteItems.AMARITE_DISC) && ((DiscComponent)Amarite.DISC.get(player)).getNextAvailableDisc() == -1) {
         cir.setReturnValue(class_573.field_3409);
      }
   }

   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")}
   )
   public void amarite$backBlade(class_5618 ctx, boolean slim, CallbackInfo ci) {
      if (QuiltLoader.isModLoaded("arsenal")) {
         this.method_4046(new AmariteLongswordFeatureRenderer(this));
      }
   }
}
