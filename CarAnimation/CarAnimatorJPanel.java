
/*
CarAnimatorJPanel.java
Author:   1506036
Modified: 13/4/2018

Animation of series of car redCar.
Uses code from Dietel and Dietel, 2006. Java: How to Program. 7th Ed. Prentice Hall.
*/

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.io.*;
import javax.swing.*;

public class CarAnimatorJPanel extends JPanel implements KeyListener {
	private Socket clientSocket = null;
	private DataOutputStream output = null;
	private BufferedReader input = null;

	private final static String IMAGE_NAME = "car"; // base image name
	protected ImageIcon redCar[]; // image array for red car
	protected ImageIcon blueCar[]; // image array for blue car
	private final int TOTAL_IMAGES = 16; // number of redCar in array
	private int redCurrentImage = 5; // current image index
	private int blueCurrentImage = 5; // current image index
	private final int ANIMATION_DELAY = 50; // delay in ms
	private int WIDTH; // width of image
	private int HEIGHT; // height of image
	private Timer animationTimer; // Timer class object
	private int red_xPos = 380;
	private int red_yPos = 500;
	private int blue_xPos = 380;
	private int blue_yPos = 550;
	private int redSpeedf = 0;
	private int blueSpeedf = 0;
	private String redSpeed;
	private String blueSpeed;
	private JLabel speedoLabel;
	private JLabel redControlsLabel;
	private JLabel blueControlsLabel;
	private String request;
	private String response;

	// no arg constructor
	public CarAnimatorJPanel() {

		try {
			clientSocket = new Socket("localhost", 5000);
			input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			output = new DataOutputStream(clientSocket.getOutputStream());
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		speedoLabel = new JLabel();
		speedoLabel.setVerticalTextPosition(JLabel.CENTER);
		speedoLabel.setHorizontalTextPosition(JLabel.CENTER);
		add(speedoLabel);
		redControlsLabel = new JLabel(
				"<html><font color='red'> Red Car: <br />Accelerate: UP arrow <br />Decelerate: DOWN Arrow <br />Left: LEFT arrow <br />Right: RIGHT arrow</html>");
		add(redControlsLabel);
		blueControlsLabel = new JLabel(
				"<html><font color='blue'> Blue car: <br />Accelerate: W key <br />Decelerate: S key <br />Left: A key <br />Right: D key</html>");
		add(blueControlsLabel);

		this.setFocusable(true);
		redCar = new ImageIcon[TOTAL_IMAGES]; // create image array
		blueCar = new ImageIcon[TOTAL_IMAGES]; // create image array

		for (int count = 0; count < redCar.length; count++) {
			// load all 16 redCar for red car
			redCar[count] = new ImageIcon(getClass().getResource("RED_CAR_SPRITES/" + IMAGE_NAME + count + ".gif"));
			// same for blue car
			blueCar[count] = new ImageIcon(getClass().getResource("BLUE_CAR_SPRITES/" + IMAGE_NAME + count + ".gif"));
			WIDTH = redCar[0].getIconWidth();
			HEIGHT = redCar[0].getIconHeight();
		} // end of forloop

		animationTimer = new Timer(ANIMATION_DELAY, new TimerHandler()); // create new Timer class obj

	} // end of constructor

	// 1 arg method paintComponent takes arg Graphics,
	// draws race track inside JPanel
	// uses anonymous Color objects to set colour for each step
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		// settings for track appearance
		g.setColor(new Color(0, 255, 0)); // set colour to green
		g.fillRect(150, 200, 550, 300); // fill centre area
		g.setColor(new Color(0, 0, 0)); // set colour to black
		g.drawRect(50, 100, 750, 500); // outer edge
		g.drawRect(150, 200, 550, 300); // inner edge
		g.setColor(new Color(255, 255, 0)); // set colour to yellow
		g.drawRect(100, 150, 650, 400); // mid-lane marker
		g.setColor(new Color(255, 255, 255)); // set colour to black
		g.drawLine(425, 500, 425, 600); // start line

		// paint red car in inside lane position
		redCar[redCurrentImage].paintIcon(this, g, red_xPos, red_yPos);
		// paint blue car in outside lane position
		blueCar[blueCurrentImage].paintIcon(this, g, blue_xPos, blue_yPos);

	}// end method paintComponent

