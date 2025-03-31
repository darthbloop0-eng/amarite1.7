package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class AmariteBlock extends class_2248 {
   public static final class_2753 FACING = class_2741.field_12525;
   public static final int PARTICLE_COLOR = -5479456;

   public AmariteBlock(class_2251 settings) {
      super(settings);
      this.method_9590((class_2680)((class_2680)this.field_10647.method_11664()).method_11657(FACING, class_2350.field_11036));
   }

   public void method_19286(@NotNull class_1937 world, class_2680 state, class_3965 hit, class_1676 projectile) {
      if (!world.field_9236) {
         class_2338 blockPos = hit.method_17777();
         world.method_8396(null, blockPos, class_3417.field_26982, class_3419.field_15245, 1.0F, 0.5F + world.field_9229.method_43057() * 0.6F);
         world.method_8396(null, blockPos, class_3417.field_26980, class_3419.field_15245, 1.0F, 0.5F + world.field_9229.method_43057() * 0.6F);
      } else {
         class_243 point = hit.method_17784();
         float r = 0.6745098F;
         float g = 0.3882353F;
         float b = 0.8784314F;

         for (int i = 0; i < 8; i++) {
            ParticleBuilders.create(AmariteParticles.AMARITE)
               .overrideAnimator(Animator.WITH_AGE)
               .setLifetime(16)
               .setAlpha(0.6F, 0.0F)
               .setAlphaEasing(Easing.CUBIC_IN)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor(r, g, b, 1.0F)
               .setScale(0.24F, 0.12F)
               .setSpinOffset(world.method_8409().method_43048(360))
               .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
               .randomMotion(0.1F)
               .spawn(world, point.field_1352, point.field_1351, point.field_1350);
         }
      }
   }

   public class_2680 method_9605(@NotNull class_1750 ctx) {
      return (class_2680)this.method_9564().method_11657(FACING, ctx.method_8038());
   }

   public class_2680 method_9598(@NotNull class_2680 state, @NotNull class_2470 rotation) {
      return (class_2680)state.method_11657(FACING, rotation.method_10503((class_2350)state.method_11654(FACING)));
   }

   public class_2680 method_9569(@NotNull class_2680 state, @NotNull class_2415 mirror) {
      return state.method_26186(mirror.method_10345((class_2350)state.method_11654(FACING)));
   }

   protected void method_9515(@NotNull class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{FACING});
   }
}
