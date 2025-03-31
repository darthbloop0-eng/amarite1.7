package xyz.amymialee.mialib.templates;

import java.util.function.Function;
import net.minecraft.class_1304;
import net.minecraft.class_1741;
import net.minecraft.class_1856;
import net.minecraft.class_3414;
import net.minecraft.class_3417;
import org.jetbrains.annotations.NotNull;

public class MArmorMaterial implements class_1741 {
   public static final MArmorMaterial EMPTY = new MArmorMaterial("mialib");
   private final String name;
   private Function<class_1304, Integer> armorDurability = type -> 0;
   private Function<class_1304, Integer> protection = type -> 0;
   private class_3414 equipSound = class_3417.field_14581;
   private float toughness = 0.0F;
   private float knockbackResistance = 0.0F;
   private int enchantability = 0;
   private class_1856 repairIngredient = class_1856.field_9017;

   public MArmorMaterial(String name) {
      this.name = name;
   }

   public MArmorMaterial(
      String name,
      Function<class_1304, Integer> armorDurability,
      Function<class_1304, Integer> protection,
      class_3414 equipSound,
      float toughness,
      float knockbackResistance,
      int enchantability,
      class_1856 repairIngredient
   ) {
      this.name = name;
      this.armorDurability = armorDurability;
      this.protection = protection;
      this.equipSound = equipSound;
      this.toughness = toughness;
      this.knockbackResistance = knockbackResistance;
      this.enchantability = enchantability;
      this.repairIngredient = repairIngredient;
   }

   public MArmorMaterial copy(String name) {
      return new MArmorMaterial(
         name, this.armorDurability, this.protection, this.equipSound, this.toughness, this.knockbackResistance, this.enchantability, this.repairIngredient
      );
   }

   public MArmorMaterial setArmorDurability(Function<class_1304, Integer> armorDurability) {
      this.armorDurability = armorDurability;
      return this;
   }

   public MArmorMaterial setArmorDurability(int[] armorDurability) {
      this.armorDurability = new MArmorMaterial.ArmorTypeFunction(armorDurability);
      return this;
   }

   public MArmorMaterial setProtection(Function<class_1304, Integer> protection) {
      this.protection = protection;
      return this;
   }

   public MArmorMaterial setProtection(int[] protection) {
      this.protection = new MArmorMaterial.ArmorTypeFunction(protection);
      return this;
   }

   public MArmorMaterial setEquipSound(class_3414 equipSound) {
      this.equipSound = equipSound;
      return this;
   }

   public MArmorMaterial setToughness(float toughness) {
      this.toughness = toughness;
      return this;
   }

   public MArmorMaterial setKnockbackResistance(float knockbackResistance) {
      this.knockbackResistance = knockbackResistance;
      return this;
   }

   public MArmorMaterial setEnchantability(int enchantability) {
      this.enchantability = enchantability;
      return this;
   }

   public MArmorMaterial setRepairIngredient(class_1856 repairIngredient) {
      this.repairIngredient = repairIngredient;
      return this;
   }

   public int method_7696(class_1304 type) {
      return this.armorDurability.apply(type);
   }

   public int method_7697(class_1304 type) {
      return this.protection.apply(type);
   }

   public class_3414 method_7698() {
      return this.equipSound;
   }

   public String method_7694() {
      return this.name;
   }

   public float method_7700() {
      return this.toughness;
   }

   public float method_24355() {
      return this.knockbackResistance;
   }

   public int method_7699() {
      return this.enchantability;
   }

   public class_1856 method_7695() {
      return this.repairIngredient;
   }

   private record ArmorTypeFunction(int[] values) implements Function<class_1304, Integer> {
      public Integer apply(@NotNull class_1304 type) {
         return this.values[type.ordinal()];
      }
   }
}
