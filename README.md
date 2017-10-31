# FX-BorderlessScene
Undecorated JavaFX Scene with implemented move, resize, minimise, maximise, close and Windows Aero Snap controls.

### Information

This project has been forked and further developed from this github repository -> https://github.com/NicolasSenetLarson/BorderlessScene

### Use it with Maven , Gradle etc ... with https://jitpack.io/#goxr3plus/FX-BorderlessScene

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
    <version>V1.0.0</version>
  </dependency>
  ```

### Example Tester 

``` JAVA
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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
		topLabel.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		topLabel.setStyle("-fx-background-color:#303030; -fx-text-fill:white; -fx-font-weight:bold;");
		topLabel.setAlignment(Pos.CENTER);
		borderPane.setTop(topLabel);
		
		//Create a Button
		borderPane.setCenter(new Button("hi i am a button"));
		
		// Constructor using your primary stage and the root Parent of your content.
		BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, borderPane, 300, 300);
		primaryStage.setScene(scene); // Set the scene to your stage and you're done!
		
		// Maximise (on/off) and minimise the application:
		//scene.maximizeStage();
		//scene.minimizeStage();
		
		// To move the window around by pressing a node:
		scene.setMoveControl(topLabel);
		
		// To disable resize:
		scene.setResizable(false);
		
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
