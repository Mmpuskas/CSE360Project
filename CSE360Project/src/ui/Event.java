package ui;
import java.util.Random;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Event implements FrontEndCommunication{
	//variables for handling events
	//change final variables as desired
	private final static int EVENT_POOL_COUNT = 10; //how many events we have written, used to select events
	private final static int MAX_ALLOWED_INSTANCES = 5; //how many events we will actually have in instance of game, don't need event for every tile
	private static int eventObjectCount = 0; //used to limit instance count and also assign the selected event
	private static int[] nonRepeatingNums = new int[EVENT_POOL_COUNT]; //array of numbers 1 to EVENT_POOL_COUNT used to make sure events don't repeat by shuffling the list then selecting the first MAX_ALLOWED_INSTANCES
	private static int[] selectedEventLineNumbers = new int[MAX_ALLOWED_INSTANCES]; //the first MAX_ALLOWED_INSTANCES elements in nonRepeatingRNG, used to determine what lines in event files text to use.
	private String eventFlavorText;
	private String passEventText;
	private String failEventText;
	private int pointsForPass;
	private int chanceToPass;
	private boolean passedEvent = false;
	
	//for rolling
	private Random rng;
	public int rollResult;
	private boolean haveRerolled = false;
	
	private Event() throws IOException{ //private constructor limits creation of event objects to MAX_ALLOWED_EVENTS
		for(int i = 0; i < EVENT_POOL_COUNT; i++){
			nonRepeatingNums[i] = i+1; // initializes array to 0 1 2 3 4...
		}
		System.arraycopy(nonRepeatingNums, 0, selectedEventLineNumbers, 0, MAX_ALLOWED_INSTANCES);
		String event = Files.readAllLines(Paths.get("/assets/EventsFile.txt")).get(selectedEventLineNumbers[eventObjectCount]); 
		String[] eventDelimited = event.split(";");
		eventFlavorText = eventDelimited[0].trim();
		passEventText = eventDelimited[1].trim();
		failEventText = eventDelimited[2].trim();
		chanceToPass = Integer.parseInt(eventDelimited[3].trim());
		pointsForPass = Integer.parseInt(eventDelimited[4].trim());
		
		this.rng = new Random();
		eventObjectCount++;
	}
	
	public static Event getEventInstance() throws IOException{
		if(eventObjectCount < MAX_ALLOWED_INSTANCES){ //call this method to get instance of Event, will return null if reached instance limit
			//incrementing eventObjectCount handled in constructor. 
			return new Event();
		}
		else{
			return null;
		}
		
	}
	
	@Override
	public void roll() {
		rollResult = rng.nextInt(100) + 1;
		if(rollResult <= chanceToPass){
			passedEvent = true;
		}
		else{
			passedEvent = false;
		}
	}
	
	@Override
	public String getAftermath() {
		if(passedEvent){
			return passEventText;
		}
		else{
			return failEventText;
		}
		
	}
	
	public int getPointsForPass(){
		return pointsForPass;
	}
	
	public int getChanceToPass(){
		return chanceToPass;
	}
	
	public static int getEVENT_POOL_COUNT(){
		return EVENT_POOL_COUNT;
	}
	
	public static int getMAX_ALLOWED_INSTANCES(){
		return MAX_ALLOWED_INSTANCES;
	}
	
	@Override
	public boolean getRollResults() {  //don't think we need this
		return passedEvent;
	}

	@Override
	public boolean reRoll() {
		if(haveRerolled == false){
			roll();
			return true;
		}
		else{
			return false;
		}
		
	}

	@Override
	public String getFlavorText() {
		return eventFlavorText;
	}

	
	
	
	
}
