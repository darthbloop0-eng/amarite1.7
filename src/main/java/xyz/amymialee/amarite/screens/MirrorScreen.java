package xyz.amymialee.amarite.screens;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1058;
import net.minecraft.class_1291;
import net.minecraft.class_1661;
import net.minecraft.class_1703;
import net.minecraft.class_1712;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_2378;
import net.minecraft.class_2540;
import net.minecraft.class_2561;
import net.minecraft.class_2580;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_339;
import net.minecraft.class_4264;
import net.minecraft.class_4587;
import net.minecraft.class_465;
import net.minecraft.class_5244;
import net.minecraft.class_5250;
import net.minecraft.class_6382;
import net.minecraft.class_757;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import xyz.amymialee.amarite.Amarite;

@Environment(EnvType.CLIENT)
public class MirrorScreen extends class_465<MirrorScreenHandler> {
   static final class_2960 TEXTURE = Amarite.id("textures/gui/container/beacon.png");
   private static final class_2561 PRIMARY_POWER_TEXT = class_2561.method_43471("block.minecraft.beacon.primary");
   private static final class_2561 SECONDARY_POWER_TEXT = class_2561.method_43471("block.minecraft.beacon.secondary");
   private final List<MirrorScreen.BeaconButtonWidget> buttons = Lists.newArrayList();
   @Nullable
   class_1291 primaryEffect;
   @Nullable
   class_1291 secondaryEffect;

   public MirrorScreen(MirrorScreenHandler handler, class_1661 inventory, class_2561 title) {
      super(handler, inventory, title);
      this.field_2792 = 230;
      this.field_2779 = 219;
      handler.method_7596(new class_1712() {
         public void method_7635(class_1703 handler, int slotId, class_1799 stack) {
         }

         public void method_7633(class_1703 handler, int property, int value) {
            MirrorScreen.this.primaryEffect = ((MirrorScreenHandler)handler).getPrimaryEffect();
            MirrorScreen.this.secondaryEffect = ((MirrorScreenHandler)handler).getSecondaryEffect();
         }
      });
   }

   private <T extends class_339 & MirrorScreen.BeaconButtonWidget> void addButton(T button) {
      this.method_37063(button);
      this.buttons.add(button);
   }

   protected void method_25426() {
      super.method_25426();
      this.buttons.clear();
      this.addButton(new MirrorScreen.DoneButtonWidget(this.field_2776 + 164, this.field_2800 + 107));
      this.addButton(new MirrorScreen.CancelButtonWidget(this.field_2776 + 190, this.field_2800 + 107));

      for (int i = 0; i <= 2; i++) {
         int j = class_2580.field_11801[i].length;
         int k = j * 22 + (j - 1) * 2;

         for (int l = 0; l < j; l++) {
            class_1291 statusEffect = class_2580.field_11801[i][l];
            MirrorScreen.EffectButtonWidget effectButtonWidget = new MirrorScreen.EffectButtonWidget(
               this.field_2776 + 76 + l * 24 - k / 2, this.field_2800 + 22 + i * 25, statusEffect, true, i
            );
            effectButtonWidget.field_22763 = false;
            this.addButton(effectButtonWidget);
         }
      }

      int j = class_2580.field_11801[3].length + 1;
      int k = j * 22 + (j - 1) * 2;

      for (int l = 0; l < j - 1; l++) {
         class_1291 statusEffect = class_2580.field_11801[3][l];
         MirrorScreen.EffectButtonWidget effectButtonWidget = new MirrorScreen.EffectButtonWidget(
            this.field_2776 + 167 + l * 24 - k / 2, this.field_2800 + 47, statusEffect, false, 3
         );
         effectButtonWidget.field_22763 = false;
         this.addButton(effectButtonWidget);
      }

      MirrorScreen.EffectButtonWidget effectButtonWidget2 = new MirrorScreen.LevelTwoEffectButtonWidget(
         this.field_2776 + 167 + (j - 1) * 24 - k / 2, this.field_2800 + 47, class_2580.field_11801[0][0]
      );
      effectButtonWidget2.field_22764 = false;
      this.addButton(effectButtonWidget2);
   }

   public void method_37432() {
      super.method_37432();
      this.tickButtons();
   }

