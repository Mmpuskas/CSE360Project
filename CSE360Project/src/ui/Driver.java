package ui;

import javafx.application.Application;
import javafx.stage.Stage;

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
    	control.initVals();
    	visual.startVisual(theStage);
    }
}
