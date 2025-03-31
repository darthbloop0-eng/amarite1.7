package xyz.amymialee.amarite.blocks;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class AmariteClusterBlock extends AmariteBlock implements class_3737 {
   public static final class_2746 WATERLOGGED = class_2741.field_12508;
   protected final class_265 northShape;
   protected final class_265 southShape;
   protected final class_265 eastShape;
   protected final class_265 westShape;
   protected final class_265 upShape;
   protected final class_265 downShape;

   public AmariteClusterBlock(int height, int xzOffset, class_2251 settings) {
      super(settings);
      this.method_9590((class_2680)((class_2680)this.method_9564().method_11657(WATERLOGGED, Boolean.FALSE)).method_11657(FACING, class_2350.field_11036));
      this.upShape = class_2248.method_9541(xzOffset, 0.0, xzOffset, 16 - xzOffset, height, 16 - xzOffset);
      this.downShape = class_2248.method_9541(xzOffset, 16 - height, xzOffset, 16 - xzOffset, 16.0, 16 - xzOffset);
      this.northShape = class_2248.method_9541(xzOffset, xzOffset, 16 - height, 16 - xzOffset, 16 - xzOffset, 16.0);
      this.southShape = class_2248.method_9541(xzOffset, xzOffset, 0.0, 16 - xzOffset, 16 - xzOffset, height);
      this.eastShape = class_2248.method_9541(0.0, xzOffset, xzOffset, height, 16 - xzOffset, 16 - xzOffset);
      this.westShape = class_2248.method_9541(16 - height, xzOffset, xzOffset, 16.0, 16 - xzOffset, 16 - xzOffset);
   }

   public void method_9496(class_2680 state, class_1937 world, class_2338 pos, @NotNull class_5819 random) {
      if (random.method_43048(5) == 0) {
         float r = 0.6745098F;
         float g = 0.3882353F;
         float b = 0.8784314F;
         class_243 point = class_243.method_24953(pos);
         ParticleBuilders.create(AmariteParticles.AMARITE)
            .overrideAnimator(Animator.WITH_AGE)
            .setLifetime(8)
            .setAlpha(0.6F, 0.0F)
            .setAlphaEasing(Easing.CUBIC_IN)
            .setColorCoefficient(0.8F)
            .setColorEasing(Easing.CIRC_OUT)
            .setSpinEasing(Easing.SINE_IN)
            .setColor(r, g, b, 1.0F)
            .setScale(0.24F, 0.12F)
            .setSpinOffset(world.method_8409().method_43048(360))
            .setSpin(world.method_8409().method_43056() ? 0.5F : -0.5F)
            .randomMotion(0.01F)
            .randomOffset(0.5)
            .spawn(world, point.field_1352, point.field_1351, point.field_1350);
      }

      super.method_9496(state, world, pos, random);
   }

   public class_3619 method_9527(class_2680 state) {
      return class_3619.field_15971;
   }

   public class_265 method_9530(@NotNull class_2680 state, class_1922 world, class_2338 pos, class_3726 context) {
      class_2350 direction = (class_2350)state.method_11654(FACING);

      return switch (direction) {
         case field_11043 -> this.northShape;
         case field_11035 -> this.southShape;
         case field_11034 -> this.eastShape;
         case field_11039 -> this.westShape;
         case field_11033 -> this.downShape;
         case field_11036 -> this.upShape;
         default -> throw new IncompatibleClassChangeError();
      };
   }

   public boolean method_9558(@NotNull class_2680 state, @NotNull class_4538 world, @NotNull class_2338 pos) {
      class_2350 direction = (class_2350)state.method_11654(FACING);
      class_2338 blockPos = pos.method_10093(direction.method_10153());
      return world.method_8320(blockPos).method_26206(world, blockPos, direction);
   }

   public class_2680 method_9559(
      @NotNull class_2680 state, class_2350 direction, class_2680 neighborState, class_1936 world, class_2338 pos, class_2338 neighborPos
   ) {
      if ((Boolean)state.method_11654(WATERLOGGED)) {
         world.method_39281(pos, class_3612.field_15910, class_3612.field_15910.method_15789(world));
      }

      return direction == ((class_2350)state.method_11654(FACING)).method_10153() && !state.method_26184(world, pos)
         ? class_2246.field_10124.method_9564()
         : super.method_9559(state, direction, neighborState, world, pos, neighborPos);
   }

   @Nullable
   @Override
   public class_2680 method_9605(@NotNull class_1750 ctx) {
      class_1936 worldAccess = ctx.method_8045();
      class_2338 blockPos = ctx.method_8037();
      return (class_2680)((class_2680)this.method_9564().method_11657(WATERLOGGED, worldAccess.method_8316(blockPos).method_15772() == class_3612.field_15910))
         .method_11657(FACING, ctx.method_8038());
   }

   public class_3610 method_9545(@NotNull class_2680 state) {
      return state.method_11654(WATERLOGGED) ? class_3612.field_15910.method_15729(false) : super.method_9545(state);
   }

   @Override
   protected void method_9515(@NotNull class_2690<class_2248, class_2680> builder) {
      builder.method_11667(new class_2769[]{WATERLOGGED, FACING});
   }

   public static class AmariteBud extends AmariteClusterBlock {
      public AmariteBud(int height, int xzOffset, class_2251 settings) {
         super(height, xzOffset, settings);
      }

      @Override
      public void method_9496(class_2680 state, class_1937 world, class_2338 pos, @NotNull class_5819 random) {
      }
   }
}
