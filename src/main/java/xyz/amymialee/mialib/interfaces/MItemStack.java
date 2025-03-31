package xyz.amymialee.mialib.interfaces;

import net.minecraft.class_1772;
import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_1889;
import org.jetbrains.annotations.NotNull;

public interface MItemStack {
   default class_1799 mialib$enchantStack(class_1887 enchantment, int level) {
      return class_1799.field_8037;
   }

   default class_1799 mialib$enchantStack(class_1889... enchantmentLevelEntry) {
      return class_1799.field_8037;
   }

   default class_1799 mialib$enchantBook(class_1887 enchantment, int level) {
      return class_1799.field_8037;
   }

   default class_1799 mialib$enchantBook(class_1889... enchantmentLevelEntry) {
      return class_1799.field_8037;
   }

   @NotNull
   static class_1799 enchantStack(@NotNull class_1799 stack, class_1887 enchantment, int level) {
      stack.method_7978(enchantment, level);
      return stack;
   }

   @NotNull
   static class_1799 enchantStack(@NotNull class_1799 stack, @NotNull class_1889... enchantmentLevelEntry) {
      for (class_1889 entry : enchantmentLevelEntry) {
         stack.method_7978(entry.field_9093, entry.field_9094);
      }

      return stack;
   }

   @NotNull
   static class_1799 enchantBook(@NotNull class_1799 stack, class_1887 enchantment, int level) {
      class_1772.method_7807(stack, new class_1889(enchantment, level));
      return stack;
   }

   @NotNull
   static class_1799 enchantBook(@NotNull class_1799 stack, @NotNull class_1889... enchantmentLevelEntry) {
      for (class_1889 entry : enchantmentLevelEntry) {
         class_1772.method_7807(stack, entry);
      }

      return stack;
   }
}
