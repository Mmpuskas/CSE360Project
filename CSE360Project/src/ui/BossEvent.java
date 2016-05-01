package ui;
/** Represents a boss event
 * 
 * @author Yifan Li
 *
 */

import java.util.Random;

public class BossEvent extends Event{
	private String flavorText;
	private String passText;
	private String failText;
	private int passPoints;
	private int failPoints;
	private boolean passedEvent;
	
	private final static int passingRoll = 65; 
	
	public BossEvent(String flavorText, String passText, String failText, int passPoints, int failPoints) {
		this.flavorText = flavorText;
		this.passText = passText;
		this.failText = failText;
		this.passPoints = passPoints;
		this.failPoints = failPoints;
		passedEvent = false;
		roll();
		
	}
	
	private void roll(){
		Random RNG = new Random();
		int roll = RNG.nextInt(101) + 1;
		if(roll <= passingRoll){
			passedEvent = true;
		}
		
	}
	
	@Override
	public String getFlavorText(){
		return flavorText;
	}
	
	public String getAftermathText(){
		if(passedEvent){
			return passText;
		}
		else{
			return failText;
		}
	}
	
	@Override
	public int getPointsChange(){
		if(passedEvent){
			return passPoints;
		}
		else{
			return failPoints;
		}
	}

}
