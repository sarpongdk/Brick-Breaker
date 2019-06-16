import java.util.*;
import java.awt.Graphics;

public class BlockController
{
   private ArrayList<Block> blocks;
   private Game game;
   private int row, col;

   public BlockController(int row, int col, Game game)
   {
      this.row = row;
      this.col = col;
      this.game = game;
      this.blocks = new ArrayList<Block>();
   }

   public void update()
   {
      for (Block block: blocks)
      {
         block.update();
      }
   }

   public void draw(Graphics g)
   {
      for (Block block: blocks)
      {
         block.draw(g);
      }
   }

   public synchronized void addBlock(Block block)
   {
      blocks.add(block);
   }

   private synchronized void removeBlock(Block block)
   {
      blocks.remove(block);
   }
}
