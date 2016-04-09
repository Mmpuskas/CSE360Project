package ui;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class Candyland extends Application 
{
	Boolean isMoving = true;
	int startX = 60;
	int startY = 270;
	int endX = 165;
	int endY = 0;
	int curX = startX;
	int curY = startY;

    public static void main(String[] args) 
    {
    	launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
        theStage.setTitle( "Candyland" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        theStage.setScene( theScene );

        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Image board = new Image( "file:assets/candyland_gameboard.png", 512, 512, true, true);
        Image fiddy = new Image( "file:assets/111209-50-cent.png", 60, 140, true, true);
        
        /*
        AnimatedImage ufo = new AnimatedImage();
        Image[] imageArray = new Image[6];
        for (int i = 0; i < 6; i++)
            imageArray[i] = new Image( "ufo_" + i + ".png" );
        ufo.frames = imageArray;
        ufo.duration = 0.100;
        */

        final long startNanoTime = System.nanoTime();

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {

				if(isMoving)
				{
					if(curX < endX)
						curX++;
					else
						curX--;
					if(curY < endY)
						curY++;
					else
						curY--;
					
					if(curX == endX && curY == endY)
						isMoving = false;
				}

                gc.drawImage( board, 0, 0 );
                gc.drawImage( fiddy, curX, curY );
                //gc.drawImage( ufo.getFrame(t), 450, 25 );
            }
        }.start();
        
        theStage.show();
    }
}
