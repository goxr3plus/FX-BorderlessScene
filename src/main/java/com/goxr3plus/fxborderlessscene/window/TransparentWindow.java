/**
 * OOUUUUUUUUU PARTYYYYYYYYYYYYYYYYYYYYY!
 */
package main.java.com.goxr3plus.fxborderlessscene.window;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author GOXR3PLUS
 *
 */
public class TransparentWindow extends StackPane {
	
	//--------------------------------------------------------
	
	@FXML
	private StackPane stackPane;
	
	//--------------------------------------------------------
	
	/** The logger. */
	private Logger logger = Logger.getLogger(getClass().getName());
	
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
		
		// ------------------------------------FXMLLOADER
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/TransparentWindow.fxml"));
		loader.setController(this);
		loader.setRoot(this);
		
		try {
			loader.load();
		} catch (IOException ex) {
			logger.log(Level.SEVERE, "", ex);
		}
		
		//Window
		window.setTitle("Transparent Window");
		window.initStyle(StageStyle.TRANSPARENT);
		window.initModality(Modality.NONE);
		window.setScene(new Scene(this, Color.TRANSPARENT));
	}
	
	/**
	 * Called as soon as .fxml is initialized
	 */
	@FXML
	private void initialize() {
		
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
