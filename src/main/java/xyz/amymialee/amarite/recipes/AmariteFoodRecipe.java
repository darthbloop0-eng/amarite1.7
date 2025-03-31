package xyz.amymialee.amarite.recipes;

import net.minecraft.class_1715;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1852;
import net.minecraft.class_1856;
import net.minecraft.class_1865;
import net.minecraft.class_1935;
import net.minecraft.class_1937;
import net.minecraft.class_2960;
import net.minecraft.class_3956;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.registry.AmariteItems;

public class AmariteFoodRecipe extends class_1852 {
   private static final class_1856 SHARD = class_1856.method_8091(new class_1935[]{AmariteItems.AMARITE_SHARD});
   private static final class_1856 HONEY = class_1856.method_8091(new class_1935[]{class_1802.field_20417});

   public AmariteFoodRecipe(class_2960 identifier) {
      super(identifier);
   }

   public boolean matches(@NotNull class_1715 craftingInventory, class_1937 world) {
      int count = 0;
      boolean hasFood = false;
      boolean hasModifier = false;

      for (int i = 0; i < craftingInventory.method_5439(); i++) {
         class_1799 itemStack = craftingInventory.method_5438(i);
         if (!itemStack.method_7960()) {
            if (!hasFood && itemStack.method_19267()) {
               hasFood = true;
            } else {
               if (hasModifier || !SHARD.method_8093(itemStack) && !HONEY.method_8093(itemStack)) {
                  return false;
               }

               hasModifier = true;
            }

            count++;
         }
      }

      return hasFood && hasModifier && count == 2;
   }

   public class_1799 craft(@NotNull class_1715 craftingInventory) {
      class_1799 outputStack = class_1799.field_8037;

      for (int i = 0; i < craftingInventory.method_5439(); i++) {
         class_1799 stack = craftingInventory.method_5438(i);
         if (!stack.method_7960() && stack.method_19267()) {
            outputStack = stack.method_7972();
            outputStack.method_7939(1);
            i = craftingInventory.method_5439();
         }
      }

      for (int ix = 0; ix < craftingInventory.method_5439(); ix++) {
         class_1799 stack = craftingInventory.method_5438(ix);
         if (!stack.method_7960()) {
            if (SHARD.method_8093(stack)) {
               outputStack.method_7948().method_10556("Budded", true);
               ix = craftingInventory.method_5439();
            }

            if (HONEY.method_8093(stack)) {
               outputStack.method_7948().method_10556("Curative", true);
               ix = craftingInventory.method_5439();
            }
         }
      }

      return outputStack;
   }

   public boolean method_8113(int width, int height) {
      return width * height >= 2;
   }

   public class_1865<?> method_8119() {
      return class_1865.field_9043;
   }

   public static class AmariteFoodRecipeType implements class_3956<AmariteFoodRecipe> {
      public static final AmariteFoodRecipe.AmariteFoodRecipeType INSTANCE = new AmariteFoodRecipe.AmariteFoodRecipeType();

      private AmariteFoodRecipeType() {
      }
   }
}
