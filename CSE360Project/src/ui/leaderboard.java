package ui;
import java.io.*;
import java.util.Arrays;


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
		highestScore = topTen[0];
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
			 
		 bubbleSort(topTen);
		 
		 /*for(int i = 0; i < TOP_TEN; i++)
		 {
			 System.out.println(topTen[i]);
		 }*/
	}
	
	private int[] bubbleSort(int[] tempArray)
	{
		
		//descending
		for(int i = 0; i < tempArray.length-1; i++)
		{
			for(int j = 1; j  <tempArray.length-i ; j++)
			{
	            if(tempArray[j-1] < tempArray[j])
	            {
	                int holder = tempArray[j - 1];
	                tempArray[j-1] = tempArray[j];
	                tempArray[j] = holder;
	            }
			}
		}
		
		return tempArray;
	}
	
	private void insertScore(int score)
	{
		int counter = 0;
		
        while(counter < TOP_TEN && topTen[counter] > score)
        {
            counter++;
        }
        
        if(counter < topTen.length)
        {
            for(int insertCount = topTen.length-1; insertCount > counter; insertCount--) //insert score
            {
                topTen[insertCount] = topTen[insertCount - 1];
            }
            topTen[counter] = score;
        }
		
	}
	
	private void rewriteFile() throws IOException
	{
		//**convert int array to string
		String allData = Integer.toString(topTen[0]) + "\n"; // = Arrays.toString(topTen);
		
		for(int i = 1; i < TOP_TEN; i++)
		{
			allData = allData + Integer.toString(topTen[i]) + "\n";
		}
		
		//System.out.println(allData);
		
		//**Rewrite file
		String historyPath = new File("").getAbsolutePath() + "/src/text/leaderboardRepository.txt";
		
		File tempFile = new File(historyPath);
		FileOutputStream fStream = new FileOutputStream(tempFile, false);	                                                                
		byte[] myBytes = allData.getBytes(); 
		fStream.write(myBytes);
		fStream.close();
	}
	
	 public static void main(String[] args) throws Exception
	 {
		 leaderboard demo = new leaderboard();
		 demo.setPointsArray();
		 demo.insertScore(800);
		 demo.rewriteFile();
	 }
	 
}