package xyz.amymialee.mialib.templates;

import java.util.function.BiFunction;
import net.minecraft.class_1282;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1309;
import net.minecraft.class_1310;
import net.minecraft.class_1799;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1887.class_1888;
import xyz.amymialee.mialib.util.QuadConsumer;
import xyz.amymialee.mialib.util.QuadFunction;
import xyz.amymialee.mialib.util.TriFunction;

public class MEnchantment extends class_1887 {
   private BiFunction<MEnchantment, Integer, Integer> minLevel = (enchantment, original) -> original;
   private BiFunction<MEnchantment, Integer, Integer> maxLevel = (enchantment, original) -> original;
   private TriFunction<MEnchantment, Integer, Integer, Integer> minPower = (enchantment, original, level) -> original;
   private TriFunction<MEnchantment, Integer, Integer, Integer> maxPower = (enchantment, original, level) -> original;
   private QuadFunction<MEnchantment, Integer, Integer, class_1282, Integer> protectionAmount = (enchantment, original, level, source) -> original;
   private QuadFunction<MEnchantment, Float, Integer, class_1310, Float> attackDamage = (enchantment, original, level, group) -> original;
   private TriFunction<MEnchantment, Boolean, class_1887, Boolean> canAccept = (enchantment, original, other) -> original;
   private TriFunction<MEnchantment, Boolean, class_1799, Boolean> isAcceptableItem = (enchantment, original, stack) -> original;
   private QuadConsumer<MEnchantment, class_1309, class_1297, Integer> onTargetDamaged = (enchantment, user, target, level) -> {};
   private QuadConsumer<MEnchantment, class_1309, class_1297, Integer> onUserDamaged = (enchantment, user, target, level) -> {};
   private BiFunction<MEnchantment, Boolean, Boolean> isTreasure = (enchantment, original) -> original;
   private BiFunction<MEnchantment, Boolean, Boolean> isCursed = (enchantment, original) -> original;
   private BiFunction<MEnchantment, Boolean, Boolean> isAvailableForEnchantedBookOffer = (enchantment, original) -> original;
   private BiFunction<MEnchantment, Boolean, Boolean> isAvailableForRandomSelection = (enchantment, original) -> original;

   public MEnchantment(class_1888 weight, class_1886 type, class_1304... slotTypes) {
      super(weight, type, slotTypes);
   }

   public MEnchantment setMinLevel(BiFunction<MEnchantment, Integer, Integer> minLevel) {
      this.minLevel = minLevel;
      return this;
   }

   public MEnchantment setMaxLevel(BiFunction<MEnchantment, Integer, Integer> maxLevel) {
      this.maxLevel = maxLevel;
      return this;
   }

   public MEnchantment setMinPower(int minPower) {
      this.minPower = (enchantment, original, level) -> minPower;
      return this;
   }

   public MEnchantment setMinPower(TriFunction<MEnchantment, Integer, Integer, Integer> minPower) {
      this.minPower = minPower;
      return this;
   }

   public MEnchantment setMaxPower(int maxPower) {
      this.maxPower = (enchantment, original, level) -> maxPower;
      return this;
   }

   public MEnchantment setMaxPower(TriFunction<MEnchantment, Integer, Integer, Integer> maxPower) {
      this.maxPower = maxPower;
      return this;
   }

   public MEnchantment setProtectionAmount(QuadFunction<MEnchantment, Integer, Integer, class_1282, Integer> protectionAmount) {
      this.protectionAmount = protectionAmount;
      return this;
   }

   public MEnchantment setAttackDamage(QuadFunction<MEnchantment, Float, Integer, class_1310, Float> attackDamage) {
      this.attackDamage = attackDamage;
      return this;
   }

   public MEnchantment setCanAccept(TriFunction<MEnchantment, Boolean, class_1887, Boolean> canAccept) {
      this.canAccept = canAccept;
      return this;
   }

   public MEnchantment setIsAcceptableItem(TriFunction<MEnchantment, Boolean, class_1799, Boolean> isAcceptableItem) {
      this.isAcceptableItem = isAcceptableItem;
      return this;
   }

   public MEnchantment setOnTargetDamaged(QuadConsumer<MEnchantment, class_1309, class_1297, Integer> onTargetDamaged) {
      this.onTargetDamaged = onTargetDamaged;
      return this;
   }

   public MEnchantment setOnUserDamaged(QuadConsumer<MEnchantment, class_1309, class_1297, Integer> onUserDamaged) {
      this.onUserDamaged = onUserDamaged;
      return this;
   }

   public MEnchantment setIsTreasure(BiFunction<MEnchantment, Boolean, Boolean> isTreasure) {
      this.isTreasure = isTreasure;
      return this;
   }

   public MEnchantment setIsCursed(BiFunction<MEnchantment, Boolean, Boolean> isCursed) {
      this.isCursed = isCursed;
      return this;
   }

   public MEnchantment setIsAvailableForEnchantedBookOffer(BiFunction<MEnchantment, Boolean, Boolean> isAvailableForEnchantedBookOffer) {
      this.isAvailableForEnchantedBookOffer = isAvailableForEnchantedBookOffer;
      return this;
   }

   public MEnchantment setIsAvailableForRandomSelection(BiFunction<MEnchantment, Boolean, Boolean> isAvailableForRandomSelection) {
      this.isAvailableForRandomSelection = isAvailableForRandomSelection;
      return this;
   }

   public int method_8187() {
      return this.minLevel.apply(this, super.method_8187());
   }

   public int method_8183() {
      return this.maxLevel.apply(this, super.method_8183());
   }

   public int method_8182(int level) {
      return this.minPower.apply(this, super.method_8182(level), level);
   }

   public int method_20742(int level) {
      return this.maxPower.apply(this, super.method_20742(level), level);
   }

   public int method_8181(int level, class_1282 source) {
      return this.protectionAmount.apply(this, super.method_8181(level, source), level, source);
   }

   public float method_8196(int level, class_1310 group) {
      return this.attackDamage.apply(this, super.method_8196(level, group), level, group);
   }

   protected boolean method_8180(class_1887 other) {
      return this.canAccept.apply(this, super.method_8180(other), other);
   }

   public boolean method_8192(class_1799 stack) {
      return this.isAcceptableItem.apply(this, super.method_8192(stack), stack);
   }

   public void method_8189(class_1309 user, class_1297 target, int level) {
      this.onTargetDamaged.accept(this, user, target, level);
   }

   public void method_8178(class_1309 user, class_1297 attacker, int level) {
      this.onUserDamaged.accept(this, user, attacker, level);
   }

   public boolean method_8193() {
      return this.isTreasure.apply(this, super.method_8193());
   }

   public boolean method_8195() {
      return this.isCursed.apply(this, super.method_8195());
   }

   public boolean method_25949() {
      return this.isAvailableForEnchantedBookOffer.apply(this, super.method_25949());
   }

   public boolean method_25950() {
      return this.isAvailableForRandomSelection.apply(this, super.method_25950());
   }
}
