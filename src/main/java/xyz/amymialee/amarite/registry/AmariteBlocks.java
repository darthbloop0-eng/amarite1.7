package xyz.amymialee.amarite.registry;

import net.minecraft.class_1294;
import net.minecraft.class_1921;
import net.minecraft.class_2246;
import net.minecraft.class_2248;
import net.minecraft.class_2362;
import net.minecraft.class_2465;
import net.minecraft.class_2482;
import net.minecraft.class_2498;
import net.minecraft.class_2510;
import net.minecraft.class_2521;
import net.minecraft.class_2544;
import net.minecraft.class_2591;
import net.minecraft.class_3614;
import net.minecraft.class_3620;
import net.minecraft.class_5616;
import net.minecraft.class_4970.class_2250;
import net.minecraft.class_4970.class_2251;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.block.entity.api.QuiltBlockEntityTypeBuilder;
import org.quiltmc.qsl.block.extensions.api.QuiltBlockSettings;
import org.quiltmc.qsl.block.extensions.api.client.BlockRenderLayerMap;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.blocks.AmariteBlock;
import xyz.amymialee.amarite.blocks.AmariteClusterBlock;
import xyz.amymialee.amarite.blocks.AmariteSparkBlock;
import xyz.amymialee.amarite.blocks.AmariteSparkBlockEntity;
import xyz.amymialee.amarite.blocks.AmethystButtonBlock;
import xyz.amymialee.amarite.blocks.AmethystPressurePlateBlock;
import xyz.amymialee.amarite.blocks.BuddingAmariteBlock;
import xyz.amymialee.amarite.blocks.WinPlushieBlock;
import xyz.amymialee.amarite.blocks.WinPlushieBlockEntity;
import xyz.amymialee.amarite.blocks.YellowCarnationPlantBlock;
import xyz.amymialee.amarite.client.WinPlushieBlockEntityRenderer;

