package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteDamageTypes;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteParticles;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;
import xyz.amymialee.mialib.raycasting.MRaycasting;

public class LongswordAccumulateComponent implements AutoSyncedComponent, CommonTickingComponent, AmariteLongswordItem.LongswordMode {
   public static final class_2960 ACCUMULATE_TARGETS = Amarite.id("accumulate");
   public static final int ACCUMULATE_MAX_CHARGE = 800;
   public static final int ACCUMULATE_TICK_COST = 13;
   public static final int ACCUMULATE_COLOR = -1082054;
   private final class_1657 player;
   private final List<class_1297> accumulateTargets = new ArrayList<>();
   private int accumulateCharge = 800;
   public boolean accumulateActive = false;
   public int accumulateActiveTicks = 0;

   public LongswordAccumulateComponent(class_1657 player) {
      this.player = player;
   }

   private void sync() {
      Amarite.ACCUMULATE.sync(this.player);
   }

   public void serverTick() {
      if (this.accumulateActive && !this.accumulateTargets.isEmpty()) {
         class_243 userAnchor = this.player.method_33571().method_1019(this.player.method_5828(1.0F).method_1021(1.32));

         for (int i = 0; i < this.accumulateTargets.size(); i++) {
            class_1297 target = this.accumulateTargets.get(i);
            if (!(target instanceof class_1665)) {
               target.method_18799(
                  target.method_18798()
                     .method_1021(0.4)
                     .method_1019(
                        userAnchor.method_1020(target.method_19538().method_1023(0.0, -target.method_17682() / 2.0F, 0.0)).method_1029().method_1021(0.15)
                     )
               );
               target.field_6037 = true;
            }

            if (target instanceof class_1309) {
               target.field_6008 = 0;
               target.method_5643(AmariteDamageTypes.accumulate(this.player), 0.04F);
               target.field_6008 = 0;
            }

            if (!this.player.method_29504()) {
               this.player.method_6033(this.player.method_6032() + 0.06F / (i + 1));
            }
         }
      }

      this.tick();
   }

   public void clientTick() {
      if (this.accumulateActive) {
         float r = 0.9372549F;
         float g = 0.49019608F;
         float b = 0.22745098F;
         if (this.player.field_6012 % 2 == 0 && class_310.method_1551().field_1724 == this.player) {
            List<class_1297> targets = MRaycasting.raycast(this.player, 96.0, (p, e) -> e.method_5668() != this.player.method_5668(), 0, 0.48);
            ClientPlayNetworking.send(ACCUMULATE_TARGETS, PacketByteBufs.create().method_10806(targets.stream().mapToInt(class_1297::method_5628).toArray()));

            for (class_1297 entity : targets) {
               for (int i = 0; i < 4; i++) {
                  class_243 pos = entity.method_19538().method_1031(0.0, this.player.method_17682() / 2.0F, 0.0);
                  ParticleBuilders.create(AmariteParticles.AMARITE)
                     .overrideAnimator(Animator.WITH_AGE)
                     .setLifetime(12)
                     .setAlpha(0.6F, 0.0F)
                     .setAlphaEasing(Easing.CUBIC_IN)
                     .setColorCoefficient(0.8F)
                     .setColorEasing(Easing.CIRC_OUT)
                     .setSpinEasing(Easing.SINE_IN)
                     .setColor(r, g, b, 1.0F)
                     .setScale(0.3F, 0.12F)
                     .setSpinOffset(this.player.method_6051().method_43048(360))
                     .setSpin(this.player.method_6051().method_43056() ? 0.5F : -0.5F)
                     .randomMotion(0.2)
                     .randomOffset(entity.method_17681() / 2.0F, entity.method_17682() / 2.0F)
                     .spawn(this.player.field_6002, pos.field_1352, pos.field_1351, pos.field_1350);
               }
            }
         }

         if (this.player.field_6012 % 4 == 0) {
            this.player
               .field_6002
               .method_8486(
                  this.player.method_23317(),
                  this.player.method_23318(),
                  this.player.method_23321(),
                  AmariteSoundEvents.SWORD_ACCUMULATE,
                  class_3419.field_15248,
                  3.0F,
                  2.0F,
                  false
               );
         }

         class_3965 hit = this.player
            .field_6002
            .method_17742(
               new class_3959(
                  this.player.method_33571(),
                  this.player.method_33571().method_1019(this.player.method_5720().method_1021(64.0)),
                  class_3960.field_17558,
                  class_242.field_1348,
                  this.player
               )
            );
         class_243 hitPos = hit.method_17784();
         double totalDist = this.player.method_33571().method_1022(hitPos);

         for (int i = 0; i < 4; i++) {
            double dist = this.player.method_6051().method_43057() * totalDist;
            class_243 point = this.player.method_33571().method_1019(this.player.method_5720().method_1021(dist));
            class_243 motion = this.player.method_5720().method_1021(-dist / 36.0);
            ParticleBuilders.create(AmariteParticles.ACCUMULATION)
               .overrideAnimator(Animator.WITH_AGE)
               .setLifetime(36)
               .setAlpha(0.6F, 0.0F)
               .setAlphaEasing(Easing.CUBIC_IN)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor(r, g, b, 1.0F)
               .setScale((float)(dist / 16.0), 0.12F)
               .setSpinOffset(this.player.method_6051().method_43048(360))
               .setSpin(this.player.method_6051().method_43056() ? 0.5F : -0.5F)
               .setMotion(motion.field_1352, motion.field_1351, motion.field_1350)
               .spawn(this.player.field_6002, point.field_1352, point.field_1351, point.field_1350);
         }
      }

      this.tick();
   }

