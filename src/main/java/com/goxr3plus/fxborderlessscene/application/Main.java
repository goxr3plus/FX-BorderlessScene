package main.java.com.goxr3plus.fxborderlessscene.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import main.java.com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;

/**
 * Testing the application to see if it works
 * 
 * @author GOXR3PLUS
 *
 */
public class Main extends Application {
	
	MainWindowController mainWindowController = new MainWindowController();
	public static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Primary Stage
		Main.primaryStage = primaryStage;
		
		// Set the scene to your stage and you're done!
		BorderlessScene borderlessScene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, mainWindowController, 250, 250);
		
		//mainWindowController
		mainWindowController.setBorderlessScene(borderlessScene);
		mainWindowController.initActions();
		
		//Show
		primaryStage.setTitle("Draggable and Undecorated JavaFX Window");
		primaryStage.setScene(borderlessScene);
		primaryStage.setWidth(900);
		primaryStage.setHeight(500);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
