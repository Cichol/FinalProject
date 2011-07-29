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
			e.gravity();
			p.movement();
			e.movement();
			cDetector();
			eDetector();
			repaint();
		}
		public void cDetector() //IF TIME REMEMBER TO USE MOD TO DETECT WHICH SIDE MORE ON
		{
			int checkY = ((p.yPos - 1) / 16); // INT -> ARRAY
			int checkX = (p.xPos - 1) / 16; // INT -> ARRAY
			death();
			if(checkY + 1 < map.length)
			{
				/*
				 * Ceiling Collision Check
				 */
				if( map[(p.yPos + 1)/16][(p.xPos + 1)/16] != '.')//top left corner up direction
				{
					p.yVel = 0;
					p.yPos = (checkY + 1) * 16;
				}
				if(map[(p.yPos + 1)/16][((p.xPos + 15)/16)] != '.') //top right corner
				{
					p.yVel = 0;
					p.yPos = (checkY + 1) * 16;
				}
				/*
				 * Floor Collision Check
				 */
				if(map[(p.yPos +15)/16][(p.xPos + 1)/16] != '.') //bottom left corner
				{
					p.yVel = 0;
					p.yPos = checkY * 16;
					p.jump = false;
					System.out.println("BOTTOM LEFT CORNER COLLISION");
				}
				if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] != '.') //bottom right corner
				{
					p.yVel = 0;
					p.yPos = checkY * 16;
					System.out.println("BOTTOM RIGHT CORNER COLLISION");
					p.jump = false;
				}
				if(p.xVel > 0) //Check to see what direction the player is moving
				{
					//Check collision for moving right
					if(map[(p.yPos + 1)/16][checkX + 1] != '.')
					{
						p.xVel = 0;
						p.xPos = checkX * 16;
					}
				}
				else if(p.xVel < 0)
				{
					if(map[(p.yPos + 5)/16][(p.xPos + 1)/16] != '.')
					{
						p.xVel = 0;
						p.xPos = (checkX + 1) * 16;
					}
				}
				/*
				 * Left Collision Check
				 */
			/*
			 	if(map[checkY][checkX] != '.')
				{
					p.xVel = 0;
					p.xPos = (checkX + 1) * 16;
				}*/
				/*if(map[checkY + 1][checkX] != '.')
				{
					p.xVel = 0;
					p.xPos = (checkX + 1) * 16;
				}*/
				/*
				 * Right Collision Check
				 */
				/*if(map[checkY][checkX + 1] != '.')
				{
					p.xVel = 0;
					p.xPos = checkX * 16;
				}*/
				/*if(map[checkY + 1][checkX + 1] != '.')
				{
					p.xVel = 0;
					p.xPos = checkX * 16;
				}*/
				/*if(map[checkY + 1][checkX] != '.') // bottom left corner down direction
				{
					p.yVel = 0;
					p.jump = false;
					p.yPos = checkY * 16;
				}
				if(map[checkY][checkX] != '.') // bottom left corner down direction
				{
					p.yVel = 0;
					p.jump = false;
					p.yPos = checkY * 16;
				}*/
			}
			else if( checkX + 1 < map[0].length )
			{
				/*if(map[checkY][checkX + 1] != '.') // top right corner right direction
				{
					p.xVel = 0;
					p.xPos = checkX * 16;
				}
				if(map[checkY][checkX] != '.') // top left corner left direction
				{
					p.xVel = 0;
					p.xPos = (checkX + 1) * 16;
				}*/
			}
			else
			{

			}
			if(checkX + 1 >= map[0].length)
			{
				p.xPos = checkX * 16;
				p.xVel = 0;
			}
			else if(p.xPos < 0 )
			{
				p.xPos = checkX * 16;
				p.xVel = 0;
			}
		}
		public void eDetector() //IF TIME REMEMBER TO USE MOD TO DETECT WHICH SIDE MORE ON
		{
			int checkY = ((e.yPos - 1) / 16); // INT -> ARRAY
			int checkX = (e.xPos - 1) / 16; // INT -> ARRAY
			death();
			if(checkY + 1 < map.length)
			{
				/*
				 * Ceiling Collision Check
				 */
				if( map[(e.yPos + 1)/16][(e.xPos + 1)/16] != '.')//top left corner up direction
				{
					e.yVel = 0;
					e.yPos = (checkY + 1) * 16;
				}
				if(map[(e.yPos + 1)/16][((e.xPos + 15)/16)] != '.') //top right corner
				{
					e.yVel = 0;
					e.yPos = (checkY + 1) * 16;
				}
				/*
				 * Floor Collision Check
				 */
				if(map[(e.yPos +15)/16][(e.xPos + 1)/16] != '.') //bottom left corner
				{
					e.yVel = 0;
					e.yPos = checkY * 16;
					System.out.println("BOTTOM LEFT CORNER COLLISION");
				}
				if(map[(e.yPos + 15)/16][(e.xPos + 15)/16] != '.') //bottom right corner
				{
					e.yVel = 0;
					e.yPos = checkY * 16;
					System.out.println("BOTTOM RIGHT CORNER COLLISION");
				}
				if(e.xVel > 0) //Check to see what direction the player is moving
				{
					//Check collision for moving right
					if(map[(e.yPos + 1)/16][checkX + 1] != '.')
					{
						e.xVel = 0;
						p.xPos = checkX * 16;
					}
				}
				else if(e.xVel < 0)
				{
					if(map[(e.yPos + 5)/16][(e.xPos + 1)/16] != '.')
					{
						e.xVel = 0;
						e.xPos = (checkX + 1) * 16;
					}
				}
				/*
				 * Left Collision Check
				 */
			/*
			 	if(map[checkY][checkX] != '.')
				{
					e.xVel = 0;
					e.xPos = (checkX + 1) * 16;
				}*/
				/*if(map[checkY + 1][checkX] != '.')
				{
					e.xVel = 0;
					e.xPos = (checkX + 1) * 16;
				}*/
				/*
				 * Right Collision Check
				 */
				/*if(map[checkY][checkX + 1] != '.')
				{
					e.xVel = 0;
					e.xPos = checkX * 16;
				}*/
				/*if(map[checkY + 1][checkX + 1] != '.')
				{
					e.xVel = 0;
					e.xPos = checkX * 16;
				}*/
				/*if(map[checkY + 1][checkX] != '.') // bottom left corner down direction
				{
					e.yVel = 0;
					e.jump = false;
					e.yPos = checkY * 16;
				}
				if(map[checkY][checkX] != '.') // bottom left corner down direction
				{
					e.yVel = 0;
					e.jump = false;
					e.yPos = checkY * 16;
				}*/
			}
			else if( checkX + 1 < map[0].length )
			{
				/*if(map[checkY][checkX + 1] != '.') // top right corner right direction
				{
					e.xVel = 0;
					e.xPos = checkX * 16;
				}
				if(map[checkY][checkX] != '.') // top left corner left direction
				{
					e.xVel = 0;
					e.xPos = (checkX + 1) * 16;
				}*/
			}
			else
			{

			}
			if(checkX + 1 >= map[0].length)
			{
				e.xPos = checkX * 16;
				e.xVel = 0;
			}
			else if(p.xPos < 0 )
			{
				e.xPos = checkX * 16;
				e.xVel = 0;
			}
		}
		
	}
	private char[][] map;
	private int width;
	private int height;
	Player p = new Player(8);
	Enemy e = new Enemy(8);
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
	public void death()
	{
		if( p.yPos + 16 >= map.length * 16 )
		{
			p.xPos = 32;
			p.yPos = 100;
		}
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