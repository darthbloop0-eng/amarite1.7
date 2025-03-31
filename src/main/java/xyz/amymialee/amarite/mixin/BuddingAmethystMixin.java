package xyz.amymialee.amarite.mixin;

import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2350;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3619;
import net.minecraft.class_5543;
import net.minecraft.class_5819;
import net.minecraft.class_2350.class_2351;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

@Mixin({class_5543.class})
public class BuddingAmethystMixin {
   @Inject(
      method = {"randomTick"},
      at = {@At("HEAD")}
   )
   private void amarite$buddingAmarite(class_2680 state, class_3218 world, class_2338 pos, @NotNull class_5819 random, CallbackInfo ci) {
      if (random.method_43048(8) == 0) {
         boolean nether = world.method_27983() == class_1937.field_25180;
         if (!nether) {
            if (pos.method_10264() > 0 || !world.method_8320(pos.method_10093(class_2350.field_11033)).method_27852(class_2246.field_10092)) {
               return;
            }

            for (class_2350 direction : class_2350.values()) {
               if (direction.method_10166() != class_2351.field_11052) {
                  class_2338 offsetPos = pos.method_10093(direction);
                  class_2680 offsetState = world.method_8320(offsetPos);
                  if (!offsetState.method_27852(class_2246.field_33510)) {
                     return;
                  }
               }
            }
         } else {
            for (class_2350 directionx : class_2350.values()) {
               if (directionx != class_2350.field_11036) {
                  class_2338 offsetPos = pos.method_10093(directionx);
                  class_2680 offsetState = world.method_8320(offsetPos);
                  if (!offsetState.method_27852(class_2246.field_33510) && !offsetState.method_27852(class_2246.field_23077)) {
                     return;
                  }
               }
            }
         }

         world.method_8501(pos, AmariteBlocks.BUDDING_AMARITE.method_9564());
         ServerPlayNetworking.send(
            world.method_18766(e -> pos.method_19769(e.method_19538(), 64.0)), Amarite.id("budgrow"), PacketByteBufs.create().method_10807(pos)
         );
         world.method_8486(pos.method_10263(), pos.method_10264(), pos.method_10260(), AmariteSoundEvents.AMARITE_DECAYS, null, 1.0F, 0.6F, false);
      }
   }

   @Inject(
      method = {"getPistonBehavior"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$nonBreaking(class_2680 state, @NotNull CallbackInfoReturnable<class_3619> cir) {
      cir.setReturnValue(class_3619.field_15974);
   }
}
