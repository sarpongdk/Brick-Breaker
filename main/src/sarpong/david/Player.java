import java.awt.*;

public class Player implements Entity
{
   private double x, y, velX;
   private int width, height;
   
   public Player(double x, double y)
   {
      this.x = x;
      this.y = y;

      this.width = 90;
      this.height = 6;
      this.velX = 0.0;
   }

   @Override
   public void update()
   {
//      x += velX;
      if (x < Game.WIDTH - width && x > 0)
      {
         x += velX;
      }
      else if (x >= Game.WIDTH - width)
      {
         x = Game.WIDTH - width;
      }
      else if (x <= 0)
      {
         x = 0;
      }
   }

   @Override
   public void draw(Graphics g)
   {
      g.setColor(Color.CYAN);
      g.fillRect((int) x, (int) y, width, height);
   }

   public void setVelX(double velX)
   {
      this.velX = velX;
   }

   @Override
   public void setX(double x)
   {
      this.x = x;
   }

   @Override
   public void setY(double y)
   {
      this.y = y;
   }

   @Override
   public double getX()
   {
      return x;
   }

   @Override
   public double getY()
   {
      return y;
   }
}
