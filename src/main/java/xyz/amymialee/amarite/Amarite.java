package xyz.amymialee.amarite;

import dev.onyxstudios.cca.api.v3.block.BlockComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.block.BlockComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import java.util.List;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.class_1291;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1866;
import net.minecraft.class_2378;
import net.minecraft.class_2580;
import net.minecraft.class_2960;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.QuiltLoader;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.amymialee.amarite.cca.BeaconComponent;
import xyz.amymialee.amarite.cca.BuddedComponent;
import xyz.amymialee.amarite.cca.DiscComponent;
import xyz.amymialee.amarite.cca.DiscPylonComponent;
import xyz.amymialee.amarite.cca.LongswordAccumulateComponent;
import xyz.amymialee.amarite.cca.LongswordDashComponent;
import xyz.amymialee.amarite.cca.LongswordDoubleDashComponent;
import xyz.amymialee.amarite.cca.LongswordMalignancyComponent;
import xyz.amymialee.amarite.compat.AmariteArsenalCompat;
import xyz.amymialee.amarite.recipes.AmariteFoodRecipe;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteDamageTypes;
import xyz.amymialee.amarite.registry.AmariteEnchantments;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteParticles;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;
import xyz.amymialee.mialib.MRegistry;

public class Amarite implements ModInitializer, BlockComponentInitializer, EntityComponentInitializer {
   public static final String MOD_ID = "amarite";
   public static final Logger LOGGER = LoggerFactory.getLogger("amarite");
   public static final MRegistry REGISTRY = new MRegistry("amarite");
   public static final ComponentKey<BeaconComponent> BEACON = ComponentRegistry.getOrCreate(id("beacon"), BeaconComponent.class);
   public static final ComponentKey<LongswordDashComponent> DASH = ComponentRegistry.getOrCreate(id("dash"), LongswordDashComponent.class);
   public static final ComponentKey<LongswordDoubleDashComponent> DOUBLE_DASH = ComponentRegistry.getOrCreate(
      id("double_dash"), LongswordDoubleDashComponent.class
   );
   public static final ComponentKey<LongswordAccumulateComponent> ACCUMULATE = ComponentRegistry.getOrCreate(
      id("accumulate"), LongswordAccumulateComponent.class
   );
   public static final ComponentKey<LongswordMalignancyComponent> MALIGNANCY = ComponentRegistry.getOrCreate(
      id("malignancy"), LongswordMalignancyComponent.class
   );
   public static final ComponentKey<DiscComponent> DISC = ComponentRegistry.getOrCreate(id("disc"), DiscComponent.class);
   public static final ComponentKey<DiscPylonComponent> PYLON = ComponentRegistry.getOrCreate(id("pylon"), DiscPylonComponent.class);
   public static final ComponentKey<BuddedComponent> BUDDED = ComponentRegistry.getOrCreate(id("budded"), BuddedComponent.class);

   public void onInitialize(@NotNull ModContainer mod) {
      AmariteBlocks.init();
      AmariteDamageTypes.init();
      AmariteEnchantments.init();
      AmariteEntities.init();
      AmariteItems.init();
      AmariteParticles.init();
      AmariteSoundEvents.init();
      if (QuiltLoader.isModLoaded("arsenal")) {
         AmariteArsenalCompat.init();
      }

      class_2378.method_10230(class_2378.field_17597, id("amarite_food"), AmariteFoodRecipe.AmariteFoodRecipeType.INSTANCE);
      class_2378.method_10230(class_2378.field_17598, id("amarite_food"), new class_1866(AmariteFoodRecipe::new));
      FabricItemGroupBuilder.create(id("mirror_group"))
         .appendItems(list -> list.addAll(AmariteItems.SPACED_AMARITE_MIRRORS))
         .icon(AmariteItems::createMirrorStack)
         .build()
         .mialib$setConstantIcon(AmariteItems::createMirrorStack)
         .method_7753("amarite_mirror.png");
      List<class_1291> defaultMirrors = List.of(
         class_1294.field_5904,
         class_1294.field_5917,
         class_1294.field_5907,
         class_1294.field_5913,
         class_1294.field_5910,
         class_1294.field_5924,
         class_1294.field_5927
      );
      AmariteItems.SPACED_AMARITE_MIRRORS.clear();
      AmariteItems.SPACED_AMARITE_MIRRORS.add(class_1799.field_8037);

      for (class_1291 primary : defaultMirrors) {
         class_1799 stack = new class_1799(AmariteItems.AMARITE_MIRROR);
         stack.method_7948().method_10569("Primary", class_1291.method_5554(primary));
         AmariteItems.SPACED_AMARITE_MIRRORS.add(stack);
      }

      AmariteItems.SPACED_AMARITE_MIRRORS.add(class_1799.field_8037);

      for (class_1291 primary : defaultMirrors) {
         int i = 1;
         AmariteItems.SPACED_AMARITE_MIRRORS.add(class_1799.field_8037);

         for (class_1291 secondary : defaultMirrors) {
            class_1799 stack = new class_1799(AmariteItems.AMARITE_MIRROR);
            stack.method_7948().method_10569("Primary", class_1291.method_5554(primary));
            stack.method_7948().method_10569("Secondary", class_1291.method_5554(secondary));
            AmariteItems.SPACED_AMARITE_MIRRORS.add(stack);
            i++;
         }

         while (i < 9) {
            AmariteItems.SPACED_AMARITE_MIRRORS.add(class_1799.field_8037);
            i++;
         }
      }

      AmariteItems.AMARITE_MIRRORS.clear();
      AmariteItems.SPACED_AMARITE_MIRRORS.forEach(stackx -> {
         if (!stackx.method_7960()) {
            AmariteItems.AMARITE_MIRRORS.add(stackx);
         }
      });
   }

   public void registerBlockComponentFactories(@NotNull BlockComponentFactoryRegistry registry) {
      registry.beginRegistration(class_2580.class, BEACON).impl(BeaconComponent.class).end(BeaconComponent::new);
   }

   public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
      registry.beginRegistration(class_1657.class, DASH).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordDashComponent::new);
      registry.beginRegistration(class_1657.class, DOUBLE_DASH).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordDoubleDashComponent::new);
      registry.beginRegistration(class_1657.class, ACCUMULATE).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordAccumulateComponent::new);
      registry.beginRegistration(class_1657.class, MALIGNANCY).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(LongswordMalignancyComponent::new);
      registry.beginRegistration(class_1657.class, DISC).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(DiscComponent::new);
      registry.beginRegistration(class_1297.class, PYLON).respawnStrategy(RespawnCopyStrategy.NEVER_COPY).end(DiscPylonComponent::new);
      registry.beginRegistration(class_1657.class, BUDDED).respawnStrategy(RespawnCopyStrategy.INVENTORY).end(BuddedComponent::new);
   }

   @NotNull
   public static class_2960 id(String name) {
      return new class_2960("amarite", name);
   }
}
