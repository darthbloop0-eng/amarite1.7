package xyz.amymialee.mialib.raycasting;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_238;
import net.minecraft.class_243;
import net.minecraft.class_3959;
import net.minecraft.class_2350.class_2351;
import net.minecraft.class_239.class_240;
import net.minecraft.class_3959.class_242;
import net.minecraft.class_3959.class_3960;
import org.jetbrains.annotations.NotNull;

public interface MRaycasting {
   BiPredicate<class_1657, class_1297> ANY_PLAYER_AND_ENTITY = (p, e) -> true;
   Predicate<class_1297> ANY_ENTITY = e -> true;

   @NotNull
   static List<class_1297> raycast(@NotNull class_1657 entity, double distance) {
      return raycast(entity, distance, ANY_PLAYER_AND_ENTITY);
   }

   @NotNull
   static List<class_1297> raycast(@NotNull class_1657 entity, double distance, BiPredicate<class_1657, class_1297> filter) {
      return raycast(entity, distance, filter, 0);
   }

   @NotNull
   static List<class_1297> raycast(@NotNull class_1657 entity, double distance, BiPredicate<class_1657, class_1297> filter, int maxHits) {
      return raycast(entity, distance, filter, maxHits, 0.0);
   }

   @NotNull
   static List<class_1297> raycast(@NotNull class_1657 entity, double distance, BiPredicate<class_1657, class_1297> filter, int maxHits, double rayRadius) {
      return raycast(
         entity.method_37908(),
         entity.method_33571(),
         anglesToVector(entity.method_5695(1.0F), entity.method_5705(1.0F)),
         distance,
         e -> filter.test(entity, e),
         rayRadius,
         maxHits
      );
   }

   @NotNull
   static List<class_1297> raycast(@NotNull class_1937 world, @NotNull class_243 startPos, @NotNull class_243 angle, double distance) {
      return raycast(world, startPos, angle, distance, ANY_ENTITY);
   }

   @NotNull
   static List<class_1297> raycast(
      @NotNull class_1937 world, @NotNull class_243 startPos, @NotNull class_243 angle, double distance, @NotNull Predicate<class_1297> filter
   ) {
      return raycast(world, startPos, angle, distance, filter, 0.0);
   }

   @NotNull
   static List<class_1297> raycast(
      @NotNull class_1937 world,
      @NotNull class_243 startPos,
      @NotNull class_243 angle,
      double distance,
      @NotNull Predicate<class_1297> filter,
      double rayRadius
   ) {
      return raycast(world, startPos, angle, distance, filter, rayRadius, 0);
   }

   @NotNull
   static List<class_1297> raycast(
      @NotNull class_1937 world,
      @NotNull class_243 startPos,
      @NotNull class_243 angle,
      double distance,
      @NotNull Predicate<class_1297> filter,
      double rayRadius,
      int maxHits
   ) {
      class_243 endPosition = startPos.method_1019(angle.method_1021(distance));
      List<class_1297> hitEntities = new ArrayList<>();

      for (class_1297 target : world.method_8333(null, class_238.method_30048(startPos, 0.1, 0.1, 0.1).method_1009(distance, distance, distance), filter)) {
         boolean intersection = intersects(startPos, endPosition, target, rayRadius);
         if (intersection) {
            boolean visible = false;

            for (class_243 pos : new class_243[]{
               target.method_19538(),
               target.method_19538().method_1031(0.0, target.method_17682() / 2.0F, 0.0),
               target.method_19538().method_1031(0.0, target.method_17682(), 0.0)
            }) {
               if (world.method_17742(new class_3959(startPos, pos, class_3960.field_17558, class_242.field_1348, target)).method_17783()
                  == class_240.field_1333) {
                  visible = true;
                  break;
               }
            }

            if (visible) {
               hitEntities.add(target);
               if (maxHits > 0 && hitEntities.size() >= maxHits) {
                  break;
               }
            }
         }
      }

      return hitEntities;
   }

   @NotNull
   static class_243 anglesToVector(double pitch, double yaw) {
      double x = -Math.sin(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
      double y = -Math.sin(Math.toRadians(pitch));
      double z = Math.cos(Math.toRadians(yaw)) * Math.cos(Math.toRadians(pitch));
      return new class_243(x, y, z);
   }

   static boolean intersects(class_243 start, @NotNull class_243 end, @NotNull class_1297 entity, double rayRadius) {
      class_243 entityMin = entity.method_19538().method_1023(entity.method_17681() / 2.0F + rayRadius, rayRadius, entity.method_17681() / 2.0F + rayRadius);
      class_243 entityMax = entity.method_19538()
         .method_1031(entity.method_17681() / 2.0F + rayRadius, entity.method_17682() + rayRadius, entity.method_17681() / 2.0F + rayRadius);
      return intersects(start, end, new class_238(entityMin, entityMax), rayRadius);
   }

   static boolean intersects(class_243 start, @NotNull class_243 end, @NotNull class_238 box, double rayRadius) {
      class_243 direction = end.method_1020(start).method_1029();
      class_243 boxMin = new class_243(box.field_1323 - rayRadius, box.field_1322 - rayRadius, box.field_1321 - rayRadius);
      class_243 boxMax = new class_243(box.field_1320 + rayRadius, box.field_1325 + rayRadius, box.field_1324 + rayRadius);
      double tMin = 0.0;
      double tMax = Double.MAX_VALUE;

      for (class_2351 axis : class_2351.values()) {
         if (Math.abs(direction.method_18043(axis)) < 1.0E-8) {
            if (start.method_18043(axis) < boxMin.method_18043(axis) || start.method_18043(axis) > boxMax.method_18043(axis)) {
               return false;
            }
         } else {
            double ood = 1.0 / direction.method_18043(axis);
            double t1 = (boxMin.method_18043(axis) - start.method_18043(axis)) * ood;
            double t2 = (boxMax.method_18043(axis) - start.method_18043(axis)) * ood;
            if (t1 > t2) {
               double temp = t1;
               t1 = t2;
               t2 = temp;
            }

            tMin = Math.max(tMin, t1);
            tMax = Math.min(tMax, t2);
            if (tMin > tMax) {
               return false;
            }
         }
      }

      return true;
   }
}
