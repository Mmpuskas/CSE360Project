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
	boolean isGenericEvent; //maybe i should have used isInstanceOf
	boolean wasVisited = false;
	
	Tile(int X, int Y)
	{
		x = X;
		y = Y;
		
	}
	
	public void setGenericEvent(String flavorText, int pointsChange){
		event = new GenericEvent(flavorText, pointsChange);
		isGenericEvent = true;
	}
	
	public void setBossEvent(String flavorText, String passText, String failText, int passPoints, int failPoints){
		event = new BossEvent(flavorText, passText, failText, passPoints, failPoints);
		isGenericEvent = false;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public int getScoreChangeFromEvent(){
		return event.getPointsChange();
	}
	
	public String getEventFlavorText(){
		return event.getFlavorText();
	}
	
	//do not call this on generic events as they do not have aftermath (pass/fail) text. 
	public String getEventAftermathText(){
		if(isGenericEvent){
			return null;
		}
		else{
			return ((BossEvent) event).getAftermathText();
		}
		
	}
	
	public void setVisited()
	{
			wasVisited = true;
	}
	
	public void setNotVisited()
	{
			wasVisited = false;
	}
	
	public boolean getVisited()
	{
		return wasVisited;
	}
}
