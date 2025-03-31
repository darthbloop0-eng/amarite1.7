package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

public class BuddingAmariteBlock extends AmariteBlock {
   public BuddingAmariteBlock(class_2251 settings) {
      super(settings);
   }

   public void method_9514(class_2680 state, class_3218 world, class_2338 pos, @NotNull class_5819 random) {
      if (random.method_43048(6) == 0) {
         for (class_2350 direction : class_2350.values()) {
            if (direction.method_10166() != class_2351.field_11052) {
               class_2338 offsetPos = pos.method_10093(direction);
               class_2680 offsetState = world.method_8320(offsetPos);
               if (!offsetState.method_27852(class_2246.field_33510)) {
                  revert(world, pos);
                  return;
               }
            }
         }

         if (!world.method_8320(pos.method_10093(class_2350.field_11033)).method_27852(class_2246.field_10092)) {
            revert(world, pos);
            return;
         }

         class_2338 blockPos = pos.method_10093(class_2350.field_11036);
         class_2680 blockState = world.method_8320(blockPos);
         class_2248 block = null;
         if (canGrowIn(blockState)) {
            block = AmariteBlocks.FRESH_AMARITE_BUD;
         } else if (blockState.method_27852(AmariteBlocks.FRESH_AMARITE_BUD) && blockState.method_11654(AmariteBlock.FACING) == class_2350.field_11036) {
            block = AmariteBlocks.PARTIAL_AMARITE_BUD;
         } else if (blockState.method_27852(AmariteBlocks.PARTIAL_AMARITE_BUD) && blockState.method_11654(AmariteBlock.FACING) == class_2350.field_11036) {
            block = AmariteBlocks.AMARITE_CLUSTER;
         }

         if (block != null) {
            class_2680 newState = (class_2680)((class_2680)block.method_9564().method_11657(class_5542.field_27087, class_2350.field_11036))
               .method_11657(class_5542.field_27086, blockState.method_26227().method_15772() == class_3612.field_15910);
            world.method_8501(blockPos, newState);
            ServerPlayNetworking.send(
               world.method_18766(e -> pos.method_19769(e.method_19538(), 64.0)), Amarite.id("budgrow"), PacketByteBufs.create().method_10807(blockPos)
            );
         }
      }
   }

   private static void revert(@NotNull class_3218 world, class_2338 pos) {
      world.method_8501(pos, class_2246.field_27160.method_9564());
      class_243 particlePos = class_243.method_24953(pos);
      world.method_14199(class_2398.field_11251, particlePos.method_10216(), particlePos.method_10214(), particlePos.method_10215(), 24, 0.25, 0.25, 0.25, 0.0);
      world.method_8486(pos.method_10263(), pos.method_10264(), pos.method_10260(), AmariteSoundEvents.AMARITE_FORMS, null, 1.0F, 0.6F, false);
   }

   public static boolean canGrowIn(@NotNull class_2680 state) {
      return state.method_26215()
         || state.method_27852(class_2246.field_27164)
         || state.method_27852(class_2246.field_27163)
         || state.method_27852(class_2246.field_27162)
         || state.method_27852(class_2246.field_27161);
   }
}
