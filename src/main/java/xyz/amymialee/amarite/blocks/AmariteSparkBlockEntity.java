package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.BuddedComponent;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class AmariteSparkBlockEntity extends class_2586 {
   private static final double RANGE = 56.0;
   private int powerTime;

   public AmariteSparkBlockEntity(class_2338 pos, class_2680 state) {
      super(AmariteBlocks.AMARITE_SPARK_BLOCK_ENTITY, pos, state);
   }

   public static void tick(class_1937 world, class_2338 pos, class_2680 state, @NotNull AmariteSparkBlockEntity spark) {
      if (world instanceof class_3218) {
         if (world.method_8510() % 20L == 0L && spark.isPowered()) {
            spark.setPowered(spark.powerTime - 1);

            for (class_1657 player : world.method_18467(class_1657.class, class_238.method_30048(class_243.method_24953(pos), 56.0, 56.0, 56.0))) {
               if (pos.method_19771(player.method_24515(), 56.0)) {
                  if (((BuddedComponent)Amarite.BUDDED.get(player)).getBudTime() > 0) {
                     player.method_6092(new class_1293(AmariteEntities.BUDDING, 24, 0, false, false, true));
                  }

                  for (AmariteLongswordItem.LongswordMode mode : AmariteLongswordItem.getModes(player)) {
                     mode.absorbDamage(0.1F);
                  }
               }
            }
         }
      } else if (spark.isPowered() || world.method_8482(pos) > 0) {
         clientTick(world, pos, state, spark);
      }
   }

   private static void clientTick(class_1937 world, class_2338 pos, class_2680 state, @NotNull AmariteSparkBlockEntity spark) {
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         if (pos.method_19771(player.method_24515(), 112.0)) {
            class_2350 direction = (class_2350)state.method_11654(AmariteSparkBlock.FACING);
            float r = 0.6745098F;
            float g = 0.3882353F;
            float b = 0.8784314F;
            class_243 point = class_243.method_24953(pos);

            for (int i = 0; i < 2; i++) {
               class_243 motion = class_243.method_24954(direction.method_10163()).method_1021(world.field_9229.method_43057() * 0.65F);
               ParticleBuilders.create(AmariteParticles.AMARITE)
                  .overrideAnimator(Animator.WITH_AGE)
                  .setLifetime(8)
                  .setAlpha(0.6F, 0.0F)
                  .setAlphaEasing(Easing.CUBIC_IN)
                  .setColorCoefficient(0.8F)
                  .setColorEasing(Easing.CIRC_OUT)
                  .setSpinEasing(Easing.SINE_IN)
                  .setColor(r, g, b, 1.0F)
                  .setScale(0.24F, 0.12F)
                  .setSpinOffset(world.method_8409().method_43048(360))
                  .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
                  .addMotion(motion.field_1352, motion.field_1351, motion.field_1350)
                  .randomOffset(0.2F)
                  .spawn(world, point.field_1352, point.field_1351, point.field_1350);
            }

            if (world.method_8510() % 20L == 0L && spark.isPowered()) {
               for (int i = 0; i < 16; i++) {
                  ParticleBuilders.create(AmariteParticles.AMARITE)
                     .overrideAnimator(Animator.WITH_AGE)
                     .setLifetime(24)
                     .setAlpha(0.6F, 0.0F)
                     .setAlphaEasing(Easing.CUBIC_IN)
                     .setColorCoefficient(0.8F)
                     .setColorEasing(Easing.CIRC_OUT)
                     .setSpinEasing(Easing.SINE_IN)
                     .setColor(r, g, b, 1.0F)
                     .setScale(0.24F, 0.12F)
                     .setSpinOffset(world.method_8409().method_43048(360))
                     .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
                     .randomMotion(0.32)
                     .randomOffset(0.2F)
                     .spawn(world, point.field_1352, point.field_1351, point.field_1350);
               }
            }

            for (int i = 0; i < 1; i++) {
               ParticleBuilders.create(AmariteParticles.AMARITE)
                  .overrideAnimator(Animator.WITH_AGE)
                  .setLifetime(64)
                  .setAlpha(0.6F, 0.0F)
                  .setAlphaEasing(Easing.CUBIC_IN)
                  .setColorCoefficient(0.8F)
                  .setColorEasing(Easing.CIRC_OUT)
                  .setSpinEasing(Easing.SINE_IN)
                  .setColor(r, g, b, 1.0F)
                  .setScale(0.24F, 0.12F)
                  .setSpinOffset(world.method_8409().method_43048(360))
                  .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
                  .randomMotion(0.02F)
                  .randomOffset(28.0)
                  .spawn(world, point.field_1352, point.field_1351, point.field_1350);
            }
         }
      }
   }

   public boolean isPowered() {
      return this.powerTime > 0;
   }

   public void setPowered(int powerTime) {
      this.powerTime = powerTime;
      if (this.field_11863 != null) {
         this.field_11863.method_8413(this.field_11867, this.method_11010(), this.method_11010(), 2);
      }
   }

   protected void method_11007(@NotNull class_2487 nbt) {
      nbt.method_10569("powerTime", this.powerTime);
   }

   public void method_11014(@NotNull class_2487 nbt) {
      this.powerTime = nbt.method_10550("powerTime");
   }

   @Nullable
   public class_2596<class_2602> method_38235() {
      return class_2622.method_38585(this);
   }

   public class_2487 method_16887() {
      return this.method_38244();
   }
}
