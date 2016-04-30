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
	
	public void setEvent(String flavorText, int pointsChange){
		event = new Event(flavorText, pointsChange);
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