	// extract data from incoming string
	public void getData(String message) {
		
	}

	// update the value in the speedo label
	public void speedoUpdate() {
		
		if (clientSocket != null && input != null && output != null) {
			try {
				request = "ping\n";
				output.writeUTF(request);
				output.flush();
				
				if((response = input.readLine()) != null) {
					System.out.println(response);
				}
				
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		String redSpeed = Integer.toString(redSpeedf * 10);
		String blueSpeed = Integer.toString(blueSpeedf * 10);
		// String blueSpeed = Integer.toString(blueSpeedf);
		speedoLabel.setText(
				"<html>Red car Speed: " + redSpeed + " mph <br /> Blue car Speed: " + blueSpeed + " mph</html>");
	}

	// no arg method startAnimation simply exists to be called in main
	// to start the race animation
	public void startAnimation() {
		animationTimer.start();
	}// end method startAnimation

	// stop animation timer
	public void stopAnimation() {
		animationTimer.stop();
	}// end of method stopAnimation

	// get minimum size of animation
	public Dimension getMinimumSize() {
		return getPreferredSize();
	}// end method getMinimumSize

	// get preferred size of animation
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH, HEIGHT);
	}// end method getPreferredSize

	// inner class to handle action events from Timer
	private class TimerHandler implements ActionListener {
		// respond to Timer's event
		public void actionPerformed(ActionEvent actionEvent) {
			// repaint animator
			changePos(); // THIS MAKES IT WORK
			speedoUpdate(); // crashes atm with blue stuff uncommented
			repaint();
		}
	}// end class TimerHandler

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			// redefine speed factor
			if (0 <= redSpeedf && redSpeedf < 10) {
				redSpeedf += 1;
			}
			System.out.println("red up");
			// redefine x and y location
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			// redefine speed factor
			if (0 < redSpeedf && redSpeedf <= 10) {
				redSpeedf -= 1;
			}
			System.out.println("red down");
			// redefine x and y location

		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			// redefine speed factor
			if (redCurrentImage == 0) {
				redCurrentImage = 15;
			} else {
				redCurrentImage -= 1;
			}
			System.out.println("red left");
			// redefine x and y location

		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			// redefine speed factor

