package xyz.amymialee.amarite.registry;

import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType;
import com.sammy.lodestone.systems.rendering.particle.type.LodestoneParticleType.Factory;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import xyz.amymialee.amarite.Amarite;

public interface AmariteParticles {
   LodestoneParticleType AMARITE = Amarite.REGISTRY.register("amarite", new LodestoneParticleType());
   LodestoneParticleType ACCUMULATION = Amarite.REGISTRY.register("accumulation", new LodestoneParticleType());

   static void init() {
   }

   @ClientOnly
   static void initFactories() {
      ParticleFactoryRegistry.getInstance().register(AMARITE, Factory::new);
      ParticleFactoryRegistry.getInstance().register(ACCUMULATION, Factory::new);
   }
}
