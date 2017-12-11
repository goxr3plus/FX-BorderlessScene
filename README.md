# FX-BorderlessScene
Undecorated JavaFX Scene with implemented move, resize, minimise, maximise, close and Windows Aero Snap controls.

### Information

This project has been forked and further developed from this github repository -> https://github.com/NicolasSenetLarson/BorderlessScene

### XR3Player is using this Library :) -> https://github.com/goxr3plus/XR3Player

### Use it with Maven , Gradle etc ... with 

https://jitpack.io/#goxr3plus/FX-BorderlessScene

``` XML
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>
```
  
  ``` XML
  <dependency>
    <groupId>com.github.goxr3plus</groupId>
    <artifactId>FX-BorderlessScene</artifactId>
    <version>V2.0.0</version>
  </dependency>
  ```

### Example Tester 

``` JAVA
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//BorderPane
		BorderPane borderPane = new BorderPane();
		
		//Create a Top Label
		Label topLabel = new Label("Drag Me :)");
		topLabel.setMinHeight(50);
		topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		topLabel.setStyle("-fx-background-color:#303030; -fx-text-fill:white; -fx-font-weight:bold;");
		topLabel.setAlignment(Pos.CENTER);
		borderPane.setTop(topLabel);
		
		//Close Button
		Button closeButton = new Button("Exit");
		closeButton.setOnAction(a -> primaryStage.close());
		
		// Constructor using your primary stage and the root Parent of your content.
		BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, borderPane, 100, 100);
		primaryStage.setScene(scene); // Set the scene to your stage and you're done!
		
		//Close Button
		Button removeDefaultCSS = new Button("Remove Default Corners CSS");
		removeDefaultCSS.setOnAction(a -> scene.removeDefaultCSS());
		
		//BorderPane
		HBox hbox = new HBox(removeDefaultCSS,closeButton);
		hbox.setAlignment(Pos.CENTER);
		hbox.setSpacing(15);
		borderPane.setCenter(hbox);
		
		//remove the default css style
		//scene.removeDefaultCSS();
		
		// Maximise (on/off) and minimise the application:
		//scene.maximizeStage();
		//scene.minimizeStage();
		
		// To move the window around by pressing a node:
		scene.setMoveControl(topLabel);
		
		// To disable resize:
		//scene.setResizable(false);
		
		// To switch the content during runtime:
		//scene.setContent(yourNewParent);
		
		// Check if maximised:
		//Boolean bool = scene.isMaximised();
		
		// Get windowed* size and position:
		//scene.getWindowedSize();
		//scene.getWindowedPosition();
		
		//Show
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}
```
