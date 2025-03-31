package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.entities.MalignancyEntity;
import xyz.amymialee.amarite.items.AmariteLongswordItem;
import xyz.amymialee.amarite.registry.AmariteEntities;
import xyz.amymialee.amarite.registry.AmariteItems;
import xyz.amymialee.amarite.registry.AmariteSoundEvents;

public class LongswordMalignancyComponent implements AutoSyncedComponent, CommonTickingComponent, AmariteLongswordItem.LongswordMode {
   public static final int MALIGNANCY_MAX_CHARGE = 800;
   public static final int MALIGNANCY_COLOR = -10639338;
   private final class_1657 player;
   private int malignancyCharge = 800;

   public LongswordMalignancyComponent(class_1657 player) {
      this.player = player;
   }

   private void sync() {
      Amarite.MALIGNANCY.sync(this.player);
   }

   public void tick() {
      if (this.malignancyCharge < 800) {
         this.malignancyCharge++;
         if (this.malignancyCharge == 800) {
            this.sync();
         }
      }
   }

   @Override
   public void absorbDamage(float base) {
      this.malignancyCharge += (int)(base * 20.0F);
      if (this.malignancyCharge > 1600) {
         this.malignancyCharge = 1600;
      }

      this.sync();
   }

   @Override
   public void useAbility() {
      if (this.malignancyCharge >= 800 || this.player.method_7337()) {
         if (!this.player.method_7337()) {
            this.malignancyCharge -= 800;
         }

         if (!this.player.field_6002.field_9236) {
            MalignancyEntity malignancy = (MalignancyEntity)AmariteEntities.MALIGNANCY.method_5883(this.player.field_6002);
            if (malignancy != null) {
               malignancy.setOwner(this.player);
               malignancy.method_5808(
                  this.player.method_23317(),
                  this.player.method_23320() - 0.1,
                  this.player.method_23321(),
                  this.player.method_36454(),
                  this.player.method_36455()
               );
               malignancy.method_18799(this.player.method_5720());
               this.player.field_6002.method_8649(malignancy);
               this.player.field_6002.method_43129(null, malignancy, AmariteSoundEvents.DISC_THROW, class_3419.field_15248, 1.0F, 2.0F);
               this.player.method_6104(class_1268.field_5808);
            }
         }

         this.sync();
      }
   }

   @Override
   public int getModeColor() {
      return -10639338;
   }

   @Override
   public int getSwordTint() {
      float percent = class_3532.method_15363(this.getChargeProgress(), 0.0F, 1.0F);
      int r = (int)(255.0F - percent * 162.0F);
      int g = (int)(255.0F - percent * 87.0F);
      int b = (int)(255.0F - percent * 233.0F);
      return class_3532.method_15383(r, g, b);
   }

   @Override
   public float getChargeProgress() {
      return this.malignancyCharge / 800.0F;
   }

   @Override
   public String getTranslationKey() {
      return AmariteItems.AMARITE_LONGSWORD.method_7876() + ".malignancy";
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.malignancyCharge = tag.method_10550("malignancyCharge");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      if (this.malignancyCharge != 0) {
         tag.method_10569("malignancyCharge", this.malignancyCharge);
      }
   }
}
