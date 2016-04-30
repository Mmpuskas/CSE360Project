package ui;

/** Represents a tile of the gameboard
 * 
 * @author Michael Puskas (mpuskas@asu.edu), Yifan Li
 *
 */
public class Tile 
{
	int x;
	int y;
	Event event;
	
	Tile(int X, int Y)
	{
		x = X;
		y = Y;
		
	}
	
	public void setGenericEvent(String flavorText, int pointsChange){
		event = new GenericEvent(flavorText, pointsChange);
	}
	
	public void setBossEvent(String flavorText, String passText, String failText, int passPoints, int failPoints){
		event = new BossEvent(flavorText, passText, failText, passPoints, failPoints);
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
}
