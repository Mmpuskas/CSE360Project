package ui;

import java.util.LinkedList;

/* UIControl
 * Date Created: April 10, 2016
 * Contributors: Michael Puskas (mpuskas@asu.edu)
 * Creates a linkedlist of position and text data.
 * Class UIVisual creates an instance of this class to receive the data.
 */
public class UIControl 
{
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
