package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;


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
		fiddy = new Image( "/assets/111209-50-cent.png", 60, 140, true, true);
		die = new Image( "/assets/die.png", 40, 40, true, true);
		rollBackground = new Image( "/assets/InnerBackground.png", 150, 100, true, true);
	}

	/* ## Initializing variables that ARE part of the javaFX tree ## */
	Group root;
	Scene theScene;
	Canvas gameCanvas; //Canvas for the game board/related things
	Canvas rollCanvas; //Canvas for the prompt window where you roll
	GraphicsContext gameGC;
	GraphicsContext rollGC;
	Button makeMove;
	
	private void initTreeMembers()
	{
		int gameWidth = 1024;
		int gameHeight = 512;
		int rollWidth = 150;
		int rollHeight = 100;

		//Control Objects
		root = new Group();
		theScene = new Scene(root);
        gameCanvas = new Canvas(gameWidth, gameHeight);
        rollCanvas = new Canvas(rollWidth, rollHeight);
		gameGC = gameCanvas.getGraphicsContext2D();
		rollGC = rollCanvas.getGraphicsContext2D();
        rollGC.drawImage(rollBackground, 0, 0);
        rollCanvas.relocate( (gameWidth / 2) - (rollWidth / 2), (gameHeight / 2) - (rollHeight / 2));
        root.getChildren().add(gameCanvas);

        //Interactables
        makeMove = new Button( "Move" );
        makeMove.relocate( rollCanvas.getLayoutX() + (rollWidth / 2) - 25 
        		, rollCanvas.getLayoutY() + (rollHeight / 2) - 12);
        makeMove.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	currentTileIndex++;
            	targetX = control.xValues[currentTileIndex];
            	targetY = control.yValues[currentTileIndex];
            	
            	isMoving = true;
            	root.getChildren().remove(rollCanvas);
				root.getChildren().remove(makeMove);
            }
        });
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
            	if(!isMoving && (currentNanoTime - startNanoTime) >= 3000 && !root.getChildren().contains(rollCanvas))
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

				gameGC.drawImage( board, 0, 0 );
				gameGC.drawImage( fiddy, curX, curY );
            }
        }.start();

        theStage.show();
    }
}
