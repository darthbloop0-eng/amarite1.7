package xyz.amymialee.mialib.interfaces;

import net.minecraft.class_2960;

public interface MPlayerEntity {
   default boolean mialib$isCoolingDown(class_2960 id) {
      return false;
   }

   default void mialib$setCooldown(class_2960 id, int ticks) {
   }

   default int mialib$getCooldown(class_2960 id) {
      return 0;
   }

   default float mialib$getCooldown(class_2960 id, float tickDelta) {
      return 0.0F;
   }

   default boolean mialib$isAttacking() {
      return false;
   }

   default boolean mialib$startedAttacking() {
      return false;
   }

   default boolean mialib$hasBeenAttacking() {
      return false;
   }

   default boolean mialib$stoppedAttacking() {
      return false;
   }

   default int mialib$getAttackingTime() {
      return 0;
   }

   default boolean mialib$isUsing() {
      return false;
   }

   default boolean mialib$startedUsing() {
      return false;
   }

   default boolean mialib$hasBeenUsing() {
      return false;
   }

   default boolean mialib$stoppedUsing() {
      return false;
   }

   default int mialib$getUsingTime() {
      return 0;
   }
}