			if (redCurrentImage == 15) {
				redCurrentImage = 0;
			} else {
				redCurrentImage += 1;
			}
			System.out.println("red right");
			// redefine x and y location

		} else if (e.getKeyCode() == 87) // blue up
		{
			// redefine speed factor
			if (0 <= blueSpeedf && blueSpeedf < 10) {
				blueSpeedf += 1;
			}
			System.out.println("blue up");
			// redefine x and y location

		} else if (e.getKeyCode() == 83)// blue down
		{
			// redefine speed factor
			if (0 < blueSpeedf && blueSpeedf <= 10) {
				blueSpeedf -= 1;
			}
			System.out.println("blue down");
			// redefine x and y location

		} else if (e.getKeyCode() == 65)// blue left
		{
			// redefine speed factor
			if (blueCurrentImage == 0) {
				blueCurrentImage = 15;
			} else {
				blueCurrentImage -= 1;
			}
			System.out.println("blue left");
			// redefine x and y location

		} else if (e.getKeyCode() == 68)// blue right
		{
			// redefine speed factor

			if (blueCurrentImage == 15) {
				blueCurrentImage = 0;
			} else {
				blueCurrentImage += 1;
			}
			System.out.println("blue right");
			// redefine x and y location
		}
	}

	// key listener methods included as necessity
	public void keyReleased(KeyEvent e) {
	}

	public void keyTyped(KeyEvent e) {
	}

	public void changePos() {

		// red car position change
		if (redCurrentImage == 1) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 2 * redSpeedf; // displacement = distance * speed up
		}

		if (redCurrentImage == 2) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 1 * redSpeedf; // displacement = distance * speed

		}

		if (redCurrentImage == 3) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 2 * redSpeedf; // displacement = distance * speed

		}

		if (redCurrentImage == 4) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 1 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 5) // if pointing a direction, go that way
		{
			red_xPos = red_xPos + 2 * redSpeedf; // displacement = distance * speed right
		}

		if (redCurrentImage == 6) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 1 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 7) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 8) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos + 1 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 9) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 2 * redSpeedf; // displacement = distance * speed down
		}

		if (redCurrentImage == 10) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 1 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 11) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 12) // if pointing a direction, go that way
		{
			red_yPos = red_yPos + 1 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 13) // if pointing a direction, go that way
		{
			red_xPos = red_xPos - 2 * redSpeedf; // displacement = distance * speed left
		}

		if (redCurrentImage == 14) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 1 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 15) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 2 * redSpeedf; // displacement = distance * speed
		}

		if (redCurrentImage == 0) // if pointing a direction, go that way
		{
			red_yPos = red_yPos - 2 * redSpeedf; // displacement = distance * speed
			red_xPos = red_xPos - 1 * redSpeedf; // displacement = distance * speed
		}

		// conditions to slow car on grass
		if (redSpeedf > 1) {
			if (red_xPos >= 120 && red_xPos <= 670 && red_yPos >= 170 && red_yPos <= 480) {
				// slow car by 1 factor per refresh
				redSpeedf = redSpeedf - 1;
			}
		}

		// stop car if outside track bounds
		if (red_xPos <= 35 || red_xPos >= 775 || red_yPos <= 80 || red_yPos >= 575) {
			redSpeedf = 0;
		}

		// blue car position change
		if (blueCurrentImage == 1) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 2 * blueSpeedf; // displacement = distance * speed up
		}

		if (blueCurrentImage == 2) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 1 * blueSpeedf; // displacement = distance * speed

		}

		if (blueCurrentImage == 3) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 2 * blueSpeedf; // displacement = distance * speed

		}

		if (blueCurrentImage == 4) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 1 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 5) // if pointing a direction, go that way
		{
			blue_xPos = blue_xPos + 2 * blueSpeedf; // displacement = distance * speed right
		}

		if (blueCurrentImage == 6) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 1 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 7) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 8) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos + 1 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 9) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 2 * blueSpeedf; // displacement = distance * speed down
		}

		if (blueCurrentImage == 10) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 1 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 11) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 12) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos + 1 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 13) // if pointing a direction, go that way
		{
			blue_xPos = blue_xPos - 2 * blueSpeedf; // displacement = distance * speed left
		}

		if (blueCurrentImage == 14) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 1 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 15) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 2 * blueSpeedf; // displacement = distance * speed
		}

		if (blueCurrentImage == 0) // if pointing a direction, go that way
		{
			blue_yPos = blue_yPos - 2 * blueSpeedf; // displacement = distance * speed
			blue_xPos = blue_xPos - 1 * blueSpeedf; // displacement = distance * speed
		}

		// conditions to slow car on grass
		if (blueSpeedf > 1) // prevent car from stopping completely, or reversing
		{
			// grass area limits
			if (blue_xPos >= 120 && blue_xPos <= 670 && blue_yPos >= 170 && blue_yPos <= 480) {
				// slow car by 1 factor per refresh
				blueSpeedf = blueSpeedf - 1;
			}
		}

		// stop car if outside track bounds
		if (blue_xPos <= 35 || blue_xPos >= 775 || blue_yPos <= 80 || blue_yPos >= 575) {
			blueSpeedf = 0;
		}

	} // end of changePos method

}// end class CarAnimatorJPanel
