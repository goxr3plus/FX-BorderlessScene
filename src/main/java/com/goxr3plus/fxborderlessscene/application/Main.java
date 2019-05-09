package com.goxr3plus.fxborderlessscene.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import com.goxr3plus.fxborderlessscene.borderless.CustomStage;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
	public void start(Stage primaryStage) {
		//Create custom stage (it is neccessary if you want it to be minimizable on icon click)
		CustomStage stage = new CustomStage(StageStyle.UNDECORATED);
		stage.setMinWidth(250);
		stage.setMinHeight(250);

		//Primary Stage. Notice that we don't care about the one got as a parameter. Instead we use our custom one.
		Main.primaryStage = stage;
		
		// Set the scene to your stage and you're done!
		borderlessScene = stage.craftBorderlessScene(mainWindowController);

		//mainWindowController
		mainWindowController.setBorderlessScene(borderlessScene);
		mainWindowController.initActions();
		

		//Show
		stage.setTitle("Draggable and Undecorated JavaFX Window");
		stage.setScene(borderlessScene);
		stage.setWidth(900);
		stage.setHeight(500);
		
		//IMPORTANT: Notice that I used showAndAdjust() rather than show().
		//It is custom method, that, besides just showing the stage, implements additional features.
		//I couldn't just override show() method, because it is final method.
		stage.showAndAdjust();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
