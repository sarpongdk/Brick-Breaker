package sarpong.david;

import java.awt.*;

public class Menu
{
   public void draw(Graphics g)
   {
      g.setFont(new Font("arial", Font.BOLD, 50));
      g.setColor(Game.FG_COLOR);
      g.drawString("Brick Breaker", Game.WIDTH / 2, 100);
   }
}
