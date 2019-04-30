package com.goxr3plus.fxborderlessscene.application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;

/**
 * Testing the application to see if it works
 * 
 * @author GOXR3PLUS
 *
 */
public class Main extends Application {
	
	private com.goxr3plus.fxborderlessscene.application.MainWindowController mainWindowController = new com.goxr3plus.fxborderlessscene.application.MainWindowController();
	static BorderlessScene borderlessScene;
	static Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//Primary Stage
		Main.primaryStage = primaryStage;
		
		// Set the scene to your stage and you're done!
		borderlessScene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, mainWindowController, 250, 250);

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
