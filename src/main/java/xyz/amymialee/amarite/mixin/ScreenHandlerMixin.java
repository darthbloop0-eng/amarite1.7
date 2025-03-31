package xyz.amymialee.amarite.mixin;

import net.minecraft.class_1657;
import net.minecraft.class_1703;
import net.minecraft.class_1713;
import net.minecraft.class_1735;
import net.minecraft.class_1799;
import net.minecraft.class_2371;
import net.minecraft.class_3419;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

@Mixin({class_1703.class})
public class ScreenHandlerMixin {
   @Shadow
   @Final
   public class_2371<class_1735> field_7761;

   @Inject(
      method = {"internalOnSlotClick"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void amarite$preventStacking(int slotIndex, int button, class_1713 actionType, class_1657 player, CallbackInfo ci) {
      if (actionType == class_1713.field_7790 && button == 1 && slotIndex >= 0 && slotIndex < this.field_7761.size()) {
         class_1735 slot = (class_1735)this.field_7761.get(slotIndex);
         class_1799 stack = slot.method_7677();
         if (stack.method_7909() instanceof MaskItem) {
            MaskItem.incrementOffset(stack);
            player.method_17356(AmariteSoundEvents.MASK_OFFSET, class_3419.field_15248, 0.9F, 1.5F);
            ci.cancel();
         }
      }
   }
}
