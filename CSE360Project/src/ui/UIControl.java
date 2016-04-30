package ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ListIterator;

/* UIControl
 * Date Created: April 10, 2016
 * Contributors: Michael Puskas (mpuskas@asu.edu)
 * Creates a linkedlist of position and text data.
 * Class UIVisual creates an instance of this class to receive the data.
 */
/**
 * Control class for "Journey to Chaos End". Comprises most of the back-end
 * systems.
 * 
 * @author Michael Puskas (mpuskas@asu.edu), Yifan Li
 *
 */
public class UIControl {
	private int currentScore = 0;

	LinkedList<Tile> tileList = new LinkedList<>();
	ListIterator<Tile> tileIterator;

	public void initTilePositions(int gameWidth, int gameHeight) {
		tileList.add(new Tile((int) (0.170572917 * gameWidth), (int) (0.178606965 * gameHeight))); 
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

	/**
	 * Fills tiles with event classes
	 * 
	 * @throws IOException
	 * 
	 */
	public void initEvents() {
		String line = null;
		String path = new File("").getAbsolutePath() + "/src/assets/EventText.txt";
		String fullText = new String();

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while ((line = br.readLine()) != null) {
				fullText += line + "\n";
			}
		} catch (IOException ex) {
			System.out.println("ERROR: Event file not found.");
		}

		// Set intro text into first tile position
		String introText = fullText.substring(fullText.indexOf('$') + 2, fullText.indexOf('@'));
		tileList.get(0).setGenericEvent(introText, 0);
		String intermediateText;

		// Remove intro text from fullText

		for (int i = 1; i <= 25; i++) {
			fullText = fullText.substring(fullText.indexOf(')') + 2);
			intermediateText = fullText.substring(0, fullText.indexOf(')') - 2);

			if (i % 5 == 0) {
				String[] eventDelimited = intermediateText.split("@");
				String flavorText = eventDelimited[0].trim();
				String passText = eventDelimited[1].trim();
				String failText = eventDelimited[3].trim();
				int passPoints = Integer.parseInt(eventDelimited[2].trim());
				int failPoints = Integer.parseInt(eventDelimited[4].trim());
				tileList.get(i).setBossEvent(flavorText, passText, failText, passPoints, failPoints);
			} else {
				String[] eventDelimited = intermediateText.split("@");
				String flavorText = eventDelimited[0].trim();
				int pointsChange = Integer.parseInt(eventDelimited[1].trim());
				tileList.get(i).setGenericEvent(flavorText, pointsChange);
			}
		}

	}
	 
	//these 3 methods chain getter methods in Tile class and Event classes
	
	//this is the new control.setScore(control.getScore() + control.tileList.get(curSpace).whateverCrap();
	//call like control.updateScoreFromTile(curSpace), it does the above^ without being confusing
	public void updateScoreFromTile(int currentTileNum){
		Tile currentTile = this.tileList.get(currentTileNum);
		int scoreDeltaChange = currentTile.getScoreChangeFromEvent();
		currentScore += scoreDeltaChange;
	}
	
	//gets the flavor text from a tile object's index
	public String getFlavorTextFromTile(int currentTileNum){
		return this.tileList.get(currentTileNum).getEventFlavorText();
	}
	
	//gets the aftermath text (pass/fail) from a tile object's index
	//don't call this if its not a BossEvent, it will return null
	public String getAftermathTextFromTile(int currentTileNum){
		return this.tileList.get(currentTileNum).getEventAftermathText();
	}
	
	public int getScore() {
		return currentScore;
	}
}
