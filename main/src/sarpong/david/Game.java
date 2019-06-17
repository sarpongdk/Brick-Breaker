package sarpong.david;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

import java.util.*;

public class Game extends Canvas implements Runnable
{
   public static final Color BG_COLOR = Color.BLACK;
   public static final Color FG_COLOR = Color.WHITE;
   public static final int WIDTH = 720;
   public static final int HEIGHT = (3 * WIDTH)/4;
   private static final int FPS = 75;
   private final String title = "Brick Breaker";
   private final Random rand = new Random();
   
   protected int currentFPS;
   private Graphics g;
   protected BlockController blockController;
   protected Menu menu;
   protected Ball ball;
   protected Player player;
   protected BufferedImage image;
   protected BufferStrategy bufferStrategy;
   protected Thread thread;
   protected JFrame frame;
   protected volatile boolean running, gameOver, isPaused;

   protected int numOfBlocks;
   protected int score;

   public Game()
   {
      running = false;
      gameOver = false;
      isPaused = false;
     
      currentFPS = 0;
      score = 0;
      numOfBlocks = 0;

      setPreferredSize(new Dimension(WIDTH, HEIGHT));
      setMaximumSize(new Dimension(WIDTH, HEIGHT));
      setMinimumSize(new Dimension(WIDTH, HEIGHT));
      setFocusable(true);

      frame = new JFrame(title);
      frame.add(this);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
   }

   public synchronized void start()
   {
      if (!running || thread == null)
      {
         running = true;
         thread = new Thread(this);
         thread.start();
      }
   }

   private synchronized void stop()
   {
      if (running)
      {
         running = false;
      }
   }

   private synchronized void pause()
   {
      if (running && !gameOver)
      {
         isPaused = true;
      }
   }

   public void keyPressed(KeyEvent e)
   {
      int keyCode = e.getKeyCode();

      switch (keyCode)
      {
         case KeyEvent.VK_RIGHT:
            player.setVelX(2.0);
            break;

         case KeyEvent.VK_LEFT:
            player.setVelX(-2.0);
            break;

         case KeyEvent.VK_ESCAPE:
            pause();
            break;
      }
   }

   public void keyReleased(KeyEvent e)
   {
      int keyCode = e.getKeyCode();

      switch (keyCode)
      {
         case KeyEvent.VK_RIGHT:
            player.setVelX(0);
            break;

         case KeyEvent.VK_LEFT:
            player.setVelX(0);
            break;
      }
   }

   private void init()
   {
      image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);   
      
      requestFocus(); 
    
      numOfBlocks = 21;
      menu = new Menu();
      ball = new Ball(rand.nextInt(getWidth() - 50) + 30, getHeight()/2, this);
      blockController = new BlockController(11, 4, this);
      player = new Player(getWidth() / 2, getHeight() - 40);
      addKeyListener(new KeyInput(this));
   }

   @Override
   public void run()
   {
      init();
      
      long initialTime = System.nanoTime();
      double timePerFrame = 1000000000/FPS;
      double delta = 0;
      int ticks = 0;
      long timer = 0;

      while (running)
      {
         while (isPaused)
         {
         
         }

         long currentTime = System.nanoTime();
         long elapsedTime = currentTime - initialTime;
         delta += elapsedTime/timePerFrame;
         timer += elapsedTime;

         if (delta >= 1)
         {
            update();
            delta--;
            ticks++;
         }

         render();
         initialTime = currentTime;

         if (timer >= 1000000000)
         {
            currentFPS = ticks;
            ticks = 0;
            timer = 0;
         }

         if (numOfBlocks == 0)
         {
            running = false;
         }
      }

      stop();
   }

   public void update()
   {
      player.update();
      ball.update();
      blockController.update();

      numOfBlocks = blockController.getNumberOfBlocks();
      score = blockController.getScore();
   }

   public void render()
   {
      bufferStrategy = getBufferStrategy();
      if (bufferStrategy == null)
      {
         createBufferStrategy(3);
         return;
      }

      g = bufferStrategy.getDrawGraphics();

      g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
      g.setColor(BG_COLOR);
      g.fillRect(0, 0, WIDTH, HEIGHT);
     
      if (isPaused)
      {
         menu.draw(g);
      }
      else
      {
         player.draw(g);
         ball.draw(g);
         blockController.draw(g);
      }

      bufferStrategy.show();
      g.dispose();
   }

   public Player getPlayer()
   {
      return player;
   }
   
   public Ball getBall()
   {
      return ball;
   }

   public BlockController getBlockController()
   {
      return blockController;
   }

   public void decreaseBlocksNumber()
   {
      numOfBlocks--;

   }

   public Graphics getGraphics()
   {
      return g;
   }

   public static void main(String[] args)
   {
      Game game = new Game();
      game.start();
   }

}
