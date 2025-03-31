package xyz.amymialee.amarite.entities;

import net.minecraft.class_1299;
import net.minecraft.class_1665;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_3966;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscPylonComponent;

public class PylonEntity extends class_1665 {
   public PylonEntity(class_1299<? extends class_1665> entityType, class_1937 world) {
      super(entityType, world);
   }

   public void method_5773() {
      super.method_5773();
      if (((DiscPylonComponent)Amarite.PYLON.get(this)).pylonCharge <= 0) {
         this.method_31472();
      }
   }

   protected void method_7454(@NotNull class_3966 entityHitResult) {
      DiscPylonComponent var10000 = (DiscPylonComponent)Amarite.PYLON.get(entityHitResult.method_17782());
      var10000.pylonCharge = var10000.pylonCharge + ((DiscPylonComponent)Amarite.PYLON.get(this)).pylonCharge;
      this.method_31472();
   }

   protected class_1799 method_7445() {
      return class_1799.field_8037;
   }
}
