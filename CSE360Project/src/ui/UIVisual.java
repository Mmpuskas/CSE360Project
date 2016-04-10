package ui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.beans.binding.Bindings;


import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class UIVisual
{
	/* ## Initializing variables that are not part of the javaFX tree ## */

	private Boolean isMoving;
	private int startX;
	private int startY;
	private int curX;
	private int curY;
	private int targetX;
	private int targetY;
	private int currentTileIndex;
    private long startNanoTime;

	private Image board;
	private Image fiddy;
	private Image die;
	private Image rollBackground;
	private Image splash;
	
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
		startX = 60;
		startY = 270;
		curX = startX;
		curY = startY;
		currentTileIndex = 0;

		//Initialize the passed in control
		control = Control;
		isMoving = false;
		startNanoTime = System.nanoTime();

		//Assets
		board = new Image( "/assets/board.png", 1024, 512, true, true);
		splash = new Image( "/assets/splash.png", 1024, 512, true, true);
		fiddy = new Image( "/assets/111209-50-cent.png", 60, 140, true, true);
		die = new Image( "/assets/die.png", 40, 40, true, true);
		rollBackground = new Image( "/assets/InnerBackground.png", 150, 100, true, true);
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
		int gameHeight = 488;
		int rollWidth = 150;
		int rollHeight = 100;

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
					if(!root.getChildren().contains(rollCanvas))
					{
						root.getChildren().clear();
						root.getChildren().add(gameCanvas);
						playLogic(currentNanoTime);
					}
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
		if(!isMoving && (currentNanoTime - startNanoTime) >= 3000)
		{
			root.getChildren().add(rollCanvas);
			root.getChildren().add(makeMove);
		}

		if(isMoving)
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
				startNanoTime = System.nanoTime();
				isMoving = false;
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
				, rollCanvas.getLayoutY() + (rollHeight / 2) - 25); //Sets the position of the button based on rollCanvas
		ImageView makeMoveImage = new ImageView();
		makeMoveImage.imageProperty().set(die);        
		makeMove.setGraphic(makeMoveImage); //Set the button's graphic to the imageview defined
		makeMove.setOnAction(new EventHandler<ActionEvent>() //Sets what the button does
		{
			@Override public void handle(ActionEvent e) 
			{
				currentTileIndex++;
				targetX = control.xValues[currentTileIndex];
				targetY = control.yValues[currentTileIndex];
				
				isMoving = true;
				root.getChildren().remove(rollCanvas);
				root.getChildren().remove(makeMove);
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