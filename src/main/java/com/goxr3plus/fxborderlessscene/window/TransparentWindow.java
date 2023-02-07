/**
 * OOUUUUUUUUU PARTYYYYYYYYYYYYYYYYYYYYY!
 */
package com.goxr3plus.fxborderlessscene.window;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.*;

/**
 * @author GOXR3PLUS
 *
 */
public class TransparentWindow extends StackPane {
	
	/** The Window */
	private Stage window = new Stage();
	
	/**
	 * @author GOXR3PLUS
	 *
	 */
	public enum ConsoleTab {
		CONSOLE, SPEECH_RECOGNITION;
	}
	
	/**
	 * Constructor
	 */
	public TransparentWindow() {
		getStyleClass().add("transparent-window");
		getStylesheets().add(getClass().getResource("/css/borderless-scene.css").toExternalForm());
		//Window
		window.setTitle("Transparent Window");
		window.initStyle(StageStyle.TRANSPARENT);
		window.initModality(Modality.NONE);
		window.setScene(new Scene(this, Color.TRANSPARENT));
	}

	
	/**
	 * @return the window
	 */
	public Stage getWindow() {
		return window;
	}
	
	/**
	 * Close the Window
	 */
	public void close() {
		window.close();
	}
	
	/**
	 * Show the Window
	 */
	public void show() {
		if (!window.isShowing())
			window.show();
		else
			window.requestFocus();
	}
	
}
