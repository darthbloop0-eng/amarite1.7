package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;

public class AmethystPressurePlateBlock extends class_2231 {
   public static final class_2746 POWERED = class_2741.field_12484;

   public AmethystPressurePlateBlock(class_2251 settings) {
      super(settings);
      this.method_9590((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(POWERED, Boolean.FALSE));
   }

   protected int method_9435(@NotNull class_2680 state) {
      return state.method_11654(POWERED) ? 15 : 0;
   }

   protected class_2680 method_9432(@NotNull class_2680 state, int rsOut) {
      return (class_2680)state.method_11657(POWERED, rsOut > 0);
   }

   protected void method_9436(@NotNull class_1936 world, class_2338 pos) {
      world.method_8396(null, pos, class_3417.field_26941, class_3419.field_15245, 0.9F, 1.6F);
      world.method_8396(null, pos, class_3417.field_15217, class_3419.field_15245, 0.3F, 0.7F);
   }

   protected void method_9438(@NotNull class_1936 world, class_2338 pos) {
      world.method_8396(null, pos, class_3417.field_26941, class_3419.field_15245, 0.9F, 1.1F);
      world.method_8396(null, pos, class_3417.field_15116, class_3419.field_15245, 0.3F, 0.6F);
   }

   protected int method_9434(@NotNull class_1937 world, class_2338 pos) {
      for (class_1657 entity : world.method_18467(class_1657.class, field_9941.method_996(pos))) {
         if (!entity.method_5696()) {
            return 15;
         }
      }

      return 0;
   }

   protected void method_9515(@NotNull class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{POWERED});
   }
}
