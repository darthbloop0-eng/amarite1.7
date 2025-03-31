package xyz.amymialee.mialib.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.class_1657;
import net.minecraft.class_2487;
import net.minecraft.class_2960;
import net.minecraft.class_3532;
import org.jetbrains.annotations.NotNull;
import xyz.amymialee.mialib.MiaLib;

public class IdCooldownComponent implements AutoSyncedComponent, CommonTickingComponent {
   private final class_1657 player;
   private final Map<class_2960, IdCooldownComponent.Entry> cooldowns = new HashMap<>();
   private int tick;

   public IdCooldownComponent(class_1657 player) {
      this.player = player;
   }

   @NotNull
   public static IdCooldownComponent get(class_1657 player) {
      return (IdCooldownComponent)MiaLib.ID_COOLDOWN_COMPONENT.get(player);
   }

   public void sync() {
      MiaLib.ID_COOLDOWN_COMPONENT.sync(this.player);
   }

   public void tick() {
      this.tick++;

      for (Map.Entry<class_2960, IdCooldownComponent.Entry> entry : this.cooldowns.entrySet()) {
         if (entry.getValue().endTick < this.tick) {
            this.cooldowns.remove(entry.getKey());
            this.sync();
         }
      }
   }

   public boolean isCoolingDown(class_2960 id) {
      return this.cooldowns.containsKey(id);
   }

   public int getCooldown(class_2960 id) {
      IdCooldownComponent.Entry entry = this.cooldowns.get(id);
      return entry == null ? 0 : entry.endTick - entry.startTick;
   }

   public float getCooldown(class_2960 id, float tickDelta) {
      if (!this.cooldowns.containsKey(id)) {
         return 0.0F;
      } else {
         IdCooldownComponent.Entry entry = this.cooldowns.get(id);
         int f = entry.endTick - entry.startTick;
         float g = entry.endTick - this.tick + tickDelta;
         return class_3532.method_15363(g / f, 0.0F, 1.0F);
      }
   }

   public void setCooldown(class_2960 id, int ticks) {
      if (ticks <= 0) {
         if (this.cooldowns.remove(id) != null) {
            this.sync();
         }
      } else {
         this.cooldowns.put(id, new IdCooldownComponent.Entry(this.tick, this.tick + ticks));
         this.sync();
      }
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.tick = tag.method_10550("tick");
      this.cooldowns.clear();
      class_2487 compound = tag.method_10562("cooldowns");
      if (compound != null) {
         for (String id : compound.method_10541()) {
            class_2487 entry = compound.method_10562(id);
            if (entry != null) {
               this.cooldowns.put(new class_2960(id), new IdCooldownComponent.Entry(entry.method_10550("start"), entry.method_10550("end")));
            }
         }
      }
   }

   public void writeToNbt(class_2487 tag) {
      tag.method_10569("tick", this.tick);
      class_2487 compound = new class_2487();

      for (class_2960 id : this.cooldowns.keySet()) {
         class_2487 entry = new class_2487();
         entry.method_10569("start", this.cooldowns.get(id).startTick);
         entry.method_10569("end", this.cooldowns.get(id).endTick);
         compound.method_10566(id.toString(), entry);
      }

      tag.method_10566("cooldowns", compound);
   }

   record Entry(int startTick, int endTick) {
   }
}
