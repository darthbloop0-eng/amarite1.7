package xyz.amymialee.mialib.templates;

import net.minecraft.class_1832;
import net.minecraft.class_1856;

public class MToolMaterial implements class_1832 {
   public static final MToolMaterial EMPTY = new MToolMaterial();
   private int toolDurability = 0;
   private float miningSpeedMultiplier = 1.0F;
   private float attackDamage = 0.0F;
   private int miningLevel = 0;
   private int enchantability = 0;
   private class_1856 repairIngredient = class_1856.field_9017;

   public MToolMaterial() {
   }

   public MToolMaterial(int toolDurability, float miningSpeedMultiplier, float attackDamage, int miningLevel, int enchantability, class_1856 repairIngredient) {
      this.toolDurability = toolDurability;
      this.miningSpeedMultiplier = miningSpeedMultiplier;
      this.attackDamage = attackDamage;
      this.miningLevel = miningLevel;
      this.enchantability = enchantability;
      this.repairIngredient = repairIngredient;
   }

   public MToolMaterial copy() {
      return new MToolMaterial(this.toolDurability, this.miningSpeedMultiplier, this.attackDamage, this.miningLevel, this.enchantability, this.repairIngredient);
   }

   public MToolMaterial setToolDurability(int toolDurability) {
      this.toolDurability = toolDurability;
      return this;
   }

   public MToolMaterial setMiningSpeedMultiplier(float miningSpeedMultiplier) {
      this.miningSpeedMultiplier = miningSpeedMultiplier;
      return this;
   }

   public MToolMaterial setAttackDamage(float attackDamage) {
      this.attackDamage = attackDamage;
      return this;
   }

   public MToolMaterial setMiningLevel(int miningLevel) {
      this.miningLevel = miningLevel;
      return this;
   }

   public MToolMaterial setEnchantability(int enchantability) {
      this.enchantability = enchantability;
      return this;
   }

   public MToolMaterial setRepairIngredient(class_1856 repairIngredient) {
      this.repairIngredient = repairIngredient;
      return this;
   }

   public int method_8025() {
      return this.toolDurability;
   }

   public float method_8027() {
      return this.miningSpeedMultiplier;
   }

   public float method_8028() {
      return this.attackDamage;
   }

   public int method_8024() {
      return this.miningLevel;
   }

   public int method_8026() {
      return this.enchantability;
   }

   public class_1856 method_8023() {
      return this.repairIngredient;
   }
}
