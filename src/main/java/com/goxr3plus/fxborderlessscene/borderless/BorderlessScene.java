/*
 *
 */
package com.goxr3plus.fxborderlessscene.borderless;

import javafx.beans.property.BooleanProperty;
import javafx.scene.*;
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

    /**
     * The borderless pane.
     */
    private BorderlessPane borderlessPane;

    /**
     * The Stage.
     */
    private Stage stage;

    /**
     * The constructor.
     *
     * @param stage      your stage.
     * @param stageStyle <b>Undecorated</b> and <b>Transparent</b> StageStyles are accepted or else the Transparent StageStyle will be set.
     * @param sceneRoot  The root of the Scene
     */
    public BorderlessScene(Stage stage, StageStyle stageStyle, Parent sceneRoot) {
        super(new Pane());

        this.borderlessPane = new BorderlessPane();
        // Set Scene root
        setRoot(borderlessPane);
        setContent(sceneRoot);

        // Initialize the Controller
        this.borderlessPane.setStage(stage);
        this.borderlessPane.createTransparentWindow(stage);

        // StageStyle
        stage.initStyle(stageStyle);
        if (stageStyle == StageStyle.UTILITY) {
            setSnapEnabled(false);
            setResizable(false);
        }
        // Stage
        this.stage = stage;
    }

    /**
     * The constructor.
     *
     * @param stage      your stage.
     * @param stageStyle <b>Undecorated</b> and <b>Transparent</b> StageStyles are accepted or else the Transparent StageStyle will be set.
     * @param sceneRoot  The root of the Scene
     * @param minWidth   The minimum width that the Stage can have
     * @param minHeight  The minimum height that the Stage can have
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
        borderlessPane.getChildren().remove(0);
        borderlessPane.getChildren().add(0, content);
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
        this.borderlessPane.setMoveControl(node);
    }

    /**
     * Toggle to maximize the application.
     */
    public void maximizeStage() {
        borderlessPane.maximize();
    }

    /**
     * Minimize the stage to the taskbar.
     */
    public void minimizeStage() {
        borderlessPane.minimize();
    }

    /**
     * Disable/enable the resizing of your stage. Enabled by default.
     *
     * @param bool false to disable, true to enable.
     */
    public void setResizable(boolean bool) {
        borderlessPane.setResizable(bool);
    }

    /**
     * Disable/enable the Aero Snap of your stage. Enabled by default.
     *
     * @param bool false to disable, true to enable.
     */
    public void setSnapEnabled(boolean bool) {
        borderlessPane.setSnapEnabled(bool);
    }

    /**
     * Maximized property.
     *
     * @return Maximized property
     */
    public BooleanProperty maximizedProperty() {
        return borderlessPane.maximizedProperty();
    }

    /**
     * Resizable property.
     *
     * @return Resizable property
     */
    public BooleanProperty resizableProperty() {
        return borderlessPane.resizableProperty();
    }

    /**
     * Aero Snap property.
     *
     * @return Aero Snap property
     */
    public BooleanProperty snapProperty() {
        return borderlessPane.snapProperty();
    }

    /**
     * True if the stage is maximized or false if not
     *
     * @return True if the stage is maximized or false if not
     */
    public boolean isMaximized() {
        return borderlessPane.maximizedProperty().get();
    }

    /**
     * True if the stage is resizable or false if not
     *
     * @return True if the stage is resizable or false if not
     */
    public boolean isResizable() {
        return borderlessPane.resizableProperty().get();
    }

    /**
     * True if Aero Snap is enabled or false if not
     *
     * @return True if Aero Snap is enabled or false if not
     */
    public boolean isSnapEnabled() {
        return borderlessPane.snapProperty().get();
    }

    /**
     * Returns the width and height of the application when windowed.
     *
     * @return instance of Delta class. Delta.x = width, Delta.y = height.
     */
    public Delta getWindowedSize() {
        if (borderlessPane.prevSize.x == null)
            borderlessPane.prevSize.x = stage.getWidth();
        if (borderlessPane.prevSize.y == null)
            borderlessPane.prevSize.y = stage.getHeight();
        return borderlessPane.prevSize;
    }

    /**
     * Returns the x and y position of the application when windowed.
     *
     * @return instance of Delta class. Use Delta.x and Delta.y.
     */
    public Delta getWindowedPositon() {
        if (borderlessPane.prevPos.x == null)
            borderlessPane.prevPos.x = stage.getX();
        if (borderlessPane.prevPos.y == null)
            borderlessPane.prevPos.y = stage.getY();
        return borderlessPane.prevPos;
    }

    /**
     * Removes the default css style of the corners
     */
    public void removeDefaultCSS() {
        borderlessPane.getStylesheets().remove(0);
    }

    /**
     * The transparent window which allows the library to have aerosnap controls can be styled using this method .
     * It is nothing more than a StackPane in a transparent window , so for example you can change it's background color , borders , everything through this method :)
     *
     * @param style The style of the transparent window of the application
     */
    public void setTransparentWindowStyle(String style) {
        borderlessPane.getTransparentWindow().setStyle("");
        borderlessPane.getTransparentWindow().setStyle(style);
    }

}
