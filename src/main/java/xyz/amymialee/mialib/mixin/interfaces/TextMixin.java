package xyz.amymialee.mialib.mixin.interfaces;

import net.minecraft.class_2561;
import org.spongepowered.asm.mixin.Mixin;
import xyz.amymialee.mialib.interfaces.MText;

@Mixin({class_2561.class})
public interface TextMixin extends MText {
   @Override
   default class_2561 mialib$withColor(int color) {
      return MText.withColor((class_2561)this, color);
   }

   @Override
   default class_2561 mialib$withItalics(boolean italics) {
      return MText.withItalics((class_2561)this, italics);
   }

   @Override
   default class_2561 mialib$withBold(boolean bold) {
      return MText.withBold((class_2561)this, bold);
   }

   @Override
   default class_2561 mialib$withUnderline(boolean underline) {
      return MText.withUnderline((class_2561)this, underline);
   }

   @Override
   default class_2561 mialib$withStrikethrough(boolean strikethrough) {
      return MText.withStrikethrough((class_2561)this, strikethrough);
   }

   @Override
   default class_2561 mialib$withObfuscated(boolean obfuscated) {
      return MText.withObfuscated((class_2561)this, obfuscated);
   }

   @Override
   default class_2561 mialib$withInsertion(String insertion) {
      return MText.withInsertion((class_2561)this, insertion);
   }
}
