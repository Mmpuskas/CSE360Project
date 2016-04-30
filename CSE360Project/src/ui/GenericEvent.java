package ui;

public class GenericEvent extends Event{
	private String flavorText;
	private int pointsChange;
	
	public GenericEvent(String flavorText, int pointsChange){
		this.flavorText = flavorText;
		this.pointsChange = pointsChange;
	}

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