   void tickButtons() {
      int i = ((MirrorScreenHandler)this.field_2797).getProperties();
      this.buttons.forEach(button -> button.tick(i));
   }

   protected void method_2388(class_4587 matrices, int mouseX, int mouseY) {
      method_27534(matrices, this.field_22793, PRIMARY_POWER_TEXT, 62, 10, 14737632);
      method_27534(matrices, this.field_22793, SECONDARY_POWER_TEXT, 169, 10, 14737632);

      for (MirrorScreen.BeaconButtonWidget beaconButtonWidget : this.buttons) {
         if (beaconButtonWidget.shouldRenderTooltip()) {
            beaconButtonWidget.method_25352(matrices, mouseX - this.field_2776, mouseY - this.field_2800);
            break;
         }
      }
   }

   protected void method_2389(class_4587 matrices, float delta, int mouseX, int mouseY) {
      RenderSystem.setShader(class_757::method_34542);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, TEXTURE);
      int i = (this.field_22789 - this.field_2792) / 2;
      int j = (this.field_22790 - this.field_2779) / 2;
      this.method_25302(matrices, i, j, 0, 0, this.field_2792, this.field_2779);
      this.field_22788.field_4730 = 100.0F;
      this.field_22788.method_4023(new class_1799(class_1802.field_22020), i + 20, j + 109);
      this.field_22788.method_4023(new class_1799(class_1802.field_8687), i + 41, j + 109);
      this.field_22788.method_4023(new class_1799(class_1802.field_8477), i + 41 + 22, j + 109);
      this.field_22788.method_4023(new class_1799(class_1802.field_8695), i + 42 + 44, j + 109);
      this.field_22788.method_4023(new class_1799(class_1802.field_8620), i + 42 + 66, j + 109);
      this.field_22788.field_4730 = 0.0F;
   }

   public void method_25394(class_4587 matrices, int mouseX, int mouseY, float delta) {
      this.method_25420(matrices);
      super.method_25394(matrices, mouseX, mouseY, delta);
      this.method_2380(matrices, mouseX, mouseY);
   }

   @Environment(EnvType.CLIENT)
   abstract static class BaseButtonWidget extends class_4264 implements MirrorScreen.BeaconButtonWidget {
      private boolean disabled;

      protected BaseButtonWidget(int x, int y) {
         super(x, y, 22, 22, class_5244.field_39003);
      }

      protected BaseButtonWidget(int x, int y, class_2561 message) {
         super(x, y, 22, 22, message);
      }

      public void method_25359(class_4587 matrices, int mouseX, int mouseY, float delta) {
         RenderSystem.setShader(class_757::method_34542);
         RenderSystem.setShaderTexture(0, MirrorScreen.TEXTURE);
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
         int j = 0;
         if (!this.field_22763) {
            j += this.field_22758 * 2;
         } else if (this.disabled) {
            j += this.field_22758;
         } else if (this.method_25367()) {
            j += this.field_22758 * 3;
         }

         this.method_25302(matrices, this.field_22760, this.field_22761, j, 219, this.field_22758, this.field_22759);
         this.renderExtra(matrices);
      }

      protected abstract void renderExtra(class_4587 var1);

      public boolean isDisabled() {
         return this.disabled;
      }

      public void setDisabled(boolean disabled) {
         this.disabled = disabled;
      }

      @Override
      public boolean shouldRenderTooltip() {
         return this.field_22762;
      }

      public void method_37020(class_6382 builder) {
         this.method_37021(builder);
      }
   }

   @Environment(EnvType.CLIENT)
   interface BeaconButtonWidget {
      boolean shouldRenderTooltip();

      void method_25352(class_4587 var1, int var2, int var3);

      void tick(int var1);
   }

   @Environment(EnvType.CLIENT)
   class CancelButtonWidget extends MirrorScreen.IconButtonWidget {
      public CancelButtonWidget(int x, int y) {
         super(x, y, 112, 220, class_5244.field_24335);
      }

      public void method_25306() {
         MirrorScreen.this.field_22787.field_1724.method_7346();
      }

      @Override
      public void tick(int level) {
      }
   }

   @Environment(EnvType.CLIENT)
   class DoneButtonWidget extends MirrorScreen.IconButtonWidget {
      public DoneButtonWidget(int x, int y) {
         super(x, y, 90, 220, class_5244.field_24334);
      }

      public void method_25306() {
         ClientPlayNetworking.send(
            Amarite.id("mirror"),
            (class_2540)PacketByteBufs.create()
               .writeInt(class_2378.field_11159.method_10206(MirrorScreen.this.primaryEffect))
               .writeInt(class_2378.field_11159.method_10206(MirrorScreen.this.secondaryEffect))
               .asByteBuf()
         );
      }

      @Override
      public void tick(int level) {
         this.field_22763 = ((MirrorScreenHandler)MirrorScreen.this.field_2797).hasPayment();
      }
   }

   @Environment(EnvType.CLIENT)
   class EffectButtonWidget extends MirrorScreen.BaseButtonWidget {
      private final boolean primary;
      protected final int level;
      private class_1291 effect;
      private class_1058 sprite;
      private class_2561 tooltip;

      public EffectButtonWidget(int x, int y, class_1291 statusEffect, boolean primary, int level) {
         super(x, y);
         this.primary = primary;
         this.level = level;
         this.init(statusEffect);
      }

      protected void init(class_1291 statusEffect) {
         this.effect = statusEffect;
         this.sprite = class_310.method_1551().method_18505().method_18663(statusEffect);
         this.tooltip = this.getEffectName(statusEffect);
      }

      protected class_5250 getEffectName(class_1291 statusEffect) {
         return class_2561.method_43471(statusEffect.method_5567());
      }

      public void method_25306() {
         if (!this.isDisabled()) {
            if (this.primary) {
               MirrorScreen.this.primaryEffect = this.effect;
            } else {
               MirrorScreen.this.secondaryEffect = this.effect;
            }

            MirrorScreen.this.tickButtons();
         }
      }

      @Override
      public void method_25352(class_4587 matrices, int mouseX, int mouseY) {
         MirrorScreen.this.method_25424(matrices, this.tooltip, mouseX, mouseY);
      }

      @Override
      protected void renderExtra(class_4587 matrices) {
         RenderSystem.setShaderTexture(0, this.sprite.method_24119().method_24106());
         method_25298(matrices, this.field_22760 + 2, this.field_22761 + 2, this.method_25305(), 18, 18, this.sprite);
      }

      @Override
      public void tick(int level) {
         this.field_22763 = this.level < level;
         this.setDisabled(this.effect == (this.primary ? MirrorScreen.this.primaryEffect : MirrorScreen.this.secondaryEffect));
      }

      protected class_5250 method_25360() {
         return this.getEffectName(this.effect);
      }
   }

   @Environment(EnvType.CLIENT)
   abstract class IconButtonWidget extends MirrorScreen.BaseButtonWidget {
      private final int u;
      private final int v;

      protected IconButtonWidget(int x, int y, int u, int v, class_2561 message) {
         super(x, y, message);
         this.u = u;
         this.v = v;
      }

      @Override
      protected void renderExtra(class_4587 matrices) {
         this.method_25302(matrices, this.field_22760 + 2, this.field_22761 + 2, this.u, this.v, 18, 18);
      }

      @Override
      public void method_25352(class_4587 matrices, int mouseX, int mouseY) {
         MirrorScreen.this.method_25424(matrices, MirrorScreen.this.field_22785, mouseX, mouseY);
      }
   }

   @Environment(EnvType.CLIENT)
   class LevelTwoEffectButtonWidget extends MirrorScreen.EffectButtonWidget {
      public LevelTwoEffectButtonWidget(int x, int y, class_1291 statusEffect) {
         super(x, y, statusEffect, false, 3);
      }

      @Override
      protected class_5250 getEffectName(class_1291 statusEffect) {
         return class_2561.method_43471(statusEffect.method_5567()).method_27693(" II");
      }

      @Override
      public void tick(int level) {
         if (MirrorScreen.this.primaryEffect != null) {
            this.field_22764 = true;
            this.init(MirrorScreen.this.primaryEffect);
            super.tick(level);
         } else {
            this.field_22764 = false;
         }
      }
   }
}
