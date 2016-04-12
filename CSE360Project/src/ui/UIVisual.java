package ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

/* Class UIVisual
 * Date Created: April 10, 2016
 * Contributors: Michael Puskas (mpuskas@asu.edu)
 * 
 * Pulls data from the back-end through Class UIControl.
 * Uses data to implement the game logic and display the current state of the program.
 */
public class UIVisual
{
	/* ## Instantiating variables that are not part of the javaFX tree ## */
	private Boolean isMoving; //True while character is moving
	private Boolean isRolling; //True during dice roll animation
	private int startX;
	private int startY;
	private int curX;
	private int curY;
	private int targetX;
	private int targetY;
	private int spacesToMove; //The number of spaces from 1-3 that need to be moved based on the dice roll
	private int curSpace;
    private long startNanoTime;

	private Image board;
	private Image fiddy;
	private Image die;
	private Image splash;
	private Image roll1;
	private Image roll2;
	private Image roll3;
	
	private enum Mode
	{
		splash,
		play,
		scores;
	}
	private Mode curMode = Mode.splash;
	
	private UIControl control;

	UIVisual(UIControl Control)
	{
		//Starting board positions
		startX = 75;
		startY = 350;
		curX = startX;
		curY = startY;
		spacesToMove = 0;
		curSpace = 0;

		//Initialize the passed in control
		control = Control;
		isMoving = false;
		isRolling = false;
		startNanoTime = System.nanoTime();

		//Character and non-interactable assets
		fiddy = new Image("/assets/111209-50-cent.png", 60, 140, true, true);
		roll1 = new Image("/assets/1.png", 250, 250, false, true);
		roll2 = new Image("/assets/2.png", 250, 250, false, true);
		roll3 = new Image("/assets/3.png", 250, 250, false, true);
	}

	/* ## Instantiating variables that ARE part of the javaFX tree ## */
	//Roots contain the nodes that make up the javaFX tree (canvas, buttons, etc)
	Group root;
	Scene theScene;
	//Canvas lets you draw (In this case, through a GraphicsContext)
	Canvas gameCanvas; //Canvas for the game board/related things
	Canvas rollCanvas; //Canvas for the prompt window where you roll
	Canvas splashCanvas; //Canvas for the splash screen where you can choose play/leaderboard
	GraphicsContext gameGC;
	GraphicsContext rollGC;
	GraphicsContext splashGC;
	Button makeMove;
	Button playButton;
	Button scoresButton;
	
	private void initTreeMembers()
	{
		//Width/Height of game board
		final int gameWidth = 1024;
		final int gameHeight = 512;
		//Width/Height of roll canvas (Used for rolling animation)
		final int rollWidth = 250;
		final int rollHeight = 250;

		//Background/button assets
		board = new Image("/assets/board.png", gameWidth, gameHeight, true, true);
		splash = new Image("/assets/splash.png", gameWidth, gameHeight, true, true);
		die = new Image("/assets/die.png", rollWidth, rollHeight, true, true);

		//Control Objects
		root = new Group();
		theScene = new Scene(root);
        gameCanvas = new Canvas(gameWidth, gameHeight);
        rollCanvas = new Canvas(rollWidth, rollHeight);
        splashCanvas = new Canvas(gameWidth, gameHeight);
		gameGC = gameCanvas.getGraphicsContext2D();
		rollGC = rollCanvas.getGraphicsContext2D();
		splashGC = splashCanvas.getGraphicsContext2D();
        rollCanvas.relocate((gameWidth / 2) - (rollWidth / 2), (gameHeight / 2) - (rollHeight / 2)); //Sets placement of roll window
        splashGC.drawImage(splash, 0, 0);
		root.getChildren().add(splashCanvas); //Gotta start with something on the root to set the size of the window

        //Interactables
        initButtons(gameWidth, gameHeight, rollWidth, rollHeight);
	}

	public void startVisual(Stage theStage) 
	{
		theStage.setTitle("TBA");
		initTreeMembers();
		theStage.setScene(theScene);

		new AnimationTimer()
		{
			public void handle(long currentNanoTime)
			{
				if(curMode == Mode.splash)
				{
					if(!root.getChildren().contains(playButton))
					{
						root.getChildren().clear();
						root.getChildren().add(splashCanvas);
						root.getChildren().add(playButton);
						root.getChildren().add(scoresButton);
					}
				}
				else if(curMode == Mode.play)
				{
						playLogic(currentNanoTime);
				}
				else if(curMode == Mode.scores)
				{
					
				}
			}
		}.start();

		theStage.show();
	}
	
