package xyz.amymialee.mialib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.class_1291;
import net.minecraft.class_1299;
import net.minecraft.class_1308;
import net.minecraft.class_1309;
import net.minecraft.class_1320;
import net.minecraft.class_1535;
import net.minecraft.class_1747;
import net.minecraft.class_1761;
import net.minecraft.class_1792;
import net.minecraft.class_1826;
import net.minecraft.class_1842;
import net.minecraft.class_1865;
import net.minecraft.class_1887;
import net.minecraft.class_2248;
import net.minecraft.class_2314;
import net.minecraft.class_2378;
import net.minecraft.class_2396;
import net.minecraft.class_2582;
import net.minecraft.class_2591;
import net.minecraft.class_2806;
import net.minecraft.class_2939;
import net.minecraft.class_2960;
import net.minecraft.class_3031;
import net.minecraft.class_3414;
import net.minecraft.class_3448;
import net.minecraft.class_3611;
import net.minecraft.class_3773;
import net.minecraft.class_3816;
import net.minecraft.class_3827;
import net.minecraft.class_3828;
import net.minecraft.class_3852;
import net.minecraft.class_3854;
import net.minecraft.class_3917;
import net.minecraft.class_3956;
import net.minecraft.class_4140;
import net.minecraft.class_4149;
import net.minecraft.class_4158;
import net.minecraft.class_4168;
import net.minecraft.class_4170;
import net.minecraft.class_4648;
import net.minecraft.class_4652;
import net.minecraft.class_4663;
import net.minecraft.class_4996;
import net.minecraft.class_5142;
import net.minecraft.class_5202;
import net.minecraft.class_5338;
import net.minecraft.class_5339;
import net.minecraft.class_5342;
import net.minecraft.class_5650;
import net.minecraft.class_5657;
import net.minecraft.class_5669;
import net.minecraft.class_5712;
import net.minecraft.class_5717;
import net.minecraft.class_5864;
import net.minecraft.class_6018;
import net.minecraft.class_6123;
import net.minecraft.class_6647;
import net.minecraft.class_6798;
import net.minecraft.class_6875;
import net.minecraft.class_7106;
import net.minecraft.class_7151;
import net.minecraft.class_7375;
import net.minecraft.class_7388;
import net.minecraft.class_7444;
import net.minecraft.class_5132.class_5133;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MRegistry {
   private static final Map<Class<?>, class_2378<?>> DEFAULT_REGISTRIES = new HashMap<>();
   public static final List<MRegistry> REGISTRIES = new ArrayList<>();
   private final String namespace;
   private final Map<class_2378<?>, Map<class_2960, Object>> objects;
   private final List<Runnable> itemGroupRegistrations = new ArrayList<>();
   private final List<Runnable> entityAttributeRegistrations = new ArrayList<>();
   public final Map<class_1299<?>, class_1826> spawnEggs = new HashMap<>();
   private Map<Class<?>, class_2378<?>> registries;
   private boolean built = false;

   public MRegistry(String namespace) {
      this.namespace = namespace;
      this.registries = new HashMap<>();
      this.registries.putAll(DEFAULT_REGISTRIES);
      this.objects = new HashMap<>();
      REGISTRIES.add(this);
   }

   public void clearRegistries() {
      this.registries = new HashMap<>();
   }

   public void addRegistry(Class<?> clazz, class_2378<?> registry) {
      this.registries.put(clazz, registry);
   }

   public class_1792 registerItem(String path, class_1792 item, @NotNull class_1761... groups) {
      return this.register(path, item);
   }

   @SafeVarargs
   public final class_1792 registerItem(String path, class_1792 item, @NotNull Consumer<class_1792>... groups) {
      for (Consumer<class_1792> group : groups) {
         this.itemGroupRegistrations.add(() -> group.accept(item));
      }

      return this.register(path, item);
   }

   public class_2248 registerBlockWithItem(String path, class_2248 block, @NotNull class_1761... groups) {
      this.register(path, block);
      this.registerItem(path, new class_1747(block, groups.length == 1 ? new FabricItemSettings().group(groups[0]) : new FabricItemSettings()), groups);
      return block;
   }

   @SafeVarargs
   public final class_2248 registerBlockWithItem(String path, class_2248 block, @NotNull Consumer<class_1792>... groups) {
      this.register(path, block);
      this.registerItem(path, new class_1747(block, new FabricItemSettings()), groups);
      return block;
   }

   public <T extends class_1308> class_1299<T> registerEntity(String path, class_1299<T> entity, @Nullable MRegistry.EggData eggData) {
      return this.registerEntity(path, entity, null, eggData);
   }

   public <T extends class_1309> class_1299<T> registerEntity(String path, class_1299<T> entity, @Nullable class_5133 attributes) {
      this.register(path, entity);
      if (attributes != null) {
         this.entityAttributeRegistrations.add(() -> FabricDefaultAttributeRegistry.register(entity, attributes));
      }

      return entity;
   }

   public <T extends class_1308> class_1299<T> registerEntity(
      String path, class_1299<T> entity, @Nullable class_5133 attributes, @Nullable MRegistry.EggData eggData
   ) {
      this.register(path, entity);
      if (attributes != null) {
         this.entityAttributeRegistrations.add(() -> FabricDefaultAttributeRegistry.register(entity, attributes));
      }

      if (eggData != null) {
         class_1826 egg = new class_1826(entity, eggData.primaryColor, eggData.secondaryColor, new FabricItemSettings());
         this.register(path + "_spawn_egg", egg);
         this.spawnEggs.put(entity, egg);
      }

      return entity;
   }

   public class_3414 registerSound(String name) {
      class_2960 id = new class_2960(this.namespace, name);
      class_3414 event = new class_3414(id);
      this.register(id, event);
      return event;
   }

   public <T> T register(String path, T thing) {
      return this.register(new class_2960(this.namespace, path), thing);
   }

   public <T> T register(class_2960 id, T thing) {
      if (this.built) {
         throw new IllegalStateException("Tried to register " + id + " to the " + this.namespace + " MRegistry after it was built!");
      } else {
         boolean registered = false;

         for (Entry<Class<?>, class_2378<?>> registry : this.registries.entrySet()) {
            if (registry.getKey().isInstance(thing)) {
               this.objects.putIfAbsent(registry.getValue(), new HashMap<>());
               Map<class_2960, Object> map = this.objects.get(registry.getValue());
               if (map.containsKey(id)) {
                  throw new IllegalStateException(
                     "Failed to register " + id + " as it already exists in the " + registry.getValue() + " " + this.namespace + " MRegistry."
                  );
               }

               map.put(id, thing);
               registered = true;
               break;
            }
         }

         if (!registered) {
            throw new IllegalStateException("Failed to register " + id + " to the " + this.namespace + " MRegistry!");
         } else {
            return thing;
         }
      }
   }

   public <T> void build() {
      if (!this.built) {
         this.built = true;

         for (Entry<class_2378<?>, Map<class_2960, Object>> regEntry : this.objects.entrySet()) {
            class_2378<?> registry = regEntry.getKey();

            for (Entry<class_2960, Object> entry : regEntry.getValue().entrySet()) {
               class_2378.method_10230(registry, entry.getKey(), entry.getValue());
            }
         }

         for (Runnable itemGroupRegistration : this.itemGroupRegistrations) {
            itemGroupRegistration.run();
         }

         for (Runnable entityAttributeRegistration : this.entityAttributeRegistrations) {
            entityAttributeRegistration.run();
         }

         this.itemGroupRegistrations.clear();
      }
   }

   static {
      DEFAULT_REGISTRIES.put(class_5712.class, class_2378.field_28264);
      DEFAULT_REGISTRIES.put(class_3414.class, class_2378.field_11156);
      DEFAULT_REGISTRIES.put(class_3611.class, class_2378.field_11154);
      DEFAULT_REGISTRIES.put(class_1291.class, class_2378.field_11159);
      DEFAULT_REGISTRIES.put(class_2248.class, class_2378.field_11146);
      DEFAULT_REGISTRIES.put(class_1887.class, class_2378.field_11160);
      DEFAULT_REGISTRIES.put(class_1299.class, class_2378.field_11145);
      DEFAULT_REGISTRIES.put(class_1792.class, class_2378.field_11142);
      DEFAULT_REGISTRIES.put(class_1842.class, class_2378.field_11143);
      DEFAULT_REGISTRIES.put(class_2396.class, class_2378.field_11141);
      DEFAULT_REGISTRIES.put(class_2591.class, class_2378.field_11137);
      DEFAULT_REGISTRIES.put(class_1535.class, class_2378.field_11150);
      DEFAULT_REGISTRIES.put(class_2960.class, class_2378.field_11158);
      DEFAULT_REGISTRIES.put(class_2806.class, class_2378.field_16643);
      DEFAULT_REGISTRIES.put(class_3827.class, class_2378.field_16792);
      DEFAULT_REGISTRIES.put(class_4996.class, class_2378.field_23398);
      DEFAULT_REGISTRIES.put(class_3917.class, class_2378.field_17429);
      DEFAULT_REGISTRIES.put(class_3956.class, class_2378.field_17597);
      DEFAULT_REGISTRIES.put(class_1865.class, class_2378.field_17598);
      DEFAULT_REGISTRIES.put(class_1320.class, class_2378.field_23781);
      DEFAULT_REGISTRIES.put(class_5717.class, class_2378.field_28265);
      DEFAULT_REGISTRIES.put(class_2314.class, class_2378.field_38000);
      DEFAULT_REGISTRIES.put(class_3448.class, class_2378.field_11152);
      DEFAULT_REGISTRIES.put(class_3854.class, class_2378.field_17166);
      DEFAULT_REGISTRIES.put(class_3852.class, class_2378.field_17167);
      DEFAULT_REGISTRIES.put(class_4158.class, class_2378.field_18792);
      DEFAULT_REGISTRIES.put(class_4140.class, class_2378.field_18793);
      DEFAULT_REGISTRIES.put(class_4149.class, class_2378.field_18794);
      DEFAULT_REGISTRIES.put(class_4170.class, class_2378.field_18795);
      DEFAULT_REGISTRIES.put(class_4168.class, class_2378.field_18796);
      DEFAULT_REGISTRIES.put(class_5338.class, class_2378.field_25293);
      DEFAULT_REGISTRIES.put(class_5339.class, class_2378.field_25294);
      DEFAULT_REGISTRIES.put(class_5342.class, class_2378.field_25299);
      DEFAULT_REGISTRIES.put(class_5657.class, class_2378.field_28008);
      DEFAULT_REGISTRIES.put(class_5650.class, class_2378.field_28009);
      DEFAULT_REGISTRIES.put(class_5669.class, class_2378.field_28010);
      DEFAULT_REGISTRIES.put(class_5864.class, class_2378.field_29076);
      DEFAULT_REGISTRIES.put(class_6018.class, class_2378.field_33093);
      DEFAULT_REGISTRIES.put(class_6123.class, class_2378.field_33095);
      DEFAULT_REGISTRIES.put(class_6647.class, class_2378.field_35090);
      DEFAULT_REGISTRIES.put(class_2939.class, class_2378.field_11157);
      DEFAULT_REGISTRIES.put(class_3031.class, class_2378.field_11138);
      DEFAULT_REGISTRIES.put(class_6875.class, class_2378.field_36467);
      DEFAULT_REGISTRIES.put(class_3773.class, class_2378.field_16645);
      DEFAULT_REGISTRIES.put(class_7151.class, class_2378.field_16644);
      DEFAULT_REGISTRIES.put(class_6798.class, class_2378.field_35760);
      DEFAULT_REGISTRIES.put(class_4652.class, class_2378.field_21445);
      DEFAULT_REGISTRIES.put(class_4648.class, class_2378.field_21447);
      DEFAULT_REGISTRIES.put(class_5142.class, class_2378.field_23782);
      DEFAULT_REGISTRIES.put(class_7388.class, class_2378.field_38807);
      DEFAULT_REGISTRIES.put(class_4663.class, class_2378.field_21448);
      DEFAULT_REGISTRIES.put(class_5202.class, class_2378.field_24331);
      DEFAULT_REGISTRIES.put(class_3828.class, class_2378.field_16794);
      DEFAULT_REGISTRIES.put(class_3816.class, class_2378.field_16793);
      DEFAULT_REGISTRIES.put(class_7375.class, class_2378.field_38803);
      DEFAULT_REGISTRIES.put(class_7106.class, class_2378.field_38805);
      DEFAULT_REGISTRIES.put(class_2582.class, class_2378.field_39208);
      DEFAULT_REGISTRIES.put(class_7444.class, class_2378.field_39210);
   }

   public record EggData(int primaryColor, int secondaryColor) {
      public EggData(int color) {
         this(color, color);
      }
   }
}
