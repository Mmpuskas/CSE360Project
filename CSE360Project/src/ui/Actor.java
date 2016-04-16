package ui;

import javafx.scene.image.Image;

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
	
	public int getTargetX()
	{
		return targetX;
	}

	public int getTargetY()
	{
		return targetY;
	}
	
	public void setMoving(boolean b)
	{
		isMoving = b;
	}
	
	public boolean getMoving()
	{
		return isMoving;
	}
}
