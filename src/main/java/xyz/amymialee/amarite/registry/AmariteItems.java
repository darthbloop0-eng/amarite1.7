package xyz.amymialee.amarite.registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map.Entry;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.class_1291;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1657;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1814;
import net.minecraft.class_1935;
import net.minecraft.class_2371;
import net.minecraft.class_2378;
import net.minecraft.class_2487;
import net.minecraft.class_2540;
import net.minecraft.class_2960;
import net.minecraft.class_310;
import net.minecraft.class_326;
import net.minecraft.class_3264;
import net.minecraft.class_3298;
import net.minecraft.class_3300;
import net.minecraft.class_5272;
import net.minecraft.class_4174.class_4175;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.item.setting.api.QuiltItemSettings;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;
import org.quiltmc.qsl.networking.api.client.ClientPlayNetworking;
import org.quiltmc.qsl.resource.loader.api.ResourceLoader;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents;
import org.quiltmc.qsl.resource.loader.api.ResourceLoaderEvents.EndDataPackReload;
import org.quiltmc.qsl.resource.loader.api.reloader.SimpleSynchronousResourceReloader;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.cca.LongswordAccumulateComponent;
import xyz.amymialee.amarite.items.AmariteDiscItem;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.items.AmariteMirrorItem;
import xyz.amymialee.amarite.items.MaskItem;
import xyz.amymialee.amarite.items.WinCookieItem;
import xyz.amymialee.mialib.MiaLibClient;

