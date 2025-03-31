package xyz.amymialee.amarite.entities;

import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1301;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1665;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1890;
import net.minecraft.class_1937;
import net.minecraft.class_2350;
import net.minecraft.class_239;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2668;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3222;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import net.minecraft.class_3532;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import net.minecraft.class_239.class_240;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.registry.AmariteDamageTypes;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

public class DiscEntity extends class_1665 {
   public static final float BASE_DAMAGE = 3.0F;
   public static final class_2940<Byte> DISC_FLAGS = class_2945.method_12791(DiscEntity.class, class_2943.field_13319);
   public static final class_2940<Integer> PLAYER_OWNER = class_2945.method_12791(DiscEntity.class, class_2943.field_13327);
   public static final class_2940<Integer> TRACKING_TICKS = class_2945.method_12791(DiscEntity.class, class_2943.field_13327);
   public class_1309 trackingTarget;
   private class_1657 playerOwner;
   public int pulledTime = 0;
   public boolean clockwise;

   public DiscEntity(class_1299<? extends DiscEntity> entityType, class_1937 world) {
      super(entityType, world);
   }

   protected void method_5693() {
      this.field_6011.method_12784(DISC_FLAGS, (byte)0);
      this.field_6011.method_12784(PLAYER_OWNER, 0);
      this.field_6011.method_12784(TRACKING_TICKS, 0);
      super.method_5693();
   }

   protected void method_7454(class_3966 entityHitResult) {
      if (!this.hasHit()) {
         class_1657 owner = this.getPlayerOwner();
         class_1297 target = entityHitResult.method_17782();
         target.field_6008 = 0;
         if (target.method_5643(AmariteDamageTypes.disc(this, owner), 3.0F)) {
            if (target.method_5864() == class_1299.field_6091) {
               return;
            }

            this.setHit(true);
            if (owner != null) {
               DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(owner);
               if (discComponent.damage(discComponent.getDiscIndex(this), 1) && !owner.method_7337()) {
                  this.method_5783(class_3417.field_15075, 1.0F, 1.6F);
                  this.method_33574(owner.method_19538());
               }
            }

            if (target instanceof class_1309 livingTarget) {
               if (owner != null) {
                  class_1890.method_8210(livingTarget, owner);
                  class_1890.method_8213(owner, livingTarget);
               }

               this.method_7450(livingTarget);
            }

            if (target != owner && target instanceof class_1657 && owner instanceof class_3222 serverPlayer && !this.method_5701()) {
               serverPlayer.field_13987.method_14364(new class_2668(class_2668.field_25651, 0.0F));
            }

            this.method_5783(AmariteSoundEvents.DISC_DAMAGE, 1.0F, 1.0F);
            class_243 dif = target.method_19538().method_1031(0.0, target.method_17682() / 2.0F, 0.0).method_1020(this.method_19538()).method_1029();
            dif = new class_243(Math.abs(dif.field_1352), Math.abs(dif.field_1351), Math.abs(dif.field_1350));
            class_243 power = this.method_18798().method_18806(dif);
            class_243 remainingPower = this.method_18798().method_1020(power);
            power = power.method_1021(-1.0).method_1019(remainingPower);
            this.method_18799(power);
         }
      }
   }

   protected void method_24920(class_3965 blockHitResult) {
      if (!this.isReturning()) {
         class_2350 direction = blockHitResult.method_17780();
         this.method_33574(this.method_19538().method_1020(this.method_18798()));
         switch (direction.method_10166()) {
            case field_11048:
               this.method_18799(this.method_18798().method_18805(-1.0, 1.0, 1.0));
               break;
            case field_11052:
               this.method_18799(this.method_18798().method_18805(1.0, -1.0, 1.0));
               break;
            case field_11051:
               this.method_18799(this.method_18798().method_18805(1.0, 1.0, -1.0));
         }

         this.method_5783(this.method_20011(), 1.0F, 1.2F / (this.field_5974.method_43057() * 0.2F + 0.9F));
      }
   }

   protected void method_7488(@NotNull class_239 hitResult) {
      if (hitResult.method_17783() != class_240.field_1333) {
         if ((Integer)this.field_6011.method_12789(TRACKING_TICKS) > 0) {
            return;
         }

         super.method_7488(hitResult);
         this.setReturning(true);
         this.clockwise = !this.clockwise;
      }
   }

