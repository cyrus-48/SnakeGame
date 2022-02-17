import javax.swing.JPanel;
import java.awt.*;

import java.awt.event.*;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.*;
 


public class GamePanel extends JPanel implements ActionListener{
	static final int SCREEN_WIDTH = 600;
	static final int SCREEN_HEIGHT = 600;
	static final int UNIT_SIZE = 25;
	static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT)/ UNIT_SIZE;
	static final int DELAY = 75;
	int applesEaten;
	final int x[] = new int[GAME_UNITS];
	final int y[] = new int[GAME_UNITS]; 

	int bodyParts = 6;
	int appleX;
	int appleY;
	char direction = 'R';
	boolean running = false;
	Timer timer;
	Random random;
	GamePanel(){
		// create the game board
		random = new Random();
		this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		this.setBackground(Color.black); // bg color
		this.setFocusable(true);
		this.addKeyListener(new MyKeyAdapter());
		startGame();
		
	}
	public void startGame() {
		// starts the game 
		newApple();// call the new appple method to create an apple 
		running = true;
		timer = new Timer(DELAY, this); // set the delay value
		timer.start(); // call the timer method and set it to start
		
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	}
	public void draw(Graphics g) {
		if (running) {
			// draw an instance of the game if the game if runnning 
			for (int  i = 0 ; i < SCREEN_HEIGHT/UNIT_SIZE;i++) {
				// draw the grid lines in the game board
				g.drawLine(i*UNIT_SIZE,0,i*UNIT_SIZE, SCREEN_HEIGHT);
				g.drawLine(0,i*UNIT_SIZE,SCREEN_WIDTH,i* UNIT_SIZE);
			}
			 // draw an apple
				g.setColor(Color.blue);
				g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
				
			for(int i =0; i<bodyParts;i++) {
				// draw the snake with color red
				if(i == 0) {
					g.setColor(Color.red);
					g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
					
				}
				else {
					g.setColor(new Color(45,180,0));
					g.fillRect(x[i],y[i], UNIT_SIZE, UNIT_SIZE);
				}  
			}
			// game score text.
				g.setColor(Color.red);
				g.setFont(new Font("Ink Free",Font.BOLD, 40));
			FontMetrics metrics = getFontMetrics(g.getFont());
			
			g.drawString("your score: " + applesEaten * 5, (SCREEN_WIDTH - metrics.stringWidth("your score: " + applesEaten * 5))/2 , SCREEN_HEIGHT/2);
			
			
		}
		else {
			//call the game over method if the game is not running 
			gameOver(g);
		}
		
	}
	public void newApple() {
		// create a new apple at random positions when the method is called 
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE)) * UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE)) * UNIT_SIZE; 
		
		
	}
	public void move() {
		for(int i = bodyParts; i > 0 ; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
			
		}
		switch(direction) { // movement of the snake
		case 'U':
			y[0] = y[0]- UNIT_SIZE; // move up 
			break;
		case 'D':
			y[0] = y[0] + UNIT_SIZE; // move down
			break;
		case 'L':
			x[0] = x[0] - UNIT_SIZE; // move to the left
			break;
		case 'R':
			x[0] = x[0] + UNIT_SIZE; // move to the right
			break;
			
		}
		
		
	}
	public void checkApple() {
		if((x[0] == appleX) && (y[0] == appleY)) {
			bodyParts++; // add a section of body part after an apple is eaten
			applesEaten++; //  add a score after the apple is eaten
			newApple(); // create a new apple
			
		}
		
	}
	public void checkCollisions() {
		// checks if the head collides with the body
		for(int i = bodyParts; i > 0; i-- ) {
			if ((x[0]==x[i]) && (y[0] == y[i]) ) {
				running = false;
				
			}
		}
		// check if the head touches the left border
		if(x[0] < 0) {
			running  = false;
		}
		// check if the head touches the right border
		if(x[0] >  SCREEN_WIDTH) {
				running  = false;
		}
		// check if the head touches the top border
		
	   if(x[0] < 0) {
		   running = false;
	   }
	// check if the head touches the bottom
	   if (x[0] > SCREEN_HEIGHT) {
		   running = false;
	   }
	   // stop the timer
	   if(!running) {
		   timer.stop();
	   }
	}
	
	public void gameOver(Graphics g) { // display the game over text after collision 
		// game over text 
		g.setColor(Color.red);
		g.setFont(new Font("Ink Free",Font.BOLD, 75));
		FontMetrics metrics = getFontMetrics(g.getFont());
		
		g.drawString("game over", (SCREEN_WIDTH - metrics.stringWidth("game over"))/2 , SCREEN_HEIGHT/2); // display the text at the moddle of the screen
		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(running) {
			move();
			checkApple();	
			checkCollisions();
			}
		repaint();
		
	}
	public class MyKeyAdapter extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT: // move left
				if(direction != 'R') {
					direction = 'L';
				}
				break;
			case KeyEvent.VK_RIGHT: // move right
				if(direction != 'L') {
					direction = 'R';
				}
				break;
				
			
		  case KeyEvent.VK_UP: // move up
					if(direction != 'D') {
						direction = 'u';
					}
					break;
		case KeyEvent.VK_DOWN: // move down
			if(direction != 'U') {
				direction = 'D';
			}
			break;
			
		}
		}
	}

}
