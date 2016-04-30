package ui;

public class Event{
	//variables for handling events
	//change final variables as desired
	private String flavorText;
	private int pointsChange;
	
	public Event(String flavorText, int pointsChange){
		this.flavorText = flavorText;
		this.pointsChange = pointsChange;
	}
	
	//getter/setter methods
	public String getFlavorText() {
		return flavorText;
	}


	public void setFlavorText(String flavorText) {
		this.flavorText = flavorText;
	}


	public int getPointsChange() {
		return pointsChange;
	}


	public void setPointsChange(int pointsChange) {
		this.pointsChange = pointsChange;
	}

	
	
	
	
}
