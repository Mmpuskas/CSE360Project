package ui;

import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

import java.io.IOException;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;


/** UI class for "Journey to Chaos End"
 * Controls all aspects of drawing to the display, along with some of the control aspects.
 * 
 * @author Michael Puskas (mpuskas@asu.edu), Yifan Li, Willian Chen
 */
public class UIVisual
{
	/* ## Instantiating variables that are not part of the javaFX tree ## */
	private Boolean isRolling; //True during dice roll animation
	private int spacesToMove; //The number of spaces from 1-3 that need to be moved based on the dice roll
	private int curSpace;
    private long startNanoTime;

	private Image board;
	private Image fiddy;
	private Image orc;
	private Image die;
	private Image splash;
	private Image roll1;
	private Image roll2;
	private Image roll3;
	private Image play;
	private Image leaderboard;
	private Image eventbackground;
	private Image closeText;
	private Image endGameImage;
	private Image fishingVillage;
	private Image forest;
	private Image greyHills;
	private Image town;
	private Image endGate;
	private Image orcThief;
	private Image troll;
	private Image flufflesDog;
	private Image spider;
	private Image boss1;
	private Image boss2;
	private Image boss3;
	private Image boss4;
	private Image boss5;
	
	private Actor orcActor;
	
	private enum Mode
	{
		splash,
		play,
		scores;
	}
	private Mode curMode = Mode.splash;
	
	private UIControl control;
    private leaderboard lb;

	UIVisual(UIControl Control)
	{
		//Starting board positions
		spacesToMove = 0;
		curSpace = 0;

		//Initialize the passed in control
		control = Control;
		isRolling = false;
		startNanoTime = System.nanoTime();

		lb = new leaderboard();
	}

	/* ## Instantiating variables that ARE part of the javaFX tree ## */
	//Roots contain the nodes that make up the javaFX tree (canvas, buttons, etc)
	private Group root;
	private Scene theScene;
	//Canvas lets you draw (In this case, through a GraphicsContext)
	private Canvas scoresCanvas; //Canvas for the leaderboard scores
	private Canvas gameCanvas; //Canvas for the game board/related things
	private Canvas rollCanvas; //Canvas for the prompt window where you roll
	private Canvas splashCanvas; //Canvas for the splash screen where you can choose play/leaderboard

	//Create a sceneCanvas where the scene imgae is displayed at the top and text in the rest of the portion
	private Canvas eventCanvas;
	private Canvas bossCanvas; // for drawing boss 
	private GraphicsContext gameGC;
	private GraphicsContext rollGC;
	private GraphicsContext splashGC;
	private GraphicsContext scoreGC;
	private GraphicsContext eventGC;   //new add
	private GraphicsContext bossGC; 
	private Button makeMove;
	private Button playButton;
	private Button scoresButton;
	private Button close;
	private Button endGameButton;
	private Text scoreText;
    
    private Rectangle2D primaryScreenBounds;
    private int[] array;
    private VBox vb;
    private VBox leaderboardVB;
    private Label title;
    private ScrollBar sc;
    
	private Text txt;// it is the text events
	private Text afterMath;
	private Label textLabel[]; //For adding scores to the leaderboard
	
