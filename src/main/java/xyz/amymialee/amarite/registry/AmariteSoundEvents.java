package xyz.amymialee.amarite.registry;

import net.minecraft.class_3414;
import xyz.amymialee.amarite.Amarite;

public interface AmariteSoundEvents {
   class_3414 DISC_DAMAGE = Amarite.REGISTRY.registerSound("item.amarite.disc_damage");
   class_3414 DISC_HIT = Amarite.REGISTRY.registerSound("item.amarite.disc_hit");
   class_3414 DISC_THROW = Amarite.REGISTRY.registerSound("item.amarite.disc_throw");
   class_3414 DISC_PICKUP_1 = Amarite.REGISTRY.registerSound("item.amarite.disc_pickup1");
   class_3414 DISC_PICKUP_2 = Amarite.REGISTRY.registerSound("item.amarite.disc_pickup2");
   class_3414 DISC_PICKUP_3 = Amarite.REGISTRY.registerSound("item.amarite.disc_pickup3");
   class_3414 DISC_REBOUND = Amarite.REGISTRY.registerSound("item.amarite.disc_rebound");
   class_3414 MIRROR_EXTRACT = Amarite.REGISTRY.registerSound("item.amarite.mirror_extract");
   class_3414 MIRROR_USE = Amarite.REGISTRY.registerSound("item.amarite.mirror_use");
   class_3414 SWORD_CHARGE = Amarite.REGISTRY.registerSound("item.amarite.sword_charge");
   class_3414 SWORD_BLOCK = Amarite.REGISTRY.registerSound("item.amarite.sword_block");
   class_3414 SWORD_DASH = Amarite.REGISTRY.registerSound("item.amarite.sword_dash");
   class_3414 SWORD_ACCUMULATE = Amarite.REGISTRY.registerSound("item.amarite.sword_accumulate");
   class_3414 MASK_OFFSET = Amarite.REGISTRY.registerSound("item.amarite.mask_offset");
   class_3414 SPARK_ACTIVATE = Amarite.REGISTRY.registerSound("block.amarite.spark_activate");
   class_3414 SPARK_AMBIENT = Amarite.REGISTRY.registerSound("block.amarite.spark_ambient");
   class_3414 SPARK_DEACTIVATE = Amarite.REGISTRY.registerSound("block.amarite.spark_deactivate");
   class_3414 AMARITE_FORMS = Amarite.REGISTRY.registerSound("block.amarite.amarite_forms");
   class_3414 AMARITE_DECAYS = Amarite.REGISTRY.registerSound("block.amarite.amarite_decays");
   class_3414 INFINITE_TOGGLE = Amarite.REGISTRY.registerSound("block.amarite.infinite_toggle");
   class_3414 PACIFICUS = Amarite.REGISTRY.registerSound("amarite.pacificus");
   class_3414 WIN_PLUSHIE = Amarite.REGISTRY.registerSound("block.amarite.win_plushie");

   static void init() {
   }

   static class_3414 getPickupSound(int i) {
      return switch (i) {
         case 2 -> DISC_PICKUP_2;
         case 3 -> DISC_PICKUP_3;
         default -> DISC_PICKUP_1;
      };
   }
}
