package xyz.amymialee.amarite.registry;

import net.minecraft.class_1304;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1887.class_1888;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.mialib.templates.MEnchantment;

public interface AmariteEnchantments {
   class_1887 DOUBLE_DASH = Amarite.REGISTRY
      .register(
         "double_dash",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_LONGSWORD))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != AmariteEnchantments.ACCUMULATE && other != AmariteEnchantments.MALIGNANCY && bool)
      );
   class_1887 ACCUMULATE = Amarite.REGISTRY
      .register(
         "accumulate",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_LONGSWORD))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != DOUBLE_DASH && other != AmariteEnchantments.MALIGNANCY && bool)
      );
   class_1887 MALIGNANCY = Amarite.REGISTRY
      .register(
         "malignancy",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_LONGSWORD))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != DOUBLE_DASH && other != ACCUMULATE && bool)
      );
   class_1887 REBOUND = Amarite.REGISTRY
      .register(
         "rebound",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_DISC))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != AmariteEnchantments.PYLON && other != AmariteEnchantments.ORBIT && bool)
      );
   class_1887 PYLON = Amarite.REGISTRY
      .register(
         "pylon",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_DISC))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != REBOUND && other != AmariteEnchantments.ORBIT && bool)
      );
   class_1887 ORBIT = Amarite.REGISTRY
      .register(
         "orbit",
         new MEnchantment(class_1888.field_9088, class_1886.field_9074, class_1304.field_6173)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_31574(AmariteItems.AMARITE_DISC))
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != REBOUND && other != PYLON && bool)
      );
   class_1887 ANONYMITY = Amarite.REGISTRY
      .register(
         "anonymity",
         new MEnchantment(class_1888.field_9088, class_1886.field_9078)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_7909() instanceof MaskItem)
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != AmariteEnchantments.CONCEALMENT && bool)
      );
   class_1887 CONCEALMENT = Amarite.REGISTRY
      .register(
         "concealment",
         new MEnchantment(class_1888.field_9088, class_1886.field_9078)
            .setIsAcceptableItem((enchant, bool, stack) -> stack.method_7909() instanceof MaskItem)
            .setMinPower(1)
            .setMaxPower(100)
            .setCanAccept((enchant, bool, other) -> other != ANONYMITY && bool)
      );

   static void init() {
   }
}
