package xyz.amymialee.amarite.blocks;

public class YellowCarnationPlantBlock extends class_2356 implements class_2256 {
   public YellowCarnationPlantBlock(class_1291 suspiciousStewEffect, int effectDuration, class_2251 settings) {
      super(suspiciousStewEffect, effectDuration, settings);
   }

   public boolean method_9651(class_1922 world, class_2338 pos, class_2680 state, boolean isClient) {
      return true;
   }

   public boolean method_9650(class_1937 world, class_5819 random, class_2338 pos, class_2680 state) {
      return true;
   }

   public void method_9652(class_3218 world, class_5819 random, class_2338 pos, class_2680 state) {
      method_9577(world, pos, new class_1799(this));
   }
}
