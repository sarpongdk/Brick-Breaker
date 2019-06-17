package sarpong.david;

import java.awt.*;

import java.util.*;

public class Ball implements Entity
{
   private double x, y, velX, velY;
   private int diameter;
   private Color color;
   private Game game;

   private final Random rand = new Random();

   public Ball(double x, double y, Game game)
   {
      this.x = x;
      this.y = y;
      
      this.color = Color.YELLOW;
      this.game = game;

      this.diameter = 10;
      this.velX = 2;
      this.velY = 2;
   }

   @Override
   public void update()
   {
      if (x > Game.WIDTH - diameter || x < 0)
      {
         velX *= -1;
      }

      if (y < 0)
      {
         velY *= -1;
      }

      if (y > Game.HEIGHT - diameter)
      {
         x = Game.WIDTH/2;
         y = Game.HEIGHT/2;
      }

      x += velX;
      y += velY; 

      if (Physics.collision(this, game.getPlayer()))
      {
         velY *= -1;
      }
   }

   @Override
   public void draw(Graphics g)
   {
      g.setColor(color);
      g.fillOval((int) x, (int) y, diameter, diameter);
   }

   public void reverseVelY()
   {
      velY *= -1;
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

   @Override
   public Rectangle getBounds()
   {
      return new Rectangle((int) x, (int) y, diameter, diameter);
   }
}
