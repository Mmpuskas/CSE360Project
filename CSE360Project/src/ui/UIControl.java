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
/** Control class for "Journey to Chaos End". Comprises most of the back-end systems.
 * 
 * @author Michael Puskas (mpuskas@asu.edu), Yifan Li
 *
 */
public class UIControl 
{
	LinkedList<Tile> tileList = new LinkedList<>();
	ListIterator<Tile> tileIterator;
	public void initTilePositions(int gameWidth, int gameHeight)
	{
		tileList.add(new Tile((int) (0.170572917 * gameWidth), (int) (0.278606965 * gameHeight)));
		tileList.add(new Tile((int) (0.170572917 * gameWidth), (int) (0.378109453 * gameHeight)));
		tileList.add(new Tile((int) (0.170572917 * gameWidth), (int) (0.47761194 * gameHeight)));
		tileList.add(new Tile((int) (0.235677083 * gameWidth), (int) (0.487562189 * gameHeight)));
		tileList.add(new Tile((int) (0.30078125 * gameWidth), (int) (0.497512438 * gameHeight)));
		tileList.add(new Tile((int) (0.365885417 * gameWidth), (int) (0.497512438 * gameHeight)));
		tileList.add(new Tile((int) (0.365885417 * gameWidth), (int) (0.427860697 * gameHeight)));
		tileList.add(new Tile((int) (0.430989583 * gameWidth), (int) (0.427860697 * gameHeight)));
		tileList.add(new Tile((int) (0.483072917 * gameWidth), (int) (0.427860697 * gameHeight)));
		tileList.add(new Tile((int) (0.489583333 * gameWidth), (int) (0.328358209 * gameHeight)));
		tileList.add(new Tile((int) (0.49609375 * gameWidth), (int) (0.23880597 * gameHeight)));
		tileList.add(new Tile((int) (0.430989583 * gameWidth), (int) (0.23880597 * gameHeight)));
		tileList.add(new Tile((int) (0.430989583 * gameWidth), (int) (0.139303483 * gameHeight)));
		tileList.add(new Tile((int) (0.444010417 * gameWidth), (int) (0.059701493 * gameHeight)));
		tileList.add(new Tile((int) (0.509114583 * gameWidth), (int) (0.059701493 * gameHeight)));
		tileList.add(new Tile((int) (0.57421875 * gameWidth), (int) (0.059701493 * gameHeight)));
		tileList.add(new Tile((int) (0.6328125 * gameWidth), (int) (0.069651741 * gameHeight)));
		tileList.add(new Tile((int) (0.6328125 * gameWidth), (int) (0.169154229 * gameHeight)));
		tileList.add(new Tile((int) (0.626302083 * gameWidth), (int) (0.258706468 * gameHeight)));
		tileList.add(new Tile((int) (0.626302083 * gameWidth), (int) (0.348258706 * gameHeight)));
		tileList.add(new Tile((int) (0.675130208 * gameWidth), (int) (0.348258706 * gameHeight)));
		tileList.add(new Tile((int) (0.675130208 * gameWidth), (int) (0.427860697 * gameHeight)));
		tileList.add(new Tile((int) (0.675130208 * gameWidth), (int) (0.517412935 * gameHeight)));
		tileList.add(new Tile((int) (0.733723958 * gameWidth), (int) (0.527363184 * gameHeight)));
		tileList.add(new Tile((int) (0.792317708 * gameWidth), (int) (0.537313433 * gameHeight)));
		tileIterator = tileList.listIterator();
	}
}
