package ui;
/** Inherited by boss event and Generic event.
 * 
 * @author Yifan Li
 *
 */

abstract class Event{
	private String flavorText;
	private int pointsChange;
	
	//getter/setter methods
	public String getFlavorText() {
		return flavorText;
	}
	
	public int getPointsChange(){
		return pointsChange;
	}
	
}
