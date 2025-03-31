package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class BeaconComponent implements AutoSyncedComponent, CommonTickingComponent {
   private static final int CONVERSION_TIME = 14400;
   public static final int PARTICLE_COLOR = -5479456;
   private final class_2580 beaconBlockEntity;
   private class_2338 target = null;
   private class_2248 targetBlock = null;
   private int conversionTime = 0;

   public BeaconComponent(class_2580 beaconBlockEntity) {
      this.beaconBlockEntity = beaconBlockEntity;
   }

   public void sync() {
      Amarite.BEACON.sync(this.beaconBlockEntity);
   }

   public void clientTick() {
      class_1937 world = this.beaconBlockEntity.method_10997();
      if (this.conversionTime > 0 && this.target != null && world != null) {
         for (int i = 0; i < 2; i++) {
            class_243 offset = new class_243(world.field_9229.method_43059(), world.field_9229.method_43059(), world.field_9229.method_43059()).method_1029();
            double distanceToXEdge = offset.field_1352 > 0.0 ? 0.5 / offset.field_1352 : 0.5 / Math.abs(offset.field_1352);
            double distanceToYEdge = offset.field_1351 > 0.0 ? 0.5 / offset.field_1351 : 0.5 / Math.abs(offset.field_1351);
            double distanceToZEdge = offset.field_1350 > 0.0 ? 0.5 / offset.field_1350 : 0.5 / Math.abs(offset.field_1350);
            double distanceToEdge = Math.min(distanceToXEdge, Math.min(distanceToYEdge, distanceToZEdge));
            class_243 pos = class_243.method_24953(this.target).method_1019(offset.method_1021(distanceToEdge));
            world.method_8406(
               new class_2388(class_2398.field_11217, world.method_8320(this.target)), pos.field_1352, pos.field_1351, pos.field_1350, 0.0, 0.0, 0.0
            );
         }

         float r = 0.6745098F;
         float g = 0.3882353F;
         float b = 0.8784314F;
         ParticleBuilders.create(AmariteParticles.AMARITE)
            .overrideAnimator(Animator.WITH_AGE)
            .setLifetime(16)
            .setAlpha(0.6F, 0.0F)
            .setAlphaEasing(Easing.CUBIC_IN)
            .setColorCoefficient(0.8F)
            .setColorEasing(Easing.CIRC_OUT)
            .setSpinEasing(Easing.SINE_IN)
            .setColor(r, g, b, 1.0F)
            .setScale(0.16F, 0.08F)
            .setSpinOffset(world.method_8409().method_43048(360))
            .setSpin(world.method_8409().method_43056() ? 0.3F : -0.3F)
            .randomMotion(0.04)
            .spawnAtEdges(world, this.target);
         if (this.conversionTime == 1) {
            for (int i = 0; i < 128; i++) {
               ParticleBuilders.create(AmariteParticles.AMARITE)
                  .overrideAnimator(Animator.WITH_AGE)
                  .setLifetime(32)
                  .setAlpha(0.6F, 0.0F)
                  .setAlphaEasing(Easing.CUBIC_IN)
                  .setColorCoefficient(0.8F)
                  .setColorEasing(Easing.CIRC_OUT)
                  .setSpinEasing(Easing.SINE_IN)
                  .setColor(r, g, b, 1.0F)
                  .setScale(0.24F, 0.12F)
                  .setSpinOffset(world.method_8409().method_43048(360))
                  .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
                  .randomMotion(0.2)
                  .spawnAtEdges(world, this.target);
            }
         }
      }

      this.tick();
   }

   public void serverTick() {
      class_1937 world = this.beaconBlockEntity.method_10997();
      if (this.conversionTime > 0 && world != null) {
         if (this.target == null || this.targetBlock == null || world.method_8320(this.target).method_26204() != this.targetBlock) {
            this.target = null;
            this.targetBlock = null;
            this.conversionTime = 0;
            this.sync();
            return;
         }

         if (this.conversionTime == 1 && this.targetBlock == class_2246.field_27159) {
            world.method_8501(this.target, class_2246.field_27160.method_9564());
         }
      }

      this.tick();
   }

   public void tick() {
      if (this.conversionTime > 0) {
         this.conversionTime--;
         if (this.conversionTime == 1 || this.conversionTime == 0) {
            this.sync();
         }
      } else {
         this.target = null;
      }
   }

   public class_2338 getTarget() {
      return this.target;
   }

   public void startConversion(class_2338 target) {
      if (this.beaconBlockEntity.method_10997() != null && !target.equals(this.target)) {
         this.target = target;
         this.targetBlock = this.beaconBlockEntity.method_10997().method_8320(target).method_26204();
         this.conversionTime = 14400;
         this.sync();
      }
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.conversionTime = tag.method_10550("conversionTime");
      if (tag.method_10545("target")) {
         class_2487 targetTag = tag.method_10562("target");
         this.target = new class_2338(targetTag.method_10550("x"), targetTag.method_10550("y"), targetTag.method_10550("z"));
      }
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      tag.method_10569("conversionTime", this.conversionTime);
      if (this.target != null) {
         class_2487 targetTag = new class_2487();
         targetTag.method_10569("x", this.target.method_10263());
         targetTag.method_10569("y", this.target.method_10264());
         targetTag.method_10569("z", this.target.method_10260());
         tag.method_10566("target", targetTag);
      }
   }
}
