package com.goxr3plus.fxborderlessscene.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Testing the application to see if it works
 *
 * @author GOXR3PLUS
 */
public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        //Need fx 13+ (it is neccessary if you want it to be minimizable on icon click)
        //fx 13+ Fixed the problem of clicking the icon to minimize the transparent/undecorated stage
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setMinWidth(250);
        primaryStage.setMinHeight(250);

        MainWindowPane mainWindowPane = new MainWindowPane();
        // Set the scene to your stage and you're done!
        BorderlessScene borderlessScene = new BorderlessScene(primaryStage,StageStyle.UNDECORATED,mainWindowPane);

        //mainWindowController
        mainWindowPane.setBorderlessScene(borderlessScene);
        mainWindowPane.initActions();

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