   public static boolean isValidTarget(@NotNull DiscEntity entity, @Nullable class_1297 target) {
      class_1657 owner = entity.getPlayerOwner();
      if (target == owner) {
         return false;
      } else if (target instanceof class_1309 livingTarget) {
         if (livingTarget.method_29504()) {
            return false;
         } else if (target.method_31481()) {
            return false;
         } else if (owner != null && target.method_5722(owner)) {
            return false;
         } else {
            return !class_1301.field_6156.test(target) ? false : target.method_5863();
         }
      } else {
         return false;
      }
   }

   public void method_5773() {
      this.field_7588 = false;
      if (this.pulledTime > 0) {
         this.pulledTime--;
      }

      class_1657 owner = this.getPlayerOwner();
      if (owner == null) {
         this.removeDisc();
      } else {
         class_243 pos = this.method_19538();
         DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(owner);
         if (this.isReturning()) {
            class_243 ownerPos = owner.method_33571();
            double distance = pos.method_1022(ownerPos);
            if (distance < 2.0) {
               this.removeDisc();
               return;
            }

            double speedToReturn = Math.max(distance / 48.0, 0.25);
            class_243 vec3d = owner.method_33571().method_1020(this.method_19538()).method_1029().method_1021(speedToReturn);
            this.method_18799(this.method_18798().method_1021(0.75).method_1019(vec3d));
         }

         if (this.hasUsedRecall()) {
            class_243 ownerPos = owner.method_33571();
            double distance = pos.method_1022(ownerPos);
            class_243 oldPos = new class_243(this.field_6038, this.field_5971, this.field_5989);
            double oldDistance = oldPos.method_1022(ownerPos);
            if (distance < 2.0 || oldDistance < distance) {
               this.removeDisc();
               return;
            }
         }

         if (discComponent.orbitDuration > 0) {
            class_243 pos1 = owner.method_19538().method_1031(0.0, owner.method_17682() / 2.0F, 0.0);
            class_243 pos2 = this.method_19538();
            class_243 difference = pos1.method_1020(pos2).method_18805(1.0, 0.0, 1.0).method_1029();
            double angleInRadians = Math.toRadians(145.0);
            double cosTheta = Math.cos(angleInRadians);
            double sinTheta = Math.sin(angleInRadians);
            class_243 spun = new class_243(
               difference.field_1352 * cosTheta + difference.field_1350 * sinTheta,
               difference.field_1351,
               -difference.field_1352 * sinTheta + difference.field_1350 * cosTheta
            );
            class_243 targetPos = pos1.method_1019(spun.method_1021(1.0));
            double distPercent = 1.0 - pos1.method_1022(pos2) / 2.0;
            this.method_18799(
               this.method_18798().method_1021(distPercent).method_1019(targetPos.method_1020(pos2).method_1029().method_1021(1.0 - distPercent))
            );
         } else if ((Integer)this.field_6011.method_12789(TRACKING_TICKS) > 0) {
            this.field_6011.method_12778(TRACKING_TICKS, (Integer)this.field_6011.method_12789(TRACKING_TICKS) - 1);
            if (this.field_6002.method_8608()) {
               this.field_6002
                  .method_8406(
                     class_2398.field_11207,
                     this.method_19538().field_1352 + this.field_5974.method_43057() - 0.5,
                     this.method_19538().field_1351 + this.field_5974.method_43057() - 0.5,
                     this.method_19538().field_1350 + this.field_5974.method_43057() - 0.5,
                     0.0,
                     0.0,
                     0.0
                  );
            }

            if (this.trackingTarget == null) {
               this.field_6011.method_12778(TRACKING_TICKS, 0);
            } else if ((Integer)this.field_6011.method_12789(TRACKING_TICKS) <= 0) {
               this.method_5875(true);
               this.method_18799(
                  this.trackingTarget
                     .method_19538()
                     .method_1031(0.0, this.trackingTarget.method_17682() / 2.0F, 0.0)
                     .method_1020(this.method_19538().method_1031(0.0, this.method_17682() / 2.0F, 0.0))
                     .method_1029()
                     .method_1021(3.0)
               );
            } else {
               class_243 towards = this.trackingTarget
                  .method_19538()
                  .method_1031(0.0, this.trackingTarget.method_17682() / 2.0F, 0.0)
                  .method_1020(this.method_19538().method_1031(0.0, this.method_17682() / 2.0F, 0.0))
                  .method_1029()
                  .method_1021(3.0);
               double d = towards.method_37267();
               this.method_36456((float)(class_3532.method_15349(towards.field_1352, towards.field_1350) * 180.0 / (float) Math.PI));
               this.method_36457((float)(class_3532.method_15349(towards.field_1351, d) * 180.0 / (float) Math.PI));
               this.field_5982 = this.method_36454();
               this.field_6004 = this.method_36455();
            }
         } else if (this.pulledTime <= 0) {
            if (this.field_6012 > 80 && !this.isReturning()) {
               this.setReturning(true);
            }

            if (this.field_6012 > 400) {
               this.removeDisc();
            }
         }

         super.method_5773();
      }
   }

