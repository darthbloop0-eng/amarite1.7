package xyz.amymialee.mialib.mixin.interfaces;

import net.minecraft.class_2580.class_2581;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import xyz.amymialee.mialib.interfaces.MBeaconBeamSegment;

@Mixin({class_2581.class})
public class BeaconBeamSegmentMixin implements MBeaconBeamSegment {
   @Unique
   private boolean hidden = false;

   @Override
   public boolean mialib$isHidden() {
      return this.hidden;
   }

   @Override
   public void mialib$setHidden(boolean hidden) {
      this.hidden = hidden;
   }
}
