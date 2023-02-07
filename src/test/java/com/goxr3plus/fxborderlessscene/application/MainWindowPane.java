package com.goxr3plus.fxborderlessscene.application;

import com.goxr3plus.fxborderlessscene.borderless.BorderlessScene;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class MainWindowPane extends StackPane {

    private Label bottomLabel;

    private Button transWStyle1;

    private Button transWStyle2;

    private Button mainWStyle1;

    private Button mainWStyle2;

    private Button mainWRemoveStyle;

    private Label topLabel;

    private Button minimize;

    private Button maximizeNormalize;

    private Button exit;

    private RadioButton aeroSnap;

    private BorderlessScene borderlessScene;
    private final BorderPane borderPane;

    /**
     * Constructor.
     */
    public MainWindowPane() {
        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setPrefSize(754, 464);
        getStylesheets().add(getClass().getResource("/css/extra.css").toExternalForm());
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: #202020; -fx-border-width: 3;");
        borderPane.getStyleClass().add("applicationSettingsLabel2");
        initTop();
        initBottom();
        initCenter();
        getChildren().add(borderPane);
    }

    private void initCenter() {
        VBox centerBox = new VBox();
        BorderPane.setAlignment(centerBox, Pos.CENTER);
        centerBox.setAlignment(Pos.CENTER);

        Label styleLabel = new Label();
        styleLabel.getStyleClass().add("white-black-label");
        styleLabel.setText("-----Transparent Aero Snap Background Window-----");

        transWStyle1 = new Button();
        transWStyle1.getStyleClass().add("jfx-button-green");
        transWStyle1.setText("Apply Style 1");
        transWStyle1.setCursor(Cursor.HAND);

        transWStyle2 = new Button();
        transWStyle2.getStyleClass().add("jfx-button-green");
        transWStyle2.setText("Apply Style 2");
        transWStyle2.setCursor(Cursor.HAND);
        HBox styleButtonBox = new HBox(5.0);
        styleButtonBox.setAlignment(Pos.CENTER);
        styleButtonBox.setPrefSize(200, 100);
        styleButtonBox.getChildren().addAll(transWStyle1, transWStyle2);

        Label checkTransparentWinLabel = new Label();
        checkTransparentWinLabel.setStyle("-fx-font-weight: bold;");
        checkTransparentWinLabel.setText("So to check the background transparent window , move the window to the corners of your computer screen");
        checkTransparentWinLabel.setTextAlignment(TextAlignment.CENTER);
        checkTransparentWinLabel.setWrapText(true);

        Separator separator = new Separator();
        separator.setPrefWidth(200.0);
        VBox.setMargin(separator, new Insets(15.0, 0.0, 15.0, 0.0));

        Label toDoLabel = new Label();
        toDoLabel.setLayoutX(275.0);
        toDoLabel.setLayoutY(220.0);
        toDoLabel.setStyle("-fx-background-color: firebrick; -fx-text-fill: white; -fx-font-weight: bold;");
        toDoLabel.setText("This feature is not yet implemeted , help me do it :)");

        Label generalLabel = new Label();
        generalLabel.setLayoutX(251.0);
        generalLabel.setLayoutY(90.0);
        generalLabel.getStyleClass().add("white-black-label");
        generalLabel.setText("----- Main Window general styling-----");
        VBox.setMargin(generalLabel, new Insets(15.0, 0.0, 5.0, 0.0));

        mainWStyle1 = new Button();
        mainWStyle1.getStyleClass().add("jfx-button-green");
        mainWStyle1.setText("Apply Style 1");
        mainWStyle1.setCursor(Cursor.HAND);

        mainWStyle2 = new Button();
        mainWStyle2.getStyleClass().add("jfx-button-green");
        mainWStyle2.setText("Apply Style 2");
        mainWStyle2.setCursor(Cursor.HAND);

        mainWRemoveStyle = new Button();
        mainWRemoveStyle.setLayoutX(401.0);
        mainWRemoveStyle.setLayoutY(48.0);
        mainWRemoveStyle.getStyleClass().add("jfx-button-green");
        mainWRemoveStyle.setText("Remove styling");
        mainWRemoveStyle.setCursor(Cursor.HAND);

        HBox styleBtnBox = new HBox(5.0);
        styleBtnBox.setAlignment(Pos.CENTER);
        styleBtnBox.setDisable(true);
        styleBtnBox.setLayoutX(10.0);
        styleBtnBox.setLayoutY(27.0);
        styleBtnBox.setPrefSize(200, 100);
        styleBtnBox.getChildren().addAll(mainWStyle1, mainWStyle2, mainWRemoveStyle);

        Label resizeTipsLabel = new Label();
        resizeTipsLabel.setStyle("-fx-font-weight: bold;");
        resizeTipsLabel.setText("Try to resize this window , you will see the style has changed ");
        resizeTipsLabel.setTextAlignment(TextAlignment.CENTER);
        resizeTipsLabel.setWrapText(true);

        Separator separator0 = new Separator();
        separator0.setLayoutX(10.0);
        separator0.setLayoutY(211.0);
        separator0.setPrefWidth(200.0);
        VBox.setMargin(separator0, new Insets(15.0, 0.0, 0.0, 0.0));

        centerBox.getChildren().addAll(styleLabel, styleButtonBox, checkTransparentWinLabel, separator, toDoLabel, generalLabel, styleBtnBox, resizeTipsLabel, separator0);
        borderPane.setCenter(centerBox);
    }

    private void initBottom() {
        bottomLabel = new Label();
        bottomLabel.setAlignment(Pos.CENTER);
        bottomLabel.setMaxWidth(Double.MAX_VALUE);
        bottomLabel.getStyleClass().add("online-music-title-label");
        bottomLabel.setText("Window Maximized : false");
        BorderPane.setAlignment(bottomLabel, Pos.CENTER);
        borderPane.setBottom(bottomLabel);
    }

    private void initTop() {
        topLabel = new Label();
        topLabel.setAlignment(Pos.CENTER);
        topLabel.setMaxWidth(Double.MAX_VALUE);
        topLabel.setStyle("-fx-cursor: move;");
        topLabel.getStyleClass().add("online-music-title-label");
        topLabel.setText("Drag here to move the window");
        topLabel.setCursor(Cursor.MOVE);
        HBox.setHgrow(topLabel, Priority.ALWAYS);

        aeroSnap = new RadioButton();
        aeroSnap.setMaxHeight(Double.MAX_VALUE);
        aeroSnap.setMaxWidth(Double.MAX_VALUE);
        aeroSnap.setSelected(true);
        aeroSnap.getStyleClass().add("jfx-button-green");
        aeroSnap.setText("Aero Snap");

        minimize = new Button();
        minimize.setMaxHeight(USE_PREF_SIZE);
        minimize.setMaxWidth(USE_PREF_SIZE);
        minimize.setMinHeight(USE_PREF_SIZE);
        minimize.setMinWidth(USE_PREF_SIZE);
        minimize.setPrefHeight(36.0);
        minimize.setPrefWidth(39.0);
        minimize.setStyle("-fx-background-radius: 0;");
        minimize.getStyleClass().add("jfx-button-green");
        minimize.setText("-");
        minimize.setCursor(Cursor.HAND);

        maximizeNormalize = new Button();
        maximizeNormalize.setLayoutX(663.0);
        maximizeNormalize.setLayoutY(10.0);
        maximizeNormalize.setMaxHeight(USE_PREF_SIZE);
        maximizeNormalize.setMaxWidth(USE_PREF_SIZE);
        maximizeNormalize.setMinHeight(USE_PREF_SIZE);
        maximizeNormalize.setMinWidth(USE_PREF_SIZE);
        maximizeNormalize.setPrefHeight(36.0);
        maximizeNormalize.setPrefWidth(39.0);
        maximizeNormalize.setStyle("-fx-background-radius: 0;");
        maximizeNormalize.getStyleClass().add("jfx-button-yellow");
        maximizeNormalize.setText("+");
        maximizeNormalize.setCursor(Cursor.HAND);
        exit = new Button();
        exit.setLayoutX(702.0);
        exit.setLayoutY(10.0);
        exit.setMaxHeight(USE_PREF_SIZE);
        exit.setMaxWidth(USE_PREF_SIZE);
        exit.setMinHeight(USE_PREF_SIZE);
        exit.setMinWidth(USE_PREF_SIZE);
        exit.setPrefSize(56, 36);
        exit.setStyle("-fx-background-radius: 0;");
        exit.getStyleClass().add("jfx-button-red");
        exit.setText("Exit");
        exit.setCursor(Cursor.HAND);
        HBox topBox = new HBox(2.0);
        BorderPane.setAlignment(topBox, Pos.CENTER);
        topBox.setAlignment(Pos.CENTER);
        topBox.getChildren().addAll(topLabel, aeroSnap, minimize, maximizeNormalize, exit);
        borderPane.setTop(topBox);
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

        exit.setOnAction(a -> ((Stage) getScene().getWindow()).close());

        minimize.setOnAction(a -> ((Stage) getScene().getWindow()).setIconified(true));

        maximizeNormalize.setOnAction(a -> ((BorderlessScene) getScene()).maximizeStage());

        aeroSnap.selectedProperty().bindBidirectional(((BorderlessScene) getScene()).snapProperty());

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
