package xyz.amymialee.amarite.cca;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.entities.DiscEntity;

public class DiscComponent implements AutoSyncedComponent, CommonTickingComponent {
   public static final int DISC_COUNT = 3;
   public static final int MAX_DISC_DURABILITY = 3;
   public static final int DISC_REPAIR_COOLDOWN = 80;
   public static final int PYLON_COOLDOWN = 40;
   public static final int REBOUND_MAX_CHARGE = 3;
   public static final int ORBIT_MAX_CHARGE = 24;
   public static final int ORBIT_MAX_DURATION = 160;
   private final class_1657 player;
   public final List<Integer> discIds = new ArrayList<>();
   public final List<Integer> discDurability = new ArrayList<>();
   private int repairCooldown = 0;
   public int reboundCharge = 3;
   public float orbitCharge = 24.0F;
   public int orbitDuration = 0;

   public DiscComponent(class_1657 playerEntity) {
      this.player = playerEntity;

      for (int i = 0; i < 3; i++) {
         this.discIds.add(-1);
         this.discDurability.add(3);
      }
   }

   public void sync() {
      Amarite.DISC.sync(this.player);
   }

   public void tick() {
      if (this.orbitDuration > 0) {
         this.orbitDuration--;
         this.orbitCharge = 24.0F * (this.orbitDuration / 160.0F);
         if (this.orbitDuration <= 0) {
            this.orbitCharge = 0.0F;
            this.sync();
         }
      }
   }

   public void clientTick() {
      super.clientTick();
   }

   public void serverTick() {
      super.serverTick();
      if (this.repairCooldown > 0) {
         this.repairCooldown--;
         if (this.repairCooldown <= 0) {
            int selected = -1;

            for (int i = 0; i < this.discDurability.size(); i++) {
               if (this.discDurability.get(i) < 3 && (selected == -1 || this.discDurability.get(i) < this.discDurability.get(selected))) {
                  selected = i;
               }
            }

            if (selected != -1) {
               this.discDurability.set(selected, this.discDurability.get(selected) + 1);
               this.sync();
            }

            this.repairCooldown = 80;
         }
      }
   }

   public int getNextAvailableDisc() {
      int selected = -1;

      for (int i = 0; i < (this.player.method_7337() ? 3 : 1) * 3; i++) {
         if (this.discIds.size() <= i) {
            this.discIds.add(-1);
            this.discDurability.add(3);
         }

         Integer id = this.discIds.get(i);
         if (id != -1 && !(this.player.field_6002.method_8469(id) instanceof DiscEntity)) {
            this.discIds.set(i, -1);
            id = -1;
            this.sync();
         }

         if (id == -1
            && (this.discDurability.get(i) > 0 || this.player.method_7337())
            && (selected == -1 || this.discDurability.get(i) > this.discDurability.get(selected))) {
            selected = i;
         }
      }

      return selected;
   }

   public boolean damage(int index, int damage) {
      int durability = Math.max(0, this.discDurability.get(index) - damage);
      this.discDurability.set(index, durability);
      if (this.repairCooldown <= 0) {
         this.repairCooldown = 80;
      }

      this.sync();
      return durability == 0;
   }

   public List<DiscEntity> getDiscEntities() {
      ArrayList<DiscEntity> discEntities = new ArrayList<>();

      for (int i = 0; i < this.discIds.size(); i++) {
         DiscEntity discEntity = this.getDiscEntity(i);
         if (discEntity != null) {
            discEntities.add(discEntity);
         }
      }

      return discEntities;
   }

   @Nullable
   public DiscEntity getDiscEntity(int index) {
      if (index >= 0 && index < this.discIds.size()) {
         return this.player.field_6002.method_8469(this.getDiscId(index)) instanceof DiscEntity discEntity ? discEntity : null;
      } else {
         return null;
      }
   }

   public void setDiscEntity(int index, @Nullable DiscEntity discEntity) {
      if (index >= 0) {
         if (index >= this.discIds.size()) {
            for (int i = this.discIds.size(); i <= index; i++) {
               this.discIds.add(-1);
            }
         }

         this.setDiscId(index, discEntity == null ? 0 : discEntity.method_5628());
      }
   }

   public Integer getDiscId(int index) {
      return index >= 0 && index < this.discIds.size() ? this.discIds.get(index) : -1;
   }

   public void setDiscId(int index, int discId) {
      if (index >= 0) {
         if (index >= this.discIds.size()) {
            for (int i = this.discIds.size(); i <= index; i++) {
               this.discIds.add(-1);
            }
         }

         this.discIds.set(index, discId);
         this.sync();
      }
   }

   public Integer getDiscIndex(DiscEntity discEntity) {
      for (int i = 0; i < this.discIds.size(); i++) {
         if (this.discIds.get(i) == discEntity.method_5628()) {
            return i;
         }
      }

      return -1;
   }

   public int getDiscDurability(int index) {
      return index >= 0 && index < this.discDurability.size() ? this.discDurability.get(index) : -1;
   }

   public void setDiscDurability(int index, int durability) {
      if (index >= 0) {
         if (index >= this.discDurability.size()) {
            for (int i = this.discDurability.size(); i <= index; i++) {
               this.discDurability.add(3);
            }
         }

         this.discDurability.set(index, this.player.method_7337() ? 3 : durability);
         this.sync();
      }
   }

   public void chargeRebound(int amount) {
      this.reboundCharge = Math.min(3, this.reboundCharge + amount);
      this.sync();
   }

   public void chargeOrbit(float amount) {
      this.orbitCharge = Math.min(24.0F, this.orbitCharge + amount);
      this.sync();
   }

   public void readFromNbt(@NotNull class_2487 tag) {
      this.discIds.clear();
      class_2499 ids = tag.method_10554("DiscIds", 3);

      for (int i = 0; i < ids.size(); i++) {
         this.discIds.add(ids.method_10600(i));
      }

      this.discDurability.clear();
      class_2499 durability = tag.method_10554("DiscDurability", 3);

      for (int i = 0; i < durability.size(); i++) {
         this.discDurability.add(durability.method_10600(i));
      }

      this.reboundCharge = tag.method_10550("ReboundCharge");
      this.orbitCharge = tag.method_10583("OrbitCharge");
      this.orbitDuration = tag.method_10550("OrbitDuration");
   }

   public void writeToNbt(@NotNull class_2487 tag) {
      class_2499 ids = new class_2499();

      for (Integer discId : this.discIds) {
         ids.add(class_2497.method_23247(discId));
      }

      tag.method_10566("DiscIds", ids);
      class_2499 durability = new class_2499();

      for (Integer integer : this.discDurability) {
         durability.add(class_2497.method_23247(integer));
      }

      tag.method_10566("DiscDurability", durability);
      tag.method_10569("ReboundCharge", this.reboundCharge);
      tag.method_10548("OrbitCharge", this.orbitCharge);
      tag.method_10569("OrbitDuration", this.orbitDuration);
   }
}