	/** Initializes the member variables that pertain to the JavaFX component tree.
	 * @throws IOException 
	 */
	public void initTreeMembers()
	{
		//Width/Height of game board
		int gameWidth = 1536;
		int gameHeight = 1005;
		primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		if(primaryScreenBounds.getWidth() < 1400 && primaryScreenBounds.getHeight() < 800)//
		{
			gameWidth = 1024;
			gameHeight = 670;
		}
		//Width/Height of roll canvas (Used for rolling animation)
		final double rollWidth = .26 * gameWidth;//
		final double rollHeight = .398 * gameHeight;
		
		//Initialize images
		control.initTilePositions(gameWidth, gameHeight);
		control.initEvents();
		initImages(gameWidth, gameHeight); 

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
        scoresCanvas = new Canvas(gameWidth, gameHeight);
        eventCanvas = new Canvas(gameWidth/1.75, gameHeight/1.2);
        bossCanvas = new Canvas(rollWidth * 3/4, rollHeight * 3/4);

		gameGC = gameCanvas.getGraphicsContext2D();
		rollGC = rollCanvas.getGraphicsContext2D();
		splashGC = splashCanvas.getGraphicsContext2D();
		scoreGC = scoresCanvas.getGraphicsContext2D();
		eventGC = eventCanvas.getGraphicsContext2D();
		bossGC = bossCanvas.getGraphicsContext2D();
		
        rollCanvas.relocate((gameWidth / 2) - (rollWidth / 2), (gameHeight / 2) - (rollHeight / 2)); //Sets placement of roll window
        eventCanvas.relocate((gameWidth / 2 - gameWidth / 3.7), (gameHeight / 12));//
        bossCanvas.relocate(gameWidth * 4.8/6, gameHeight * 1/6);
        
        //Score text sits at the top of the screen
        scoreText = new Text("" + control.getScore());
        scoreText.setFont(Font.font("Verdana", 50)); //Possibly import font object
        scoreText.relocate(gameWidth / 2,  gameHeight * .005);
        scoreText.setStroke(Color.BLACK);
        scoreText.setFill(Color.GRAY);

        //Set up backgrounds
        scoreGC.drawImage(splash, 0, 0 );
        splashGC.drawImage(splash, 0, 0);
        eventGC.drawImage(eventbackground, 0, 0);
        bossGC.drawImage(splash, 0, 0);

		root.getChildren().add(splashCanvas); //Gotta start with something on the root to set the size of the window

		vb = new VBox();
		leaderboardVB = new VBox();
		txt = new Text();
		afterMath = new Text();
		sc = new ScrollBar();
		title = new Label("Leaderboard\n");
		textLabel = new Label[10];

		txt.setText(control.getFlavorTextFromTile(0));
		vb.getChildren().add(txt);
		vb.getChildren().add(afterMath);
		leaderboardVB.getChildren().add(title);
		sc.setOrientation(Orientation.VERTICAL);
		sc.setMin(0);
		sc.setValue(0);

		/* TODO: Convert the positioning on all of these to be based on gameheight/gamewidth */
		sc.setMax(primaryScreenBounds.getHeight());
		sc.setPrefHeight(primaryScreenBounds.getHeight());
		sc.setLayoutX(theScene.getWidth() - sc.getWidth());
		sc.valueProperty().addListener(event->{title.setTranslateY(50 - sc.getValue());});
		sc.valueProperty().addListener(event->{leaderboardVB.setTranslateY(50 - sc.getValue());});
		/* TODO: Convert the positioning on all of these to be based on gameheight/gamewidth */

		afterMath.setWrappingWidth(.306 * gameWidth);
		txt.setWrappingWidth(.54 * gameWidth);
		vb.setPadding(new Insets(.11 * gameHeight, .047 * gameWidth, .75 * gameHeight, .25 * gameWidth)); //(top/right/bottom/left)
		title.setPadding(new Insets(0, 0, .0097 * gameWidth, 0));
		title.setFont(Font.font("Times New Roman", .059 * gameHeight));
		txt.setFont(Font.font("Times New Roman", .022 * gameHeight));
		afterMath.setFont(Font.font("Times New Roman", .022 * gameHeight));
		leaderboardVB.relocate(gameWidth * 6.2/12, gameHeight * 1.3/25);
		for(int i = 0; i < 10; i++)
		{
			textLabel[i] = new Label();
			textLabel[i].setFont(Font.font("Times New Roman", .022 * gameHeight));
			textLabel[i].setPadding(new Insets(0, 0, .0048 * gameWidth, 0));
		}

        //Interactables
        initButtons(gameWidth, gameHeight, rollWidth, rollHeight);
	}

