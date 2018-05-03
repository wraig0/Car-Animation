/*
CarAnimator.java
Author:   1506036
Modified: 13/03/2018

Animation of series of car images.
Uses code from Dietel and Dietel, 2006. Java: How to Program. 7th Ed. Prentice Hall.
*/
import javax.swing.JFrame;

public class CarAnimator 
{
    // execute animation in a JFrame
    public static void main(String args[])
    {
        // create instance of CarAnimatorJPanel
        CarAnimatorJPanel animation = new CarAnimatorJPanel();
        
        // create instance of JFrame
        JFrame window = new JFrame("Car Race");
        animation.addKeyListener(animation);
        // set up the JFrame window
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);        
        window.add(animation);
        window.setSize(900,660);
        window.setVisible(true);
        // start the race animation
        animation.startAnimation();
                
    }// end main
}//end class CarAnimator