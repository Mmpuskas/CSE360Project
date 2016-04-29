package ui;

public interface FrontEndCommunication { //for events and fights scenes?

	//Pass descriptions of events to frontend
	public String getFlavorText();
	public String getAftermath();
	public void setFlavorText(String flavorText);
}