   public void tick() {
      if (!this.accumulateActive) {
         this.accumulateActiveTicks = 0;
         if (this.accumulateCharge < 800) {
            this.accumulateCharge++;
            if (this.accumulateCharge == 800) {
               this.sync();
            }
         }
      } else {
         this.accumulateActiveTicks++;
         if (!this.player.method_7337()) {
            this.accumulateCharge -= 13;
         }

         if (this.accumulateCharge <= 0
            || this.accumulateActiveTicks > 2 && this.player.mialib$startedAttacking()
            || AmariteLongswordItem.getMode(this.player, this.player.method_6047()) != this) {
            this.accumulateActive = false;
            this.sync();
         }
      }
   }

   @Override
   public void absorbDamage(float base) {
      this.accumulateCharge += (int)(base * 20.0F);
      if (this.accumulateCharge > 1600) {
         this.accumulateCharge = 1600;
      }

      this.sync();
   }

   @Override
   public void useAbility() {
      if (this.accumulateCharge >= 800 || this.player.method_7337()) {
         this.accumulateTargets.clear();
         this.accumulateActive = true;
         this.sync();
      }
   }

   @Override
   public int getModeColor() {
      return -1082054;
   }

   @Override
   public int getSwordTint() {
      float percent = class_3532.method_15363(this.getChargeProgress(), 0.0F, 1.0F);
      int r = (int)(255.0F - percent * 16.0F);
      int g = (int)(255.0F - percent * 130.0F);
      int b = (int)(255.0F - percent * 197.0F);
      return class_3532.method_15383(r, g, b);
   }

   @Override
   public float getChargeProgress() {
      return this.accumulateCharge / 800.0F;
   }

   @Override
   public String getTranslationKey() {
      return AmariteItems.AMARITE_LONGSWORD.method_7876() + ".accumulate";
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.accumulateCharge = tag.method_10550("accumulateCharge");
      this.accumulateActive = tag.method_10577("accumulateActive");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      if (this.accumulateCharge != 0) {
         tag.method_10569("accumulateCharge", this.accumulateCharge);
      }

      if (this.accumulateActive) {
         tag.method_10556("accumulateActive", true);
      }
   }

   static {
      ServerPlayNetworking.registerGlobalReceiver(ACCUMULATE_TARGETS, (server, player, handler, buf, responseSender) -> {
         int[] array = buf.method_10787();
         server.execute(() -> {
            LongswordAccumulateComponent component = (LongswordAccumulateComponent)Amarite.ACCUMULATE.get(player);
            component.accumulateTargets.clear();

            for (int id : array) {
               class_1297 entity = player.field_6002.method_8469(id);
               if (entity != null) {
                  component.accumulateTargets.add(entity);
               }
            }
         });
      });
   }
}