public interface AmariteItems {
   class_1761 AMARITE_GROUP = FabricItemGroupBuilder.create(Amarite.id("amarite_group")).icon(AmariteItems::createIconStack).appendItems(list -> {
      list.add(AmariteItems.AMARITE_LONGSWORD.method_7854());
      list.add(AmariteItems.AMARITE_LONGSWORD.method_7854().mialib$enchantStack(AmariteEnchantments.DOUBLE_DASH, 1));
      list.add(AmariteItems.AMARITE_LONGSWORD.method_7854().mialib$enchantStack(AmariteEnchantments.ACCUMULATE, 1));
      list.add(AmariteItems.AMARITE_LONGSWORD.method_7854().mialib$enchantStack(AmariteEnchantments.MALIGNANCY, 1));
      list.add(AmariteItems.AMARITE_DISC.method_7854());
      list.add(AmariteItems.AMARITE_DISC.method_7854().mialib$enchantStack(AmariteEnchantments.REBOUND, 1));
      list.add(AmariteItems.AMARITE_DISC.method_7854().mialib$enchantStack(AmariteEnchantments.PYLON, 1));
      list.add(AmariteItems.AMARITE_DISC.method_7854().mialib$enchantStack(AmariteEnchantments.ORBIT, 1));
      list.add(AmariteItems.AMARITE_MIRROR.method_7854());
      list.add(AmariteItems.WIN_COOKIE.method_7854());
      list.add(AmariteBlocks.WIN_PLUSHIE.method_8389().method_7854());
      list.add(AmariteItems.AXOLOTL_MASK.method_7854());
      list.add(AmariteItems.BUNNY_MASK.method_7854());
      list.add(AmariteItems.BUTTERFLY_MASK.method_7854());
      list.add(AmariteItems.DEMON_MASK.method_7854());
      list.add(AmariteItems.FOX_MASK.method_7854());
      list.add(AmariteItems.GUARDIAN_MASK.method_7854());
      list.add(AmariteItems.MOON_MASK.method_7854());
      list.add(AmariteItems.ONI_MASK.method_7854());
      list.add(AmariteItems.UNICORN_MASK.method_7854());
      list.add(AmariteItems.WARDEN_MASK.method_7854());
      list.add(AmariteItems.WINSWEEP_MASK.method_7854());
      list.add(AmariteItems.AMARITE_CROWN.method_7854());
      list.add(AmariteBlocks.AMETHYST_BRICKS.method_8389().method_7854());
      list.add(AmariteBlocks.AMETHYST_BRICK_WALL.method_8389().method_7854());
      list.add(AmariteBlocks.AMETHYST_BRICK_STAIRS.method_8389().method_7854());
      list.add(AmariteBlocks.AMETHYST_BRICK_SLAB.method_8389().method_7854());
      list.add(AmariteBlocks.AMETHYST_PILLAR.method_8389().method_7854());
      list.add(AmariteBlocks.CHISELED_AMETHYST.method_8389().method_7854());
      list.add(AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE.method_8389().method_7854());
      list.add(AmariteBlocks.CHISELED_AMETHYST_BUTTON.method_8389().method_7854());
      list.add(AmariteItems.AMARITE_SHARD.method_7854());
      list.add(AmariteBlocks.AMARITE_BLOCK.method_8389().method_7854());
      list.add(AmariteBlocks.BUDDING_AMARITE.method_8389().method_7854());
      list.add(AmariteBlocks.FRESH_AMARITE_BUD.method_8389().method_7854());
      list.add(AmariteBlocks.PARTIAL_AMARITE_BUD.method_8389().method_7854());
      list.add(AmariteBlocks.AMARITE_CLUSTER.method_8389().method_7854());
      list.add(AmariteBlocks.AMARITE_SPARK.method_8389().method_7854());
      list.add(AmariteBlocks.YELLOW_CARNATION_BOUQUET.method_8389().method_7854());
      list.add(AmariteBlocks.YELLOW_CARNATION.method_8389().method_7854());
      list.add(AmariteBlocks.POTTED_YELLOW_CARNATION.method_8389().method_7854());
   }).build().mialib$setConstantIcon(AmariteItems::createIconStack);
   class_2371<class_1799> AMARITE_ITEMS = class_2371.method_10211();
   class_2371<class_1799> AMARITE_MIRRORS = class_2371.method_10211();
   class_2371<class_1799> SPACED_AMARITE_MIRRORS = class_2371.method_10211();
   class_1792 AMARITE_LONGSWORD = Amarite.REGISTRY
      .register("amarite_longsword", new AmariteLongswordItem(new QuiltItemSettings().rarity(class_1814.field_8904).group(AMARITE_GROUP)));
   class_1792 AMARITE_DISC = Amarite.REGISTRY
      .register("amarite_disc", new AmariteDiscItem(new QuiltItemSettings().rarity(class_1814.field_8904).maxCount(1).group(AMARITE_GROUP)));
   class_1792 AMARITE_MIRROR = Amarite.REGISTRY
      .register("amarite_mirror", new AmariteMirrorItem(new QuiltItemSettings().maxCount(1).rarity(class_1814.field_8903).group(AMARITE_GROUP)));
   class_1792 AMARITE_SHARD = Amarite.REGISTRY.register("amarite_shard", new class_1792(new QuiltItemSettings().group(AMARITE_GROUP)));
   class_1792 WIN_COOKIE = Amarite.REGISTRY
      .register(
         "win_cookie",
         new WinCookieItem(
            new QuiltItemSettings()
               .food(
                  new class_4175()
                     .method_19238(2)
                     .method_19237(0.2F)
                     .method_19240()
                     .method_19239(new class_1293(class_1294.field_5926, 1200, 0, true, false, true), 1.0F)
                     .method_19242()
               )
               .rarity(class_1814.field_8903)
               .group(AMARITE_GROUP)
         )
      );
   class_1792 AXOLOTL_MASK = Amarite.REGISTRY
      .register(
         "axolotl_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/axolotl.png"), 13538338, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 BUNNY_MASK = Amarite.REGISTRY
      .register(
         "bunny_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/bunny.png"), 4276551, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 BUTTERFLY_MASK = Amarite.REGISTRY
      .register(
         "butterfly_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/butterfly.png"), 1791664, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 DEMON_MASK = Amarite.REGISTRY
      .register(
         "demon_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/demon.png"), 3883334, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 FOX_MASK = Amarite.REGISTRY
      .register(
         "fox_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/fox.png"), 14643506, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 GUARDIAN_MASK = Amarite.REGISTRY
      .register(
         "guardian_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/guardian.png"), 5813148, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 MOON_MASK = Amarite.REGISTRY
      .register(
         "moon_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/moon.png"), 12302788, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 ONI_MASK = Amarite.REGISTRY
      .register(
         "oni_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/oni.png"), 9313305, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 UNICORN_MASK = Amarite.REGISTRY
      .register(
         "unicorn_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/unicorn.png"), 13231849, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 WARDEN_MASK = Amarite.REGISTRY
      .register(
         "warden_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/warden.png"), 288381, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 WINSWEEP_MASK = Amarite.REGISTRY
      .register(
         "winsweep_mask",
         new MaskItem(
            Amarite.id("textures/entity/mask/winsweep.png"), 13070705, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );
   class_1792 AMARITE_CROWN = Amarite.REGISTRY
      .register(
         "amarite_crown",
         new MaskItem(
            Amarite.id("textures/entity/mask/crown.png"), 11297760, new QuiltItemSettings().rarity(class_1814.field_8907).maxCount(1).group(AMARITE_GROUP)
         )
      );

   static void init() {
      ResourceLoader.get(class_3264.field_14190).registerReloader(new AmariteItems.MirrorCooldownReloader());
      ResourceLoaderEvents.END_DATA_PACK_RELOAD.register((EndDataPackReload)(server, manager, throwable) -> {
         if (server != null) {
            int i = 0;
            int[] array = new int[AmariteMirrorItem.MIRROR_COOLDOWNS.size() * 2];

            for (Entry<class_1291, Integer> entry : AmariteMirrorItem.MIRROR_COOLDOWNS.entrySet()) {
               array[i * 2] = class_1291.method_5554(entry.getKey());
               array[i * 2 + 1] = entry.getValue();
               i++;
            }

            class_2540 buf = PacketByteBufs.create().method_10806(array);
            ServerPlayNetworking.send(server.method_3760().method_14571(), Amarite.id("mirror_cooldown"), buf);
         }
      });
      AMARITE_ITEMS.add(new class_1799(AMARITE_LONGSWORD));
      AMARITE_ITEMS.add(new class_1799(AMARITE_DISC));
   }

   @ClientOnly
   static void initClient() {
      ClientPlayNetworking.registerGlobalReceiver(Amarite.id("mirror_cooldown"), (client, handler, buf, responseSender) -> {
         int[] cooldowns = buf.method_10787();
         client.execute(() -> {
            AmariteMirrorItem.MIRROR_COOLDOWNS.clear();

            for (int i = 0; i < cooldowns.length && cooldowns.length > i + 1; i += 2) {
               AmariteMirrorItem.MIRROR_COOLDOWNS.put(class_1291.method_5569(cooldowns[i]), cooldowns[i + 1]);
            }
         });
      });
      ColorProviderRegistry.ITEM
         .register(
            (class_326)(stack, tintIndex) -> {
               if (tintIndex == 0) {
                  class_1657 player = (class_1657)(MiaLibClient.renderingEntityWithItem instanceof class_1657
                     ? (class_1657)MiaLibClient.renderingEntityWithItem
                     : class_310.method_1551().field_1724);
                  if (player != null) {
                     return AmariteLongswordItem.getMode(player, stack).getSwordTint();
                  }
               }

               class_2487 nbt = stack.method_7969();
               return nbt != null && nbt.method_10573("malignant", 3) ? nbt.method_10550("malignant") : 16777215;
            },
            new class_1935[]{AMARITE_LONGSWORD}
         );
      ColorProviderRegistry.ITEM
         .register(
            (class_326)(stack, tintIndex) -> tintIndex != 1
               ? 16777215
               : AmariteMirrorItem.getMirrorColor(
                  stack,
                  class_310.method_1551().field_1724 == null ? -1.0F : class_310.method_1551().field_1724.method_7357().method_7905(stack.method_7909(), 1.0F)
               ),
            new class_1935[]{AMARITE_MIRROR}
         );
      class_5272.method_27879(
         AMARITE_LONGSWORD,
         Amarite.id("blocking"),
         (stack, world, entity, seed) -> entity instanceof class_1657 player && entity.method_6047() == stack && player.mialib$isUsing() ? 1.0F : 0.0F
      );
      class_5272.method_27879(
         AMARITE_LONGSWORD,
         Amarite.id("accumulate"),
         (stack, world, entity, seed) -> entity instanceof class_1657 player && ((LongswordAccumulateComponent)Amarite.ACCUMULATE.get(player)).accumulateActive
            ? 1.0F
            : 0.0F
      );
      class_5272.method_27879(
         AMARITE_LONGSWORD, Amarite.id("malignant"), (stack, world, entity, seed) -> stack.method_7948().method_10545("malignant") ? 1.0F : 0.0F
      );
   }

   @NotNull
   static class_1799 createIconStack() {
      return !AMARITE_ITEMS.isEmpty()
         ? (class_1799)AMARITE_ITEMS.get(Math.abs((int)System.currentTimeMillis() / 800) % AMARITE_ITEMS.size())
         : class_1802.field_8477.method_7854();
   }

   @NotNull
   static class_1799 createMirrorStack() {
      return !AMARITE_MIRRORS.isEmpty()
         ? (class_1799)AMARITE_MIRRORS.get(Math.abs((int)System.currentTimeMillis() / 800) % AMARITE_MIRRORS.size())
         : class_1802.field_8477.method_7854();
   }

   public static class MirrorCooldownReloader implements SimpleSynchronousResourceReloader {
      @NotNull
      public class_2960 getQuiltId() {
         return Amarite.id("mirror_effects");
      }

      public void method_14491(@NotNull class_3300 manager) {
         AmariteMirrorItem.MIRROR_COOLDOWNS.clear();
         manager.method_41265("amarite_mirror", path -> path.method_12832().endsWith(".json"))
            .forEach(
               (identifier, resources) -> {
                  for (class_3298 resource : resources) {
                     try (InputStream stream = resource.method_14482()) {
                        JsonObject json = JsonParser.parseReader(new JsonReader(new InputStreamReader(stream))).getAsJsonObject();
                        class_2960 effectId = new class_2960(
                           identifier.method_12836(), identifier.method_12832().substring(15, identifier.method_12832().length() - 5)
                        );
                        class_1291 effect = (class_1291)class_2378.field_11159.method_10223(effectId);
                        if (effect != null) {
                           AmariteMirrorItem.MIRROR_COOLDOWNS.put(effect, json.get("cooldown").getAsInt());
                        }
                     } catch (Exception var10) {
                     }
                  }
               }
            );
      }
   }
}
