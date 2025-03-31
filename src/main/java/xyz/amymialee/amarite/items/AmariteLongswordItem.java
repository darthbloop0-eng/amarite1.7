package xyz.amymialee.amarite.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.ImmutableMultimap.Builder;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.UUID;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1271;
import net.minecraft.class_1297;
import net.minecraft.class_1304;
import net.minecraft.class_1320;
import net.minecraft.class_1322;
import net.minecraft.class_1657;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import net.minecraft.class_1829;
import net.minecraft.class_1836;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1893;
import net.minecraft.class_1937;
import net.minecraft.class_2371;
import net.minecraft.class_2561;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_310;
import net.minecraft.class_327;
import net.minecraft.class_5134;
import net.minecraft.class_5150;
import net.minecraft.class_746;
import net.minecraft.class_918;
import net.minecraft.class_1322.class_1323;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.LongswordAccumulateComponent;
import xyz.amymialee.amarite.registry.AmariteEnchantments;
import xyz.amymialee.mialib.MiaLib;
import xyz.amymialee.mialib.cca.HoldingComponent;
import xyz.amymialee.mialib.templates.MToolMaterial;

public class AmariteLongswordItem extends class_1829 implements class_5150 {
   private static final MToolMaterial MATERIAL = MToolMaterial.EMPTY.copy().setMiningSpeedMultiplier(10.5F).setMiningLevel(4).setEnchantability(36);
   private static final UUID ATTACK_REACH_MODIFIER_ID = UUID.fromString("76a8dee3-3e7e-4e11-ba46-a19b0c724567");
   private static final UUID REACH_MODIFIER_ID = UUID.fromString("a31c8afc-a716-425d-89cd-0d373380e6e7");
   private final Multimap<class_1320, class_1322> attributeModifiers;

   public AmariteLongswordItem(QuiltItemSettings settings) {
      super(MATERIAL, 8, -2.7F, settings);
      Builder<class_1320, class_1322> builder = ImmutableMultimap.builder();
      builder.put(class_5134.field_23721, new class_1322(field_8006, "Weapon modifier", 7.5, class_1323.field_6328));
      builder.put(class_5134.field_23723, new class_1322(field_8001, "Weapon modifier", -2.7, class_1323.field_6328));
      builder.put(ReachEntityAttributes.ATTACK_RANGE, new class_1322(ATTACK_REACH_MODIFIER_ID, "Weapon modifier", 0.75, class_1323.field_6328));
      builder.put(ReachEntityAttributes.REACH, new class_1322(REACH_MODIFIER_ID, "Weapon modifier", 1.5, class_1323.field_6328));
      this.attributeModifiers = builder.build();
   }

   public class_1271<class_1799> method_7836(class_1937 world, @NotNull class_1657 user, class_1268 hand) {
      user.method_6019(hand);
      return class_1271.method_22428(user.method_5998(hand));
   }

   public void method_7888(class_1799 stack, class_1937 world, class_1297 entity, int slot, boolean selected) {
      if (selected && entity instanceof class_1657 player && player.method_6047() == stack) {
         HoldingComponent component = (HoldingComponent)MiaLib.HOLDING.get(player);
         if (!world.method_8608() && component.hasBeenUsing() && component.startedAttacking()) {
            getMode(player, stack).useAbility();
         }

         if (component.stoppedUsing() && !((LongswordAccumulateComponent)Amarite.ACCUMULATE.get(player)).accumulateActive) {
            player.method_7350();
         }
      }

      super.method_7888(stack, world, entity, slot, selected);
   }

   public int mialib$enchantLevel(class_1887 enchantment, class_1799 stack, int level) {
      return enchantment == class_1893.field_9115 ? level + 5 : super.mialib$enchantLevel(enchantment, stack, level);
   }

   public class_1269 mialib$checkEnchantment(class_1886 target, class_1887 enchantment) {
      return enchantment != AmariteEnchantments.DOUBLE_DASH && enchantment != AmariteEnchantments.ACCUMULATE && enchantment != AmariteEnchantments.MALIGNANCY
         ? class_1269.field_5814
         : class_1269.field_5812;
   }

   @ClientOnly
   public int mialib$getNameColor(class_1799 stack) {
      return getMode(class_310.method_1551().field_1724, stack).getModeColor();
   }

   @ClientOnly
   public void method_7851(class_1799 stack, @Nullable class_1937 world, @NotNull List<class_2561> tooltip, class_1836 context) {
      AmariteLongswordItem.LongswordMode mode = getMode(class_310.method_1551().field_1724, stack);
      tooltip.add(
         class_2561.method_43469(
               this.method_7869() + ".desc_1", new Object[]{class_310.method_1551().field_1690.field_1904.method_16007().mialib$withColor(7699656)}
            )
            .method_27692(class_124.field_1080)
      );
      tooltip.add(
         class_2561.method_43469(
               this.method_7869() + ".desc_2",
               new Object[]{
                  class_310.method_1551().field_1690.field_1886.method_16007().mialib$withColor(7699656),
                  class_2561.method_43471(mode.getTranslationKey()).mialib$withColor(mode.getModeColor())
               }
            )
            .method_27692(class_124.field_1080)
      );
   }

