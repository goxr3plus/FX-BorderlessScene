package com.goxr3plus.fxborderlessscene.application;

import java.io.IOException;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.StackPane;
import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;

public class MainWindowController extends StackPane {

	//-----------------------------------------------------------

	@FXML
	private Label bottomLabel;

	@FXML
	private Button transWStyle1;

	@FXML
	private Button transWStyle2;

	@FXML
	private Button mainWStyle1;

	@FXML
	private Button mainWStyle2;

	@FXML
	private Button mainWRemoveStyle;

	@FXML
	private Label topLabel;

	@FXML
	private Button minimize;

	@FXML
	private Button maximizeNormalize;

	@FXML
	private Button exit;


	@FXML
	private RadioButton aeroSnap;

	// -------------------------------------------------------------

	private BorderlessScene borderlessScene;

	/**
	 * Constructor.
	 */
	public MainWindowController() {

		// ------------------------------------FXMLLOADER ----------------------------------------
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainWindowController.fxml"));
		loader.setController(this);
		loader.setRoot(this);

		try {
			loader.load();
		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * Called as soon as .fxml is initialised
	 */
	@FXML
	private void initialize() {

	}

	/**
	 * Checking the functionality of the Borderless Scene Library
	 */
	public void initActions() {

		// To move the window around by pressing a node:
		borderlessScene.setMoveControl(topLabel);

		//------------------------ Main Resizable window styling -------------------------

		//Apply a new style for the resizable window
		//Change the corner colors etc.... whatever :)

		//remove the default css style for resizable window
		//scene.removeDefaultCSS()

		//------------------------ Background Transparent window styling ----------------------

		//transWStyle1
		transWStyle1.setOnAction(a -> {
			//Change the style of the background transparent window
			borderlessScene.setTransparentWindowStyle("-fx-background-color:rgb(0,0,0,0.9); -fx-border-color:firebrick; -fx-border-width:2px;");
		});

		//transWStyle2
		transWStyle2.setOnAction(a -> {
			//Change the style of the background transparent window
			borderlessScene.setTransparentWindowStyle("-fx-background-color:rgb(255,255,255,0.9); -fx-border-color:black; -fx-border-width:2px;");
		});

		//------------------------ Other Useful methods --------------------------------
		// Maximise (on/off) and minimise the application:
		//scene.maximizeStage()
		//scene.minimizeStage()

		// To disable resize:
		//borderlessScene.setResizable(false)

		// To switch the content during runtime:
		//borderlessScene.setContent(yourNewParent)

		// Is the resizable window maximized ?
		//boolean isSceneMaximized = borderlessScene.isMaximized()

		// Get windowed* size and position:
		//borderlessScene.getWindowedSize()
		//borderlessScene.getWindowedPosition()

		//Let's do some magic here
		bottomLabel.textProperty()
			.bind(Bindings.createStringBinding(() -> "Window Maximized : [ " + borderlessScene.maximizedProperty().get() + " ]", borderlessScene.maximizedProperty()));

		/* Action Buttons */

		exit.setOnAction(a -> Main.primaryStage.close());

		minimize.setOnAction(a -> Main.primaryStage.setIconified(true));

		maximizeNormalize.setOnAction(a -> Main.borderlessScene.maximizeStage());

		aeroSnap.selectedProperty().bindBidirectional(Main.borderlessScene.snapProperty());

	}

	/**
	 * @return the borderlessScene
	 */
	public BorderlessScene getBorderlessScene() {
		return borderlessScene;
	}

	/**
	 * @param borderlessScene the borderlessScene to set
	 */
	public void setBorderlessScene(BorderlessScene borderlessScene) {
		this.borderlessScene = borderlessScene;
	}
}
