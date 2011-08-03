import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import java.awt.event.*;
import java.awt.image.BufferedImage;

public class Trollformer extends JComponent implements KeyListener
{
	public int mapNum = 1;
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
			c.moveCam();
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
				if( map[(p.yPos + 1)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 'c' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 't')//top left corner up direction
				{
					if(map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'E')
					{
						p.xVel = 1;
					}
					else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'i' )
					{
						map[(p.yPos + 1)/16][(p.xPos + 1)/16] = '.';
					}
					else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'D' || map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'V' )
					{
						death();
					} 
					else
					{
						p.yVel = 0;
						p.yPos = (checkY + 1) * 16;
					} //Checks for upper left corner
				}
				if(map[(p.yPos + 1)/16][((p.xPos + 15)/16)] != '.' && map[(p.yPos + 1)/16][(p.xPos + 15)/16] != 'c' && map[(p.yPos + 1)/16][((p.xPos + 15)/16)] != 't') //top right corner
				{
					if(map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'E')
					{
						p.xVel = 1;
					}
					else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'i' )
					{
						map[(p.yPos + 1)/16][(p.xPos + 15)/16] = '.';
					}
					else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'D' || map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'V' )
					{
						death();
					}
					else
					{
						p.yVel = 0;
						p.yPos = (checkY + 1) * 16;
					} //Checks for upper right corner
				}
				/*
				 * Floor Collision Check
				 */
				if(map[(p.yPos + 15)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos + 15)/16][(p.xPos + 1)/16] != 'c' && map[(p.yPos +15)/16][(p.xPos + 1)/16] != 't') //bottom left corner
				{
					if(map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'E')
					{
						p.xVel = 1;
					}
					else if ( map[(p.yPos + 15)/16][(p.xPos + 1)/16] == 'i' )
					{
						map[(p.yPos + 15)/16][(p.xPos + 1)/16] = '.';
					}
					else if ( map[(p.yPos + 15)/16][(p.xPos + 1)/16] == 'D'  || map[(p.yPos + 15)/16][(p.xPos + 1)/16] == 'V' )
					{
						death();
					}
					else
					{
						p.yVel = 0;
						p.yPos = checkY * 16;
						p.jump = false;
					}
					//	System.out.println("BOTTOM LEFT CORNER COLLISION");
				}
				if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] != '.' && map[(p.yPos + 15)/16][(p.xPos + 15)/16] != 'c' && map[(p.yPos + 15)/16][(p.xPos + 15)/16] != 't') //bottom right corner
				{
					if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'E')
					{
						p.xVel = 1;
					}
					else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'i' )
					{
						map[(p.yPos + 15)/16][(p.xPos + 15)/16] = '.';
					}
					else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'D' || map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'V'  )
					{
						death();
					}
					else
					{
						p.yVel = 0;
						p.yPos = checkY * 16;

						System.out.println("BOTTOM RIGHT CORNER COLLISION");
						p.jump = false;
					}
				}
				if(p.xVel > 0) //Check to see what direction the player is moving
				{
					//Check collision for moving right
					if(map[(p.yPos + 1)/16][checkX + 1] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 'c' && map[(p.yPos + 1)/16][checkX + 1] != 't')
						
						if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'E')
						{
							System.out.println("Right");
							p.xVel = 1;
							mapNum += 1;
							mapLoader();
						}
						else
						{
							System.out.println("Right");
							p.xVel = 0;
							p.xPos = checkX * 16;
						}
				}
				else if(p.xVel < 0)
				{
					if(map[(p.yPos + 1)/16][(p.xPos + 1)/16] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 'c' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 't')
					{
						p.xVel = 0;
						p.xPos = (checkX + 1) * 16;
					}
				}
				else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'i' )
				{
					map[(p.yPos + 15)/16][(p.xPos + 15)/16] = '.';
				}
				else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'D' || map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'V'  )
				{
					death();
				}
				/*
				 * Left Collision Check
				 */

			}
			else if( checkX + 1 < map[0].length )
			{
				if(map[checkY][checkX + 1] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 'c') // top right corner right direction
				{
					p.xVel = 0;
					p.xPos = checkX * 16;
				}
				if(map[checkY][checkX] != '.' && map[(p.yPos + 1)/16][(p.xPos + 1)/16] != 'c') // top left corner left direction
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
	Camera c;
	Mario m = new Mario();
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	GameThread thread = new GameThread();
	JFrame f;
	BufferedImage offscreenImage;
	Graphics g;

	BufferedImage grass;
	BufferedImage dirt;
	BufferedImage wood;
	BufferedImage leaf;
	BufferedImage cake;
	BufferedImage crystal;

	Timer timer;
	Mario mario;
	BufferedImage sprite;
	public Trollformer(JFrame frame)
	{
		timer = new Timer(100, new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				m.updateFrame();
			}
		});
		f = frame;
		loadImages();
		this.map = map;
		f.addKeyListener(this);
		mapLoader();
		c = new Camera(p, map);
		thread.start();
		offscreenImage = new BufferedImage(400, 400, BufferedImage.TYPE_INT_ARGB);
		g = offscreenImage.getGraphics();

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
		mapLoader();
		p.xPos = 32;	
		p.yPos = 100;

	}
	public void loadImages()
	{
		try {
			grass = ImageIO.read(new File("grass.png"));
			dirt = ImageIO.read(new File("dirt.png"));
			crystal = ImageIO.read(new File("crystal.png"));
			wood = ImageIO.read(new File("wood.png"));
			leaf = ImageIO.read(new File("leaf.png"));
			cake = ImageIO.read(new File("cake.png"));
		} catch (IOException e) {
		}
	}
	public void mapLoader()
	{
		Scanner scan;
		this.mapNum = mapNum;
		try {
			scan = new Scanner((new File( "Map " + mapNum + ".txt" )));
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
	public void paint(Graphics offscreen)
	{
		
		timer.start();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 400, 400);
		if ( p.xVel == 0 )
		{
			m.loadImages();
			m.state = 1;
			
		}
		g.drawImage(m.getSprite(), p.xPos - c.xPos, p.yPos - c.yPos, null);
		for(int index = 0; index < enemies.size(); index++)
		{
			enemies.get(index).paint(g, c);
		}
		for(int row = c.yPos/16; row < map.length && row < (c.yPos + c.yRange)/16; row++) //Within the for loops are for drawing terrain.
		{
			for(int col = c.xPos/16; col < map[0].length && col < (c.xPos + c.xRange)/16; col++)
			{
				switch(map[row][col])
				{
				case '.':
					break;
				case 't':
					break;
				case 'g':
					g.drawImage(grass, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'd':
					g.drawImage(dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'c':
					g.setColor(new Color ( 205, 205, 205 ) );
					g.fillRect(col * 16 - c.xPos, row * 16 - c.yPos, 16, 16);
					break;
				case 'w':
					g.drawImage(wood, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'l':
					g.drawImage(leaf, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'b':
					g.setColor(Color.red);
					g.fillRect(col * 16 - c.xPos, row * 16 - c.yPos, 16, 16);
					break;
				case 'i':
					g.drawImage(dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'D':
					break;
				case 'V':
					g.drawImage(dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'E':
					g.drawImage(cake, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				}
			}
		}
		offscreen.drawImage(offscreenImage, 0, 0, null);
	}
	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
		{
			p.moveRight();
			m.facing = true;
			m.state = 2;
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			p.moveLeft();
			m.facing = false;
			m.state = 2;
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			p.Jump();
			m.state = 0;
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