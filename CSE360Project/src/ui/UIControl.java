package ui;

import java.util.LinkedList;

public class UIControl 
{
	int xValues[] = new int[25];
	int yValues[] = new int[25];
	
	LinkedList<Tile> tileList = new LinkedList<>();
	
	void initVals()
	{
		Tile tile = new Tile(0,0);
		for(int i = 0; i < 25; i++)
		{
			tile.x = i*25;
			tile.y = (i % 5) * 25;
			tileList.add(tile);
		}
	}
}
