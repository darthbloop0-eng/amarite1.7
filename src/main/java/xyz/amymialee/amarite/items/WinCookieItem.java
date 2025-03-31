package xyz.amymialee.amarite.items;

import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1937;
import net.minecraft.class_2561;
import net.minecraft.class_1792.class_1793;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class WinCookieItem extends class_1792 {
   public WinCookieItem(class_1793 settings) {
      super(settings);
   }

   public int mialib$getNameColor(class_1799 stack) {
      return 13070705;
   }

   public void method_7851(class_1799 stack, @Nullable class_1937 world, @NotNull List<class_2561> tooltip, class_1836 context) {
      tooltip.add(class_2561.method_43471(this.method_7869() + ".desc").method_27692(class_124.field_1080));
   }
}
