package xyz.amymialee.amarite.mixin;

import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1817;
import net.minecraft.class_3222;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.items.AmariteMirrorItem;
import xyz.amymialee.amarite.registry.AmariteItems;

@Mixin({class_1817.class})
public class ServerItemCooldownManagerMixin {
   @Shadow
   @Final
   private class_3222 field_8910;

   @Inject(
      method = {"onCooldownUpdate(Lnet/minecraft/item/Item;)V"},
      at = {@At("TAIL")}
   )
   private void amarite$mirrorRefresh(class_1792 item, CallbackInfo info) {
      if (item == AmariteItems.AMARITE_MIRROR) {
         class_1799 stack = this.field_8910.method_6079();
         if (stack.method_31574(AmariteItems.AMARITE_MIRROR)) {
            AmariteMirrorItem.useMirror(this.field_8910.field_6002, this.field_8910, stack, false);
         }
      }
   }
}
