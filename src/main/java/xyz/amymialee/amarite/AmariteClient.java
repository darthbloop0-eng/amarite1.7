package xyz.amymialee.amarite;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import team.lodestar.lodestonedav.systems.rendering.particle.Easing;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteParticles;

public class AmariteClient implements ClientModInitializer {
   public void onInitializeClient(ModContainer mod) {
      AmariteBlocks.initClient();
      AmariteEntities.initRenderers();
      AmariteItems.initClient();
      AmariteParticles.initFactories();
      FabricLoader.getInstance()
         .getModContainer("amarite")
         .ifPresent(
            modContainer -> ResourceManagerHelper.registerBuiltinResourcePack(
               Amarite.id("gooberfied"), modContainer, "§dGooberfied Amarite", ResourcePackActivationType.NORMAL
            )
         );
      ClientPlayNetworking.registerGlobalReceiver(
         Amarite.id("budgrow"),
         (client, handler, buf, responseSender) -> {
			 BlockPos pos = buf.readBlockPos();
            client.execute(
               () -> {
                  if (client.world != null) {
					  Vec3d point = Vec3d.ofCenter(pos);
                     float r = 0.6745098F;
                     float g = 0.3882353F;
                     float b = 0.8784314F;

                     for (int i = 0; i < 32; i++) {
                        ParticleBuilder.create(AmariteParticles.AMARITE)
                           .overrideAnimator(Animator.WITH_AGE)
                           .setLifetime(16)
                           .setAlpha(0.6F, 0.0F)
                           .setAlphaEasing(Easing.CUBIC_IN)
                           .setColorCoefficient(0.8F)
                           .setColorEasing(Easing.CIRC_OUT)
                           .setSpinEasing(Easing.SINE_IN)
                           .setColor(r, g, b, 1.0F)
                           .setScale(0.24F, 0.12F)
                           .setSpinOffset(client.world.getRandom().method_43048(360))
                           .setSpin(client.field_1687.method_8409().method_43056() ? 0.5F : -0.5F)
                           .randomMotion(0.32F)
                           .randomOffset(0.5)
                           .spawn(client.field_1687, point.field_1352, point.field_1351, point.field_1350);
                     }
                  }
               }
            );
         }
      );
   }
}
