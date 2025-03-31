package xyz.amymialee.amarite.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1271;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1657;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1838;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2398;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_3218;
import net.minecraft.class_3419;
import net.minecraft.class_3544;
import net.minecraft.class_5250;
import net.minecraft.class_1792.class_1793;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;
import xyz.amymialee.mialib.mixin.accessors.BeaconBlockEntityAccessor;

public class AmariteMirrorItem extends class_1792 {
   public static final Map<class_1291, Integer> MIRROR_COOLDOWNS = new HashMap<>();

   public AmariteMirrorItem(class_1793 settings) {
      super(settings);
   }

   public class_1269 method_7884(@NotNull class_1838 context) {
      class_1937 world = context.method_8045();
      if (!world.method_8608() && context.method_8036() != null && context.method_8036().method_5715()) {
         class_1799 itemStack = context.method_8041();
         class_2338 pos = context.method_8037();
         class_2680 state = world.method_8320(pos);
         if (state.method_27852(class_2246.field_10327)) {
            if (world.method_8321(pos) instanceof BeaconBlockEntityAccessor beaconBlock) {
               class_1291 primaryEffect = beaconBlock.getPrimary();
               class_1291 secondaryEffect = beaconBlock.getSecondary();
               if (writeEffects(itemStack, primaryEffect, secondaryEffect)) {
                  world.method_43129(null, context.method_8036(), AmariteSoundEvents.MIRROR_EXTRACT, class_3419.field_15248, 1.0F, 1.0F);
                  if (world instanceof class_3218 serverWorld) {
                     class_243 hitPos = context.method_17698();
                     serverWorld.method_14199(
                        class_2398.field_11207, hitPos.method_10216(), hitPos.method_10214(), hitPos.method_10215(), 12, 0.15, 0.15, 0.15, 0.1
                     );
                  }
               }
            }
         } else if (state.method_27852(class_2246.field_10502) && writeEffects(itemStack, class_1294.field_5927, null)) {
            itemStack.method_7948().method_10556("Conduit", true);
            world.method_43129(null, context.method_8036(), AmariteSoundEvents.MIRROR_EXTRACT, class_3419.field_15248, 1.0F, 0.9F);
            if (world instanceof class_3218 serverWorld) {
               class_243 hitPos = context.method_17698();
               serverWorld.method_14199(class_2398.field_11207, hitPos.method_10216(), hitPos.method_10214(), hitPos.method_10215(), 12, 0.15, 0.15, 0.15, 0.1);
            }
         }
      }

      return super.method_7884(context);
   }

   public class_1271<class_1799> method_7836(class_1937 world, @NotNull class_1657 user, class_1268 hand) {
      if (!user.method_5715()) {
         class_1799 itemStack = user.method_5998(hand);
         useMirror(world, user, itemStack, true);
      }

      return super.method_7836(world, user, hand);
   }

   public static void useMirror(@NotNull class_1937 world, class_1657 user, class_1799 itemStack, boolean particles) {
      class_1291 primaryEffect = getPrimary(itemStack);
      class_1291 secondaryEffect = getSecondary(itemStack);
      if (!world.method_8608() && primaryEffect != null) {
         boolean success = false;
         boolean repeat = user.method_6059(primaryEffect);
         if (user.method_6092(new class_1293(primaryEffect, 300, primaryEffect == secondaryEffect ? 1 : 0, particles, particles, true))) {
            success = true;
         }

         if (primaryEffect != secondaryEffect && secondaryEffect != null) {
            if (user.method_6059(secondaryEffect)) {
               repeat = true;
            }

            if (user.method_6092(new class_1293(secondaryEffect, 300, 0, particles, particles, true))) {
               success = true;
            }
         }

         if (success) {
            user.method_7357().method_7906(itemStack.method_7909(), getCooldown(itemStack));
            if (!repeat) {
               world.method_43129(null, user, AmariteSoundEvents.MIRROR_USE, class_3419.field_15248, 1.0F, 1.0F);
            }
         }
      }
   }

