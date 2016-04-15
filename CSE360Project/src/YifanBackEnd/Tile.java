package YifanBackEnd;

public class Tile {
	private boolean tileOccupied;
	
	public Tile(){
		this.tileOccupied = false;
	}
	
	public void flipTile(){
		tileOccupied = !tileOccupied;
	}
	
	
}
