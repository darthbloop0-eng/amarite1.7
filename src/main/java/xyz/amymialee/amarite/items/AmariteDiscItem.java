package xyz.amymialee.amarite.items;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import net.minecraft.class_124;
import net.minecraft.class_1268;
import net.minecraft.class_1269;
import net.minecraft.class_1271;
import net.minecraft.class_1297;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1761;
import net.minecraft.class_1799;
import net.minecraft.class_1813;
import net.minecraft.class_1836;
import net.minecraft.class_1886;
import net.minecraft.class_1887;
import net.minecraft.class_1890;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2371;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_2561;
import net.minecraft.class_2680;
import net.minecraft.class_287;
import net.minecraft.class_289;
import net.minecraft.class_310;
import net.minecraft.class_3222;
import net.minecraft.class_327;
import net.minecraft.class_3419;
import net.minecraft.class_3468;
import net.minecraft.class_746;
import net.minecraft.class_918;
import net.minecraft.class_1792.class_1793;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.cca.DiscPylonComponent;
import xyz.amymialee.amarite.entities.DiscEntity;
import xyz.amymialee.amarite.entities.PylonEntity;
import xyz.amymialee.amarite.registry.AmariteEnchantments;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;
import xyz.amymialee.mialib.MiaLib;
import xyz.amymialee.mialib.cca.HoldingComponent;

public class AmariteDiscItem extends class_1813 {
   public AmariteDiscItem(class_1793 settings) {
      super(15, AmariteSoundEvents.PACIFICUS, settings, 97);
   }

   public class_1271<class_1799> method_7836(class_1937 world, @NotNull class_1657 user, class_1268 hand) {
      class_1799 stack = user.method_5998(hand);
      DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(user);
      int next = discComponent.getNextAvailableDisc();
      if (next == -1) {
         return class_1271.method_22428(stack);
      } else {
         user.method_7259(class_3468.field_15372.method_14956(this));
         if (!world.field_9236) {
            DiscEntity discEntity = (DiscEntity)AmariteEntities.DISC.method_5883(world);
            if (discEntity != null) {
               discEntity.method_7432(user);
               discEntity.method_5808(user.method_23317(), user.method_23320() - 0.1, user.method_23321(), user.method_36454(), user.method_36455());
               discEntity.method_24919(user, user.method_36455(), user.method_36454(), 0.0F, 1.5F, 0.0F);
               discComponent.setDiscEntity(next, discEntity);
               world.method_8649(discEntity);
               world.method_43129(null, discEntity, AmariteSoundEvents.DISC_THROW, class_3419.field_15248, 1.0F, 2.0F);
               user.method_7357().method_7906(this, 1);
            }
         }

         return class_1271.method_22427(stack);
      }
   }

   public void method_7888(class_1799 stack, class_1937 world, class_1297 entity, int slot, boolean selected) {
      if (selected && entity instanceof class_3222 player && player.method_6047() == stack) {
         HoldingComponent component = (HoldingComponent)MiaLib.HOLDING.get(player);
         if (!world.method_8608() && component.startedAttacking()) {
            this.discUsage(player);
         }
      }

      super.method_7888(stack, world, entity, slot, selected);
   }

