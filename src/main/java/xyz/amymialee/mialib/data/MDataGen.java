package xyz.amymialee.mialib.data;

import java.util.function.Consumer;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider.TranslationBuilder;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.BlockTagProvider;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider.ItemTagProvider;
import net.minecraft.class_141;
import net.minecraft.class_1792;
import net.minecraft.class_1935;
import net.minecraft.class_2248;
import net.minecraft.class_2444;
import net.minecraft.class_3414;
import net.minecraft.class_44;
import net.minecraft.class_4910;
import net.minecraft.class_4915;
import net.minecraft.class_52;
import net.minecraft.class_55;
import net.minecraft.class_5662;
import net.minecraft.class_6862;
import net.minecraft.class_77;
import net.minecraft.class_52.class_53;
import org.jetbrains.annotations.NotNull;

public abstract class MDataGen implements DataGeneratorEntrypoint {
   public void onInitializeDataGenerator(@NotNull FabricDataGenerator generator) {
      generator.addProvider(gen -> new MDataGen.MBlockLootTableProvider(this, gen));
      generator.addProvider(gen -> new MDataGen.MLanguageProvider(this, gen));
      generator.addProvider(gen -> new MDataGen.MModelProvider(this, gen));
      generator.addProvider(gen -> new MDataGen.MRecipeProvider(this, gen));
      generator.addProvider(gen -> new MDataGen.MBlockTagProvider(this, gen));
      generator.addProvider(gen -> new MDataGen.MItemTagProvider(this, gen));
   }

   protected void generateBlockLootTables(MDataGen.MBlockLootTableProvider provider) {
   }

   protected void generateTranslations(MDataGen.MLanguageProvider provider, TranslationBuilder builder) {
   }

   protected void generateBlockStateModels(MDataGen.MModelProvider provider, class_4910 generator) {
   }

   protected void generateItemModels(MDataGen.MModelProvider provider, class_4915 generator) {
   }

   protected void generateRecipes(MDataGen.MRecipeProvider provider, Consumer<class_2444> exporter) {
   }

   protected void generateBlockTags(MDataGen.MBlockTagProvider provider) {
   }

   protected void generateItemTags(MDataGen.MItemTagProvider provider) {
   }

   protected static class MBlockLootTableProvider extends FabricBlockLootTableProvider {
      private final MDataGen dataGen;

      public MBlockLootTableProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator);
         this.dataGen = gen;
      }

      protected void generateBlockLootTables() {
         this.dataGen.generateBlockLootTables(this);
      }

      public static class_53 makeItemWithRange(class_1935 item, int min, int max) {
         return class_52.method_324()
            .method_336(
               class_55.method_347()
                  .method_352(class_44.method_32448(1.0F))
                  .method_351(class_77.method_411(item).method_438(class_141.method_621(class_5662.method_32462(min, max))))
            );
      }
   }

   protected static class MBlockTagProvider extends BlockTagProvider {
      private final MDataGen dataGen;

      public MBlockTagProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator);
         this.dataGen = gen;
      }

      protected void generateTags() {
         this.dataGen.generateBlockTags(this);
      }

      public FabricTagProvider<class_2248>.FabricTagBuilder<class_2248> getOrCreateTagBuilder(class_6862<class_2248> tag) {
         return super.getOrCreateTagBuilder(tag);
      }
   }

   protected static class MItemTagProvider extends ItemTagProvider {
      private final MDataGen dataGen;

      public MItemTagProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator);
         this.dataGen = gen;
      }

      protected void generateTags() {
         this.dataGen.generateItemTags(this);
      }

      public FabricTagProvider<class_1792>.FabricTagBuilder<class_1792> getOrCreateTagBuilder(class_6862<class_1792> tag) {
         return super.getOrCreateTagBuilder(tag);
      }
   }

   protected static class MLanguageProvider extends FabricLanguageProvider {
      private final MDataGen dataGen;

      public MLanguageProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator, "en_us");
         this.dataGen = gen;
      }

      public void generateTranslations(TranslationBuilder builder) {
         this.dataGen.generateTranslations(this, builder);
      }

      public String getTagTranslationKey(@NotNull class_6862<?> tag) {
         return "tag." + tag.comp_327().method_12836() + "." + tag.comp_327().method_12832();
      }

      public String getSoundKey(@NotNull class_3414 event) {
         return "subtitles." + event.method_14833().method_12832();
      }

      public String[] getDamageKeys(String damageName) {
         return new String[]{"death.attack." + damageName, "death.attack." + damageName + ".player", "death.attack." + damageName + ".item"};
      }
   }

   protected static class MModelProvider extends FabricModelProvider {
      private final MDataGen dataGen;

      public MModelProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator);
         this.dataGen = gen;
      }

      public void generateBlockStateModels(class_4910 generator) {
         this.dataGen.generateBlockStateModels(this, generator);
      }

      public void generateItemModels(class_4915 generator) {
         this.dataGen.generateItemModels(this, generator);
      }
   }

   protected static class MRecipeProvider extends FabricRecipeProvider {
      private final MDataGen dataGen;

      public MRecipeProvider(MDataGen gen, FabricDataGenerator generator) {
         super(generator);
         this.dataGen = gen;
      }

      protected void generateRecipes(Consumer<class_2444> exporter) {
         this.dataGen.generateRecipes(this, exporter);
      }
   }
}
