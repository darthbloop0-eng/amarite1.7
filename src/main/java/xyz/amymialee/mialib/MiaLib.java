package xyz.amymialee.mialib;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.class_1657;
import net.minecraft.class_2960;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.amymialee.mialib.cca.HoldingComponent;
import xyz.amymialee.mialib.cca.IdCooldownComponent;

public class MiaLib implements ModInitializer, EntityComponentInitializer {
   public static final String MOD_ID = "mialib";
   public static final Logger LOGGER = LoggerFactory.getLogger("mialib");
   public static final ComponentKey<IdCooldownComponent> ID_COOLDOWN_COMPONENT = ComponentRegistry.getOrCreate(
      id("identifier_cooldown"), IdCooldownComponent.class
   );
   public static final ComponentKey<HoldingComponent> HOLDING = ComponentRegistry.getOrCreate(id("holding"), HoldingComponent.class);

   public void onInitialize() {
      ServerPlayNetworking.registerGlobalReceiver(id("attacking"), (server, player, handler, buf, responseSender) -> {
         boolean holding = buf.readBoolean();
         server.execute(() -> ((HoldingComponent)HOLDING.get(player)).setAttacking(holding));
      });
      ServerPlayNetworking.registerGlobalReceiver(id("using"), (server, player, handler, buf, responseSender) -> {
         boolean holding = buf.readBoolean();
         server.execute(() -> ((HoldingComponent)HOLDING.get(player)).setUsing(holding));
      });
   }

   public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
      registry.beginRegistration(class_1657.class, ID_COOLDOWN_COMPONENT).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(IdCooldownComponent::new);
      registry.beginRegistration(class_1657.class, HOLDING).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(HoldingComponent::new);
   }

   @NotNull
   public static class_2960 id(String path) {
      return new class_2960("mialib", path);
   }
}
