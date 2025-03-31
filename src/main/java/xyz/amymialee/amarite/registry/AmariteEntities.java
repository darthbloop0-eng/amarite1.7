package xyz.amymialee.amarite.registry;

import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.class_1291;
import net.minecraft.class_1299;
import net.minecraft.class_1311;
import net.minecraft.class_4048;
import net.minecraft.class_4081;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.client.DiscEntityRenderer;
import xyz.amymialee.amarite.client.MalignancyEntityRenderer;
import xyz.amymialee.amarite.client.PylonEntityRenderer;
import xyz.amymialee.amarite.effects.BuddingStatusEffect;
import xyz.amymialee.amarite.entities.DiscEntity;
import xyz.amymialee.amarite.entities.MalignancyEntity;
import xyz.amymialee.amarite.entities.PylonEntity;

public interface AmariteEntities {
   class_1299<DiscEntity> DISC = Amarite.REGISTRY
      .register(
         "disc",
         FabricEntityTypeBuilder.create(class_1311.field_17715, DiscEntity::new)
            .dimensions(class_4048.method_18385(0.8F, 0.8F))
            .trackRangeChunks(6)
            .disableSaving()
            .trackedUpdateRate(1)
            .build()
      );
   class_1299<PylonEntity> PYLON = Amarite.REGISTRY
      .register(
         "pylon",
         FabricEntityTypeBuilder.create(class_1311.field_17715, PylonEntity::new)
            .dimensions(class_4048.method_18385(0.5F, 1.0F))
            .trackRangeChunks(6)
            .disableSaving()
            .trackedUpdateRate(1)
            .build()
      );
   class_1299<MalignancyEntity> MALIGNANCY = Amarite.REGISTRY
      .register(
         "malignancy",
         FabricEntityTypeBuilder.create(class_1311.field_17715, MalignancyEntity::new)
            .dimensions(class_4048.method_18385(0.2F, 1.2F))
            .trackRangeChunks(6)
            .disableSaving()
            .trackedUpdateRate(1)
            .build()
      );
   class_1291 BUDDING = Amarite.REGISTRY.register("budding", new BuddingStatusEffect(class_4081.field_18272, 11145642));

   static void init() {
   }

   @ClientOnly
   static void initRenderers() {
      EntityRendererRegistry.register(DISC, DiscEntityRenderer::new);
      EntityRendererRegistry.register(PYLON, PylonEntityRenderer::new);
      EntityRendererRegistry.register(MALIGNANCY, MalignancyEntityRenderer::new);
   }
}
