package xyz.amymialee.amarite.client;

import net.minecraft.class_2960;
import net.minecraft.class_876;
import net.minecraft.class_5617.class_5618;
import xyz.amymialee.amarite.Amarite;
import xyz.amymialee.amarite.entities.PylonEntity;

public class PylonEntityRenderer extends class_876<PylonEntity> {
   public static final class_2960 TEXTURE = Amarite.id("textures/entity/pylon.png");

   public PylonEntityRenderer(class_5618 context) {
      super(context);
   }

   public class_2960 getTexture(PylonEntity arrowEntity) {
      return TEXTURE;
   }
}
