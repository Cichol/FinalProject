import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Timer;


public class Mario {

	BufferedImage walkLeft1;
	BufferedImage walkLeft2;
	BufferedImage walkLeft3;

	BufferedImage walkRight1;
	BufferedImage walkRight2;
	BufferedImage walkRight3;

	BufferedImage jumpRight;
	BufferedImage jumpLeft;

	BufferedImage standRight;
	BufferedImage standLeft;

	BufferedImage dying;

	int frameCount = 0;
	public boolean facing = true;
	public int state = 1;

	public Mario()
	{
		frameCount = 0;
	} 
	public void loadImages()
	{
		try {
			dying = ImageIO.read(new File("dying.png"));

			walkLeft1 = ImageIO.read(new File("walkLeft1.png"));
			walkLeft2 = ImageIO.read(new File("walkLeft2.png"));
			walkLeft3 = ImageIO.read(new File("walkLeft3.png"));

			walkRight1 = ImageIO.read(new File("walkRight1.png"));
			walkRight2 = ImageIO.read(new File("walkRight2.png"));
			walkRight3 = ImageIO.read(new File("walkRight3.png"));

			standLeft = ImageIO.read(new File("standLeft.png"));
			standRight = ImageIO.read(new File("standRight.png"));

			jumpLeft = ImageIO.read(new File("jumpLeft.png"));
			jumpRight = ImageIO.read(new File("jumpRight.png"));

		} catch (IOException e) {
			System.out.println("File load failed");
		}

	}
	public BufferedImage getSprite()
	{
		switch(state)
		{
		case 0: //jump
			if(facing == true)//true = right
				return jumpRight;
			else
				return jumpLeft;
		case 1: //stand
			if(facing == true)
				return standRight;
			else
				return standLeft;
		case 2: //Horizontal movement
			if(facing == true)
			{
				if(frameCount == 0)
					return walkRight1;
				else if(frameCount == 1)
					return walkRight2;
				else if(frameCount == 2)
				{
					frameCount = 0;
					return walkRight3;

				}

				return walkRight1;
			}
			else
			{
				if(frameCount == 0)
					return walkLeft1;
				else if(frameCount == 1)
					return walkLeft2;
				else if(frameCount == 2)
					frameCount = 0;
				return walkLeft3;
			}
		case 3:
		{
			System.out.println("Dying");
			return dying;
		}
		}
		return jumpRight;
	}
	/*public BufferedImage updateWalkLeft()
	{
		switch(frameCount)
		{
		case '0':
			frameCount++;
			return walkLeft1;
		case '1':
			frameCount++;
			return walkLeft2;
		case '2':
			frameCount = 0;
			return walkLeft3;

		}
		return null;
	}
	public BufferedImage updateWalkRight()
	{	
		switch(frameCount)
		{
		case '0':
			frameCount++;
			return walkLeft1;
		case '1':
			frameCount++;
			return walkLeft2;
		case '2':
			frameCount = 0;
			return walkLeft3;

		}
		return null;
	}*/
	public void updateFrame()
	{
		frameCount++;
		if(frameCount > 2)
		{
			frameCount = 0;
		}
	}
}
