package xyz.amymialee.amarite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import java.util.List;
import net.minecraft.class_1922;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2580;
import net.minecraft.class_2680;
import net.minecraft.class_3414;
import net.minecraft.class_2580.class_2581;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.blocks.AmariteSparkBlockEntity;
import xyz.amymialee.amarite.cca.BeaconComponent;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.mialib.interfaces.MBeaconBeamSegment;
import xyz.amymialee.mialib.mixin.accessors.BeaconBlockEntityAccessor;

@Mixin({class_2580.class})
public abstract class BeaconBlockEntityMixin {
   @Shadow
   public static void method_10938(class_1937 world, class_2338 pos, class_3414 sound) {
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")}
   )
   private static void amarite$sparkling(
      @NotNull class_1937 world,
      class_2338 pos,
      class_2680 state,
      class_2580 blockEntity,
      CallbackInfo ci,
      @Share("stop") @NotNull LocalBooleanRef stop,
      @Share("started") @NotNull LocalBooleanRef started
   ) {
      stop.set(false);
      started.set(false);
   }

   @WrapOperation(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/block/BlockState;getBlock()Lnet/minecraft/block/Block;"
      )}
   )
   private static class_2248 amarite$sparkling(
      class_2680 instance,
      Operation<class_2248> original,
      class_1937 world,
      class_2338 pos,
      class_2680 state,
      class_2580 blockEntity,
      @Local(ordinal = 1) class_2338 blockPos,
      @Share("stop") LocalBooleanRef stop,
      @Share("started") @NotNull LocalBooleanRef started
   ) {
      if (started.get()) {
         BeaconBlockEntityAccessor accessor = (BeaconBlockEntityAccessor)blockEntity;
         accessor.setMinY(world.method_31607() - 1);
         return class_2246.field_10124;
      } else {
         class_2680 targetBlock = world.method_8320(blockPos);
         boolean stopped = false;
         if (targetBlock.method_27852(AmariteBlocks.AMARITE_SPARK) && world.method_8321(blockPos) instanceof AmariteSparkBlockEntity spark) {
            spark.setPowered(2);
            stopped = true;
         } else if (targetBlock.method_27852(class_2246.field_27115)) {
            stopped = true;
         } else if (targetBlock.method_27852(class_2246.field_27159)) {
            ((BeaconComponent)Amarite.BEACON.get(blockEntity)).startConversion(blockPos);
            stopped = true;
         }

         if (stopped) {
            class_2581 endSegment = new class_2581(new float[]{1.0F, 1.0F, 1.0F});
            ((MBeaconBeamSegment)endSegment).mialib$setHidden(true);
            BeaconBlockEntityAccessor accessor = (BeaconBlockEntityAccessor)blockEntity;
            List<class_2581> segments = accessor.getField();
            segments.add(endSegment);
            accessor.setBeamSegments(segments);
            accessor.setMinY(world.method_31607() - 1);
            stop.set(true);
            started.set(true);
            return class_2246.field_10124;
         } else {
            return (class_2248)original.call(new Object[]{instance});
         }
      }
   }

   @WrapOperation(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/block/BlockState;getOpacity(Lnet/minecraft/world/BlockView;Lnet/minecraft/util/math/BlockPos;)I"
      )}
   )
   private static int amarite$ord(
      class_2680 instance, class_1922 blockView, class_2338 blockPos, Operation<Integer> original, @Share("stop") @NotNull LocalBooleanRef stop
   ) {
      return !stop.get() ? (Integer)original.call(new Object[]{instance, blockView, blockPos}) : 0;
   }

   @WrapOperation(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/block/entity/BeaconBlockEntity$BeamSegment;increaseHeight()V",
         ordinal = 1
      )}
   )
   private static void amarite$ord(class_2581 instance, Operation<Void> original, @Share("stop") @NotNull LocalBooleanRef stop) {
      if (!stop.get()) {
         original.call(new Object[]{instance});
      }
   }

   @WrapOperation(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/util/math/BlockPos;up()Lnet/minecraft/util/math/BlockPos;"
      )}
   )
   private static class_2338 amarite$up(
      class_2338 instance,
      Operation<class_2338> original,
      class_1937 world,
      class_2338 pos,
      class_2680 state,
      class_2580 blockEntity,
      @Share("stop") @NotNull LocalBooleanRef stop
   ) {
      if (stop.get()) {
         ((BeaconBlockEntityAccessor)blockEntity).setMinY(world.method_31607() - 1);
         return instance.method_10069(0, world.method_31600(), 0);
      } else {
         return (class_2338)original.call(new Object[]{instance});
      }
   }

   @WrapOperation(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/block/entity/BeaconBlockEntity;updateLevel(Lnet/minecraft/world/World;III)I"
      )}
   )
   private static int amarite$stop(class_1937 world, int x, int y, int z, @NotNull Operation<Integer> original, @Share("stop") @NotNull LocalBooleanRef stop) {
      Integer level = (Integer)original.call(new Object[]{world, x, y, z});
      return level == 0 && stop.get() ? -1 : level;
   }

   @Inject(
      method = {"tick"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/world/World;getBottomY()I"
      )},
      cancellable = true
   )
   private static void amarite$on(
      class_1937 world, class_2338 pos, class_2680 state, class_2580 blockEntity, CallbackInfo ci, @Share("stop") @NotNull LocalBooleanRef stop
   ) {
      if (stop.get()) {
         ci.cancel();
      }
   }
}
