# FX-BorderlessScene

[![Latest Version](https://img.shields.io/github/release/goxr3plus/FX-BorderlessScene.svg?style=flat-square)](https://github.com/goxr3plus/FX-BorderlessScene/releases)
[![GitHub contributors][contributors-image]][contributors-url]
[![HitCount](http://hits.dwyl.io/goxr3plus/FX-BorderlessScene.svg)](http://hits.dwyl.io/goxr3plus/FX-BorderlessScene)
[![Total Downloads](https://img.shields.io/github/downloads/goxr3plus/FX-BorderlessScene/total.svg)](https://github.com/goxr3plus/FX-BorderlessScene/releases)


[contributors-url]: https://github.com/goxr3plus/FX-BorderlessScene/graphs/contributors
[contributors-image]: https://img.shields.io/github/contributors/goxr3plus/FX-BorderlessScene.svg



![java_2019-04-30_17-43-54](https://user-images.githubusercontent.com/20374208/56970311-8b0df380-6b6f-11e9-83f1-65a5e4a03b8a.png)



Undecorated JavaFX Scene with :

**1)** implemented move

**2)** resize, minimise, maximise, close

**3)** Windows Aero Snap controls.

**4)** Styling Aero Snap window

**5)** Styling Main window

This project has been forked and further developed from [this](https://github.com/NicolasSenetLarson/BorderlessScene) github repository

### Use it with Maven , Gradle etc ... with 

https://jitpack.io/#goxr3plus/FX-BorderlessScene

Add JitPack on your repositories :
``` XML
<repositories>
   <repository>
     <id>jitpack.io</id>
     <url>https://jitpack.io</url>
   </repository>
</repositories>
```

Add the dependency : 
  
``` XML
<dependency>
  <groupId>com.github.goxr3plus</groupId>
  <artifactId>FX-BorderlessScene</artifactId>
  <version>4.3.1</version>
</dependency>
```

### > Advanced example (styling AeroSnap Window , spying window maximize status etc ) with ready code for you to run 

Main Window -> [here](https://github.com/goxr3plus/FX-BorderlessScene/blob/master/src/main/java/com/goxr3plus/fxborderlessscene/application/Main.java)

Main Window Controller -> [here](https://github.com/goxr3plus/FX-BorderlessScene/blob/master/src/main/java/com/goxr3plus/fxborderlessscene/application/MainWindowController.java)

### > Simple example

``` JAVA
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
		BorderlessScene scene = new BorderlessScene(primaryStage, StageStyle.UNDECORATED, borderPane, 250, 250);
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
		primaryStage.setTitle("Draggable and Undecorated JavaFX Window");
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

```

### [XR3Player](https://github.com/goxr3plus/XR3Player) is using this Library 
| Video|
|:-:|
| [![First](https://user-images.githubusercontent.com/20374208/48313813-34fdc180-e5ca-11e8-9da7-c6148dc0cbe5.png)](https://www.youtube.com/watch?v=7Hai7cavmUY)  |
---


### Donate if you love me 

<a href="https://patreon.com/preview/8adae1b75d654b2899e04a9e1111f0eb" title="Donate to this project using Patreon"><img src="https://img.shields.io/badge/patreon-donate-yellow.svg" alt="Patreon donate button" /></a>
<a href="https://www.paypal.me/GOXR3PLUSCOMPANY" title="Donate to this project using Paypal"><img src="https://img.shields.io/badge/paypal-donate-yellow.svg" alt="PayPal donate button" /></a>