public interface AmariteBlocks {
   class_2248 AMETHYST_BRICKS = Amarite.REGISTRY
      .registerBlockWithItem(
         "amethyst_bricks",
         new class_2248(
            QuiltBlockSettings.of(class_3614.field_27340, class_3620.field_16014).strength(1.6F, 8.0F).sounds(class_2498.field_27197).requiresTool()
         ),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 AMETHYST_BRICK_WALL = Amarite.REGISTRY
      .registerBlockWithItem("amethyst_brick_wall", new class_2544(QuiltBlockSettings.method_9630(AMETHYST_BRICKS)), AmariteItems.AMARITE_GROUP);
   class_2248 AMETHYST_BRICK_STAIRS = Amarite.REGISTRY
      .registerBlockWithItem(
         "amethyst_brick_stairs", new class_2510(AMETHYST_BRICKS.method_9564(), QuiltBlockSettings.method_9630(AMETHYST_BRICKS)), AmariteItems.AMARITE_GROUP
      );
   class_2248 AMETHYST_BRICK_SLAB = Amarite.REGISTRY
      .registerBlockWithItem(
         "amethyst_brick_slab", new class_2482(QuiltBlockSettings.method_9630(AMETHYST_BRICKS).method_9629(2.4F, 8.0F)), AmariteItems.AMARITE_GROUP
      );
   class_2248 AMETHYST_PILLAR = Amarite.REGISTRY
      .registerBlockWithItem("amethyst_pillar", new class_2465(QuiltBlockSettings.method_9630(AMETHYST_BRICKS)), AmariteItems.AMARITE_GROUP);
   class_2248 CHISELED_AMETHYST = Amarite.REGISTRY
      .registerBlockWithItem("chiseled_amethyst", new class_2248(QuiltBlockSettings.method_9630(AMETHYST_BRICKS)), AmariteItems.AMARITE_GROUP);
   class_2248 CHISELED_AMETHYST_PRESSURE_PLATE = Amarite.REGISTRY
      .registerBlockWithItem(
         "amethyst_brick_pressure_plate",
         new AmethystPressurePlateBlock(class_2251.method_9639(class_3614.field_27340, class_3620.field_16014).method_29292().method_9634().method_9632(0.5F)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 CHISELED_AMETHYST_BUTTON = Amarite.REGISTRY
      .registerBlockWithItem(
         "amethyst_brick_button",
         new AmethystButtonBlock(false, class_2251.method_9637(class_3614.field_27340).method_9634().method_9632(0.5F)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 AMARITE_BLOCK = Amarite.REGISTRY
      .registerBlockWithItem(
         "amarite_block", new AmariteBlock(QuiltBlockSettings.method_9630(AMETHYST_BRICKS).method_9629(1.8F, 8.0F)), AmariteItems.AMARITE_GROUP
      );
   class_2248 BUDDING_AMARITE = Amarite.REGISTRY
      .registerBlockWithItem(
         "budding_amarite",
         new BuddingAmariteBlock(QuiltBlockSettings.method_9630(AMARITE_BLOCK).method_9640().method_29292().method_9629(16.0F, 64.0F)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 AMARITE_CLUSTER = Amarite.REGISTRY
      .registerBlockWithItem(
         "amarite_cluster",
         new AmariteClusterBlock(7, 3, QuiltBlockSettings.method_9630(AMARITE_BLOCK).method_22488().method_9626(class_2498.field_27198).method_9631(state -> 3)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 PARTIAL_AMARITE_BUD = Amarite.REGISTRY
      .registerBlockWithItem(
         "partial_amarite_bud",
         new AmariteClusterBlock.AmariteBud(5, 3, QuiltBlockSettings.method_9630(AMARITE_CLUSTER).method_9626(class_2498.field_27201).method_9631(state -> 2)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 FRESH_AMARITE_BUD = Amarite.REGISTRY
      .registerBlockWithItem(
         "fresh_amarite_bud",
         new AmariteClusterBlock.AmariteBud(4, 3, QuiltBlockSettings.method_9630(AMARITE_CLUSTER).method_9626(class_2498.field_27200).method_9631(state -> 1)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 AMARITE_SPARK = Amarite.REGISTRY
      .registerBlockWithItem(
         "amarite_spark",
         new AmariteSparkBlock(QuiltBlockSettings.method_9630(AMARITE_BLOCK).method_9631(state -> 10).method_22488().method_9640().method_9629(5.0F, 32.0F)),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 YELLOW_CARNATION_BOUQUET = Amarite.REGISTRY
      .registerBlockWithItem(
         "yellow_carnation_bouquet",
         new class_2521(
            QuiltBlockSettings.of(class_3614.field_15956).noCollision().breakInstantly().sounds(class_2498.field_11535).method_43281(class_2250.field_10657)
         ),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 YELLOW_CARNATION = Amarite.REGISTRY
      .registerBlockWithItem(
         "yellow_carnation",
         new YellowCarnationPlantBlock(
            class_1294.field_5908,
            12,
            QuiltBlockSettings.of(class_3614.field_15935).noCollision().breakInstantly().sounds(class_2498.field_11535).method_43281(class_2250.field_10657)
         ),
         AmariteItems.AMARITE_GROUP
      );
   class_2248 POTTED_YELLOW_CARNATION = Amarite.REGISTRY
      .registerBlockWithItem(
         "potted_yellow_carnation", new class_2362(YELLOW_CARNATION, QuiltBlockSettings.method_9630(class_2246.field_10354)), AmariteItems.AMARITE_GROUP
      );
   class_2248 WIN_PLUSHIE = Amarite.REGISTRY
      .registerBlockWithItem("win_plushie", new WinPlushieBlock(QuiltBlockSettings.method_9630(class_2246.field_10259)), AmariteItems.AMARITE_GROUP);
   class_2591<? extends AmariteSparkBlockEntity> AMARITE_SPARK_BLOCK_ENTITY = Amarite.REGISTRY
      .register("amarite_spark", QuiltBlockEntityTypeBuilder.create(AmariteSparkBlockEntity::new, new class_2248[]{AMARITE_SPARK}).build());
   class_2591<? extends WinPlushieBlockEntity> WIN_PLUSHIE_BLOCK_ENTITY = Amarite.REGISTRY
      .register("win_plushie", QuiltBlockEntityTypeBuilder.create(WinPlushieBlockEntity::new, new class_2248[]{WIN_PLUSHIE}).build());

   static void init() {
   }

   @ClientOnly
   static void initClient() {
      BlockRenderLayerMap.put(
         class_1921.method_23581(),
         new class_2248[]{
            FRESH_AMARITE_BUD,
            PARTIAL_AMARITE_BUD,
            AMARITE_CLUSTER,
            AMARITE_SPARK,
            YELLOW_CARNATION,
            YELLOW_CARNATION_BOUQUET,
            POTTED_YELLOW_CARNATION,
            WIN_PLUSHIE
         }
      );
      class_5616.method_32144(WIN_PLUSHIE_BLOCK_ENTITY, WinPlushieBlockEntityRenderer::new);
   }
}
