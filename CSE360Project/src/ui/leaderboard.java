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
	
	/**
	 * getTotalScore()
	 * @return the total score kept throughout the game
	 */
	public int getTotalScore() 
	{
		return totalScore;
	}

	/**
	 * setTotalScore(int)
	 * @param totalScore
	 */
	public void setTotalScore(int totalScore) 
	{
		this.totalScore = totalScore;
	}

	/**
	 * getHighestScore()
	 * @return the highest score out of the top 10 saved
	 */
	public int getHighestScore() 
	{
		highestScore = topTen[0];
		return highestScore;
	}

	/**
	 * setHighestScore(int)
	 * @param highestScore
	 */
	public void setHighestScore(int highestScore) 
	{
		this.highestScore = highestScore;
	}
	
	/**
	 * getTopTen()
	 * @return the array of top 10 scores
	 */
	public int[] getTopTen()
	{
		return topTen;
	}
	
	//functions that actually do stuff
	
	/**
	 * addToScore(int)
	 * @param addIt
	 * 	adds passed int to the total score kept throughout current game
	 */
	private void addToScore(int addIt)
	{
		int tots = getTotalScore() + addIt;
		setTotalScore(tots);
	}
	
	/**
	 * setPointsArray()
	 * @throws Exception
	 * 	Will open text file that keeps the scores and save to an int array for easier access.
	 * 	Then use bubble sort on array
	 */
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
	
	/**
	 * bubbleSort(int[])
	 * @param tempArray
	 * @return sorted array in descending order
	 */
	private int[] bubbleSort(int[] tempArray)
	{
		//descending
		for(int i = 0; i < tempArray.length-1; i++)
		{
			for(int j = 1; j  <tempArray.length-i ; j++)
			{
	            if(tempArray[j-1] < tempArray[j])
	            {
	                int holder = tempArray[j - 1]; //hold one before
	                tempArray[j-1] = tempArray[j];
	                tempArray[j] = holder;
	            }
			}
		}
		
		return tempArray;
	}
	
	/**
	 * insertScore(int)
	 * @param score
	 * @throws IOException 
	 *   1. finds the spot where the new score should be inserted 
	 *   2. puts into top ten array
	 *   3. calls rewriteFIle to save
	 */
	private void insertScore(int score) throws IOException
	{
		int counter = 0;
		
		//find the spot where score is higher than one after and lower than one before
        while(counter < TOP_TEN && topTen[counter] > score)
        {
            counter++;
        }
        
        if(counter < topTen.length) //if it was found
        {
            for(int insertCount = topTen.length - 1; insertCount > counter; insertCount--) 
            {
                topTen[insertCount] = topTen[insertCount - 1];
            }
            topTen[counter] = score;//insert score
        }
		
        rewriteFile();
	}
	
	/**
	 * rewriteFile()
	 * @throws IOException
	 * 	converts the int array to a string one score at a time,
	 *  separated by a tab rewriting the text file
	 */
	private void rewriteFile() throws IOException
	{
		//**convert int array to string
		String allData = Integer.toString(topTen[0]) + "\n";
		
		for(int i = 1; i < TOP_TEN ; i++)
		{
			if(TOP_TEN - 1 == i)
			{
				allData = allData + Integer.toString(topTen[i]);
			}
			else
			{
				allData = allData + Integer.toString(topTen[i]) + "\n";	
			}
			
			
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
		 demo.insertScore(1);
		 demo.rewriteFile();
	 }
	 
	 
}