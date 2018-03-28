/*
Author:   1506036
Modified: 23/03/2018

Animation of series of car redCar.
Uses code from Dietel and Dietel, 2006. Java: How to Program. 7th Ed. Prentice Hall.
*/

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CarAnimatorJPanel extends JPanel implements KeyListener
{
    private final static String IMAGE_NAME = "car"; // base image name
    protected ImageIcon redCar[]; // image array for red car
    protected ImageIcon blueCar[]; // image array for blue car
    private final int TOTAL_IMAGES = 16; // number of redCar in array
    private int currentImage = 0; // current image index
    private final int ANIMATION_DELAY = 50; // delay in ms
    private int width; // width of image
    private int height; // height of image
    private Timer animationTimer; // Timer class object
    private int xPos = 400;
    private int yPos = 400;
    private int speedf = 0;
    
    
    
    // no arg constructor
    public CarAnimatorJPanel()
    {
        redCar = new ImageIcon[TOTAL_IMAGES]; // create image array
        //blueCar = new ImageIcon[TOTAL_IMAGES]; // create image array
        for(int count = 0; count < redCar.length; count++)
        {
            // load all 16 redCar for red car
            redCar[count] = new ImageIcon(getClass().getResource("RED_CAR_SPRITES/" + IMAGE_NAME + count + ".gif"));
            // same for blue car
            //blueCar[count] = new ImageIcon(getClass().getResource("BLUE_CAR_SPRITES/" + IMAGE_NAME + count + ".gif"));
            width = redCar[0].getIconWidth();
            height = redCar[0].getIconHeight();            
        } // end of forloop
        currentImage = 1; // sets current image in red car array to something
        animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler()); // create new Timer class obj
    
    // car class    
          
        
    } // end of constructor
    
    // 1 arg method paintComponent takes arg Graphics,
    // draws race track inside JPanel
    // uses anonymous Color objects to set color for each step
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        // settings for track appearance
        g.setColor(new Color(0,255,0)); // set color to green
        g.fillRect(150, 200, 550, 300); // fill centre area
        g.setColor(new Color(0,0,0)); // set color to black
        g.drawRect(50, 100, 750, 500);  // outer edge
        g.drawRect(150, 200, 550, 300); // inner edge
        g.setColor(new Color(255,255,0)); // set color to yellow
        g.drawRect(100, 150, 650, 400); // mid-lane marker
        g.setColor(new Color(255,255,255)); // set color to black
        g.drawLine(425, 500, 425, 600); // start line
      
        // paint car in outside lane position
        redCar[currentImage].paintIcon(this, g, xPos, yPos); 
        
    }// end method paintComponent
    
    
    
    // no arg method startAnimation simply exists to be called in main
    // to start the race animation
    public void startAnimation()
    {
        animationTimer.start();
    }// end method startAnimation
    
    // stop animation timer
    public void stopAnimation()
    {
        animationTimer.stop();
    }// end of method stopAnimation
    
    // get minimum size of animation
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }// end method getMinimumSize
    
    // get preferred size of animation
    public Dimension getPreferredSize()
    {
        return new Dimension(width, height);
    }// end method getPreferredSize
    
    // inner class to handle action events from Timer
    private class TimerHandler implements ActionListener
    {
    // respond to Timer's event 
        public void actionPerformed( ActionEvent actionEvent ) 
        { 
            repaint();
        }// repaint animator 
    }// end class TimerHandler
    
    
    public void keyPressed(KeyEvent e)
    {
    
       if (e.getKeyCode() == KeyEvent.VK_UP)
       {
           // refine speed factor
           speedf += 1;
       
           //redefine x and y location
           changePos();
       }
       else if (e.getKeyCode() == KeyEvent.VK_DOWN)
       {
           // refine speed factor
           speedf -= 1;
           
           //redefine x and y location
           changePos();  
       }
       else if (e.getKeyCode() == KeyEvent.VK_LEFT)
       {
           // refine speed factor
           currentImage -= 1;
           
           //redefine x and y location
           changePos();
       }
       else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
       {
           // refine speed factor
           currentImage += 1;
         
           //redefine x and y location
           changePos();
       }
    }
    // key listener methods included as necessity
    public void keyReleased(KeyEvent e)
    {
    }
    public void keyTyped(KeyEvent e)
    {
    }
    
    public void changePos()
    {
         if (currentImage == 1) // if pointing a direction, go that way with 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed     up
         }
         
         if (currentImage == 2) // if pointing a direction, go that way with 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos + 1 * speedf; // displacement = distance * speed

         }
         
         if (currentImage == 3) // if pointing a direction, go that way with 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos + 2 * speedf; // displacement = distance * speed

         }
         
         if (currentImage == 4) // if pointing a direction, go that way with 
         {
             yPos = yPos + 1 * speedf; // displacement = distance * speed
             xPos = xPos + 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 5) // if pointing a direction, go that way with 
         {
             xPos = xPos + 2 * speedf; // displacement = distance * speed     right
         }
         
         if (currentImage == 6) // if pointing a direction, go that way with 
         {
             yPos = yPos - 1 * speedf; // displacement = distance * speed
             xPos = xPos + 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 7) // if pointing a direction, go that way with 
         {
             yPos = yPos - 2 * speedf; // displacement = distance * speed
             xPos = xPos + 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 8) // if pointing a direction, go that way with 
         {
             yPos = yPos - 2 * speedf; // displacement = distance * speed
             xPos = xPos + 1 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 9) // if pointing a direction, go that way with 
         {
             yPos = yPos - 2 * speedf; // displacement = distance * speed        down   
         }
         
         if (currentImage == 10) // if pointing a direction, go that way with 
         {
             yPos = yPos - 2 * speedf; // displacement = distance * speed
             xPos = xPos - 1 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 11) // if pointing a direction, go that way with 
         {
             yPos = yPos - 2 * speedf; // displacement = distance * speed
             xPos = xPos - 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 12) // if pointing a direction, go that way with 
         {
             yPos = yPos - 1 * speedf; // displacement = distance * speed
             xPos = xPos - 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 13) // if pointing a direction, go that way with 
         {
             xPos = xPos - 2 * speedf; // displacement = distance * speed     left
         }
         
         if (currentImage == 14) // if pointing a direction, go that way with 
         {
             yPos = yPos + 1 * speedf; // displacement = distance * speed
             xPos = xPos - 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 15) // if pointing a direction, go that way with 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos - 2 * speedf; // displacement = distance * speed
         }
         
         if (currentImage == 0) // if pointing a direction, go that way with 
         {
             yPos = yPos + 2 * speedf; // displacement = distance * speed
             xPos = xPos - 1 * speedf; // displacement = distance * speed
         }
         
         
    }
    
}// end class CarAnimatorJPanel

