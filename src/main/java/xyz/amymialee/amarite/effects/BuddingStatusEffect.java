package xyz.amymialee.amarite.effects;

import net.minecraft.class_1291;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_4081;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteDamageTypes;

public class BuddingStatusEffect extends class_1291 {
   public BuddingStatusEffect(class_4081 statusEffectCategory, int color) {
      super(statusEffectCategory, color);
   }

   public void method_5572(class_1309 entity, int amplifier) {
      if (entity instanceof class_1657 player) {
         for (AmariteLongswordItem.LongswordMode mode : AmariteLongswordItem.getModes(player)) {
            mode.absorbDamage(8.0F);
         }
      }

      entity.field_6008 = 0;
      entity.method_5643(AmariteDamageTypes.budding(), 2.0F);
      entity.field_6008 = 0;
   }

   public boolean method_5552(int duration, int amplifier) {
      int i = 32 >> amplifier;
      return i > 0 ? duration % i == 0 : true;
   }
}
