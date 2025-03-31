package xyz.amymialee.mialib.interfaces;

import java.util.function.Supplier;
import net.minecraft.class_1761;
import net.minecraft.class_1799;

public interface MItemGroup {
   default Supplier<class_1799> mialib$hasConstantIcon() {
      return null;
   }

   default class_1761 mialib$setConstantIcon(Supplier<class_1799> constantIcon) {
      return null;
   }
}
