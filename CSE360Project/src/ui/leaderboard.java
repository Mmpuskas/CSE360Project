package ui;
/*
 * Author: Mercedes
 * Purpose: Keep track of the score of the user. 
 * 	Also, utilize ScoreHistory.txt to save all scores and retrieve the highest top scores.
 * 
 */

public class leaderboard
{
	private int totalScore;
	private int highestScore;
	
	//default construct
	public leaderboard()
	{
		totalScore = 0;
		highestScore = 0;
		
	}

	//getters & setters
	public int getTotalScore() 
	{
		return totalScore;
	}

	public void setTotalScore(int totalScore) 
	{
		this.totalScore = totalScore;
	}

	public int getHighestScore() 
	{
		return highestScore;
	}

	public void setHighestScore(int highestScore) 
	{
		this.highestScore = highestScore;
	}
	
	//functions that actually do stuff
	
	private void addToScore(int addIt)
	{
		int tots = getTotalScore() + addIt;
		setTotalScore(tots);
	}
	
	private void checkHighest(int tempScore)
	{
		
	}
	
	private void saveScore(int tempScore)
	{
	
		//move into text file
	}
	
	
	
	
}
