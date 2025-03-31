package xyz.amymialee.mialib.interfaces;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_286;
import net.minecraft.class_287;
import net.minecraft.class_290;
import net.minecraft.class_2960;
import net.minecraft.class_327;
import net.minecraft.class_757;
import net.minecraft.class_918;
import net.minecraft.class_293.class_5596;
import net.minecraft.class_572.class_573;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface MItem {
   class_2960[] EMPTY = new class_2960[0];

   default boolean mialib$shouldSmelt(
      class_1937 world, class_2680 state, class_2338 pos, @Nullable class_2586 blockEntity, @Nullable class_1297 entity, class_1799 stack
   ) {
      return false;
   }

   default boolean mialib$attack(class_1937 world, class_1799 stack, class_1309 attacker, class_1297 target) {
      return false;
   }

   default void mialib$killEntity(class_1937 world, class_1799 stack, class_1309 user, class_1309 victim) {
   }

   default class_2960[] mialib$cooldownDisplays() {
      return EMPTY;
   }

   @Environment(EnvType.CLIENT)
   default class_573 mialib$pose(class_1309 entity, class_1268 hand, class_1799 stack) {
      return null;
   }

   @Environment(EnvType.CLIENT)
   default boolean mialib$shouldHideInHand(class_1309 entity, class_1268 hand, class_1799 stack) {
      return false;
   }

   default int mialib$enchantLevel(class_1887 enchantment, class_1799 stack, int level) {
      return level;
   }

   default class_1269 mialib$checkEnchantment(class_1886 target, class_1887 enchantment) {
      return class_1269.field_5811;
   }

   default int mialib$getNameColor(class_1799 stack) {
      return -1;
   }

   @Environment(EnvType.CLIENT)
   default void mialib$renderCustomBar(class_918 drawContext, class_327 renderer, class_1799 stack, int x, int y, String countLabel) {
   }

   default void mialib$renderGuiQuad(@NotNull class_287 buffer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
      RenderSystem.setShader(class_757::method_34540);
      buffer.method_1328(class_5596.field_27382, class_290.field_1576);
      buffer.method_22912(x, y, 0.0).method_1336(red, green, blue, alpha).method_1344();
      buffer.method_22912(x, y + height, 0.0).method_1336(red, green, blue, alpha).method_1344();
      buffer.method_22912(x + width, y + height, 0.0).method_1336(red, green, blue, alpha).method_1344();
      buffer.method_22912(x + width, y, 0.0).method_1336(red, green, blue, alpha).method_1344();
      class_286.method_43433(buffer.method_1326());
   }
}
