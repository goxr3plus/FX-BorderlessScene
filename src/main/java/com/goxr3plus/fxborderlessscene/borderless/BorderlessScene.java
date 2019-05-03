/*
 *
 */
package com.goxr3plus.fxborderlessscene.borderless;

import java.io.IOException;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Undecorated JavaFX Scene with implemented move, resize, minimize, maximize and Aero Snap.
 * <p>
 * Usage:
 *
 * <pre>
 * {
 * 	&#64;code
 *     //add the code here
 * }
 * </pre>
 *
 * @author Nicolas Senet-Larson
 * @author GOXR3PLUS STUDIO
 * @version 1.0
 */
public class BorderlessScene extends Scene {

	/** The controller. */
	private BorderlessController controller;

	/** The root. */
	private BorderlessPane root;

	/** The Stage. */
	private Stage stage;

	/**
	 * The constructor.
	 *
	 * @param stage your stage.
	 * @param stageStyle <b>Undecorated</b> and <b>Transparent</b> StageStyles are accepted or else the Transparent StageStyle will be set.
	 * @param sceneRoot The root of the Scene
	 */
	public BorderlessScene(Stage stage, StageStyle stageStyle, Parent sceneRoot) {
		super(new Pane());
		try {
			this.controller = new BorderlessController();
			// Load the FXML
			this.root = new BorderlessPane(this.controller);

			// Set Scene root
			setRoot(this.root);
			setContent(sceneRoot);

			// Initialize the Controller
			this.controller.setStage(stage);
			this.controller.createTransparentWindow(stage);

			// StageStyle
			stage.initStyle(stageStyle);
			if (stageStyle == StageStyle.UTILITY) {
				setSnapEnabled(false);
				setResizable(false);
			}

			// Stage
			this.stage = stage;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * The constructor.
	 *
	 * @param stage your stage.
	 * @param stageStyle <b>Undecorated</b> and <b>Transparent</b> StageStyles are accepted or else the Transparent StageStyle will be set.
	 * @param sceneRoot The root of the Scene
	 * @param minWidth The minimum width that the Stage can have
	 * @param minHeight The minimum height that the Stage can have
	 */
	public BorderlessScene(Stage stage, StageStyle stageStyle, Parent sceneRoot, double minWidth, double minHeight) {
		this(stage, stageStyle, sceneRoot);

		// minSize
		stage.setMinWidth(minWidth);
		stage.setMinHeight(minHeight);
	}

	/**
	 * Change the content of the scene.
	 *
	 * @param content the root Parent of your new content.
	 */
	public void setContent(Parent content) {
		this.root.getChildren().remove(0);
		this.root.getChildren().add(0, content);
		AnchorPane.setLeftAnchor(content, 0.0D);
		AnchorPane.setTopAnchor(content, 0.0D);
		AnchorPane.setRightAnchor(content, 0.0D);
		AnchorPane.setBottomAnchor(content, 0.0D);
	}

	/**
	 * Set a node that can be pressed and dragged to move the application around.
	 *
	 * @param node the node.
	 */
	public void setMoveControl(Node node) {
		this.controller.setMoveControl(node);
	}

	/**
	 * Toggle to maximize the application.
	 */
	public void maximizeStage() {
		controller.maximize();
	}

	/**
	 * Minimize the stage to the taskbar.
	 */
	public void minimizeStage() {
		controller.minimize();
	}

	/**
	 * Disable/enable the resizing of your stage. Enabled by default.
	 *
	 * @param bool false to disable, true to enable.
	 */
	public void setResizable(boolean bool) {
		controller.setResizable(bool);
	}

	/**
	 * Disable/enable the Aero Snap of your stage. Enabled by default.
	 *
	 * @param bool false to disable, true to enable.
	 */
	public void setSnapEnabled(boolean bool) {
		controller.setSnapEnabled(bool);
	}

	/**
	 * Maximized property.
	 *
	 * @return Maximized property
	 */
	public BooleanProperty maximizedProperty() {
		return controller.maximizedProperty();
	}

	/**
	 * Resizable property.
	 *
	 * @return Resizable property
	 */
	public BooleanProperty resizableProperty() {
		return controller.resizableProperty();
	}

	/**
	 * Aero Snap property.
	 *
	 * @return Aero Snap property
	 */
	public BooleanProperty snapProperty() {
		return controller.snapProperty();
	}

	/**
	 * True if the stage is maximized or false if not
	 *
	 * @return True if the stage is maximized or false if not
	 */
	public boolean isMaximized() {
		return controller.maximizedProperty().get();
	}

	/**
	 * True if the stage is resizable or false if not
	 *
	 * @return True if the stage is resizable or false if not
	 */
	public boolean isResizable() {
		return controller.resizableProperty().get();
	}

	/**
	 * True if Aero Snap is enabled or false if not
	 *
	 * @return True if Aero Snap is enabled or false if not
	 */
	public boolean isSnapEnabled() {
		return controller.snapProperty().get();
	}

	/**
	 * Returns the width and height of the application when windowed.
	 *
	 * @return instance of Delta class. Delta.x = width, Delta.y = height.
	 */
	public Delta getWindowedSize() {
		if (controller.prevSize.x == null)
			controller.prevSize.x = stage.getWidth();
		if (controller.prevSize.y == null)
			controller.prevSize.y = stage.getHeight();
		return controller.prevSize;
	}

	/**
	 * Returns the x and y position of the application when windowed.
	 *
	 * @return instance of Delta class. Use Delta.x and Delta.y.
	 */
	public Delta getWindowedPositon() {
		if (controller.prevPos.x == null)
			controller.prevPos.x = stage.getX();
		if (controller.prevPos.y == null)
			controller.prevPos.y = stage.getY();
		return controller.prevPos;
	}

	/**
	 * Removes the default css style of the corners
	 */
	public void removeDefaultCSS() {
		this.root.getStylesheets().remove(0);
	}

	/**
	 * The transparent window which allows the library to have aerosnap controls can be styled using this method .
	 * It is nothing more than a StackPane in a transparent window , so for example you can change it's background color , borders , everything through this method :)
	 *
	 * @param style The style of the transparent window of the application
	 */
	public void setTransparentWindowStyle(String style) {
		controller.getTransparentWindow().setStyle("");
		controller.getTransparentWindow().setStyle(style);
	}

}