   public static boolean writeEffects(@NotNull class_1799 stack, class_1291 primary, class_1291 secondary) {
      boolean changed = false;
      class_2487 compoundTag = stack.method_7948();
      int primaryId = class_1291.method_5554(primary);
      if (!compoundTag.method_10545("Primary") || compoundTag.method_10550("Primary") != primaryId) {
         compoundTag.method_10569("Primary", primaryId);
         changed = true;
      }

      int secondaryId = class_1291.method_5554(secondary);
      if (!compoundTag.method_10545("Secondary") || compoundTag.method_10550("Secondary") != secondaryId) {
         compoundTag.method_10569("Secondary", secondaryId);
         changed = true;
      }

      return changed;
   }

   @Nullable
   public static class_1291 getPrimary(@NotNull class_1799 stack) {
      return stack.method_7969() == null ? null : class_1291.method_5569(stack.method_7969().method_10550("Primary"));
   }

   @Nullable
   public static class_1291 getSecondary(@NotNull class_1799 stack) {
      return stack.method_7969() == null ? null : class_1291.method_5569(stack.method_7969().method_10550("Secondary"));
   }

   public static int getCooldown(class_1799 stack) {
      class_1291 primaryEffect = getPrimary(stack);
      class_1291 secondaryEffect = getSecondary(stack);
      return MIRROR_COOLDOWNS.getOrDefault(primaryEffect, 160) + MIRROR_COOLDOWNS.getOrDefault(secondaryEffect, 0);
   }

   public int mialib$getNameColor(class_1799 stack) {
      return getMirrorColor(stack, 0.0F);
   }

   public void method_7851(class_1799 stack, @Nullable class_1937 world, List<class_2561> tooltip, class_1836 context) {
      class_1291 primaryEffect = getPrimary(stack);
      class_1291 secondaryEffect = getSecondary(stack);
      boolean doubled = primaryEffect == secondaryEffect;
      if (primaryEffect == null) {
         tooltip.add(class_2561.method_43471("item.amarite.amarite_mirror.empty").method_27692(class_124.field_1080));
      } else {
         class_5250 primaryText = class_2561.method_43471(primaryEffect.method_5567());
         if (doubled) {
            primaryText = class_2561.method_43469("potion.withAmplifier", new Object[]{primaryText, class_2561.method_43471("potion.potency.1")});
         }

         primaryText = class_2561.method_43469("potion.withDuration", new Object[]{primaryText, class_3544.method_15439(300)});
         tooltip.add(primaryText.method_27692(primaryEffect.method_18792().method_18793()));
         if (secondaryEffect != null && !doubled) {
            class_5250 secondaryText = class_2561.method_43471(secondaryEffect.method_5567());
            secondaryText = class_2561.method_43469("potion.withDuration", new Object[]{secondaryText, class_3544.method_15439(300)});
            tooltip.add(secondaryText.method_27692(secondaryEffect.method_18792().method_18793()));
         }

         tooltip.add(
            class_2561.method_43469("item.amarite.amarite_mirror.cooldown", new Object[]{class_3544.method_15439(getCooldown(stack))})
               .method_27692(class_124.field_1080)
         );
      }

      super.method_7851(stack, world, tooltip, context);
   }

   public static int getMirrorColor(class_1799 stack, float charge) {
      float i = 2.0F;
      float r = 0.0F;
      float g = 0.0F;
      float b = 0.0F;
      class_1291 primaryEffect = getPrimary(stack);
      if (primaryEffect != null) {
         int primaryColor = primaryEffect.method_5556();
         r += charge + (1.0F - charge) * (primaryColor >> 16 & 0xFF) / 255.0F;
         g += charge + (1.0F - charge) * (primaryColor >> 8 & 0xFF) / 255.0F;
         b += charge + (1.0F - charge) * (primaryColor & 0xFF) / 255.0F;
      } else {
         r++;
         g++;
         b++;
         i--;
      }

      class_1291 secondaryEffect = getSecondary(stack);
      if (secondaryEffect != null) {
         int secondaryColor = secondaryEffect.method_5556();
         r += charge + (1.0F - charge) * (secondaryColor >> 16 & 0xFF) / 255.0F;
         g += charge + (1.0F - charge) * (secondaryColor >> 8 & 0xFF) / 255.0F;
         b += charge + (1.0F - charge) * (secondaryColor & 0xFF) / 255.0F;
      } else {
         r++;
         g++;
         b++;
         i--;
      }

      if (i > 0.0F) {
         r /= i;
         g /= i;
         b /= i;
      }

      return (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F);
   }
}
