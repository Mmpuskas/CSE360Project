package ui;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

public class JUnitTest {
	UIControl control = new UIControl();
	
	@Test
	/*
	 * Test whether or not the tileList is empty or not
	 */
	public void testEmpty()
	{
		control.initVals();
		assertNotNull("The tile list is not empty", control);
	}
	
	@Test
	/*
	 * Test whether the coordinates of the tile list is right
	 */
	public void testCoordinates() {
		control.initVals();
		//test the first tile
		assertTrue("The first tile's x coordinate is 262", control.tileList.getFirst().x == 262);
		assertTrue("The first tile's y coordinate is 280", control.tileList.getFirst().y == 280);
		//test the middle tile
		assertTrue("Index 13 of the tile's has an x coordinate of 682", control.tileList.get(13).x == 682);
		assertTrue("Index 13 of the tile's has an x coordinate of 60", control.tileList.get(13).y == 60);
		//test the last tile
		assertTrue("The last tile's x coordinate is 1217", control.tileList.getLast().x == 1217);
		assertTrue("The last tile's y coordinate is 540", control.tileList.getLast().y == 540);
	}
	

}
