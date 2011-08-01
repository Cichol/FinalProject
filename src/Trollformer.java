import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
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
				if(now - lastTime >= 20)
				{
					update();
					lastTime = now;
				}
			}
		}
		public void update()
		{
			for(int index = 0; index < enemies.size(); index++)
			{

				enemies.get(index).movement();
				enemies.get(index).gravity();
			}
			p.gravity();
			p.movement();
			cDetector();
			eDetector();
			repaint();
		}
		public void cDetector() //IF TIME REMEMBER TO USE MOD TO DETECT WHICH SIDE MORE ON
		{
			for(int a = 0; a < enemies.size(); a++)
			{
				if(enemies.get(a).xPos > p.xPos - 16 && enemies.get(a).xPos < p.xPos + 16 && enemies.get(a).yPos > p.yPos - 16 && enemies.get(a).yPos < p.yPos + 16)
				{
					death();
				}
			}
			int checkY = ((p.yPos - 1) / 16); // INT -> ARRAY
			int checkX = (p.xPos) / 16; // INT -> ARRAY
			if( p.yPos + 16 >= map.length * 16 )
			{
				death();
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
			if(checkY + 1 < map.length)
			{
				/*
				 * Ceiling Collision Check
				 */
				if( map[(p.yPos + 1)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 't')//top left corner up direction
				{
					p.yVel = 0;
					p.yPos = (checkY + 1) * 16;
				}
				if(map[(p.yPos + 1)/16][((p.xPos + 15)/16)] != '.' && map[(p.yPos + 1)/16][((p.xPos + 15)/16)] != 't') //top right corner
				{
					p.yVel = 0;
					p.yPos = (checkY + 1) * 16;
				}
				/*
				 * Floor Collision Check
				 */
				if(map[(p.yPos +15)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos +15)/16][(p.xPos + 1)/16] != 't') //bottom left corner
				{
					p.yVel = 0;
					p.yPos = checkY * 16;
					p.jump = false;
					System.out.println("BOTTOM LEFT CORNER COLLISION");
				}
				if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] != '.' && map[(p.yPos + 15)/16][(p.xPos + 15)/16] != 't') //bottom right corner
				{
					p.yVel = 0;
					p.yPos = checkY * 16;
					System.out.println("BOTTOM RIGHT CORNER COLLISION");
					p.jump = false;
				}
				if(p.xVel > 0) //Check to see what direction the player is moving
				{
					//Check collision for moving right
					if(map[(p.yPos + 1)/16][checkX + 1] != '.' && map[(p.yPos + 1)/16][checkX + 1] != 't')
					{
						p.xVel = 0;
						p.xPos = checkX * 16;
					}
				}
				else if(p.xVel < 0)
				{
					if(map[(p.yPos + 5)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos + 5)/16][(p.xPos + 1)/16] != 't')
					{
						p.xVel = 0;
						p.xPos = (checkX + 1) * 16;
					}
				}
				/*
				 * Left Collision Check
				 */

			}
			else if( checkX + 1 < map[0].length )
			{
				if(map[checkY][checkX + 1] != '.') // top right corner right direction
				{
					p.xVel = 0;
					p.xPos = checkX * 16;
				}
				if(map[checkY][checkX] != '.') // top left corner left direction
				{
					p.xVel = 0;
					p.xPos = (checkX + 1) * 16;
				}
			}
			else
			{

			}

		}
		public void eDetector() //IF TIME REMEMBER TO USE MOD TO DETECT WHICH SIDE MORE ON
		{
			for(int index = 0; index < enemies.size(); index++)
			{
				int checkY = ((enemies.get(index).yPos - 1) / 16); // INT -> ARRAY
				int checkX = (enemies.get(index).xPos) / 16; // INT -> ARRAY
				if(checkX + 1 >= map[0].length)
				{
					enemies.get(index).xPos = checkX * 16;
					enemies.get(index).xVel = 0;
				}
				else if(enemies.get(index).xPos < 0 )
				{
					enemies.get(index).xPos = checkX * 16;
					enemies.get(index).xVel = 0;
				}
				if(checkY + 1 < map.length)
				{
					/*
					 * Ceiling Collision Check
					 */
					if( map[(enemies.get(index).yPos + 1)/16][(enemies.get(index).xPos + 1)/16] != '.')//top left corner up direction
					{
						enemies.get(index).yVel = 0;
						enemies.get(index).yPos = (checkY + 1) * 16;
					}
					if(map[(enemies.get(index).yPos + 1)/16][((enemies.get(index).xPos + 15)/16)] != '.') //top right corner
					{
						enemies.get(index).yVel = 0;
						enemies.get(index).yPos = (checkY + 1) * 16;
					}
					/*
					 * Floor Collision Check
					 */
					if(map[(enemies.get(index).yPos +15)/16][(enemies.get(index).xPos + 1)/16] != '.') //bottom left corner
					{
						enemies.get(index).yVel = 0;
						enemies.get(index).yPos = checkY * 16;
						//	enemies.get(index).jump = false;
						//	System.out.println("BOTTOM LEFT CORNER COLLISION");
					}
					if(map[(enemies.get(index).yPos + 15)/16][(enemies.get(index).xPos + 15)/16] != '.') //bottom right corner
					{
						enemies.get(index).yVel = 0;
						enemies.get(index).yPos = checkY * 16;
						//	System.out.println("BOTTOM RIGHT CORNER COLLISION");
						//	enemies.get(index).jump = false;
					}
					if(enemies.get(index).xVel > 0) //Check to see what direction the player is moving
					{
						//Check collision for moving right
						if(map[(enemies.get(index).yPos + 1)/16][checkX + 1] != '.')
						{
							enemies.get(index).xVel *= -1;
							enemies.get(index).xPos = checkX * 16;
						}
					}
					else if(enemies.get(index).xVel < 0)
					{
						if(map[(enemies.get(index).yPos + 5)/16][(enemies.get(index).xPos + 1)/16] != '.')
						{
							enemies.get(index).xVel *= -1;
							enemies.get(index).xPos = (checkX + 1) * 16;
						}
					}
					/*
					 * Left Collision Check
					 */

				}
				else if( checkX + 1 < map[0].length )
				{
					if(map[checkY][checkX + 1] != '.') // top right corner right direction
					{
						enemies.get(index).xVel *= -1;
						enemies.get(index).xPos = checkX * 16;
					}
					if(map[checkY][checkX] != '.') // top left corner left direction
					{
						enemies.get(index).xVel *= -1;
						enemies.get(index).xPos = (checkX + 1) * 16;
					}
				}
				else
				{

				}
			}
		} 
	}
	private char[][] map;
	private int width;
	private int height;
	Player p = new Player(8);
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
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

		p.xPos = 32;
		p.yPos = 100;

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
			while(scan.hasNextInt() == true)
			{
				Enemy temp = new Enemy(8);
				temp.xPos = scan.nextInt();
				temp.yPos = scan.nextInt();
				enemies.add(temp);
			}
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
		for(int index = 0; index < enemies.size(); index++)
		{
			enemies.get(index).paint(g);
		}
		for(int row = 0; row < map.length; row++) //Within the for loops are for drawing terrain.
		{
			for(int col = 0; col < map[0].length; col++)
			{
				switch(map[row][col])
				{
				case '.':
					break;
				case 't':
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