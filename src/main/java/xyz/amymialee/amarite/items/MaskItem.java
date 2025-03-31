package xyz.amymialee.amarite.items;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.TrinketsApi;
import java.util.List;
import java.util.Optional;
import net.minecraft.class_124;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_1836;
import net.minecraft.class_1890;
import net.minecraft.class_1937;
import net.minecraft.class_2487;
import net.minecraft.class_2561;
import net.minecraft.class_2960;
import net.minecraft.class_3545;
import net.minecraft.class_5151;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import xyz.amymialee.amarite.registry.AmariteEnchantments;
import xyz.amymialee.mialib.util.MMath;

public class MaskItem extends TrinketItem implements class_5151 {
   public final class_2960 maskTexture;
   private final int color;

   public MaskItem(class_2960 texture, int color, QuiltItemSettings settings) {
      super(settings);
      this.maskTexture = texture;
      this.color = color;
   }

   public static class_1799 getWornMask(class_1309 livingEntity) {
      Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(livingEntity);
      if (component.isPresent()) {
         for (class_3545<SlotReference, class_1799> pair : component.get().getAllEquipped()) {
            if (((class_1799)pair.method_15441()).method_7909() instanceof MaskItem) {
               return (class_1799)pair.method_15441();
            }
         }
      }

      return class_1799.field_8037;
   }

   public static int getOffset(@NotNull class_1799 stack) {
      return stack.method_7969() == null ? 0 : stack.method_7969().method_10550("offset");
   }

   public static void incrementOffset(@NotNull class_1799 stack) {
      class_2487 compound = stack.method_7948();
      compound.method_10569("offset", MMath.clampLoop(compound.method_10550("offset") + 1, -2, 3));
   }

   public int mialib$getNameColor(class_1799 stack) {
      return this.color;
   }

   public void method_7851(@NotNull class_1799 stack, @Nullable class_1937 world, @NotNull List<class_2561> tooltip, class_1836 context) {
      if (class_1890.method_8225(AmariteEnchantments.ANONYMITY, stack) > 0) {
         tooltip.add(class_2561.method_43471("item.amarite.mask.anonymity").mialib$withColor(5789879));
      }

      if (class_1890.method_8225(AmariteEnchantments.CONCEALMENT, stack) > 0) {
         tooltip.add(class_2561.method_43471("item.amarite.mask.concealment").mialib$withColor(13992792));
      }

      tooltip.add(
         class_2561.method_43469("item.amarite.mask.offset_1", new Object[]{class_2561.method_43471("key.mouse.right").mialib$withColor(7699656)})
            .method_27692(class_124.field_1080)
      );
      if (stack.method_7969() != null) {
         tooltip.add(
            class_2561.method_43469("item.amarite.mask.offset_2", new Object[]{stack.method_7948().method_10550("offset")}).method_27692(class_124.field_1080)
         );
      }

      super.method_7851(stack, world, tooltip, context);
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
