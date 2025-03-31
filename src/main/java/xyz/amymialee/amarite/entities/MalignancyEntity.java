package xyz.amymialee.amarite.entities;

import com.sammy.lodestone.setup.LodestoneParticles;
import com.sammy.lodestone.systems.rendering.particle.Easing;
import com.sammy.lodestone.systems.rendering.particle.ParticleBuilders;
import com.sammy.lodestone.systems.rendering.particle.SimpleParticleEffect.Animator;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_1293;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1313;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2596;
import net.minecraft.class_2604;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import xyz.amymialee.amarite.registry.AmariteDamageTypes;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class MalignancyEntity extends class_1297 {
   public static final class_2940<Float> POWER = class_2945.method_12791(MalignancyEntity.class, class_2943.field_13320);
   public static final int TOTAL_DURATION = 320;
   public static final int ACTIVATION_TIME = 80;
   @Nullable
   private UUID ownerUuid;
   @Nullable
   private class_1297 owner;
   public boolean firing;
   @ClientOnly
   public float rotation;

   public MalignancyEntity(class_1299<? extends MalignancyEntity> entityType, class_1937 world) {
      super(entityType, world);
      this.field_5960 = true;
   }

   public void method_5773() {
      super.method_5773();
      this.field_6014 = this.method_23317();
      this.field_6036 = this.method_23318();
      this.field_5969 = this.method_23321();
      class_243 velocity = this.method_18798();
      if (this.method_18798().method_37268() > 1.0E-5F || (this.field_6012 + this.method_5628()) % 4 == 0) {
         this.method_5784(class_1313.field_6308, this.method_18798());
         float g = 0.8F;
         this.method_18799(this.method_18798().method_18805(g, g, g));
      }

      if (!this.field_6002.field_9236) {
         double d = this.method_18798().method_1020(velocity).method_1027();
         if (d > 0.01) {
            this.field_6007 = true;
         }
      }

      if (this.field_6012 >= 80) {
         Float power = (Float)this.field_6011.method_12789(POWER);
         List<class_1297> list = this.field_6002
            .method_8335(this, class_238.method_30048(this.method_19538(), power.floatValue(), power.floatValue(), power.floatValue()));
         if (!this.field_6002.method_8608()) {
            for (class_1297 entity : list) {
               if (!this.isOwner(entity) && entity instanceof class_1309 living) {
                  if (!living.method_6059(AmariteEntities.BUDDING)) {
                     living.method_6092(new class_1293(AmariteEntities.BUDDING, 48, 0, false, false, true));
                  }

                  living.field_6008 = 0;
                  if (this.field_6012 % 40 == 0) {
                     entity.method_5643(AmariteDamageTypes.budding(), 3.0F);

                     for (class_1297 sub : this.field_6002
                        .method_8335(this, class_238.method_30048(entity.method_19538(), power / 2.0F, power / 2.0F, power / 2.0F))) {
                        if (!this.isOwner(sub) && sub != entity && sub instanceof class_1309 subTarget) {
                           if (!living.method_6059(AmariteEntities.BUDDING)) {
                              subTarget.method_6092(new class_1293(AmariteEntities.BUDDING, 48, 0, false, false, true));
                           }

                           subTarget.field_6008 = 0;
                           sub.method_5643(AmariteDamageTypes.budding(), 1.0F);
                           subTarget.field_6008 = 0;
                        }
                     }
                  }

                  living.field_6008 = 0;
               }
            }
         } else {
            float r = 0.3647059F;
            float g = 0.65882355F;
            float b = 0.08627451F;
            ParticleBuilders.create(AmariteParticles.AMARITE)
               .setLifetime(32)
               .overrideAnimator(Animator.WITH_AGE)
               .setAlpha(0.8F, 0.0F)
               .setAlphaEasing(Easing.CUBIC_IN)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor(r, g, b, 1.0F)
               .setScale(0.2F, 0.12F)
               .setSpinOffset(this.field_5974.method_43048(360))
               .setSpin(this.field_5974.method_43056() ? 0.1F : -0.1F)
               .setMotion(this.field_5974.method_43057() * 0.04 - 0.02, -0.016F, this.field_5974.method_43057() * 0.04 - 0.02)
               .randomOffset(this.method_17681() / 4.0F, this.method_17682())
               .spawn(this.field_6002, this.method_23317(), this.method_23318(), this.method_23321());
            ParticleBuilders.create(LodestoneParticles.SMOKE_PARTICLE)
               .setLifetime(32)
               .overrideAnimator(Animator.WITH_AGE)
               .setAlphaEasing(Easing.BOUNCE_IN_OUT)
               .setColorCoefficient(0.8F)
               .setColorEasing(Easing.CIRC_OUT)
               .setSpinEasing(Easing.SINE_IN)
               .setColor((1.0F - r) / 2.0F, (1.0F - g) / 2.0F, (1.0F - b) / 2.0F, 0.1F)
               .setAlpha(0.4F, 0.0F)
               .setScale(1.0F + this.field_5974.method_43057(), 1.0F + this.field_5974.method_43057())
               .setSpinOffset(this.field_5974.method_43048(360))
               .setSpin(this.field_5974.method_43056() ? 0.1F : -0.1F)
               .randomMotion(0.04, 0.01)
               .randomOffset(power / 2.0F, power / 2.0F)
               .spawn(this.field_6002, this.method_23317(), this.method_23318(), this.method_23321());
            boolean shot = false;

            for (class_1297 entityx : list) {
               if (!this.isOwner(entityx) && entityx instanceof class_1309 living) {
                  for (int i = 0; i < 6; i++) {
                     class_243 pos = this.method_19538()
                        .method_1031(
                           this.field_5974.method_43059() / 2.0,
                           this.field_5974.method_43059() / 4.0 + this.method_17682() / 2.0F,
                           this.field_5974.method_43059() / 2.0
                        );
                     class_243 motion = living.method_19538()
                        .method_1031(0.0, living.method_17682() / 2.0F, 0.0)
                        .method_1020(pos)
                        .method_1029()
                        .method_1021(0.4);
                     ParticleBuilders.create(AmariteParticles.ACCUMULATION)
                        .overrideAnimator(Animator.WITH_AGE)
                        .setLifetime(12)
                        .setAlpha(0.6F, 0.0F)
                        .setAlphaEasing(Easing.CUBIC_IN)
                        .setColorCoefficient(0.8F)
                        .setColorEasing(Easing.CIRC_OUT)
                        .setSpinEasing(Easing.SINE_IN)
                        .setColor(r, g, b, 1.0F)
                        .setScale(0.3F, 0.12F)
                        .setSpinOffset(this.field_5974.method_43048(360))
                        .setSpin(this.field_5974.method_43056() ? 0.5F : -0.5F)
                        .setMotion(motion.field_1352, motion.field_1351, motion.field_1350)
                        .spawn(this.field_6002, pos.field_1352, pos.field_1351, pos.field_1350);
                  }

                  shot = true;

                  for (class_1297 subx : this.field_6002
                     .method_8335(this, class_238.method_30048(entityx.method_19538(), power / 2.0F, power / 2.0F, power / 2.0F))) {
                     if (!this.isOwner(subx) && subx instanceof class_1309 subTarget) {
                        for (int i = 0; i < 2; i++) {
                           class_243 pos = entityx.method_19538()
                              .method_1031(
                                 this.field_5974.method_43059() / 4.0,
                                 this.field_5974.method_43059() / 6.0 + entityx.method_17682() / 2.0F,
                                 this.field_5974.method_43059() / 4.0
                              );
                           class_243 motion = subTarget.method_19538()
                              .method_1031(0.0, subTarget.method_17682() / 2.0F, 0.0)
                              .method_1020(pos)
                              .method_1029()
                              .method_1021(0.3);
                           ParticleBuilders.create(AmariteParticles.ACCUMULATION)
                              .overrideAnimator(Animator.WITH_AGE)
                              .setLifetime(12)
                              .setAlpha(0.6F, 0.0F)
                              .setAlphaEasing(Easing.CUBIC_IN)
                              .setColorCoefficient(0.8F)
                              .setColorEasing(Easing.CIRC_OUT)
                              .setSpinEasing(Easing.SINE_IN)
                              .setColor(r, g, b, 1.0F)
                              .setScale(0.3F, 0.12F)
                              .setSpinOffset(this.field_5974.method_43048(360))
                              .setSpin(this.field_5974.method_43056() ? 0.5F : -0.5F)
                              .setMotion(motion.field_1352, motion.field_1351, motion.field_1350)
                              .spawn(this.field_6002, pos.field_1352, pos.field_1351, pos.field_1350);
                        }
                     }
                  }
               }
            }

            this.firing = shot;
         }
      }

      if (this.field_6012 > 320) {
         if (!this.field_6002.method_8608()) {
            this.method_31472();
         } else {
            float r = 0.3647059F;
            float g = 0.65882355F;
            float b = 0.08627451F;

            for (int i = 0; i < 40; i++) {
               ParticleBuilders.create(AmariteParticles.AMARITE)
                  .setLifetime(64)
                  .overrideAnimator(Animator.WITH_AGE)
                  .setAlpha(1.0F, 0.0F)
                  .setAlphaEasing(Easing.CUBIC_IN)
                  .setColorCoefficient(0.8F)
                  .setColorEasing(Easing.CIRC_OUT)
                  .setSpinEasing(Easing.SINE_IN)
                  .setColor(r, g, b, 1.0F)
                  .setScale(0.2F, 0.12F)
                  .setSpinOffset(this.field_5974.method_43048(360))
                  .setSpin(this.field_5974.method_43056() ? 0.1F : -0.1F)
                  .setMotion(this.field_5974.method_43057() * 0.1 - 0.05, -0.016F, this.field_5974.method_43057() * 0.1 - 0.05)
                  .randomOffset(this.method_17681(), this.method_17682())
                  .spawn(this.field_6002, this.method_23317(), this.method_23318(), this.method_23321());
            }
         }
      }
   }

   public void setOwner(@Nullable class_1297 entity) {
      if (entity != null) {
         this.ownerUuid = entity.method_5667();
         this.owner = entity;
      }
   }

   protected boolean isOwner(@NotNull class_1297 entity) {
      return entity.method_5667().equals(this.ownerUuid);
   }

   @Nullable
   public class_1297 getOwner() {
      if (this.owner != null && !this.owner.method_31481()) {
         return this.owner;
      } else {
         return this.ownerUuid != null && this.field_6002 instanceof class_3218
            ? (this.owner = ((class_3218)this.field_6002).method_14190(this.ownerUuid))
            : null;
      }
   }

   protected void method_5749(@NotNull class_2487 nbt) {
      if (nbt.method_25928("owner")) {
         this.ownerUuid = nbt.method_25926("owner");
      }
   }

   protected void method_5652(class_2487 nbt) {
      if (this.ownerUuid != null) {
         nbt.method_25927("owner", this.ownerUuid);
      }
   }

   public class_2596<?> method_18002() {
      class_1297 entity = this.getOwner();
      return new class_2604(this, entity == null ? 0 : entity.method_5628());
   }

   public void method_31471(class_2604 packet) {
      super.method_31471(packet);
      class_1297 entity = this.field_6002.method_8469(packet.method_11166());
      if (entity != null) {
         this.setOwner(entity);
      }
   }

   protected void method_5693() {
      this.field_6011.method_12784(POWER, 8.0F);
   }
}
