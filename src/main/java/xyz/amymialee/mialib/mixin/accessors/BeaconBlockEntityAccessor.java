package xyz.amymialee.mialib.mixin.accessors;

import java.util.List;
import net.minecraft.class_1291;
import net.minecraft.class_2580;
import net.minecraft.class_2580.class_2581;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({class_2580.class})
public interface BeaconBlockEntityAccessor {
   @Accessor("level")
   int getLevel();

   @Accessor("primary")
   class_1291 getPrimary();

   @Accessor("secondary")
   class_1291 getSecondary();

   @Accessor("field_19178")
   List<class_2581> getField();

   @Accessor("minY")
   void setMinY(int var1);

   @Accessor("level")
   void setLevel(int var1);

   @Accessor("beamSegments")
   void setBeamSegments(List<class_2581> var1);
}
