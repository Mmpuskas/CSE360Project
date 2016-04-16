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
}
