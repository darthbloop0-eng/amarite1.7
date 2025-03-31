package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

public class WinPlushieBlock extends class_2237 {
   public static final class_2753 FACING = class_2741.field_12481;
   private static final class_265 SHAPE = method_9541(3.0, 0.0, 3.0, 13.0, 15.0, 13.0);

   public WinPlushieBlock(class_2251 settings) {
      super(settings);
   }

   public class_2464 method_9604(class_2680 state) {
      return class_2464.field_11456;
   }

   public void method_9606(class_2680 state, @NotNull class_1937 world, class_2338 pos, class_1657 player) {
      if (!world.field_9236) {
         class_243 mid = class_243.method_24953(pos);
         float pitch = 1.2F + world.field_9229.method_43057() * 0.4F;
         class_2680 note = world.method_8320(pos.method_10074());
         if (note.method_28498(class_2741.field_12524)) {
            pitch = (float)Math.pow(2.0, ((Integer)note.method_11654(class_2741.field_12524) - 12) / 12.0);
         }

         world.method_43128(
            null, mid.method_10216(), mid.method_10214(), mid.method_10215(), AmariteSoundEvents.WIN_PLUSHIE, class_3419.field_15245, 1.0F, pitch
         );
         if (world.method_8321(pos) instanceof WinPlushieBlockEntity plushie) {
            plushie.squish(24);
         }

         player.method_6092(new class_1293(class_1294.field_5926, 100, 0));
      }
   }

   protected void method_33614(class_1937 world, class_1657 player, class_2338 pos, class_2680 state) {
      if (world.method_8321(pos) instanceof WinPlushieBlockEntity plushie) {
         plushie.squish(4);
      }
   }

   public class_1269 method_9534(class_2680 state, @NotNull class_1937 world, class_2338 pos, class_1657 player, class_1268 hand, class_3965 hit) {
      if (!world.field_9236) {
         class_243 mid = class_243.method_24953(pos);
         float pitch = 0.8F + world.field_9229.method_43057() * 0.4F;
         class_2680 note = world.method_8320(pos.method_10074());
         if (note.method_28498(class_2741.field_12524)) {
            pitch = (float)Math.pow(2.0, ((Integer)note.method_11654(class_2741.field_12524) - 12) / 12.0);
         }

         world.method_43128(
            null, mid.method_10216(), mid.method_10214(), mid.method_10215(), AmariteSoundEvents.WIN_PLUSHIE, class_3419.field_15245, 1.0F, pitch
         );
         if (world.method_8321(pos) instanceof WinPlushieBlockEntity plushie) {
            plushie.squish(1);
         }

         player.method_6092(new class_1293(class_1294.field_5926, 100, 0));
      }

      return class_1269.field_5812;
   }

   public class_265 method_9530(class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
      return SHAPE;
   }

   public boolean method_9526(class_2680 state) {
      return true;
   }

   @Nullable
   public <T extends class_2586> class_5558<T> method_31645(class_1937 world, class_2680 state, class_2591<T> type) {
      return method_31618(type, AmariteBlocks.WIN_PLUSHIE_BLOCK_ENTITY, WinPlushieBlockEntity::tick);
   }

   @Nullable
   public class_2586 method_10123(class_2338 pos, class_2680 state) {
      return new WinPlushieBlockEntity(pos, state);
   }

   public class_2680 method_9605(@NotNull class_1750 ctx) {
      return (class_2680)this.method_9564().method_11657(FACING, ctx.method_8042().method_10153());
   }

   public class_2680 method_9598(class_2680 state, class_2470 rotation) {
      return (class_2680)state.method_11657(FACING, rotation.method_10503((class_2350)state.method_11654(FACING)));
   }

   public class_2680 method_9569(class_2680 state, class_2415 mirror) {
      return state.method_26186(mirror.method_10345((class_2350)state.method_11654(FACING)));
   }

   protected void method_9515(@NotNull class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{FACING});
   }
}
