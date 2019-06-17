package sarpong.david;

import java.util.*;

import java.awt.*;

public class BlockController
{
   private ArrayList<Block> blocks;
   private Game game;
   private int row, col, score;

   public BlockController(int row, int col, Game game)
   {
      this.row = row;
      this.col = col;
      this.game = game;
      this.blocks = new ArrayList<Block>();
      this.score = 0;

      initBlocks();
   }

   private void initBlocks()
   {
      for (int j = 0; j < col; j++)
      {
         for (int i = 0; i < row; i++)
         {
            addBlock(new Block((i + 1) * Block.SIZE + (5 * i), (j + 1) * Block.SIZE + (5 * j), game));
         }
      }
   }

   public int getNumberOfBlocks()
   {
      return blocks.size();
   }

   public int getScore()
   {
      return score;
   }

   public void update()
   {
      Iterator<Block> iter = blocks.iterator();
      Ball ball = game.getBall(); 

      while (iter.hasNext())
      {
         Block block = iter.next();
         if (Physics.collision(block, ball))
         {
            iter.remove();
            score++;
            ball.reverseVelY();
            //game.decreaseBlocksNumber();
         }
      }
   }

   public void draw(Graphics g)
   {
      //g.setFont(new Font("arial", Font.PLAIN, 15));
      //g.setColor(Game.FG_COLOR);
      //g.drawString("Score: " + Integer.toString(score), (2 * Game.WIDTH)/3, 10);

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

   public class Block implements Entity
   {
      public static final int SIZE = 50;

      private double x, y;
      private Color color;
      private Game game;

      public Block(double x, double y, Game game)
      {
         this.x = x;
         this.y = y;

         this.game = game;
         this.color = Color.WHITE;
      }

      @Override
      public void update()
      {
         if (Physics.collision(this, game.getBall()))
         {
            removeBlock(this);
         }
      }

      @Override
      public void draw(Graphics g)
      {
         g.setColor(color);
         g.fillRect((int) x, (int) y, SIZE, SIZE);
      }

      private void clearBlock(Graphics g)
      {
         g.setColor(Game.BG_COLOR);
         g.fillRect((int) x, (int) y, SIZE, SIZE);
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
      public Rectangle getBounds()
      {
         return new Rectangle((int) x, (int) y, SIZE, SIZE);
      }
   }
}
