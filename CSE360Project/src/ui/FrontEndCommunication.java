package ui;

public interface FrontEndCommunication { //for events and fights scenes?

	//pass descriptions of events to frontend
	public String getFlavorText();
	public String getAftermath();
	public void setFlavorText(String flavorText);
}