   @ClientOnly
   public void mialib$renderCustomBar(class_918 drawContext, class_327 renderer, class_1799 stack, int x, int y, String countLabel) {
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         AmariteLongswordItem.LongswordMode mode = getMode(player, stack);
         RenderSystem.disableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.disableBlend();
         class_289 tessellator = class_289.method_1348();
         class_287 bufferBuilder = tessellator.method_1349();
         float width = mode.getChargeProgress();
         int color = width >= 1.0F ? mode.getModeColor() : -8355712;
         this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
         if (mode.getModeColor() != -11627802) {
            this.mialib$renderGuiQuad(
               bufferBuilder, x + 2, y + 13, (int)(Math.min(1.0F, width) * 13.0F), 1, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255
            );
            this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 13, (int)(Math.min(1.0F, width - 1.0F) * 13.0F), 1, 240, 204, 98, 255);
         } else {
            color = width >= 0.5F ? mode.getModeColor() : -8355712;
            this.mialib$renderGuiQuad(
               bufferBuilder, x + 2, y + 13, (int)(Math.min(1.0F, width * 2.0F) * 6.0F), 1, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255
            );
            this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 13, (int)(Math.min(1.0F, width * 2.0F - 2.0F) * 6.0F), 1, 240, 204, 98, 255);
            color = width >= 1.0F ? mode.getModeColor() : -8355712;
            this.mialib$renderGuiQuad(
               bufferBuilder, x + 9, y + 13, (int)(Math.min(1.0F, width * 2.0F - 1.0F) * 6.0F), 1, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255
            );
            this.mialib$renderGuiQuad(bufferBuilder, x + 9, y + 13, (int)(Math.min(1.0F, width * 2.0F - 3.0F) * 6.0F), 1, 240, 204, 98, 255);
         }

         RenderSystem.enableBlend();
         RenderSystem.enableTexture();
         RenderSystem.enableDepthTest();
      }
   }

   public boolean method_7886(class_1799 stack) {
      return false;
   }

   public boolean method_7870(class_1799 stack) {
      return true;
   }

   public Multimap<class_1320, class_1322> method_7844(class_1304 slot) {
      return slot == class_1304.field_6173 ? this.attributeModifiers : super.method_7844(slot);
   }

   public void method_7850(class_1761 group, class_2371<class_1799> stacks) {
      if (this.method_7877(group)) {
         for (class_1887 enchantment : new class_1887[]{null, AmariteEnchantments.DOUBLE_DASH, AmariteEnchantments.ACCUMULATE, AmariteEnchantments.MALIGNANCY}) {
            class_1799 stack = new class_1799(this);
            if (enchantment != null) {
               stack.method_7978(enchantment, enchantment.method_8183());
            }

            stacks.add(stack);
         }
      }
   }

   @NotNull
   public static AmariteLongswordItem.LongswordMode getMode(class_1657 user, class_1799 stack) {
      if (class_1890.method_8225(AmariteEnchantments.DOUBLE_DASH, stack) > 0) {
         return (AmariteLongswordItem.LongswordMode)Amarite.DOUBLE_DASH.get(user);
      } else if (class_1890.method_8225(AmariteEnchantments.ACCUMULATE, stack) > 0) {
         return (AmariteLongswordItem.LongswordMode)Amarite.ACCUMULATE.get(user);
      } else {
         return class_1890.method_8225(AmariteEnchantments.MALIGNANCY, stack) > 0
            ? (AmariteLongswordItem.LongswordMode)Amarite.MALIGNANCY.get(user)
            : (AmariteLongswordItem.LongswordMode)Amarite.DASH.get(user);
      }
   }

   @NotNull
   public static AmariteLongswordItem.LongswordMode[] getModes(class_1657 user) {
      return new AmariteLongswordItem.LongswordMode[]{
         (AmariteLongswordItem.LongswordMode)Amarite.DASH.get(user),
         (AmariteLongswordItem.LongswordMode)Amarite.DOUBLE_DASH.get(user),
         (AmariteLongswordItem.LongswordMode)Amarite.ACCUMULATE.get(user),
         (AmariteLongswordItem.LongswordMode)Amarite.MALIGNANCY.get(user)
      };
   }

   public interface LongswordMode {
      int OVERCHARGE_COLOR = -996254;
      int UNCHARGED_COLOR = -8355712;

      void absorbDamage(float var1);

      void useAbility();

      int getModeColor();

      int getSwordTint();

      float getChargeProgress();

      String getTranslationKey();
   }
}
