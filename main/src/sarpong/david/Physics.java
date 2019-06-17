package sarpong.david;

import java.awt.*;

public class Physics
{
   public static boolean collision(Entity objectA, Entity objectB)
   {
      Rectangle objectARectangle = objectA.getBounds();
      Rectangle objectBRectangle = objectB.getBounds();

      if (objectARectangle.intersects(objectBRectangle))
      {
         return true;
      }

      return false;
   }
}
