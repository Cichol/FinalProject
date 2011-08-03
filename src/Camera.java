import java.util.Map;


public class Camera 
{
	Player player;
	int xPos;
	int yPos;
	int xRange;
	int yRange;
	char[][] map;
	public Camera(Player p, char[][] map)
	{
		player = p;
		xRange = 400;
		yRange = 400;
		xPos = 0;
		yPos = 0;
		this.map = map;
	}
	public void moveCam()
	{
		//CEILING
		if(player.yPos < yPos + 100 )
		{
			yPos = player.yPos - 100;
			if(yPos < 0)
			{
				yPos = 0;
			}
		}
		//Floor
		if(player.yPos > yPos + (yRange - 100))
		{
			yPos = player.yPos + 100;
		}
		//Right
		if(player.xPos > xPos + (xRange - 200))
		{
			xPos = player.xPos - (xRange - 200);
			if(xPos + xRange > map[0].length* 16)
			{
				xPos = (map[0].length * 16) - xRange;
			}
		}
		//Left
		if(player.xPos < xPos + 200)
		{
			xPos = player.xPos - 200;
			if(xPos < 0)
			{
				xPos = 0;
			}
		}
	}
}
