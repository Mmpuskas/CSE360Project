package ui;

public interface FrontEndCommunication { //for events and fights scenes?
	//RNG
	void roll();
	public boolean getRollResults();
	boolean reRoll();

	//pass descriptions of events to frontend
	public String getFlavorText();
	public String getAftermath();
	
}
