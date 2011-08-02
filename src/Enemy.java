import java.awt.Color;
import java.awt.Graphics;


public class Enemy 
{
	
	int xPos;
	int yPos;
	int xVel;
	int yVel;
	int maxGrav;
	public Enemy ( int maxGrav )
	{
		this.maxGrav = maxGrav;
		yPos = 5;
		xVel = 1;
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
	public void paint(Graphics g, Camera c)
	{
		g.setColor(Color.GRAY);
		g.fillRect(xPos - c.xPos, yPos - c.yPos, 16, 16);
	}
}
