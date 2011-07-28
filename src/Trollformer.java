import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;
import java.awt.event.*;

public class Trollformer extends JComponent implements KeyListener
{
	class GameThread extends Thread
	{
		Long lastTime;

		public GameThread()
		{
			lastTime = System.currentTimeMillis();
		}
		public void run() 
		{

			while(true){
				Long now = System.currentTimeMillis();
				if(now - lastTime >= 100)
				{
					update();
					lastTime = now;
				}
			}
		}
		public void update()
		{
			p.gravity();
			p.movement();
			cDetector();
			repaint();
		}
		public void cDetector()
		{
			int checkY = 0;
			int checkX = p.xPos / 16;
			checkY = (p.yPos / 16);
			if(checkY + 1 < map.length){
				if(map[checkY + 1][checkX] != '.')
				{
					//p.xPos -= p.xPos % 16;
					//p.yPos -= p.yPos % 16;
					p.yVel = 0;
					p.jump = false;
					p.yPos = checkY * 16;
				}
			}
			else
			{
				//They died!
			}
			if(checkX + 1 >= map[0].length)
			{
				p.xPos = checkX * 16;
				p.xVel = 0;
			}
			else if(checkX - 1 <= 0 )
			{
				p.xPos = checkX * 16;
				p.xVel = 0;
			}
		}
		
	}
	private char[][] map;
	private int width;
	private int height;
	Player p = new Player(8);
	GameThread thread = new GameThread();
	JFrame f;
	public Trollformer(JFrame frame)
	{
		f = frame;
		this.map = map;
		f.addKeyListener(this);
		mapLoader();
		thread.start();

	}
	public int getXPos()
	{
		return p.xPos;
	}
	public int getYPos()
	{
		return p.yPos;
	}
	public void mapLoader()
	{
		Scanner scan;
		try {
			scan = new Scanner((new File("map.txt")));
			width = scan.nextInt();
			height = scan.nextInt();
			p.xPos = scan.nextInt();
			p.yPos = scan.nextInt();
			map = new char[height][width];
			for(int row = 0; row < height; row++)
			{
				String line = scan.next();
				System.out.println(line);
				for(int col = 0; col < width; col++)
				{
					map[row][col] = line.charAt(col); 
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public void paint(Graphics g)
	{
		p.paint(g);
		for(int row = 0; row < map.length; row++) //Within the for loops are for drawing terrain.
		{
			for(int col = 0; col < map[0].length; col++)
			{
				switch(map[row][col])
				{
				case '.':
					break;
				case 'd':
					g.setColor(Color.BLACK);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 'g':
					g.setColor(Color.green);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 's':
					g.setColor(Color.DARK_GRAY);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 'b':
					g.setColor(Color.RED);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;				
				case 'B':
					g.setColor(Color.BLUE);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 'c':
					g.setColor(Color.CYAN);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 'w':
					g.setColor(Color.orange);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				case 'l':
					g.setColor(Color.green);
					g.fillRect(col * 16, row * 16, 16, 16);
					break;
				}
			}
		}
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			p.moveRight();
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			p.moveLeft();
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
				p.Jump();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			p.xVel = 0;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			p.xVel = 0;
		}		
	}
	@Override
	public void keyTyped(KeyEvent arg0) 
	{
	
		
	}
}

