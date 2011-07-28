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
		this.maxGrav = maxGrav;
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
			yVel += 4;
		}
	}
	public void paint(Graphics g)
	{
		g.fillRect(xPos, yPos, 16, 16);
	}
	public void moveRight()
	{
		xVel = 10;
	}
	public void moveLeft()
	{
		xVel = -10;
	}
	public void Jump()
	{
		if ( jump == false )
		{
			yVel = -20;
			jump = true;
		}
	}
}