   public void discUsage(@NotNull class_3222 user) {
      class_1799 stack = user.method_6047();
      DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(user);
      if (class_1890.method_8225(AmariteEnchantments.REBOUND, stack) > 0) {
         for (int i = 0; i < discComponent.discIds.size(); i++) {
            if (discComponent.reboundCharge <= 0) {
               return;
            }

            DiscEntity disc = discComponent.getDiscEntity(i);
            if (disc != null) {
               class_238 box = disc.method_5829().method_1014(16.0);
               List<class_1309> targets = disc.field_6002.method_8390(class_1309.class, box, e -> DiscEntity.isValidTarget(disc, e));
               if (!targets.isEmpty()) {
                  targets.sort((first, second) -> (int)(first.method_5739(disc) * 100.0F - second.method_5739(disc) * 100.0F));
                  disc.trackingTarget = targets.get(0);
                  disc.setUsedRebound(true);
                  disc.method_5841().method_12778(DiscEntity.TRACKING_TICKS, 16);
                  disc.method_5875(true);
                  disc.method_18800(0.0, 0.0, 0.0);
                  disc.field_6002.method_43129(null, disc, AmariteSoundEvents.DISC_REBOUND, disc.method_5634(), 1.6F, 1.0F);
                  discComponent.reboundCharge--;
               }
            }
         }
      } else if (class_1890.method_8225(AmariteEnchantments.PYLON, stack) > 0) {
         if (!user.method_7337() && user.mialib$isCoolingDown(Amarite.id("pylon"))) {
            return;
         }

         PylonEntity pylon = (PylonEntity)AmariteEntities.PYLON.method_5883(user.field_6002);
         if (pylon != null) {
            pylon.method_7432(user);
            pylon.method_5808(user.method_23317(), user.method_23320() - 0.1, user.method_23321(), user.method_36454(), user.method_36455());
            pylon.method_24919(user, user.method_36455(), user.method_36454(), 0.0F, 1.5F, 0.0F);
            ((DiscPylonComponent)Amarite.PYLON.get(pylon)).pylonCharge = 160;
            user.field_6002.method_8649(pylon);
            user.field_6002.method_43129(null, pylon, AmariteSoundEvents.DISC_THROW, class_3419.field_15248, 1.0F, 0.7F);
            user.method_6104(class_1268.field_5810);
            user.mialib$setCooldown(Amarite.id("pylon"), 240);
         }
      } else if (class_1890.method_8225(AmariteEnchantments.ORBIT, stack) > 0) {
         if (!user.method_7337() && discComponent.orbitCharge < 24.0F) {
            return;
         }

         discComponent.orbitDuration = 160;
         discComponent.orbitCharge = 0.0F;
         discComponent.sync();

         for (int i = 0; i < 3; i++) {
            if (discComponent.getDiscEntity(i) == null && discComponent.getDiscDurability(i) > 0) {
               DiscEntity discEntity = (DiscEntity)AmariteEntities.DISC.method_5883(user.field_6002);
               if (discEntity != null) {
                  discEntity.method_7432(user);
                  class_243 offset = new class_243(Math.cos((i + 1) * Math.toRadians(240.0)) * 2.0, 0.0, Math.sin((i + 1) * Math.toRadians(240.0)) * 2.0);
                  discEntity.method_5808(
                     offset.field_1352 + user.method_23317(),
                     user.method_23320() - 0.1,
                     offset.field_1350 + user.method_23321(),
                     user.method_36454(),
                     user.method_36455()
                  );
                  discEntity.method_24919(user, user.method_36455(), user.method_36454(), 0.0F, 1.5F, 0.0F);
                  discComponent.setDiscEntity(i, discEntity);
                  user.field_6002.method_8649(discEntity);
               }
            }
         }
      } else {
         for (int ix = 0; ix < discComponent.discIds.size(); ix++) {
            DiscEntity disc = discComponent.getDiscEntity(ix);
            if (disc != null && !disc.hasUsedRecall()) {
               disc.trackingTarget = user;
               disc.setReturning(false);
               disc.setUsedRecall(true);
               disc.method_5841().method_12778(DiscEntity.TRACKING_TICKS, 12);
               disc.method_5875(true);
               disc.method_18800(0.0, 0.0, 0.0);
               disc.field_6002.method_43129(null, disc, AmariteSoundEvents.DISC_REBOUND, disc.method_5634(), 1.6F, 1.35F);
            }
         }
      }
   }

   public class_1269 mialib$checkEnchantment(class_1886 target, class_1887 enchantment) {
      return enchantment != AmariteEnchantments.REBOUND && enchantment != AmariteEnchantments.PYLON && enchantment != AmariteEnchantments.ORBIT
         ? class_1269.field_5814
         : class_1269.field_5812;
   }

   @ClientOnly
   public int mialib$getNameColor(class_1799 stack) {
      return this.getModeColor(stack);
   }

