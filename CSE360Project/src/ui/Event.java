package ui;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Event implements FrontEndCommunication{
	//variables for handling events
	//change final variables as desired
	private String eventFlavorText;
	private String passEventText;
	private String failEventText;
	private int pointsForSpecEvent;
	private boolean passedEvent = false;
	
	@Override
	public String getAftermath() {
		if(passedEvent){
			return passEventText;
		}
		else{
			return failEventText;
		}
		
	}
	
	@Override
	public void setFlavorText(String flavorText){
		eventFlavorText = flavorText;
	}

	@Override
	public String getFlavorText(){
		return eventFlavorText;
	}
	
	@Override
	public boolean equals(Object otherEvent){
		if(otherEvent == this){
			return true;
		}
		if(!(otherEvent instanceof Event)){ 
			return false;
		}
		
		Event test = (Event)otherEvent;
		
		if(eventFlavorText.equals(test.getEventFlavorText())
			&& passEventText.equals(test.getPassEventText())
			&& failEventText.equals(test.getFailEventText())
			){
			
			return true;
		}
		else{
			return false;
		}
	}
	
	public void forceEqualsFail(){
		eventFlavorText = "asdafasdf";
	}
	
	//getter methods
	public int getPointsSpecEvent(){
		return pointsForSpecEvent;
	}
	
	public String getEventFlavorText() {
		return eventFlavorText;
	}

	public void setEventFlavorText(String eventFlavorText) {
		this.eventFlavorText = eventFlavorText;
	}

	public String getPassEventText() {
		return passEventText;
	}

	public void setPassEventText(String passEventText) {
		this.passEventText = passEventText;
	}

	public String getFailEventText() {
		return failEventText;
	}

	public void setFailEventText(String failEventText) {
		this.failEventText = failEventText;
	}
}
