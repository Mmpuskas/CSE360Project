package ui;

import java.util.LinkedList;

public class UIControl 
{
	int xValues[] = new int[25];
	int yValues[] = new int[25];
	
	LinkedList<Tile> tileList = new LinkedList<>();
	
	void initVals()
	{
		Tile tile;
		for(int i = 0; i < 25; i++)
		{
			tile = new Tile((i*55)+75, 350);
			tileList.add(i, tile);
		}
	}
}
