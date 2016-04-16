package ui;

import javafx.scene.image.Image;

public class Actor 
{
	int curX;
	int curY;
	Image charImage;
	
	Actor(int X, int Y, Image CHARIMAGE)
	{
		curX = X;
		curY = Y;
		charImage = CHARIMAGE;
	}
	
	public void moveToPosition(int targetX, int targetY){
		if(curX < targetX)
			curX++;
		else if (curX > targetX)
			curX--;
		if(curY < targetY)
			curY++;
		else if (curY > targetY)
			curY--;
		
		
	}
}
