import java.awt.*;

public class Ball implements Entity
{
   private double x, y, velX;
   private int diameter;
   private Color color;

   public Ball(double x, double y)
   {
      this.x = x;
      this.y = y;
      
      this.color = Color.YELLOW;

      this.diameter = 10;
      this.velX = 2;
   }

   @Override
   public void update()
   {
      if (x > 640 - diameter || x < 0)
      {
         velX *= -1;
      }

      x += velX;
   }

   @Override
   public void draw(Graphics g)
   {
      g.setColor(color);
      g.fillOval((int) x, (int) y, diameter, diameter);
   }

   public void setVelX(double velX)
   {
      this.velX += velX;
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
