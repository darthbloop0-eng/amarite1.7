package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.entities.DiscEntity;

public class DiscPylonComponent implements AutoSyncedComponent, CommonTickingComponent {
   public static final int PYLON_MAX_DURATION = 160;
   private final class_1297 entity;
   public int pylonCharge = 0;

   public DiscPylonComponent(class_1297 entity) {
      this.entity = entity;
   }

   public void tick() {
      if (this.pylonCharge > 0) {
         this.pylonCharge--;

         for (DiscEntity disc : this.entity.field_6002.method_8390(DiscEntity.class, this.entity.method_5829().method_1014(12.0), discx -> true)) {
            class_243 pos1 = this.entity.method_19538().method_1031(0.0, this.entity.method_17682() / 2.0F, 0.0);
            class_243 pos2 = disc.method_19538();
            class_243 difference = pos1.method_1020(pos2).method_18805(1.0, 0.0, 1.0).method_1029();
            double angleInRadians = Math.toRadians(180 + (disc.clockwise ? -30 : 30));
            double cosTheta = Math.cos(angleInRadians);
            double sinTheta = Math.sin(angleInRadians);
            class_243 spun = new class_243(
               difference.field_1352 * cosTheta + difference.field_1350 * sinTheta,
               difference.field_1351,
               -difference.field_1352 * sinTheta + difference.field_1350 * cosTheta
            );
            class_243 targetPos = pos1.method_1019(spun.method_1021(2.0));
            double distPercent = 1.0 - pos1.method_1022(pos2) / 8.0;
            disc.method_18799(
               disc.method_18798()
                  .method_1021(distPercent)
                  .method_1019(targetPos.method_1020(pos2).method_1029().method_1021(1.0 - distPercent))
                  .method_18805(1.0, 0.6F, 1.0)
            );
            disc.setReturning(false);
            disc.pulledTime = 4;
            disc.setHit(false);
         }
      }
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.pylonCharge = tag.method_10550("PylonCharge");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      tag.method_10569("PylonCharge", this.pylonCharge);
   }
}