	/** Receives the Stage from the driver class, and controls which "Mode" is currently being drawn.
	 * 
	 * @param theStage The stage from the start method of the driver class
	 */
	public void startVisual(Stage theStage) 
	{
		theStage.setTitle("Journey to Chaos End");
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
					if(!root.getChildren().contains(playButton))
					{
						primaryScreenBounds = Screen.getPrimary().getVisualBounds();

						try {
							lb.setPointsArray();
						} catch (Exception e) {
							System.out.println("There aren't any points");
						}
						array = lb.getTopTen();
					
						//Strings of the top 10 scores
						for(int i = 0; i < 10; i++)
						{
							textLabel[i].setText(Integer.toString(array[i]));
							leaderboardVB.getChildren().add(textLabel[i]);
						}
						
						playButton.relocate(primaryScreenBounds.getWidth() * 7.5 / 12, primaryScreenBounds.getHeight() * 9 / 12);
						root.getChildren().add(playButton);
					}
				}
			}
		}.start();

		theStage.show();
	}
	
	/** Controls what happens during the "Play" mode of the game.
	 * 
	 * @param currentNanoTime The current system time in nanoseconds.
	 */
	public void playLogic(long currentNanoTime)
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
				orcActor.setMoving(true);
			}
		}
		else if(orcActor.getMoving()) //Triggers after roll, moves the character
		{
			orcActor.moveToPosition();
			
			if(orcActor.curX == orcActor.getTargetX() && orcActor.curY == orcActor.getTargetY())
			{
				if(spacesToMove > 0)
				{	
					if(curSpace % 5 == 0 && curSpace > 0 && !control.getTileVisited(curSpace))
					{
						spacesToMove = 0;
						control.setTileVisited(curSpace);
					}
					else
					{
						curSpace++;
						orcActor.setTargetX(control.tileList.get(curSpace).x);
						orcActor.setTargetY(control.tileList.get(curSpace).y);
						spacesToMove--;
					}
				}
				else
				{
					if(curSpace % 5 == 0 && curSpace > 0)
						control.setTileVisited(curSpace);

					control.updateScoreFromTile(curSpace);
					orcActor.setMoving(false);
					control.updateScoreFromTile(curSpace);

					if(curSpace % 5 == 0 && curSpace > 0)
					{
						control.setTileVisited(curSpace);
						afterMath.setText(control.getAftermathTextFromTile(curSpace));
						
						bossGC.clearRect(0, 0, bossCanvas.getWidth(), bossCanvas.getHeight());
						switch(curSpace / 5)
						{
							case 1: bossGC.drawImage(boss1, 0, 0); break;
							case 2: bossGC.drawImage(boss2, 0, 0); break;
							case 3: bossGC.drawImage(boss3, 0, 0); break;
							case 4: bossGC.drawImage(boss4, 0, 0); break;
							case 5: bossGC.drawImage(boss5, 0, 0); break;
						}
						
						root.getChildren().add(bossCanvas);
					}
					txt.setText(control.getFlavorTextFromTile(curSpace));
					root.getChildren().add(eventCanvas);
					root.getChildren().add(vb);
					if(curSpace == 25)
						root.getChildren().add(endGameButton);
					else
						root.getChildren().add(close);	
				}
			}
		}

		//We always want to draw the board and main character
		gameGC.drawImage(board, 0, 0);
		gameGC.drawImage(orcActor.charImage, orcActor.curX, orcActor.curY);
		scoreText.setText("" + control.getScore());
	}
	
	/** Initializes all buttons and button handlers.
	 * 
	 * @param gameWidth The width of the game board.
	 * @param gameHeight The height of the game board.
	 * @param rollWidth The width of the roll canvas (where the animations are drawn).
	 * @param rollHeight The height of the roll canvas.
	 */
	 private void initButtons(int gameWidth, int gameHeight, double rollWidth, double rollHeight)
	 {
		 //close button
		 close = new Button();
		 close.relocate(eventCanvas.getLayoutX() + eventCanvas.getWidth() / 2 - closeText.getWidth() / 1.6, gameCanvas.getHeight() * 10.95 / 12);
		 ImageView closeImage = new ImageView();
		 closeImage.imageProperty().set(closeText);        
		 close.setGraphic(closeImage);
		 close.setStyle("-fx-focus-color: darkgoldenrod;");
		 close.setOnAction(new EventHandler<ActionEvent>() 
		 {
			 @Override public void handle(ActionEvent e) 
			 {
				 if(curSpace==25)
				 {
					 root.getChildren().remove(close);
					 root.getChildren().remove(vb);
					 root.getChildren().remove(eventCanvas);
					 root.getChildren().add(scoresButton); 
				 }
				 if(curSpace % 5 == 0 && curSpace != 0)
				 { 
					 root.getChildren().remove(bossCanvas);
				   	 vb.getChildren().remove(afterMath);
				 }
				 if(curSpace < 25)
				 {
					 root.getChildren().remove(close);
					 root.getChildren().remove(vb);
					 root.getChildren().remove(eventCanvas);
					 root.getChildren().add(makeMove);
				 }
			 }
		 });
		 
		//makeMove button for rolling die
		makeMove = new Button();
		makeMove.relocate(gameCanvas.getWidth() / 2 - rollWidth / 2 - (gameWidth * 0.00651)
				,gameCanvas.getHeight() / 2 - rollHeight / 2 + (gameHeight * 0.02487)); //Sets the position of the button
		ImageView makeMoveImage = new ImageView();
		makeMoveImage.imageProperty().set(die);        
		makeMove.setGraphic(makeMoveImage); //Set the button's graphic to the imageview defined
		makeMove.setStyle("-fx-focus-color: darkgoldenrod;");
		makeMove.setOnAction(new EventHandler<ActionEvent>() //Sets what the button does on click
		{
			@Override public void handle(ActionEvent e) 
			{
				root.getChildren().remove(makeMove); //Get the roll button out of the way
				spacesToMove = (int) (Math.random() * 3) + 1; //Set the spaces to move to a random number
				
				startNanoTime = System.nanoTime();
				isRolling = true;
			}
		});
		
		//Play button for starting the game from the splash
		playButton = new Button();
		ImageView playButtonImage = new ImageView();
		playButtonImage.imageProperty().set(play);        
		playButton.setGraphic(playButtonImage); 
		playButton.setStyle("-fx-focus-color: darkgoldenrod;");
		playButton.relocate(gameWidth - (gameWidth * 0.26), gameHeight / 2);
		playButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override public void handle(ActionEvent e) 
			{
				curMode = Mode.play;
				curSpace=0;
				root.getChildren().clear();
				root.getChildren().add(gameCanvas);
				root.getChildren().add(rollCanvas);
				root.getChildren().add(scoreText);
						
				root.getChildren().add(eventCanvas);
				root.getChildren().add(vb);
				root.getChildren().add(close);

				orcActor.setTargetX(control.tileList.get(0).x);
				orcActor.setTargetY(control.tileList.get(0).y);
				orcActor.setCurX(control.tileList.get(0).x);
				orcActor.setCurY(control.tileList.get(0).y);
				control.setScore(0);
				
				for(int i = 0; i < 25; i++)
					control.setTileNotVisited(i);

				txt.setText(control.getFlavorTextFromTile(0));
			}
		});

		//Score button for viewing the leaderboard
		scoresButton = new Button();
		ImageView scoresButtonImage = new ImageView();
		scoresButtonImage.imageProperty().set(leaderboard);        
		scoresButton.setGraphic(scoresButtonImage); 
		scoresButton.setStyle("-fx-focus-color: darkgoldenrod;");
		scoresButton.relocate(gameWidth - (gameWidth * 0.5208), gameHeight * 8 / 12);
		scoresButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				curMode = Mode.scores;
				root.getChildren().clear();
				root.getChildren().add(splashCanvas);
				root.getChildren().add(leaderboardVB);
				root.getChildren().add(sc);
			}
		});

		//Pops at 25 tile, saves the curScore and goes back to the leaderboard
		endGameButton = new Button();
		ImageView endGameButtonImage = new ImageView();
		endGameButtonImage.imageProperty().set(endGameImage);        
		endGameButton.setGraphic(endGameButtonImage); 
		endGameButton.setStyle("-fx-focus-color: darkgoldenrod;");
		endGameButton.relocate(eventCanvas.getLayoutX() + eventCanvas.getWidth() / 2 - closeText.getWidth() / 1.6, gameCanvas.getHeight() * 10.95 / 12);
		endGameButton.setOnAction(new EventHandler<ActionEvent>()
		{
			@Override public void handle(ActionEvent e) 
			{
				try 
				{
					lb.setNewScore(control.getScore());
				} catch (IOException e1) {
					System.out.println("Error setting final score.");
				}
				curMode = Mode.scores;
				root.getChildren().clear();
				root.getChildren().add(splashCanvas);
				root.getChildren().add(leaderboardVB);
				root.getChildren().add(sc);
			}
		});
	 }
	
	 /**
	  * Initializes the size of image files based off of coefficients in relation to gameWidth/gameHeight
	  * @param gameWidth The width of the game board.
	  * @param gameHeight The height of the game board.
	  */
	public void initImages(int gameWidth, int gameHeight)
	{
		//Character and non-interactable assets
		orc = new Image("/assets/orc.png", (gameWidth * 0.0911), (gameHeight * 0.199), true, true);
		roll1 = new Image("/assets/1.png", (gameWidth * 0.2604), (gameHeight * 0.398), false, true);
		roll2 = new Image("/assets/2.png", (gameWidth * 0.2604), (gameHeight * 0.398), false, true);
		roll3 = new Image("/assets/3.png", (gameWidth * 0.2604), (gameHeight * 0.398), false, true);
		play = new Image("/assets/play.png", (gameWidth * 0.1302), (gameHeight * 0.199), true, true);
		leaderboard = new Image("/assets/leaderboard.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);
		orcActor = new Actor(control.tileList.get(0).x, control.tileList.get(0).y, orc);
		closeText = new Image("/assets/close.png", (gameWidth * 0.05), (gameHeight * 0.05), true, true);
		eventbackground = new Image("/assets/eventbackground.png", gameWidth / 1.75, gameHeight / 1.2, true, false);
		endGameImage = new Image("/assets/leaderboard.png", (gameWidth * 0.05), (gameHeight * 0.05), true, true);
		boss1 = new Image("/assets/boss1.png", (gameWidth * 3/4 * 0.2604), (gameHeight * 3/4 * 0.398), true, true);
		boss2 = new Image("/assets/boss2.png", (gameWidth * 3/4 * 0.2604), (gameHeight * 3/4 * 0.398), true, true);
		boss3 = new Image("/assets/boss3.png", (gameWidth * 3/4 * 0.2604), (gameHeight * 3/4 * 0.398), true, true);
		boss4 = new Image("/assets/boss4.png", (gameWidth * 3/4 * 0.2604), (gameHeight * 3/4 * 0.398), true, true);
		boss5 = new Image("/assets/111209-50-cent.png", (gameWidth * 3/4 * 0.2604), (gameHeight * 3/4 * 0.398), true, true);

		/*
		//Set the sizes of the scene images that will go in the sceneCanvas(all the same size)
		fishingVillage = new Image("/assets/FishingVillage.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);
		forest = new Image("/assets/Forest.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		greyHills = new Image("/assets/GreyHills.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		town = new Image("/assets/Town.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		endGate= new Image("/assets/ChaosEndGate.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		
		//Set the sizes of end-scene bosses that will go in the sceneCanvas(all the same size)
		orcThief = new Image("/assets/OrcThief.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		troll = new Image("/assets/Troll.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		flufflesDog = new Image("/assets/Fluffles.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		spider = new Image("/assets/spider.png", (gameWidth * 0.3906), (gameHeight * 0.796), true, true);;
		*/
	}
	
	
	public Scene getTheScene() 
	{
		return theScene;
	}

	public Canvas getGameCanvas() 
	{
		return gameCanvas;
	}

	public Canvas getRollCanvas() 
	{
		return rollCanvas;
	}

	public Canvas getSplashCanvas() 
	{
		return splashCanvas;
	}

	public Button getMakeMove() 
	{
		return makeMove;
	}

	public Button getPlayButton() 
	{
		return playButton;
	}

	public Button getScoresButton() 
	{
		return scoresButton;
	}
}