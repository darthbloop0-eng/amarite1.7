package xyz.amymialee.mialib.interfaces;

import java.util.List;
import net.minecraft.class_2561;
import org.jetbrains.annotations.NotNull;

public interface MText {
   default class_2561 mialib$withColor(int color) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withItalics(boolean italics) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withBold(boolean bold) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withUnderline(boolean underline) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withStrikethrough(boolean strikethrough) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withObfuscated(boolean obfuscated) {
      return class_2561.method_43473();
   }

   default class_2561 mialib$withInsertion(String insertion) {
      return class_2561.method_43473();
   }

   static class_2561 withColor(@NotNull class_2561 text, int color) {
      return repack(text.method_36136(text.method_10866().method_36139(color)));
   }

   static class_2561 withItalics(@NotNull class_2561 text, boolean italics) {
      return repack(text.method_36136(text.method_10866().method_10978(italics)));
   }

   static class_2561 withBold(@NotNull class_2561 text, boolean bold) {
      return repack(text.method_36136(text.method_10866().method_10982(bold)));
   }

   static class_2561 withUnderline(@NotNull class_2561 text, boolean underline) {
      return repack(text.method_36136(text.method_10866().method_30938(underline)));
   }

   static class_2561 withStrikethrough(@NotNull class_2561 text, boolean strikethrough) {
      return repack(text.method_36136(text.method_10866().method_36140(strikethrough)));
   }

   static class_2561 withObfuscated(@NotNull class_2561 text, boolean obfuscated) {
      return repack(text.method_36136(text.method_10866().method_36141(obfuscated)));
   }

   static class_2561 withInsertion(@NotNull class_2561 text, String insertion) {
      return repack(text.method_36136(text.method_10866().method_10975(insertion)));
   }

   static class_2561 repack(@NotNull List<class_2561> textList) {
      if (textList.isEmpty()) {
         return class_2561.method_43470("");
      } else {
         class_2561 first = textList.get(0);

         for (int i = 1; i < textList.size(); i++) {
            first = first.method_27661().method_10852(textList.get(i));
         }

         return first;
      }
   }
}
