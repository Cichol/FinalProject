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
			if(dead == false)
			{
				for(int a = 0; a < enemies.size(); a++)
				{
					if(enemies.get(a).xPos > p.xPos - 16 && enemies.get(a).xPos < p.xPos + 16 && enemies.get(a).yPos > p.yPos - 16 && enemies.get(a).yPos < p.yPos + 16)
					{
						death();
					}
				}
				if((p.yPos + 15)/16 >= map.length)
				{
					death();
				}
				int checkY = (p.yPos - 1) / 16; // INT -> ARRAY
				int checkX = (p.xPos) / 16; // INT -> ARRAY
				if(checkX + 1 >= map[0].length)
				{
					p.xPos = checkX * 16;
					p.xVel = 0;
				}
				else if(p.xPos < 0 )
					//map[(p.yPos + 5)/16][(p.xPos + 1)/16] = 'b';
				{
					p.xPos = checkX * 16;
					p.xVel = 0;
				}
				if(checkY + 1 < map.length && p.yPos > 0 )
				{
					/*
					 * Ceiling Collision Check
					 */
					if( map[(p.yPos + 1)/16][(p.xPos + 1)/16] != '.')//top left corner up direction
					{
						if(map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'E')
						{
							mapNum++;
							enemies.clear();
							mapLoader();

						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'i' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 1)/16] = '.';
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'D' || map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'V' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 1)/16] = 'b';
							death();
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'I' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 1)/16] = 'b';
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 1)/16] == 'L' )
						{
							death();
						}
						else
						{					
							p.yVel = 0;
							p.yPos = (checkY + 1) * 16;
						}
					}
					if(map[(p.yPos + 1)/16][(p.xPos + 15)/16] != '.') //top right corner
					{
						if(map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'E')
						{
							mapNum++;
							enemies.clear();
							mapLoader();
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'i' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 15)/16] = '.';
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'D' || map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'V' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 15)/16] = 'b';
							death();
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'I' )
						{
							map[(p.yPos + 1)/16][(p.xPos + 15)/16] = 'b';
						}
						else if ( map[(p.yPos + 1)/16][(p.xPos + 15)/16] == 'L' )
						{
							death();
						}
						else
						{
							p.yVel = 0;
							p.yPos = (checkY + 1) * 16;
						}
					}
					/*
					 * Floor Collision Check
					 */
					if(map[(p.yPos +15)/16][(p.xPos + 1)/16] != '.') //bottom left corner
					{
						if(map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'E')
						{
							mapNum++;
							enemies.clear();
							mapLoader();
						}
						else if ( map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'i' )
						{
							map[(p.yPos +15)/16][(p.xPos + 1)/16] = '.';
						}
						else if ( map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'D' || map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'V' )
						{
							map[(p.yPos +15)/16][(p.xPos + 1)/16] = 'b';
							death();
						}
						else if ( map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'I' )
						{
							map[(p.yPos +15)/16][(p.xPos + 1)/16] = 'b';
						}
						else if ( map[(p.yPos +15)/16][(p.xPos + 1)/16] == 'L' )
						{
							death();
						}
						else
						{
							p.yVel = 0;
							p.yPos = checkY * 16;
							p.jump = false;
							if (p.xVel == 0)
							{
								m.state = 1;
							}
							else
							{
								m.state = 2;
							}
						}
						//	System.out.println("BOTTOM LEFT CORNER COLLISION");
					}
					if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] != '.') //bottom right corner
					{
						if(map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'E')
						{
							mapNum++;
							enemies.clear();
							mapLoader();
						}
						else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'i' )
						{
							map[(p.yPos + 15)/16][(p.xPos + 15)/16] = '.';
						}
						else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'D' || map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'V' )
						{
							map[(p.yPos + 15)/16][(p.xPos + 15)/16] = 'b';
							death();
						}
						else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'I' )
						{
							map[(p.yPos + 15)/16][(p.xPos + 15)/16] = 'b';
						}
						else if ( map[(p.yPos + 15)/16][(p.xPos + 15)/16] == 'L' )
						{
							death();
						}
						else
						{
							p.yVel = 0;
							p.yPos = checkY * 16;
							//	System.out.println("BOTTOM RIGHT CORNER COLLISION");
							p.jump = false;
						}
					}
					if(p.xVel > 0) //Check to see what direction the player is moving
					{
						//Check collision for moving right
						if(map[(p.yPos + 1)/16][checkX + 1] != '.')
						{
							p.xPos = checkX * 16;
						}
						else if ( map[(p.yPos + 1)/16][checkX + 1] == 'i' )
						{
							map[(p.yPos + 1)/16][checkX + 1] = '.';
						}
						else if ( map[(p.yPos + 1)/16][checkX + 1] == 'D' || map[(p.yPos + 1)/16][checkX + 1] == 'V' )
						{
							map[(p.yPos + 1)/16][checkX + 1] = 'b';
							death();
						}
						else if ( map[(p.yPos + 1)/16][checkX + 1] == 'I' )
						{
							map[(p.yPos + 1)/16][checkX + 1] = 'b';
						}
						else if ( map[(p.yPos + 1)/16][checkX + 1] == 'L' )
						{
							death();
						}
					}
					else if(p.xVel < 0)
					{
						if(map[(p.yPos + 5)/16][(p.xPos + 1)/16] != '.')
						{
							p.xPos = (checkX + 1) * 16;
						}
						if ( map[(p.yPos + 5)/16][(p.xPos + 1)/16] == 'i' )
						{
							map[(p.yPos + 5)/16][(p.xPos + 1)/16] = '.';
						}
						else if ( map[(p.yPos + 5)/16][(p.xPos + 1)/16] == 'D' || map[(p.yPos + 5)/16][(p.xPos + 1)/16] == 'V' )
						{
							map[(p.yPos + 5)/16][(p.xPos + 1)/16] = 'b';
							death();
						}
						else if ( map[(p.yPos + 5)/16][(p.xPos + 1)/16] == 'I' )
						{
							map[(p.yPos + 5)/16][(p.xPos + 1)/16] = 'b';
						}
						else if ( map[(p.yPos + 5)/16][(p.xPos + 1)/16] == 'L' )
						{
							death();
						}
					}
					/*
					 * Left Collision Check
					 */

				}
				else if( checkX + 1 < map[0].length && p.yPos <= 0)
				{
					if(map[checkY][checkX + 1] != '.') // top right corner right direction
					{
						p.xPos = checkX * 16;
					}
					if(map[checkY][checkX] != '.') // top left corner left direction
					{
						p.xPos = (checkX + 1) * 16;
					}
				}
			}
			else if(p.yPos > 300) 
			{
				dead = false;
				enemies.clear();
				mapLoader();
			}
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
					enemies.get(index).xPos = checkX * 16;
				}
				if(map[checkY][checkX] != '.') // top left corner left direction
				{
					enemies.get(index).xPos = (checkX + 1) * 16;
				}
			}
			else
			{

			}
		}
	} 
	private char[][] map;
	private int width;
	private int height;

	boolean dead = false;

	Player p = new Player(8);
	Camera c;
	Mario m = new Mario();
	LinkedList<Enemy> enemies = new LinkedList<Enemy>();
	GameThread thread = new GameThread();
	JFrame f;
	BufferedImage offscreenImage;
	Graphics g;

	BufferedImage background;
	BufferedImage Grass;
	BufferedImage Dirt;
	BufferedImage Tree;
	BufferedImage Leaves;
	BufferedImage cake;
	BufferedImage Crystal;
	BufferedImage Brick;
	BufferedImage Lava;

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
		c = new Camera(p);
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
		m.state = 3;
		try {
			p.xVel = 0;
			p.yVel = 0;
			m.state = 3;
			thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		m.state = 3;
		p.yPos += 5;
		p.yVel = -20;
		dead = true;
	}
	public void loadImages()
	{
		try {
			background = ImageIO.read(new File("space-background.jpg"));
			Grass = ImageIO.read(new File("Grass.png"));
			Dirt = ImageIO.read(new File("Dirt.png"));
			Crystal = ImageIO.read(new File("Crystal.png"));
			Tree = ImageIO.read(new File("Tree.png"));
			Leaves = ImageIO.read(new File("Leaves.png"));
			cake = ImageIO.read(new File("cake.png"));
			Brick = ImageIO.read(new File("Brick.png"));
			Lava = ImageIO.read(new File("lava.png"));
		} catch (IOException e) {
		}
	}
	public void mapLoader()
	{
		Scanner scan;
		this.mapNum = mapNum;
		try
		{
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
		g.drawImage(background, 0, 0, null);
		timer.start();
		if ( p.xVel == 0 && p.jump == false)
		{
			m.loadImages();
			m.state = 1;

		}
		g.drawImage(m.getSprite(), p.xPos - c.xPos, p.yPos - c.yPos, null);
		for(int index = 0; index < enemies.size(); index++)
		{
			enemies.get(index).paint(g, c);
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
				case 'g':
					g.drawImage(Grass, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'd':
					g.drawImage(Dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'c':
					g.setColor(new Color ( 205, 205, 205 ) );
					g.fillRect(col * 16 - c.xPos, row * 16 - c.yPos, 16, 16);
					break;
				case 'w':
					g.drawImage(Tree, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'l':
					g.drawImage(Leaves, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'b':
					g.drawImage(Brick, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'i':
					g.drawImage(Dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'D':
					break;
				case 'B':
					g.setColor(Color.blue );
					g.fillRect(col * 16 - c.xPos, row * 16 - c.yPos, 16, 16);
					break;
				case 'V':
					g.drawImage(Dirt, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'E':
					g.drawImage(cake, col * 16 - c.xPos, row * 16 - c.yPos, null);
					break;
				case 'L':
					g.drawImage(Lava, col * 16 - c.xPos, row * 16 - c.yPos, null);
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
			if(dead == false)
			{
				p.moveRight();
				m.facing = true;
				if (p.jump = false)
				{
					m.state = 2;
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_LEFT)
		{
			if(dead == false)
			{
				p.moveLeft();
				m.facing = false;
				if (p.jump = false)
				{
					m.state = 2;
				}
			}
		}
		else if(e.getKeyCode() == KeyEvent.VK_SPACE)
		{
			if(dead == false)
			{
				p.Jump();
				m.state = 0;
			}
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