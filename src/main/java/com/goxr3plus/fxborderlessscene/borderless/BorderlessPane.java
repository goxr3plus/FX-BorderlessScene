/*
 *
 */
package com.goxr3plus.fxborderlessscene.borderless;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

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
public class BorderlessPane extends AnchorPane {



	public BorderlessPane () throws IOException {

		// ------------------------------------FXMLLOADER ----------------------------------------

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Borderless.fxml"));
		loader.setController(this);
		loader.setRoot(this);
        loader.load();

	}


	/**
	 * Called as soon as .fxml is initialised
	 */
	@FXML
	private void initialize() {

	}
}
