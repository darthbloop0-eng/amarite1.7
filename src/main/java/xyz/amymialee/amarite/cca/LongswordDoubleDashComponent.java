package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteParticles;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

public class LongswordDoubleDashComponent implements AutoSyncedComponent, CommonTickingComponent, AmariteLongswordItem.LongswordMode {
   public static final int DOUBLE_DASH_MAX_CHARGE = 240;
   public static final int DOUBLE_DASH_MAX_DURATION = 3;
   public static final int DOUBLE_DASH_COLOR = -11627802;
   private final class_1657 player;
   private int doubleDashCharge = 240;
   private int doubleDashDuration = 0;

   public LongswordDoubleDashComponent(class_1657 player) {
      this.player = player;
   }

   private void sync() {
      Amarite.DOUBLE_DASH.sync(this.player);
   }

   public void serverTick() {
      if (this.isDashing()) {
         this.player.field_6017 = 0.0F;
      }

      this.tick();
   }

   public void clientTick() {
      if (this.isDashing()) {
         this.player.method_18799(this.player.method_5720().method_18805(3.0, 1.0, 3.0));
         class_746 clientPlayer = class_310.method_1551().field_1724;
         if (clientPlayer != null) {
            double offset = 0.0;
            if (clientPlayer != this.player) {
               double dist = clientPlayer.method_33571().method_1022(this.player.method_19538());
               double oldDist = clientPlayer.method_33571().method_1022(new class_243(this.player.field_6014, this.player.field_6036, this.player.field_5969));
               offset = class_3532.method_16436(-0.2, 0.2, dist - oldDist);
            }

            this.player
               .field_6002
               .method_8486(
                  this.player.method_23317(),
                  this.player.method_23318(),
                  this.player.method_23321(),
                  AmariteSoundEvents.SWORD_DASH,
                  class_3419.field_15248,
                  1.0F,
                  (float)(1.2F + offset),
                  false
               );
         }

         float r = 0.30588236F;
         float g = 0.57254905F;
         float b = 0.9019608F;
         class_243 motion = this.player.method_18798();

         for (int i = 0; i < 4; i++) {
            class_243 point = this.player.method_19538().method_1031(0.0, this.player.method_17682() / 2.0F, 0.0);
            ParticleBuilders.create(AmariteParticles.ACCUMULATION)
               .overrideAnimator(Animator.WITH_AGE)
               .setLifetime(8)
               .setAlpha(0.6F, 0.0F)
               .setAlphaEasing(Easing.CUBIC_IN)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor(r, g, b, 1.0F)
               .setScale(1.0F, 0.12F)
               .setSpinOffset(this.player.method_6051().method_43048(360))
               .setSpin(this.player.method_6051().method_43056() ? 0.5F : -0.5F)
               .setMotion(motion.field_1352, motion.field_1351, motion.field_1350)
               .spawn(this.player.field_6002, point.field_1352, point.field_1351, point.field_1350);
         }

         for (int i = 0; i < 8; i++) {
            class_243 point = this.player.method_19538().method_1031(0.0, this.player.method_17682() / 2.0F, 0.0);
            class_243 randVel = motion.method_1019(
                  new class_243(this.player.method_6051().method_43057(), this.player.method_6051().method_43057(), this.player.method_6051().method_43057())
                     .method_1023(0.5, 0.5, 0.5)
                     .method_1021(0.6)
               )
               .method_1029()
               .method_1021(motion.method_1033());
            ParticleBuilders.create(AmariteParticles.AMARITE)
               .overrideAnimator(Animator.WITH_AGE)
               .setLifetime(6)
               .setAlpha(0.6F, 0.0F)
               .setAlphaEasing(Easing.CUBIC_IN)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor(r, g, b, 1.0F)
               .setScale(0.24F, 0.12F)
               .setSpinOffset(this.player.method_6051().method_43048(360))
               .setSpin(this.player.method_6051().method_43056() ? 0.5F : -0.5F)
               .setMotion(randVel.field_1352, randVel.field_1351, randVel.field_1350)
               .spawn(this.player.field_6002, point.field_1352, point.field_1351, point.field_1350);
         }
      }

      this.tick();
   }

   public void tick() {
      if (!this.isDashing()) {
         if (this.doubleDashCharge < 240) {
            this.doubleDashCharge++;
            if (this.doubleDashCharge == 240) {
               this.sync();
            }
         }
      } else {
         this.doubleDashDuration--;
         if (this.doubleDashDuration == 0) {
            this.sync();
         }
      }
   }

   public boolean isDashing() {
      return this.doubleDashDuration > 0;
   }

   @Override
   public void absorbDamage(float base) {
      this.doubleDashCharge += (int)(base * 20.0F);
      if (this.doubleDashCharge > 480) {
         this.doubleDashCharge = 480;
      }

      this.sync();
   }

   @Override
   public void useAbility() {
      if (this.doubleDashCharge >= 120 || this.player.method_7337()) {
         if (!this.player.method_7337()) {
            this.doubleDashCharge -= 120;
         }

         this.doubleDashDuration = 3;
         this.sync();
      }
   }

   @Override
   public int getModeColor() {
      return -11627802;
   }

   @Override
   public int getSwordTint() {
      float percent = class_3532.method_15363(this.getChargeProgress() * 2.0F, 0.0F, 1.0F);
      int r = (int)(255.0F - percent * 177.0F);
      int g = (int)(255.0F - percent * 109.0F);
      int b = (int)(255.0F - percent * 25.0F);
      return class_3532.method_15383(r, g, b);
   }

   @Override
   public float getChargeProgress() {
      return this.doubleDashCharge / 240.0F;
   }

   @Override
   public String getTranslationKey() {
      return AmariteItems.AMARITE_LONGSWORD.method_7876() + ".double_dash";
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.doubleDashCharge = tag.method_10550("dashCharge");
      this.doubleDashDuration = tag.method_10550("dashDuration");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      if (this.doubleDashCharge != 0) {
         tag.method_10569("dashCharge", this.doubleDashCharge);
      }

      if (this.doubleDashDuration != 0) {
         tag.method_10569("dashDuration", this.doubleDashDuration);
      }
   }
}
