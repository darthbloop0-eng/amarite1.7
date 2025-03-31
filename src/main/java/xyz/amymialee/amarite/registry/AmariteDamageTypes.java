package xyz.amymialee.amarite.registry;

import net.minecraft.class_1282;
import net.minecraft.class_1284;
import net.minecraft.class_1285;
import net.minecraft.class_1297;
import org.jetbrains.annotations.NotNull;

public interface AmariteDamageTypes {
   static class_1282 budding(@NotNull class_1297... sourceAttacker) {
      return sourceAttacker.length == 1
         ? new class_1285("amarite.budding", sourceAttacker[0]).method_44328()
         : new class_1282("amarite.budding").method_44328();
   }

   static class_1282 dash(@NotNull class_1297... sourceAttacker) {
      return sourceAttacker.length == 1 ? new class_1285("amarite.dash", sourceAttacker[0]).method_44328() : new class_1282("amarite.dash").method_44328();
   }

   static class_1282 accumulate(@NotNull class_1297... sourceAttacker) {
      return sourceAttacker.length == 1
         ? new class_1285("amarite.accumulate", sourceAttacker[0]).method_5509()
         : new class_1282("amarite.accumulate").method_5509();
   }

   static class_1282 malignancy(@NotNull class_1297... sourceAttacker) {
      if (sourceAttacker.length == 1) {
         return new class_1285("amarite.malignancy", sourceAttacker[0]).method_5508().method_5515().method_5509();
      } else {
         return sourceAttacker.length == 2
            ? new class_1284("amarite.malignancy", sourceAttacker[0], sourceAttacker[1]).method_5508().method_5515().method_5509()
            : new class_1282("amarite.malignancy").method_5508().method_5515().method_5509();
      }
   }

   static class_1282 disc(@NotNull class_1297... sourceAttacker) {
      if (sourceAttacker.length == 1) {
         return new class_1285("amarite.disc", sourceAttacker[0]).method_5508().method_5515().method_5509();
      } else {
         return sourceAttacker.length == 2
            ? new class_1284("amarite.disc", sourceAttacker[0], sourceAttacker[1]).method_5508().method_5515().method_5509()
            : new class_1282("amarite.disc").method_5508().method_5515().method_5509();
      }
   }

   static void init() {
   }
}
