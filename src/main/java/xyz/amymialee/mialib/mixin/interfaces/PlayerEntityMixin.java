package xyz.amymialee.mialib.mixin.interfaces;

import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import org.spongepowered.asm.mixin.Mixin;
import xyz.amymialee.mialib.MiaLib;
import xyz.amymialee.mialib.cca.HoldingComponent;
import xyz.amymialee.mialib.interfaces.MPlayerEntity;

@Mixin({class_1657.class})
public abstract class PlayerEntityMixin extends class_1297 implements MPlayerEntity {
   public PlayerEntityMixin(class_1299<?> type, class_1937 world) {
      super(type, world);
   }

   @Override
   public boolean mialib$isAttacking() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).isAttacking();
   }

   @Override
   public boolean mialib$startedAttacking() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).startedAttacking();
   }

   @Override
   public boolean mialib$hasBeenAttacking() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).hasBeenAttacking();
   }

   @Override
   public boolean mialib$stoppedAttacking() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).stoppedAttacking();
   }

   @Override
   public int mialib$getAttackingTime() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).getTickAttacking();
   }

   @Override
   public boolean mialib$isUsing() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).isUsing();
   }

   @Override
   public boolean mialib$startedUsing() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).startedUsing();
   }

   @Override
   public boolean mialib$hasBeenUsing() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).hasBeenUsing();
   }

   @Override
   public boolean mialib$stoppedUsing() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).stoppedUsing();
   }

   @Override
   public int mialib$getUsingTime() {
      return ((HoldingComponent)MiaLib.HOLDING.get(this)).getTickUsing();
   }
}