   @ClientOnly
   public void method_7851(class_1799 stack, @Nullable class_1937 world, @NotNull List<class_2561> tooltip, class_1836 context) {
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
                  class_2561.method_43471(this.getModeTranslationKey(stack)).mialib$withColor(this.getModeColor(stack))
               }
            )
            .method_27692(class_124.field_1080)
      );
      tooltip.add(class_2561.method_43471(this.method_7869() + ".desc").method_27692(class_124.field_1080));
   }

   @ClientOnly
   public void mialib$renderCustomBar(class_918 drawContext, class_327 renderer, class_1799 stack, int x, int y, String countLabel) {
      class_746 player = class_310.method_1551().field_1724;
      if (player != null) {
         RenderSystem.disableDepthTest();
         RenderSystem.disableTexture();
         RenderSystem.disableBlend();
         class_289 tessellator = class_289.method_1348();
         class_287 bufferBuilder = tessellator.method_1349();
         this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 13, 13, 2, 0, 0, 0, 255);
         int color = this.getModeColor(stack);
         if (class_1890.method_8225(AmariteEnchantments.REBOUND, stack) > 0) {
            this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 11, 13, 2, 0, 0, 0, 255);
            int reboundCharge = ((DiscComponent)Amarite.DISC.get(player)).reboundCharge;

            for (int i = 0; i < reboundCharge; i++) {
               this.mialib$renderGuiQuad(bufferBuilder, x + 3 + i * 4, y + 11, 3, 1, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255);
            }
         } else if (class_1890.method_8225(AmariteEnchantments.PYLON, stack) > 0) {
            this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 11, 13, 2, 0, 0, 0, 255);
            int pylonColor = player.mialib$isCoolingDown(Amarite.id("pylon")) ? -8355712 : color;
            this.mialib$renderGuiQuad(
               bufferBuilder,
               x + 3,
               y + 11,
               (int)((1.0F - player.mialib$getCooldown(Amarite.id("pylon"), 1.0F)) * 11.0F),
               1,
               pylonColor >> 16 & 0xFF,
               pylonColor >> 8 & 0xFF,
               pylonColor & 0xFF,
               255
            );
         } else if (class_1890.method_8225(AmariteEnchantments.ORBIT, stack) > 0) {
            this.mialib$renderGuiQuad(bufferBuilder, x + 2, y + 11, 13, 2, 0, 0, 0, 255);
            float charge = ((DiscComponent)Amarite.DISC.get(player)).orbitCharge;
            int orbitColor = charge < 24.0F ? -8355712 : color;
            this.mialib$renderGuiQuad(
               bufferBuilder,
               x + 3,
               y + 11,
               (int)(Math.min(1.0F, charge / 24.0F) * 11.0F),
               1,
               orbitColor >> 16 & 0xFF,
               orbitColor >> 8 & 0xFF,
               orbitColor & 0xFF,
               255
            );
         }

         for (int i = 0; i < 3; i++) {
            int disc = ((DiscComponent)Amarite.DISC.get(player)).getDiscDurability(i);
            this.mialib$renderGuiQuad(bufferBuilder, x + 3 + i * 4, y + 13, disc, 1, color >> 16 & 0xFF, color >> 8 & 0xFF, color & 0xFF, 255);
         }

         RenderSystem.enableBlend();
         RenderSystem.enableTexture();
         RenderSystem.enableDepthTest();
      }
   }

   public boolean mialib$shouldHideInHand(class_1309 entity, class_1268 hand, class_1799 stack) {
      if (entity instanceof class_1657 player) {
         DiscComponent discComponent = (DiscComponent)Amarite.DISC.get(player);
         int next = discComponent.getNextAvailableDisc();
         return next == -1;
      } else {
         return super.mialib$shouldHideInHand(entity, hand, stack);
      }
   }

   public void method_7850(class_1761 group, class_2371<class_1799> stacks) {
      if (this.method_7877(group)) {
         for (class_1887 enchantment : new class_1887[]{null, AmariteEnchantments.REBOUND, AmariteEnchantments.PYLON, AmariteEnchantments.ORBIT}) {
            class_1799 stack = new class_1799(this);
            if (enchantment != null) {
               stack.method_7978(enchantment, enchantment.method_8183());
            }

            stacks.add(stack);
         }
      }
   }

   public int getModeColor(class_1799 stack) {
      if (class_1890.method_8225(AmariteEnchantments.REBOUND, stack) > 0) {
         return 7466555;
      } else if (class_1890.method_8225(AmariteEnchantments.PYLON, stack) > 0) {
         return 3917038;
      } else {
         return class_1890.method_8225(AmariteEnchantments.ORBIT, stack) > 0 ? 16767516 : 14771072;
      }
   }

   public String getModeTranslationKey(class_1799 stack) {
      if (class_1890.method_8225(AmariteEnchantments.REBOUND, stack) > 0) {
         return this.method_7876() + ".rebound";
      } else if (class_1890.method_8225(AmariteEnchantments.PYLON, stack) > 0) {
         return this.method_7876() + ".pylon";
      } else {
         return class_1890.method_8225(AmariteEnchantments.ORBIT, stack) > 0 ? this.method_7876() + ".orbit" : this.method_7876() + ".recall";
      }
   }

   public boolean method_7885(class_2680 state, class_1937 world, class_2338 pos, class_1657 miner) {
      return false;
   }

   public boolean method_7886(class_1799 stack) {
      return false;
   }

   public boolean method_7870(class_1799 stack) {
      return true;
   }

   public int method_7837() {
      return 36;
   }
}
