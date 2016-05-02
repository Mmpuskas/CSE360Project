package ui;

import javafx.scene.image.Image;

/** Represents an actor. For the purposes of "Journey to Chaos End", represents the main character.
 * 
 * @author Michael Puskas (mpuskas@asu.edu), Yifan Li
 *
 */
public class Actor 
{
	int curX;
	int curY;
	Image charImage;
	private int targetX;
	private int targetY;
	private boolean isMoving;
	
	Actor(int X, int Y, Image CHARIMAGE)
	{
		curX = X;
		curY = Y;
		charImage = CHARIMAGE;
		isMoving = false;
	}
	
	/** Moves the actor up to two pixels in the direction of the target x,y coordinate.
	 * 
	 */
	public void moveToPosition()
	{
		if(curX < targetX)
			curX++;
		else if (curX > targetX)
			curX--;
		if(curY < targetY)
			curY++;
		else if (curY > targetY)
			curY--;
	}
	
	public void setTargetX(int x)
	{
		targetX = x;
	}

	public void setTargetY(int y)
	{
		targetY = y;
	}
	
	public void setCurX(int x)
	{
		curX = x;
	}

	public void setCurY(int y)
	{
		curY = y;
	}

	public void setMoving(boolean b)
	{
		isMoving = b;
	}

	public int getTargetX()
	{
		return targetX;
	}

	public int getTargetY()
	{
		return targetY;
	}
	
	public boolean getMoving()
	{
		return isMoving;
	}
}
