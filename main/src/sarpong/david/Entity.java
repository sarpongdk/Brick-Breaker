package sarpong.david;

import java.awt.*;

public interface Entity
{
   public void update();
   public void draw(Graphics g);
   
   public void setX(double x);
   public void setY(double y);
   
   public double getX();
   public double getY();

   public Rectangle getBounds();
}