   private void removeDisc() {
      if (!this.field_6002.method_8608()) {
         class_1657 owner = this.getPlayerOwner();
         if (owner != null) {
            DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(owner);
            discComponent.setDiscId(discComponent.getDiscIndex(this), -1);
         }

         this.method_31472();
      }
   }

   @Nullable
   protected class_3966 method_7434(@NotNull class_243 currentPosition, @NotNull class_243 nextPosition) {
      class_3966 result = super.method_7434(currentPosition.method_1023(1.0, 2.0, 1.0), nextPosition.method_1031(1.0, 2.0, 1.0));
      return result != null && isValidTarget(this, result.method_17782()) ? result : null;
   }

   protected class_1799 method_7445() {
      return class_1802.field_8162.method_7854();
   }

   @Nullable
   public class_1657 getPlayerOwner() {
      if (this.field_6002.method_8608()) {
         return null;
      } else if (this.playerOwner != null) {
         return this.playerOwner;
      } else {
         int id = (Integer)this.field_6011.method_12789(PLAYER_OWNER);
         if (id != -1 && this.field_6002.method_8469(id) instanceof class_1657 player) {
            this.playerOwner = player;
            return player;
         } else {
            this.method_31472();
            return null;
         }
      }
   }

   public void method_7432(@Nullable class_1297 entity) {
      if (entity instanceof class_1657 player) {
         this.field_6011.method_12778(PLAYER_OWNER, player.method_5628());
      } else {
         this.field_6011.method_12778(PLAYER_OWNER, -1);
      }

      super.method_7432(entity);
   }

   protected class_3414 method_7440() {
      return AmariteSoundEvents.DISC_HIT;
   }

   protected float method_7436() {
      return 0.99F;
   }

   protected boolean method_34713(class_1657 player) {
      return false;
   }

   public boolean hasHit() {
      return this.getDiscFlag(0);
   }

   public void setHit(boolean hit) {
      this.setDiscFlag(0, hit);
   }

   public boolean isReturning() {
      return this.getDiscFlag(1);
   }

   public void setReturning(boolean returning) {
      this.setDiscFlag(1, returning);
      this.method_7433(returning);
   }

   public boolean hasUsedRecall() {
      return this.getDiscFlag(6);
   }

   public void setUsedRecall(boolean rebound) {
      this.setDiscFlag(6, rebound);
   }

   public boolean hasUsedRebound() {
      return this.getDiscFlag(7);
   }

   public void setUsedRebound(boolean rebounded) {
      this.setDiscFlag(7, rebounded);
   }

   private boolean getDiscFlag(int flag) {
      if (flag >= 0 && flag <= 8) {
         return ((Byte)this.field_6011.method_12789(DISC_FLAGS) >> flag & 1) == 1;
      } else {
         Amarite.LOGGER.warn("Invalid disc flag index: " + flag + " for disc " + this);
         return false;
      }
   }

   private void setDiscFlag(int flag, boolean value) {
      if (flag >= 0 && flag <= 8) {
         if (value) {
            this.field_6011.method_12778(DISC_FLAGS, (byte)((Byte)this.field_6011.method_12789(DISC_FLAGS) | 1 << flag));
         } else {
            this.field_6011.method_12778(DISC_FLAGS, (byte)((Byte)this.field_6011.method_12789(DISC_FLAGS) & ~(1 << flag)));
         }
      } else {
         Amarite.LOGGER.warn("Invalid disc flag index: " + flag + " for disc " + this);
      }
   }
}
