package ui;

import javafx.application.Application;
import javafx.stage.Stage;

/** Driver class for CSE360 group project "Journey to Chaos End"
 * @author Michael Puskas (mpuskas@asu.edu)
 */
public class Driver extends Application
{
	UIControl control = new UIControl();
	UIVisual visual = new UIVisual(control);

    public static void main(String[] args) 
    {
    	launch(args);
    }

    @Override
    public void start(Stage theStage) 
    {
    	visual.startVisual(theStage);
    }
}
