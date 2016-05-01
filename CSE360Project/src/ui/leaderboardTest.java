package ui;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

public class leaderboardTest {

	@Test
	public void testLeaderboard() throws IOException{
		leaderboard lb = new leaderboard();
		assertNotNull(lb);
		int[] test = lb.getTopTen();
		String result = Arrays.toString(test);
		assertEquals("[10, 9, 8, 7, 6, 5, 4, 3, 2, 1]", result);
		lb.setScoreTableForUnitTest();
	}

	@Test
	public void testSetNewScore() throws IOException {
		leaderboard lb = new leaderboard();
		lb.setNewScore(11);
		lb.setNewScore(15);
		lb.setNewScore(20);
		int[] test = lb.getTopTen();
		String result = Arrays.toString(test);
		assertEquals("[20, 15, 11, 10, 9, 8, 7, 6, 5, 4]", result);
		lb.setScoreTableForUnitTest();
	}

	

}
