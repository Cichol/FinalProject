import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.JComponent;
import javax.swing.JFrame;


public class Trollformer extends JComponent
{
	private char[][] map;
	private int width;
	private int height;
	public Trollformer()
	{
		this.map = map;
		mapLoader();

	}
	public void mapLoader()
	{
		Scanner scan;
		try {
			scan = new Scanner((new File("map.txt")));
			width = scan.nextInt();
			height = scan.nextInt();
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
		for(int row = 0; row < map.length; row++)
		{
			for(int col = 0; col < map[0].length; col++)
			{
				switch(map[row][col])
				{
					case 'a':
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
}
