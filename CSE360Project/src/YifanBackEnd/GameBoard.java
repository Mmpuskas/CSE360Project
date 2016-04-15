package YifanBackEnd;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Random;

public class GameBoard {
	private LinkedList<Tile> tileList;
	private ListIterator<Tile> tilesIterator;
	private Random RNGSeed;
	private static GameBoard singletonInst;
	private final int NUMTILES = 25;
	
	private GameBoard(){
		setUpBoard();
		RNGSeed = new Random();
	}
	
	public static GameBoard getGameBoardInstance(){
		if(singletonInst == null){
			singletonInst = new GameBoard();
		}
		
		return singletonInst;
	}
	
	private void setUpBoard(){
		tileList = new LinkedList<>();
		tilesIterator = tileList.listIterator();
		while(tilesIterator.hasNext()){
			tilesIterator.next();
			tilesIterator.set(new Tile());
		}
	}
	
	public void TraverseTiles(){
		int advanceTiles = RNGSeed.nextInt(7);
		for(int tileNo = 0; tileNo < advanceTiles; tileNo++){
			while(tilesIterator.hasNext()){
				tilesIterator.next();
			}
				
		}
	}
	
	
	
}
