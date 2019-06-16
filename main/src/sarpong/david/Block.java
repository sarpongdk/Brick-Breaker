import java.awt.Graphics;

public class Block implements Entity
{
   private double x, y;
   private int size;
   private Color color;
   private Game game;

   public Block(double x, double y, Game game)
   {
      this.x = x;
      this.y = y;
      
      this.game = game;
      this.size = 30;
      this.color = Color.WHITE;
   }

   @Override
   public void update {}

   @Override
   public void draw(Graphics g)
   {
      g.setColor(color);
      g.fillRect((int) x, (int) y, size, size);
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
   public double setX(double x)
   {
      this.x = x;
   }

   @Override
   public double setY(double y)
   {
      this.y = y;
   }
}
