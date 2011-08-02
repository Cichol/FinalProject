import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;


public class Player
{
	int xPos;
	int yPos;
	int xVel;
	int yVel;
	int maxGrav;
	boolean jump = false;
	public Player ( int maxGrav )
	{
		this.maxGrav = 3;
	}
	public void movement (  )
	{
		yPos += yVel;
		xPos += xVel;
	}
	public void gravity (  )
	{
		if(yVel < maxGrav)
		{
			yVel += 1;
		}
	}
	public void paint(Graphics g, Camera c)
	{
		g.setColor(Color.BLACK);
		g.fillRect(xPos - c.xPos, yPos - c.yPos, 16, 16);
	}
	public void moveRight()
	{
		xVel = 2;
	}
	public void moveLeft()
	{
		xVel = -2;
	}
	public void Jump()
	{
		if ( jump == false )
		{
			yVel = -10;
			jump = true;
		}
	}
}
