package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.amarite.Amarite;

public class BuddedComponent implements AutoSyncedComponent, CommonTickingComponent {
   public static final int BUD_MAX_TIME = 4800;
   private final class_1657 player;
   public int budTime = 0;

   public BuddedComponent(class_1657 playerEntity) {
      this.player = playerEntity;
   }

   public void sync() {
      Amarite.BUDDED.sync(this.player);
   }

   public void tick() {
      if (this.budTime > 0) {
         this.budTime--;
         this.sync();
      }
   }

   public int getBudTime() {
      return this.budTime;
   }

   public void setBudTime(int budTime) {
      this.budTime = budTime;
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.budTime = tag.method_10550("budtime");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      tag.method_10569("budtime", this.budTime);
   }
}
