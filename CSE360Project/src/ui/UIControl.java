package ui;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

/* UIControl
 * Date Created: April 10, 2016
 * Contributors: Michael Puskas (mpuskas@asu.edu)
 * Creates a linkedlist of position and text data.
 * Class UIVisual creates an instance of this class to receive the data.
 */
public class UIControl 
{
	LinkedList<Tile> tileList = new LinkedList<>();
	ListIterator<Tile> tileIterator;
	
	
	public UIControl(){
		tileList.add(new Tile(262, 280));
		tileList.add(new Tile(262, 380));
		tileList.add(new Tile(262, 480));
		tileList.add(new Tile(362, 490));
		tileList.add(new Tile(462, 500));
		tileList.add(new Tile(562, 500));
		tileList.add(new Tile(562, 430));
		tileList.add(new Tile(662, 430));
		tileList.add(new Tile(742, 430));
		tileList.add(new Tile(752, 330));
		tileList.add(new Tile(762, 240));
		tileList.add(new Tile(662, 240));
		tileList.add(new Tile(662, 140));
		tileList.add(new Tile(682, 60));
		tileList.add(new Tile(782, 60));
		tileList.add(new Tile(882, 60));
		tileList.add(new Tile(972, 70));
		tileList.add(new Tile(972, 170));
		tileList.add(new Tile(962, 260));
		tileList.add(new Tile(962, 350));
		tileList.add(new Tile(1037, 350));
		tileList.add(new Tile(1037, 430));
		tileList.add(new Tile(1037, 520));
		tileList.add(new Tile(1127, 530));
		tileList.add(new Tile(1217, 540));
		tileIterator = tileList.listIterator();
		
	}
	
	public Tile moveToNextTile(){
		try{
			return tileIterator.next();
		}
		catch(NoSuchElementException ex){
			return tileList.getLast();
		}
		
		
	}
	
	public int roll(){
		return (int) (Math.random() * 3 + 1);
	}
}
