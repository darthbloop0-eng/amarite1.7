package xyz.amymialee.amarite.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_329;
import net.minecraft.class_332;
import net.minecraft.class_4587;
import net.minecraft.class_746;
import net.minecraft.class_329.class_6411;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.registry.AmariteEntities;

@Mixin({class_329.class})
public abstract class InGameHudMixin extends class_332 {
   @Unique
   private static final class_2960 AMARITE_ICONS_TEXTURE = Amarite.id("textures/gui/icons.png");
   @Unique
   private final Set<Long> containers = new HashSet<>();

   @Inject(
      method = {"renderHealthBar"},
      at = {@At("HEAD")}
   )
   private void amarite$resetSet(
      class_4587 matrices,
      class_1657 player,
      int x,
      int y,
      int lines,
      int regeneratingHeartIndex,
      float maxHealth,
      int lastHealth,
      int health,
      int absorption,
      boolean blinking,
      CallbackInfo ci
   ) {
      this.containers.clear();
   }

   @WrapOperation(
      method = {"drawHeart"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/InGameHud;drawTexture(Lnet/minecraft/client/util/math/MatrixStack;IIIIII)V"
      )}
   )
   private void amarite$buddingHearts(
      class_329 instance,
      class_4587 matrices,
      int x,
      int y,
      int u,
      int v,
      int width,
      int height,
      Operation<Void> original,
      class_4587 hmatrices,
      class_6411 type,
      int hx,
      int hy,
      int hv,
      boolean blinking,
      boolean halfHeart
   ) {
      class_746 player = class_310.method_1551().field_1724;
      if (player != null && player.method_6059(AmariteEntities.BUDDING)) {
         if (type == class_6411.field_33944) {
            this.containers.add((long)x << 32 | y);
            return;
         }

         this.containers.remove((long)x << 32 | y);
         RenderSystem.setShaderTexture(0, AMARITE_ICONS_TEXTURE);
         int renderU = halfHeart ? height : 0;
         int renderV = blinking ? 9 : 0;
         method_25290(matrices, x, y, renderU, renderV, width, height, 32, 32);
         RenderSystem.setShaderTexture(0, class_332.field_22737);
      } else {
         original.call(new Object[]{instance, matrices, x, y, u, v, width, height});
      }
   }

   @Inject(
      method = {"renderHealthBar"},
      at = {@At("TAIL")}
   )
   private void amarite$containers(
      class_4587 matrices,
      class_1657 player,
      int x,
      int y,
      int lines,
      int regeneratingHeartIndex,
      float maxHealth,
      int lastHealth,
      int health,
      int absorption,
      boolean blinking,
      CallbackInfo ci
   ) {
      RenderSystem.setShaderTexture(0, AMARITE_ICONS_TEXTURE);

      for (Long container : this.containers) {
         int cX = (int)(container >> 32);
         int cY = (int)(container & 4294967295L);
         method_25290(matrices, cX, cY, 0.0F, 18.0F, 9, 9, 32, 32);
      }

      RenderSystem.setShaderTexture(0, class_332.field_22737);
   }
}
