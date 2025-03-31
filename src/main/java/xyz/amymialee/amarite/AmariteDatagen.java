package xyz.amymialee.amarite;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder;
import net.fabricmc.fabric.api.mininglevel.v1.FabricMineableTags;
import net.minecraft.data.family.BlockFamily;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.registry.AmariteBlocks;
import xyz.amymialee.amarite.registry.AmariteEnchantments;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;
import xyz.amymialee.mialib.data.MDataGen;

public class AmariteDatagen extends MDataGen {
   private static final BlockFamily AMETHYST_BRICK = class_5793.method_33468(AmariteBlocks.AMETHYST_BRICKS)
      .method_33497(AmariteBlocks.AMETHYST_BRICK_WALL)
      .method_33493(AmariteBlocks.AMETHYST_BRICK_STAIRS)
      .method_33492(AmariteBlocks.AMETHYST_BRICK_SLAB)
      .method_33481();
   private static final class_5794 CHISELED_AMETHYST = class_5793.method_33468(AmariteBlocks.CHISELED_AMETHYST)
      .method_33494(AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE)
      .method_33482(AmariteBlocks.CHISELED_AMETHYST_BUTTON)
      .method_33481();

   @Override
   public void onInitializeDataGenerator(@NotNull FabricDataGenerator generator) {
      super.onInitializeDataGenerator(generator);
      generator.addProvider(gen -> new AmariteDatagen.MirrorCooldownProvider(gen) {
         @Override
         public void generateCooldowns(BiConsumer<class_2960, Integer> consumer) {
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5904), 120);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5917), 100);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5907), 640);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5913), 80);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5910), 560);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5924), 640);
            consumer.accept(class_2378.field_11159.method_10221(class_1294.field_5927), 240);
         }
      });
   }

   @Override
   protected void generateBlockLootTables(@NotNull MDataGen.MBlockLootTableProvider provider) {
      provider.method_16329(AmariteBlocks.AMETHYST_BRICKS);
      provider.method_16329(AmariteBlocks.AMETHYST_BRICK_WALL);
      provider.method_16329(AmariteBlocks.AMETHYST_BRICK_STAIRS);
      provider.method_16293(AmariteBlocks.AMETHYST_BRICK_SLAB, class_2430::method_10383);
      provider.method_16329(AmariteBlocks.AMETHYST_PILLAR);
      provider.method_16329(AmariteBlocks.CHISELED_AMETHYST);
      provider.method_16329(AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE);
      provider.method_16329(AmariteBlocks.CHISELED_AMETHYST_BUTTON);
      provider.method_16329(AmariteBlocks.AMARITE_BLOCK);
      provider.method_16258(AmariteBlocks.BUDDING_AMARITE, class_52.method_324());
      provider.method_16293(
         AmariteBlocks.AMARITE_CLUSTER,
         block -> class_2430.method_10397(block, class_77.method_411(AmariteItems.AMARITE_SHARD).method_438(class_141.method_621(class_44.method_32448(3.0F))))
      );
      provider.method_16293(AmariteBlocks.PARTIAL_AMARITE_BUD, class_2430::method_10373);
      provider.method_16293(AmariteBlocks.FRESH_AMARITE_BUD, class_2430::method_10373);
      provider.method_16329(AmariteBlocks.AMARITE_SPARK);
      provider.method_16293(AmariteBlocks.YELLOW_CARNATION_BOUQUET, block -> class_2430.method_10375(block, class_2320.field_10929, class_2756.field_12607));
      provider.method_16329(AmariteBlocks.YELLOW_CARNATION);
      provider.method_16285(AmariteBlocks.POTTED_YELLOW_CARNATION);
      provider.method_16329(AmariteBlocks.WIN_PLUSHIE);
   }

   @Override
   protected void generateTranslations(@NotNull MDataGen.MLanguageProvider provider, @NotNull TranslationBuilder builder) {
      builder.add(AmariteBlocks.AMETHYST_BRICKS.method_9539(), "Amethyst Bricks");
      builder.add(AmariteBlocks.AMETHYST_BRICK_WALL.method_9539(), "Amethyst Brick Wall");
      builder.add(AmariteBlocks.AMETHYST_BRICK_STAIRS.method_9539(), "Amethyst Brick Stairs");
      builder.add(AmariteBlocks.AMETHYST_BRICK_SLAB.method_9539(), "Amethyst Brick Slab");
      builder.add(AmariteBlocks.AMETHYST_PILLAR.method_9539(), "Amethyst Pillar");
      builder.add(AmariteBlocks.CHISELED_AMETHYST.method_9539(), "Chiseled Amethyst");
      builder.add(AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE.method_9539(), "Chiseled Amethyst Pressure Plate");
      builder.add(AmariteBlocks.CHISELED_AMETHYST_BUTTON.method_9539(), "Chiseled Amethyst Button");
      builder.add(AmariteBlocks.AMARITE_BLOCK.method_9539(), "Amarite Block");
      builder.add(AmariteBlocks.BUDDING_AMARITE.method_9539(), "Budding Amarite");
      builder.add(AmariteBlocks.AMARITE_CLUSTER.method_9539(), "Amarite Cluster");
      builder.add(AmariteBlocks.PARTIAL_AMARITE_BUD.method_9539(), "Partial Amarite Bud");
      builder.add(AmariteBlocks.FRESH_AMARITE_BUD.method_9539(), "Fresh Amarite Bud");
      builder.add(AmariteBlocks.AMARITE_SPARK.method_9539(), "Amarite Spark");
      builder.add(AmariteBlocks.YELLOW_CARNATION.method_9539(), "Yellow Carnation");
      builder.add(AmariteBlocks.YELLOW_CARNATION_BOUQUET.method_9539(), "Yellow Carnation Bouquet");
      builder.add(AmariteBlocks.POTTED_YELLOW_CARNATION.method_9539(), "Potted Yellow Carnation");
      builder.add(AmariteBlocks.WIN_PLUSHIE.method_9539(), "Win Plush");
      String[] buddingKeys = provider.getDamageKeys("amarite.budding");
      builder.add(buddingKeys[0], "%1$s was vitrified");
      builder.add(buddingKeys[1], "%1$s was vitrified by %2$s");
      builder.add(buddingKeys[2], "%1$s was vitrified by %2$s using %3$s");
      String[] dashKeys = provider.getDamageKeys("amarite.dash");
      builder.add(dashKeys[0], "%1$s was subdivided");
      builder.add(dashKeys[1], "%1$s was subdivided by %2$s");
      builder.add(dashKeys[2], "%1$s was subdivided by %2$s wielding %3$s");
      String[] accumulateKeys = provider.getDamageKeys("amarite.accumulate");
      builder.add(accumulateKeys[0], "%1$s was absorbed");
      builder.add(accumulateKeys[1], "%1$s was absorbed by %2$s");
      builder.add(accumulateKeys[2], "%1$s was absorbed by %2$s wielding %3$s");
      String[] malignancyKeys = provider.getDamageKeys("amarite.malignancy");
      builder.add(malignancyKeys[0], "%1$s was tarnished");
      builder.add(malignancyKeys[1], "%1$s was tarnished by %2$s");
      builder.add(malignancyKeys[2], "%1$s was tarnished by %2$s wielding %3$s");
      String[] discKeys = provider.getDamageKeys("amarite.disc");
      builder.add(discKeys[0], "%1$s was diced up");
      builder.add(discKeys[1], "%1$s was diced up by %2$s");
      builder.add(discKeys[2], "%1$s was diced up by %2$s lobbing %3$s");
      builder.add(AmariteEnchantments.DOUBLE_DASH.method_8184(), "Double Dash");
      builder.add(AmariteEnchantments.DOUBLE_DASH.method_8184() + ".desc", "Allows you to use two faster dashes that dont deal damage.");
      builder.add(AmariteEnchantments.ACCUMULATE.method_8184(), "Accumulate");
      builder.add(AmariteEnchantments.ACCUMULATE.method_8184() + ".desc", "Replaces your dash with a beam which can pull in entities.");
      builder.add(AmariteEnchantments.MALIGNANCY.method_8184(), "Malignancy");
      builder.add(AmariteEnchantments.MALIGNANCY.method_8184() + ".desc", "Replaces your dash with toxic amarite clouds.");
      builder.add(AmariteEnchantments.REBOUND.method_8184(), "Rebound");
      builder.add(AmariteEnchantments.REBOUND.method_8184() + ".desc", "Causes all discs to target nearby enemies.");
      builder.add(AmariteEnchantments.PYLON.method_8184(), "Pylon");
      builder.add(AmariteEnchantments.PYLON.method_8184() + ".desc", "Fire a pylon which attracts discs.");
      builder.add(AmariteEnchantments.ORBIT.method_8184(), "Orbit");
      builder.add(AmariteEnchantments.ORBIT.method_8184() + ".desc", "When charged, activate to have all of your discs circle you.");
      builder.add(AmariteEnchantments.ANONYMITY.method_8184(), "Anonymity");
      builder.add(AmariteEnchantments.ANONYMITY.method_8184() + ".desc", "Hides your name from kill messages.");
      builder.add(AmariteEnchantments.CONCEALMENT.method_8184(), "Concealment");
      builder.add(AmariteEnchantments.CONCEALMENT.method_8184() + ".desc", "Conceals your name tag.");
      builder.add(AmariteEntities.DISC.method_5882(), "Amarite Disc");
      builder.add(AmariteEntities.PYLON.method_5882(), "Amarite Pylon");
      builder.add(AmariteEntities.MALIGNANCY.method_5882(), "Malignancy");
      builder.add(AmariteEntities.BUDDING.method_5567(), "Budding");
      builder.add("itemGroup.amarite.amarite_group", "Amarite");
      builder.add("itemGroup.amarite.mirror_group", "Amarite Mirrors");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876(), "Amarite Longsword");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".desc_1", "Block attacks by holding %s to absorb half of incoming damage.");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".desc_2", "%s while blocking to %s");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".dash", "dash forward slicing through enemies.");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".double_dash", "dash forward with great speed.");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".accumulate", "absorb entities in front of you.");
      builder.add(AmariteItems.AMARITE_LONGSWORD.method_7876() + ".malignancy", "launch a toxic cloud forward.");
      builder.add(AmariteItems.AMARITE_DISC.method_7876(), "Amarite Disc");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".desc_1", "Throw discs with %s");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".desc_2", "%s to %s");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".desc", "StarbornNebula - Pacificus");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".recall", "recall all discs at high speed.");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".rebound", "cause all discs to target nearby enemies.");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".pylon", "fire a pylon that will attract discs.");
      builder.add(AmariteItems.AMARITE_DISC.method_7876() + ".orbit", "spin your discs around yourself.");
      builder.add(AmariteItems.AMARITE_MIRROR.method_7876(), "Amarite Pocket Mirror");
      builder.add(AmariteItems.AMARITE_MIRROR.method_7876() + ".empty", "Crouch use on a beacon or conduit to extract its effects.");
      builder.add(AmariteItems.AMARITE_MIRROR.method_7876() + ".cooldown", "Has a cooldown of %s and lasts for 15 seconds.");
      builder.add(AmariteItems.AXOLOTL_MASK.method_7876(), "Axolotl Mask");
      builder.add(AmariteItems.BUNNY_MASK.method_7876(), "Bunny Mask");
      builder.add(AmariteItems.BUTTERFLY_MASK.method_7876(), "Butterfly Mask");
      builder.add(AmariteItems.DEMON_MASK.method_7876(), "Demon Mask");
      builder.add(AmariteItems.FOX_MASK.method_7876(), "Fox Mask");
      builder.add(AmariteItems.GUARDIAN_MASK.method_7876(), "Guardian Mask");
      builder.add(AmariteItems.MOON_MASK.method_7876(), "Moon Mask");
      builder.add(AmariteItems.ONI_MASK.method_7876(), "Oni Mask");
      builder.add(AmariteItems.UNICORN_MASK.method_7876(), "Unicorn Mask");
      builder.add(AmariteItems.WARDEN_MASK.method_7876(), "Warden Mask");
      builder.add(AmariteItems.WINSWEEP_MASK.method_7876(), "Winsweep Mask");
      builder.add(AmariteItems.AMARITE_CROWN.method_7876(), "Amarite Crown");
      builder.add("item.amarite.mask.anonymity", "Hides your name from kill messages.");
      builder.add("item.amarite.mask.concealment", "Conceals your name tag.");
      builder.add("item.amarite.mask.offset_1", "Model can be offset with %s.");
      builder.add("item.amarite.mask.offset_2", "Current offset: %s.");
      builder.add(AmariteItems.WIN_COOKIE.method_7876(), "Win Cookie");
      builder.add(AmariteItems.WIN_COOKIE.method_7876() + ".desc", "This is what the point of the mask is.");
      builder.add(AmariteItems.AMARITE_SHARD.method_7876(), "Amarite Shard");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_DAMAGE), "Disc hits Entity");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_HIT), "Disc hits Block");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_THROW), "Disc Thrown");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_PICKUP_1), "Disc Picked Up");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_PICKUP_2), "Disc Picked Up");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_PICKUP_3), "Disc Picked Up");
      builder.add(provider.getSoundKey(AmariteSoundEvents.DISC_REBOUND), "Disc Rebounds");
      builder.add(provider.getSoundKey(AmariteSoundEvents.MIRROR_EXTRACT), "Mirror Extracts Effects");
      builder.add(provider.getSoundKey(AmariteSoundEvents.MIRROR_USE), "Mirror Used");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SWORD_CHARGE), "Longsword Charged");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SWORD_BLOCK), "Longsword Absorbs Damage");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SWORD_DASH), "Longsword Dashes");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SWORD_ACCUMULATE), "Longsword Accumulates");
      builder.add(provider.getSoundKey(AmariteSoundEvents.MASK_OFFSET), "Mask Offsets");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SPARK_ACTIVATE), "Spark Activates");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SPARK_AMBIENT), "Spark Ambience");
      builder.add(provider.getSoundKey(AmariteSoundEvents.SPARK_DEACTIVATE), "Spark Deactivates");
      builder.add(provider.getSoundKey(AmariteSoundEvents.AMARITE_FORMS), "Amarite Forms");
      builder.add(provider.getSoundKey(AmariteSoundEvents.AMARITE_DECAYS), "Amarite Decays");
      builder.add(provider.getSoundKey(AmariteSoundEvents.PACIFICUS), "StarbornNebula - Pacificus");
      builder.add(provider.getSoundKey(AmariteSoundEvents.WIN_PLUSHIE), "Squeak!");
   }

   @Override
   protected void generateBlockStateModels(MDataGen.MModelProvider provider, @NotNull class_4910 generator) {
      generator.method_25554(AmariteBlocks.AMETHYST_PILLAR, class_4946.field_23055, class_4946.field_23056);
      generator.method_25650(AmariteBlocks.AMETHYST_BRICKS).method_33522(AMETHYST_BRICK);
      generator.method_25650(AmariteBlocks.CHISELED_AMETHYST).method_33522(CHISELED_AMETHYST);
      generator.method_25545(AmariteBlocks.YELLOW_CARNATION, AmariteBlocks.POTTED_YELLOW_CARNATION, class_4913.field_22840);
      generator.method_25540(AmariteBlocks.POTTED_YELLOW_CARNATION);
      generator.method_25621(AmariteBlocks.YELLOW_CARNATION_BOUQUET, class_4913.field_22840);
      generator.method_32229(AmariteBlocks.AMARITE_CLUSTER);
      generator.method_32229(AmariteBlocks.PARTIAL_AMARITE_BUD);
      generator.method_32229(AmariteBlocks.FRESH_AMARITE_BUD);
   }

   @Override
   protected void generateItemModels(MDataGen.MModelProvider provider, @NotNull class_4915 generator) {
      generator.method_25733(AmariteItems.AMARITE_SHARD, class_4943.field_22938);
      generator.method_25733(AmariteItems.WIN_COOKIE, class_4943.field_22938);
   }

   @Override
   protected void generateRecipes(MDataGen.MRecipeProvider provider, Consumer<class_2444> exporter) {
      class_2447.method_10437(AmariteItems.AMARITE_LONGSWORD)
         .method_10434('A', AmariteItems.AMARITE_SHARD)
         .method_10434('G', class_1802.field_8494)
         .method_10434('N', class_1802.field_22022)
         .method_10434('B', AmariteBlocks.AMARITE_BLOCK)
         .method_10439(" AA")
         .method_10439("GBA")
         .method_10439("NG ")
         .method_10429("shard", class_2446.method_10426(AmariteItems.AMARITE_SHARD))
         .method_10431(exporter);
      class_2447.method_10437(AmariteItems.AMARITE_DISC)
         .method_10434('L', class_1802.field_8745)
         .method_10434('G', class_1802.field_8695)
         .method_10434('A', AmariteItems.AMARITE_SHARD)
         .method_10434('B', AmariteBlocks.AMARITE_BLOCK)
         .method_10439("BGA")
         .method_10439("G G")
         .method_10439("LGB")
         .method_10429("shard", class_2446.method_10426(AmariteItems.AMARITE_SHARD))
         .method_10431(exporter);
      class_2447.method_10437(AmariteItems.AMARITE_MIRROR)
         .method_10434('G', class_1802.field_8695)
         .method_10434('N', class_1802.field_8397)
         .method_10434('B', class_1802.field_8494)
         .method_10434('A', AmariteBlocks.AMARITE_BLOCK)
         .method_10439(" GN")
         .method_10439("GAG")
         .method_10439("BG ")
         .method_10429("shard", class_2446.method_10426(AmariteItems.AMARITE_SHARD))
         .method_10431(exporter);
      class_2450.method_10448(AmariteItems.AMARITE_SHARD, 4)
         .method_10454(AmariteBlocks.AMARITE_BLOCK)
         .method_10442("block", class_2446.method_10426(AmariteBlocks.AMARITE_BLOCK))
         .method_10431(exporter);
      class_2446.method_33715(exporter, AmariteItems.WIN_COOKIE, class_1802.field_8423, 1);
      offerMaskRecipe(exporter, class_1802.field_8158, AmariteItems.AXOLOTL_MASK);
      offerMaskRecipe(exporter, class_1802.field_8245, AmariteItems.BUNNY_MASK);
      offerMaskRecipe(exporter, class_1856.method_8106(class_3489.field_20344), AmariteItems.BUTTERFLY_MASK);
      offerMaskRecipe(exporter, class_1802.field_23843, AmariteItems.DEMON_MASK);
      offerMaskRecipe(exporter, class_1802.field_16998, AmariteItems.FOX_MASK);
      offerMaskRecipe(exporter, class_1802.field_8662, AmariteItems.GUARDIAN_MASK);
      offerMaskRecipe(exporter, class_1802.field_8614, AmariteItems.MOON_MASK);
      offerMaskRecipe(exporter, class_1802.field_8729, AmariteItems.ONI_MASK);
      offerMaskRecipe(exporter, class_1802.field_27063, AmariteItems.UNICORN_MASK);
      offerMaskRecipe(exporter, class_1802.field_37523, AmariteItems.WARDEN_MASK);
      offerMaskRecipe(exporter, AmariteItems.WIN_COOKIE, AmariteItems.WINSWEEP_MASK);
      offerMaskRecipe(exporter, AmariteItems.AMARITE_SHARD, AmariteItems.AMARITE_CROWN);
      class_2447.method_10436(AmariteBlocks.AMETHYST_BRICKS, 4)
         .method_10434('A', class_1802.field_27064)
         .method_10439("AA")
         .method_10439("AA")
         .method_10429("block", class_2446.method_10426(class_1802.field_27064))
         .method_10431(exporter);
      class_2447.method_10436(AmariteBlocks.AMETHYST_BRICK_WALL, 6)
         .method_10434('A', AmariteBlocks.AMETHYST_BRICKS)
         .method_10439("AAA")
         .method_10439("AAA")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.AMETHYST_BRICKS))
         .method_10431(exporter);
      class_2447.method_10436(AmariteBlocks.AMETHYST_BRICK_STAIRS, 6)
         .method_10434('A', AmariteBlocks.AMETHYST_BRICKS)
         .method_10439("A  ")
         .method_10439("AA ")
         .method_10439("AAA")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.AMETHYST_BRICKS))
         .method_10431(exporter);
      class_2447.method_10436(AmariteBlocks.AMETHYST_BRICK_SLAB, 6)
         .method_10434('A', AmariteBlocks.AMETHYST_BRICKS)
         .method_10439("AAA")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.AMETHYST_BRICKS))
         .method_10431(exporter);
      class_2447.method_10436(AmariteBlocks.AMETHYST_PILLAR, 2)
         .method_10434('A', AmariteBlocks.AMETHYST_BRICKS)
         .method_10439("A")
         .method_10439("A")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.AMETHYST_BRICKS))
         .method_10431(exporter);
      class_2447.method_10436(AmariteBlocks.CHISELED_AMETHYST, 4)
         .method_10434('A', AmariteBlocks.AMETHYST_BRICKS)
         .method_10439("AA")
         .method_10439("AA")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.AMETHYST_BRICKS))
         .method_10431(exporter);
      class_2447.method_10437(AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE)
         .method_10434('A', AmariteBlocks.CHISELED_AMETHYST)
         .method_10439("AA")
         .method_10429("block", class_2446.method_10426(AmariteBlocks.CHISELED_AMETHYST))
         .method_10431(exporter);
      class_2450.method_10447(AmariteBlocks.CHISELED_AMETHYST_BUTTON)
         .method_10454(AmariteBlocks.CHISELED_AMETHYST)
         .method_10442("block", class_2446.method_10426(AmariteBlocks.CHISELED_AMETHYST))
         .method_10431(exporter);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICKS, class_1802.field_27064, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_WALL, class_1802.field_27064, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_STAIRS, class_1802.field_27064, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_SLAB, class_1802.field_27064, 2);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_PILLAR, class_1802.field_27064, 1);
      class_2446.method_33715(exporter, AmariteBlocks.CHISELED_AMETHYST, class_1802.field_27064, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_WALL, AmariteBlocks.AMETHYST_BRICKS, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_STAIRS, AmariteBlocks.AMETHYST_BRICKS, 1);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_BRICK_SLAB, AmariteBlocks.AMETHYST_BRICKS, 2);
      class_2446.method_33715(exporter, AmariteBlocks.AMETHYST_PILLAR, AmariteBlocks.AMETHYST_BRICKS, 1);
      class_2446.method_33715(exporter, AmariteBlocks.CHISELED_AMETHYST, AmariteBlocks.AMETHYST_BRICKS, 1);
      class_2447.method_10437(AmariteBlocks.AMARITE_BLOCK)
         .method_10434('A', AmariteItems.AMARITE_SHARD)
         .method_10439("AA")
         .method_10439("AA")
         .method_10429("shard", class_2446.method_10426(AmariteItems.AMARITE_SHARD))
         .method_10431(exporter);
      class_2447.method_10437(AmariteBlocks.AMARITE_SPARK)
         .method_10434('A', AmariteItems.AMARITE_SHARD)
         .method_10434('G', class_1802.field_8801)
         .method_10434('B', AmariteItems.AMARITE_SHARD)
         .method_10439(" A ")
         .method_10439("AGA")
         .method_10439(" B ")
         .method_10429("shard", class_2446.method_10426(AmariteItems.AMARITE_SHARD))
         .method_10431(exporter);
      class_2450.method_10448(AmariteBlocks.YELLOW_CARNATION, 2)
         .method_10454(AmariteBlocks.YELLOW_CARNATION_BOUQUET)
         .method_10442("bouquet", class_2446.method_10426(AmariteBlocks.YELLOW_CARNATION_BOUQUET))
         .method_10431(exporter);
      class_2450.method_10448(class_1802.field_8192, 1)
         .method_10454(AmariteBlocks.YELLOW_CARNATION)
         .method_10442("carnation", class_2446.method_10426(AmariteBlocks.YELLOW_CARNATION))
         .method_10431(exporter);
      class_2447.method_10437(AmariteBlocks.WIN_PLUSHIE)
         .method_10434('B', class_1802.field_19059)
         .method_10434('W', class_1802.field_19044)
         .method_10434('A', AmariteBlocks.AMARITE_BLOCK)
         .method_10434('P', class_1802.field_19054)
         .method_10439("PPP")
         .method_10439("WAW")
         .method_10439("BBB")
         .method_10429("amarite", class_2446.method_10426(AmariteBlocks.AMARITE_BLOCK))
         .method_10431(exporter);
   }

   private static void offerMaskRecipe(Consumer<class_2444> exporter, class_1935 material, class_1935 output) {
      offerMaskRecipe(exporter, class_1856.method_8091(new class_1935[]{material}), output);
   }

   private static void offerMaskRecipe(Consumer<class_2444> exporter, class_1856 material, class_1935 output) {
      class_2447.method_10437(output)
         .method_10433('W', class_3489.field_15544)
         .method_10434('S', class_1802.field_8276)
         .method_10434('L', class_1802.field_8745)
         .method_10428('A', material)
         .method_10439(" W ")
         .method_10439("SAS")
         .method_10439(" L ")
         .method_10429("string", class_2446.method_10426(class_1802.field_8276))
         .method_10431(exporter);
   }

   @Override
   protected void generateBlockTags(@NotNull MDataGen.MBlockTagProvider provider) {
      provider.getOrCreateTagBuilder(class_3481.field_33715)
         .add(
            new class_2248[]{
               AmariteBlocks.AMETHYST_BRICKS,
               AmariteBlocks.AMETHYST_BRICK_WALL,
               AmariteBlocks.AMETHYST_BRICK_STAIRS,
               AmariteBlocks.AMETHYST_BRICK_SLAB,
               AmariteBlocks.AMETHYST_PILLAR,
               AmariteBlocks.CHISELED_AMETHYST,
               AmariteBlocks.CHISELED_AMETHYST_PRESSURE_PLATE,
               AmariteBlocks.CHISELED_AMETHYST_BUTTON,
               AmariteBlocks.AMARITE_BLOCK,
               AmariteBlocks.BUDDING_AMARITE,
               AmariteBlocks.AMARITE_CLUSTER,
               AmariteBlocks.PARTIAL_AMARITE_BUD,
               AmariteBlocks.FRESH_AMARITE_BUD,
               AmariteBlocks.AMARITE_SPARK,
               AmariteBlocks.POTTED_YELLOW_CARNATION
            }
         );
      provider.getOrCreateTagBuilder(FabricMineableTags.SHEARS_MINEABLE).add(AmariteBlocks.WIN_PLUSHIE);
   }

   @Override
   protected void generateItemTags(@NotNull MDataGen.MItemTagProvider provider) {
      provider.getOrCreateTagBuilder(class_3489.field_15543).add(AmariteBlocks.YELLOW_CARNATION.method_8389());
   }

   public abstract static class MirrorCooldownProvider implements class_2405 {
      protected final FabricDataGenerator dataGenerator;

      protected MirrorCooldownProvider(FabricDataGenerator dataGenerator) {
         this.dataGenerator = dataGenerator;
      }

      public abstract void generateCooldowns(BiConsumer<class_2960, Integer> var1);

      public void method_10319(class_7403 writer) throws IOException {
         HashMap<class_2960, Integer> cooldowns = new HashMap<>();
         this.generateCooldowns(cooldowns::put);

         for (Entry<class_2960, Integer> entry : cooldowns.entrySet()) {
            class_2960 id = entry.getKey();
            JsonObject cooldownEntry = new JsonObject();
            cooldownEntry.addProperty("cooldown", entry.getValue());
            class_2405.method_10320(
               writer,
               cooldownEntry,
               this.dataGenerator.method_10313().resolve("data/%s/amarite_mirror/%s.json".formatted(id.method_12836(), id.method_12832()))
            );
         }
      }

      public String method_10321() {
         return "Mirror Cooldowns";
      }
   }
}
