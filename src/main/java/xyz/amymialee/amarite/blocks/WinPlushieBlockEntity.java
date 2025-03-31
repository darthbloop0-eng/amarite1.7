package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.amarite.registry.AmariteBlocks;

public class WinPlushieBlockEntity extends class_2586 {
   public double squash;

   public WinPlushieBlockEntity(class_2338 pos, class_2680 state) {
      super(AmariteBlocks.WIN_PLUSHIE_BLOCK_ENTITY, pos, state);
   }

   public void squish(int squash) {
      this.squash += squash;
      if (this.field_11863 != null) {
         this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 2);
      }

      this.method_5431();
   }

   public static void tick(class_1937 world, class_2338 pos, class_2680 state, @NotNull WinPlushieBlockEntity spark) {
      if (spark.squash > 0.0) {
         spark.squash /= 3.0;
         if (spark.squash < 0.01F) {
            spark.squash = 0.0;
            if (world != null) {
               world.method_8413(pos, state, state, 2);
            }
         }
      }
   }

   protected void method_11007(@NotNull class_2487 nbt) {
      nbt.method_10549("squash", this.squash);
   }

   public void method_11014(@NotNull class_2487 nbt) {
      this.squash = nbt.method_10574("squash");
   }

   @Nullable
   public class_2596<class_2602> method_38235() {
      return class_2622.method_38585(this);
   }

   public class_2487 method_16887() {
      return this.method_38244();
   }
}
