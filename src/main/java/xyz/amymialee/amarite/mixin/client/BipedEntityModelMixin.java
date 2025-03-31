package xyz.amymialee.amarite.mixin.client;

import net.minecraft.class_1306;
import net.minecraft.class_1309;
import net.minecraft.class_3532;
import net.minecraft.class_4592;
import net.minecraft.class_572;
import net.minecraft.class_630;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_572.class})
public abstract class BipedEntityModelMixin<T extends class_1309> extends class_4592<T> {
   @Shadow
   @Final
   public class_630 field_3391;
   @Shadow
   @Final
   public class_630 field_3398;

   @Shadow
   protected abstract class_1306 method_2806(T var1);

   @Shadow
   protected abstract class_630 method_2808(class_1306 var1);

   @Inject(
      method = {"animateArms"},
      at = {@At("TAIL")}
   )
   protected void amarite$twoHanding(T entity, float animationProgress, CallbackInfo ci) {
      if (!(this.field_3447 <= 0.0F) && entity.method_6047().method_31574(AmariteItems.AMARITE_LONGSWORD)) {
         class_1306 arm = this.method_2806(entity).method_5928();
         class_630 modelPart = this.method_2808(arm);
         double f = 1.0 - Math.pow(1.0F - this.field_3447, 3.0);
         float h = class_3532.method_15374(this.field_3447 * (float) Math.PI) * -(this.field_3398.field_3654 - 0.7F) * 0.75F;
         modelPart.field_3654 = modelPart.field_3654 - (class_3532.method_15374((float)(f * Math.PI)) * 1.2F + h);
         modelPart.field_3675 = modelPart.field_3675 + this.field_3391.field_3675 * 2.0F;
         modelPart.field_3674 = modelPart.field_3674 + class_3532.method_15374(this.field_3447 * (float) Math.PI) * -0.4F;
      }
   }
}