	private void playLogic(long currentNanoTime)
	{
		if(isRolling) //Trigger this when you want to roll the dice
		{
			int timeDif = (int) ((currentNanoTime - startNanoTime) / 100000000);
			
			if(timeDif < 30) //Draw the dice animation
			{
				rollGC.clearRect(0, 0, rollCanvas.getWidth(), rollCanvas.getHeight());
				if(timeDif % 3 == 0)
					rollGC.drawImage(roll3, 0, 0);
				else if(timeDif % 2 == 0)
					rollGC.drawImage(roll2, 0, 0);
				else
					rollGC.drawImage(roll1, 0, 0);
			}
			else if(timeDif == 30) //Draw the result
			{
				//Set the image to be displayed
				rollGC.clearRect(0, 0, rollCanvas.getWidth(), rollCanvas.getHeight());
				if(spacesToMove == 1)
					rollGC.drawImage(roll1, 0, 0);
				else if(spacesToMove == 2)
					rollGC.drawImage(roll2, 0, 0);
				else if(spacesToMove == 3)
					rollGC.drawImage(roll3, 0, 0);
			}
			else
			{
				//Pause for them to bask in their number's beauty, then move to moving phase
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					System.out.println("Error in waiting after dice was rolled.");
				}
				rollGC.clearRect(0, 0, rollCanvas.getWidth(), rollCanvas.getHeight());
				isRolling = false;
				isMoving = true;
			}
		}
		else if(isMoving) //Triggers after roll, moves the character
		{
			if(curX < targetX)
				curX++;
			else if (curX > targetX)
				curX--;
			if(curY < targetY)
				curY++;
			else if (curY > targetY)
				curY--;
			
			if(curX == targetX && curY == targetY)
			{
				if(spacesToMove > 0)
				{
					curSpace++;
					targetX = control.tileList.get(curSpace).x;
					targetY = control.tileList.get(curSpace).y;
					spacesToMove--;
				}
				else if(!root.getChildren().contains(makeMove))
				{
					isMoving = false;
					root.getChildren().add(makeMove);
				}
			}
		}

		//We always want to draw the board and main character
		gameGC.drawImage(board, 0, 0);
		gameGC.drawImage(fiddy, curX, curY);
	}
	
	 private void initButtons(int gameWidth, int gameHeight, int rollWidth, int rollHeight)
	 {
		//makeMove button for rolling die
		makeMove = new Button();
		makeMove.relocate(gameCanvas.getWidth() / 2 - rollWidth / 2 - 10
				,gameCanvas.getHeight() / 2 - rollHeight / 2 + 25); //Sets the position of the button
		ImageView makeMoveImage = new ImageView();
		makeMoveImage.imageProperty().set(die);        
		makeMove.setGraphic(makeMoveImage); //Set the button's graphic to the imageview defined
		makeMove.setOnAction(new EventHandler<ActionEvent>() //Sets what the button does on click
		{
			@Override public void handle(ActionEvent e) 
			{
				root.getChildren().remove(makeMove); //Get the roll button out of the way
				spacesToMove = (int) (Math.random() * 3) + 1; //Set the spaces to move to a random number

				curSpace++;
				targetX = control.tileList.get(curSpace).x;
				targetY = control.tileList.get(curSpace).y;

				startNanoTime = System.nanoTime();
				isRolling = true;
			}
		});
		
		//Play button for starting the game from the splash
		playButton = new Button("Play");
		playButton.relocate(100, gameHeight / 2);
		playButton.setPrefHeight(200);
		playButton.setPrefWidth(200);
		playButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				curMode = Mode.play;
				root.getChildren().clear();
				root.getChildren().add(gameCanvas);
				root.getChildren().add(rollCanvas);
				root.getChildren().add(makeMove);
			}
		});

		//Score button for viewing the leaderboard
		scoresButton = new Button("Leaderboard");
		scoresButton.relocate(gameWidth - 300, gameHeight / 2);
		scoresButton.setPrefHeight(200);
		scoresButton.setPrefWidth(200);
		scoresButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				curMode = Mode.scores;
			}
		});
	 }
}