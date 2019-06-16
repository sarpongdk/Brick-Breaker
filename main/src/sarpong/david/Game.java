import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;

import javax.swing.*;

public class Game extends Canvas implements Runnable
{
   public static final int WIDTH = 640;
   public static final int HEIGHT = 480;
   private static final int FPS = 75;
   private final String title = "Brick Breaker";

   protected Player player;
   protected BufferedImage image;
   protected BufferStrategy bufferStrategy;
   protected Thread thread;
   protected JFrame frame;
   protected volatile boolean running, gameOver, isPaused;

   public Game()
   {
      running = false;
      gameOver = false;
      isPaused = false;

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

         if (timer == 1000000000)
         {
            System.out.println("Ticks/FPS: " + ticks);
            ticks = 0;
            timer = 0;
         }
      }

      stop();
   }

   public void update()
   {
      player.update();
   }

   public void render()
   {
      bufferStrategy = getBufferStrategy();
      if (bufferStrategy == null)
      {
         createBufferStrategy(3);
         return;
      }

      Graphics g = bufferStrategy.getDrawGraphics();

      g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
      g.setColor(Color.BLACK);
      g.fillRect(0, 0, WIDTH, HEIGHT);

      player.draw(g);

      bufferStrategy.show();
      g.dispose();
   }

   public static void main(String[] args)
   {
      Game game = new Game();
      game.start();
   }

}
