package xyz.amymialee.mialib;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.class_1309;
import net.minecraft.class_1799;
import net.minecraft.class_809.class_811;

public class MiaLibClient implements ClientModInitializer {
   public static class_1309 renderingEntityWithItem = null;
   public static class_811 currentMode = class_811.field_4315;

   public void onInitializeClient() {
      if (!MRegistry.REGISTRIES.isEmpty()) {
         MiaLib.LOGGER.info("Building %d MiaLib Registr%s on Client".formatted(MRegistry.REGISTRIES.size(), MRegistry.REGISTRIES.size() == 1 ? "y" : "ies"));
         MRegistry.REGISTRIES.forEach(MRegistry::build);
      }

      ClientPlayNetworking.registerGlobalReceiver(MiaLib.id("floaty"), (minecraftClient, playNetworkHandler, packetByteBuf, packetSender) -> {
         class_1799 stack = packetByteBuf.method_10819();
         minecraftClient.execute(() -> minecraftClient.field_1773.method_3189(stack));
      });
   }

   static {
      for (class_811 mode : class_811.values()) {
         FabricModelPredicateProviderRegistry.register(MiaLib.id(mode.name().toLowerCase()), (stack, world, entity, seed) -> mode == currentMode ? 1.0F : 0.0F);
      }
   }
}
