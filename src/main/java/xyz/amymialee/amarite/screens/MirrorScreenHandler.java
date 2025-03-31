package xyz.amymialee.amarite.screens;

import java.util.Optional;
import net.minecraft.class_1263;
import net.minecraft.class_1277;
import net.minecraft.class_1291;
import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_3913;
import net.minecraft.class_3914;
import net.minecraft.class_3917;
import net.minecraft.class_3919;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.registry.AmariteItems;

public class MirrorScreenHandler extends class_1703 {
   private final class_1263 mirror = new class_1277(1) {
      public boolean method_5437(int slot, @NotNull class_1799 stack) {
         return stack.method_31574(AmariteItems.AMARITE_MIRROR);
      }
   };
   private final MirrorScreenHandler.MirrorSlot mirrorSlot;
   private final class_3914 context;
   private final class_3913 propertyDelegate;

   public MirrorScreenHandler(int syncId, class_1263 inventory) {
      this(syncId, inventory, new class_3919(3), class_3914.field_17304);
   }

   public MirrorScreenHandler(int syncId, class_1263 inventory, class_3913 propertyDelegate, class_3914 context) {
      super(class_3917.field_17330, syncId);
      method_17361(propertyDelegate, 3);
      this.propertyDelegate = propertyDelegate;
      this.context = context;
      this.mirrorSlot = new MirrorScreenHandler.MirrorSlot(this.mirror, 0, 136, 110);
      this.method_7621(this.mirrorSlot);
      this.method_17360(propertyDelegate);

      for (int k = 0; k < 3; k++) {
         for (int l = 0; l < 9; l++) {
            this.method_7621(new class_1735(inventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
         }
      }

      for (int k = 0; k < 9; k++) {
         this.method_7621(new class_1735(inventory, k, 36 + k * 18, 195));
      }
   }

   public void method_7595(class_1657 player) {
      super.method_7595(player);
      if (!player.field_6002.field_9236) {
         class_1799 itemStack = this.mirrorSlot.method_7671(this.mirrorSlot.method_7675());
         if (!itemStack.method_7960()) {
            player.method_7328(itemStack, false);
         }
      }
   }

   public boolean method_7597(class_1657 player) {
      return method_17695(this.context, player, class_2246.field_10327);
   }

   public void method_7606(int id, int value) {
      super.method_7606(id, value);
      this.method_7623();
   }

   public class_1799 method_7601(class_1657 player, int index) {
      class_1799 itemStack = class_1799.field_8037;
      class_1735 slot = (class_1735)this.field_7761.get(index);
      if (slot.method_7681()) {
         class_1799 itemStack2 = slot.method_7677();
         itemStack = itemStack2.method_7972();
         if (index == 0) {
            if (!this.method_7616(itemStack2, 1, 37, true)) {
               return class_1799.field_8037;
            }

            slot.method_7670(itemStack2, itemStack);
         } else if (!this.mirrorSlot.method_7681() && this.mirrorSlot.method_7680(itemStack2) && itemStack2.method_7947() == 1) {
            if (!this.method_7616(itemStack2, 0, 1, false)) {
               return class_1799.field_8037;
            }
         } else if (index >= 1 && index < 28) {
            if (!this.method_7616(itemStack2, 28, 37, false)) {
               return class_1799.field_8037;
            }
         } else if (index >= 28 && index < 37) {
            if (!this.method_7616(itemStack2, 1, 28, false)) {
               return class_1799.field_8037;
            }
         } else if (!this.method_7616(itemStack2, 1, 37, false)) {
            return class_1799.field_8037;
         }

         if (itemStack2.method_7960()) {
            slot.method_7673(class_1799.field_8037);
         } else {
            slot.method_7668();
         }

         if (itemStack2.method_7947() == itemStack.method_7947()) {
            return class_1799.field_8037;
         }

         slot.method_7667(player, itemStack2);
      }

      return itemStack;
   }

   public int getProperties() {
      return this.propertyDelegate.method_17390(0);
   }

   @Nullable
   public class_1291 getPrimaryEffect() {
      return class_1291.method_5569(this.propertyDelegate.method_17390(1));
   }

   @Nullable
   public class_1291 getSecondaryEffect() {
      return class_1291.method_5569(this.propertyDelegate.method_17390(2));
   }

   public void setEffects(Optional<class_1291> primary, Optional<class_1291> secondary) {
      if (this.mirrorSlot.method_7681()) {
         this.propertyDelegate.method_17391(1, primary.<Integer>map(class_1291::method_5554).orElse(-1));
         this.propertyDelegate.method_17391(2, secondary.<Integer>map(class_1291::method_5554).orElse(-1));
         this.mirrorSlot.method_7671(1);
         this.context.method_17393(class_1937::method_8524);
      }
   }

   public boolean hasPayment() {
      return !this.mirror.method_5438(0).method_7960();
   }

   static {
      ServerPlayNetworking.registerGlobalReceiver(Amarite.id("mirror"), (server, player, handler, buf, responseSender) -> {
         class_1291 primary = buf.readBoolean() ? class_1291.method_5569(buf.readInt()) : null;
         class_1291 secondary = buf.readBoolean() ? class_1291.method_5569(buf.readInt()) : null;
         server.execute(() -> {
            class_1703 screenHandler = player.field_7512;
            if (screenHandler instanceof MirrorScreenHandler) {
               ((MirrorScreenHandler)screenHandler).setEffects(Optional.ofNullable(primary), Optional.ofNullable(secondary));
            }
         });
      });
   }

   private static class MirrorSlot extends class_1735 {
      public MirrorSlot(class_1263 inventory, int index, int x, int y) {
         super(inventory, index, x, y);
      }

      public boolean method_7680(@NotNull class_1799 stack) {
         return stack.method_31574(AmariteItems.AMARITE_MIRROR);
      }

      public int method_7675() {
         return 1;
      }
   }
}
