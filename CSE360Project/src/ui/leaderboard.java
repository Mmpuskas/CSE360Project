package ui;
import java.io.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/*
 * Author: Mercedes
 * Purpose: Keep track of the score of the user. 
 * 	Also, utilize ScoreHistory.txt to save all scores and retrieve the highest top scores.
 * 
 */

public class leaderboard
{
	private static final int TOP_TEN = 10;
	private int totalScore;
	private int highestScore;
	private int topTen[] = new int[TOP_TEN];
	
	
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
	
	private void setPointsArray() throws Exception
	{
		//read all points from file and save to array to access easily
		 String	thisLine = null;
		 String historyPath = new File("").getAbsolutePath() + "/src/text/leaderboardRepository.txt";
		 int counter = 0;
		 int tempHolder;
		 
		 try
		 {
			 BufferedReader br = new BufferedReader(new FileReader(historyPath));
			 
			 while(((thisLine = br.readLine()) != null) && (counter < TOP_TEN))
			 {
				 tempHolder = Integer.parseInt(thisLine);
				 topTen[counter] = tempHolder;
				// System.out.println(topTen[counter]);
				 counter++;
			 }	 
		 }
		 catch(Exception e)
		 {
			 e.printStackTrace();
		 }	 
			 
	}
	
	private void checkHighest(int tempScore)
	{
		boolean isHigher = false;
		
		for(int counter = 0; counter < TOP_TEN; counter++)
		{
			//if(tempScore
		}
		 
		
	}
	
	private void saveScore()
	{
	
		//move into text file
	}
	
	
	 public static void main(String[] args) throws Exception
	 {
		 leaderboard demo = new leaderboard();
		 demo.setPointsArray();
		 
	 }
	 
}