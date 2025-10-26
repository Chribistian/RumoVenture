package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 Pixel
	final int scale = 3;
	
	final int tileSize = originalTileSize * scale; // also 48x48
	final int maxScreenCol = 16; //Spalten
	final int maxScreenRow = 12; //Reihen
	final int screenWidth = tileSize * maxScreenCol; // 768p
	final int screenHeight = tileSize * maxScreenRow; // 576p
	
	//FPS
	int FPS = 60;
	
	KeyHandler keyH = new KeyHandler ();
	
	
	// Thread wiederholt sich, bis man aktiv stoppt
	Thread gameThread;
	
	// Set player's default position
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}

	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	@Override
	public void run() {
		
		//Geschwindigkeit reduzieren 1sec in nanosec/FPS
		
		double drawInterval = 1000000000/FPS; //0.01666 seconds
		double nextDrawTime = System.nanoTime() + drawInterval;

		while(gameThread != null) {
			
			
			// 1 UPDATE: update Information such as character Position
			update();
			
			// 2 DRAW: draw the screen with the updated information
			repaint();
			
			//loop should pause the remaining time
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				//sleep(s.u.) is in millisec
				remainingTime = remainingTime/1000000;
				
				//if more time is needed then expected
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void update() {
		
		if(keyH.upPressed == true) {
			playerY = playerY - playerSpeed;
		}
		
		else if(keyH.downPressed == true) {
			playerY = playerY + playerSpeed;
		}
		else if(keyH.leftPressed == true) {
			playerX = playerX - playerSpeed;
		}
		else if(keyH.rightPressed == true) {
			playerX = playerX + playerSpeed;
		}
		
	}
	public void paintComponent(Graphics g) {
		
		super.paintComponent (g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		g2.setColor(Color.white);
		
		g2.fillRect(playerX, playerY, tileSize, tileSize);
		
		g2.dispose();
	}
	
	
	
	
	}
