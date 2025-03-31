package xyz.amymialee.mialib.mixin.interfaces;

import net.minecraft.class_1799;
import net.minecraft.class_1887;
import net.minecraft.class_1889;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import xyz.amymialee.mialib.interfaces.MItemStack;

@Mixin({class_1799.class})
public abstract class ItemStackMixin implements MItemStack {
   @Override
   public class_1799 mialib$enchantStack(class_1887 enchantment, int level) {
      return MItemStack.enchantStack((class_1799)this, enchantment, level);
   }

   @Override
   public class_1799 mialib$enchantStack(@NotNull class_1889... enchantmentLevelEntry) {
      return MItemStack.enchantStack((class_1799)this, enchantmentLevelEntry);
   }

   @Override
   public class_1799 mialib$enchantBook(class_1887 enchantment, int level) {
      return MItemStack.enchantBook((class_1799)this, enchantment, level);
   }

   @Override
   public class_1799 mialib$enchantBook(class_1889... enchantmentLevelEntry) {
      return MItemStack.enchantBook((class_1799)this, enchantmentLevelEntry);
   }
}
