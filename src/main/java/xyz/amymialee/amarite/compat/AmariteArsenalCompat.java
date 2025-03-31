package xyz.amymialee.amarite.compat;

import doctor4t.arsenal.common.util.WeaponSlotCallback;
import net.minecraft.class_1269;
import xyz.amymialee.amarite.registry.AmariteItems;

public class AmariteArsenalCompat {
   public static void init() {
      WeaponSlotCallback.EVENT
         .register((WeaponSlotCallback)(player, stack) -> stack.method_7909() == AmariteItems.AMARITE_LONGSWORD ? class_1269.field_5814 : class_1269.field_5811);
   }
}
