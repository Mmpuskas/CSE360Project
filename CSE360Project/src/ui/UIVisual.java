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

public class UIVisual
{
	/* ## Initializing variables that are not part of the javaFX tree ## */

	private Boolean isMoving; //True while character is moving
	private Boolean isRolling; //True during dice roll animation
	private int startX;
	private int startY;
	private int curX;
	private int curY;
	private int targetX;
	private int targetY;
	private int spacesToMove; //The number of spaces from 0-3 that need to be moved based on the dice roll
	private int curSpace;
    private long startNanoTime;

	private Image board;
	private Image fiddy;
	private Image die;
	private Image rollBackground;
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
		roll1 = new Image("/assets/1.png", 100, 200, true, true);
		roll2 = new Image("/assets/2.png", 100, 200, true, true);
		roll3 = new Image("/assets/3.png", 100, 200, true, true);
	}

	/* ## Initializing variables that ARE part of the javaFX tree ## */
	Group root;
	Scene theScene;
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
		int gameWidth = 1024;
		int gameHeight = 512;
		int rollWidth = 350;
		int rollHeight = 300;

		//Background/button assets
		board = new Image( "/assets/board.png", gameWidth, gameHeight, true, true);
		splash = new Image( "/assets/splash.png", gameWidth, gameHeight, true, true);
		rollBackground = new Image( "/assets/InnerBackground.png", rollWidth, rollHeight, true, true);
		die = new Image( "/assets/die.png", 40, 40, true, true);

		//Control Objects
		root = new Group();
		theScene = new Scene(root);
        gameCanvas = new Canvas(gameWidth, gameHeight);
        rollCanvas = new Canvas(rollWidth, rollHeight);
        splashCanvas = new Canvas(gameWidth, gameHeight);
		gameGC = gameCanvas.getGraphicsContext2D();
		rollGC = rollCanvas.getGraphicsContext2D();
		splashGC = splashCanvas.getGraphicsContext2D();
        rollGC.drawImage(rollBackground, 0, 0); //Sets background of roll window
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
		if(isRolling)
		{
			int timeDif = (int) ((currentNanoTime - startNanoTime) / 100000000);
			int numXPos = ((int) rollCanvas.getWidth() / 2) - ((int) roll1.getWidth() / 2) - 20;
			int numYPos = ((int) rollCanvas.getHeight() / 2) - ((int) roll1.getHeight() / 2) - 50;
			
			if(timeDif < 30)
			{
				rollGC.clearRect(0, 0, rollCanvas.getWidth(), rollCanvas.getHeight());
				if(timeDif % 3 == 0)
					rollGC.drawImage(roll3, numXPos, numYPos);
				else if(timeDif % 2 == 0)
					rollGC.drawImage(roll2, numXPos, numYPos);
				else
					rollGC.drawImage(roll1, numXPos, numYPos);
			}
			else if(timeDif == 30)
			{
				//Set the image to be displayed
				rollGC.clearRect(0, 0, rollCanvas.getWidth(), rollCanvas.getHeight());
				if(spacesToMove == 1)
					rollGC.drawImage(roll1, numXPos, numYPos);
				else if(spacesToMove == 2)
					rollGC.drawImage(roll2, numXPos, numYPos);
				else if(spacesToMove == 3)
					rollGC.drawImage(roll3, numXPos, numYPos);
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
		else if(isMoving)
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
				if(spacesToMove == 0)
					isMoving = false;
				else
				{
					curSpace++;
					targetX = control.tileList.get(curSpace).x;
					targetY = control.tileList.get(curSpace).y;
					spacesToMove--;
				}
			}
		}

		gameGC.drawImage(board, 0, 0);
		gameGC.drawImage(fiddy, curX, curY);
	}
	
	 private void initButtons(int gameWidth, int gameHeight, int rollWidth, int rollHeight)
	 {
		//makeMove button for rolling die
		makeMove = new Button();
		makeMove.relocate( rollCanvas.getLayoutX() + (rollWidth / 2) - 32 
				, rollCanvas.getLayoutY() + (rollHeight / 2) - 50); //Sets the position of the button based on rollCanvas
		ImageView makeMoveImage = new ImageView();
		makeMoveImage.imageProperty().set(die);        
		makeMove.setGraphic(makeMoveImage); //Set the button's graphic to the imageview defined
		makeMove.setOnAction(new EventHandler<ActionEvent>() //Sets what the button does
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
		
		//play button for starting the game from the splash
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

		//score button for viewing the leaderboard
		scoresButton = new Button("Leaderboard");
		scoresButton.relocate(gameWidth - 300, gameHeight / 2);
		scoresButton.setPrefHeight(200);
		scoresButton.setPrefWidth(200);
		scoresButton.setOnAction(new EventHandler<ActionEvent>() //Sets what the button does
		{
			@Override public void handle(ActionEvent e) 
			{
				curMode = Mode.scores;
			}
		});
	 }
}