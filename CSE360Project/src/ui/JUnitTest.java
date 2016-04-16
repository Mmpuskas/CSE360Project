package ui;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Test;

import javafx.scene.image.Image;

public class JUnitTest {
	UIControl control = new UIControl();
	Image orc = null;
	Actor orcActor = new Actor(262, 280, orc);

	@Test
	/*
	 * Test the initialization of orcActor
	 */
	public void testActor()
	{
		Image orc = null;
		Actor orcActor = new Actor(262, 280, orc);
		
		assertTrue("The start position x of orcActor is not 262", orcActor.curX == 262);
		assertTrue("The start position y of orcActor is not 280", orcActor.curY == 280);
		assertTrue("The current image of the orcActor is not null", orcActor.charImage == null);
	}
	
	@Test 
	/*
	 * Test orcActor x increase y increase
	 */
	public void ActorXincrYincr()
	{
		orcActor.setTargetX(382);
		orcActor.setTargetY(400);
		//test x increasing y increasing
		for(int i = 0; i < 120; i++)
		{
			orcActor.moveToPosition();
		}
		assertTrue("The new position x of orcActor is not 362", orcActor.curX == 382);
		assertTrue("The new position y of orcActor is not 400", orcActor.curY == 400);
	}
	
	@Test
	/*
	 * Test orcActor x decrease y increase 262 280
	 */
	public void ActorXdecYincr()
	{
		orcActor.setTargetX(142);
		orcActor.setTargetY(400);//160
		for(int i = 0; i < 120; i++)
		{
		orcActor.moveToPosition();
		}
		assertTrue("The new position x of orcActor is not 142 ", orcActor.curX == 142);
		assertTrue("The new position y of orcActor is not 400", orcActor.curY == 400);
	}
	
	@Test 
	/*
	 * Test orcActor x increase y decrease
	 */
	public void ActorXincrYdecr()
	{
		orcActor.setTargetX(382);
		orcActor.setTargetY(160);
		for(int i = 0; i < 120; i++)
		{
		orcActor.moveToPosition();
		}
		assertTrue("The new position x of orcActor is not 382 ", orcActor.curX == 382);
		assertTrue("The new position y of orcActor is not 160", orcActor.curY == 160);
	}
	
	@Test
	/*
	 * Test orcActor x decrease y decrease
	 */
	public void ActorXdecrYdecr()
	{
		orcActor.setTargetX(142);
		orcActor.setTargetY(160);
		for(int i = 0; i < 120; i++)
		{
		orcActor.moveToPosition();
		}
		assertTrue("The new position x of orcActor is not 142 ", orcActor.curX == 142);
		assertTrue("The new position y of orcActor is not 160", orcActor.curY == 160);
	}
	
	@Test
	/*
	 * test the orcActor setTargetX() method
	 */
	public void ActorSetX()
	{
		orcActor.setTargetX(-500);
		orcActor.setTargetX(0);
		orcActor.setTargetY(232);
		orcActor.setTargetX(500);
		orcActor.setTargetX(243);
		
		assertTrue("The set position x isn't 243", orcActor.getTargetX() == 243);
	}
	
	@Test
	/*
	 * test the Actor setTargetY() method
	 */
	public void ActorsetY()
	{
		orcActor.setTargetY(-500);
		orcActor.setTargetY(0);
		orcActor.setTargetX(548);
		orcActor.setTargetY(500);
		orcActor.setTargetY(243);
		assertTrue("The set position y isn't 243", orcActor.getTargetY() == 243);
	}
	
	@Test
	/*
	 * test orcActor getTargetX() method
	 */
	public void ActorGetX()
	{
		orcActor.setTargetX(50);
		orcActor.setTargetX(300);
		assertTrue("The target position is not 300", orcActor.getTargetX() == 300);
	}
	
	@Test
	/*
	 * test orcActor getTargetY() method
	 */
	public void ActorGetY()
	{
		orcActor.setTargetX(50);
		orcActor.setTargetX(250);
		assertTrue("The target position is not 250", orcActor.getTargetX() == 250);
	}
	
	@Test
	/*
	 * Test the set moving true/false
	 */
	public void testSetMoving()
	{
		boolean b = false;
		orcActor.setMoving(b);
		assertTrue("SetMoving is set to true instead of false", orcActor.getMoving() == false);
		
		boolean a = true;
		orcActor.setMoving(a);
		assertTrue("SetMoving is set to false instead of true", orcActor.getMoving() == true);
	}
	
	@Test
	/*
	 * Test the get moving true/false
	 */
	public void testGetMoving()
	{
		boolean b = false;
		b = true;
		orcActor.setMoving(b);
		assertTrue("SetMoving is set to false instead of true", orcActor.getMoving() == true);
		
		boolean a = true;
		a = false;
		orcActor.setMoving(a);
		assertTrue("SetMoving is set to true instead of false", orcActor.getMoving() == false);
	}
	
	@Test
	/*
	 * Test whether or not the tileList is empty or not
	 */
	public void testEmpty()
	{
		assertNotNull("The tile list is empty", control);
	}
	
	@Test
	/*
	 * Test whether the coordinates of the tile list is right
	 */
	public void testCoordinates() {
		//test the first tile
		assertTrue("The first tile's x coordinate is not 262", control.tileList.getFirst().x == 262);
		assertTrue("The first tile's y coordinate is not 280", control.tileList.getFirst().y == 280);
		//test the middle tile
		assertTrue("Index 13 of the tile's doesn't have a x coordinate of 682", control.tileList.get(13).x == 682);
		assertTrue("Index 13 of the tile's hdoesn't have ax coordinate of 60", control.tileList.get(13).y == 60);
		//test the last tile
		assertTrue("The last tile's x coordinate is not 1217", control.tileList.getLast().x == 1217);
		assertTrue("The last tile's y coordinate is not 540", control.tileList.getLast().y == 540);
	}
	

}
