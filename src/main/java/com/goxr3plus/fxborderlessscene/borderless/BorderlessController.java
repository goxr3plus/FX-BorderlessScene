/*
 * 
 */
package main.java.com.goxr3plus.fxborderlessscene.borderless;

import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import main.java.com.goxr3plus.fxborderlessscene.window.TransparentWindow;


/**
 * Controller implements window controls: maximize, minimize, drag, and Aero Snap.
 * 
 * @author Nicolas Senet-Larson
 * @author GOXR3PLUS STUDIO
 * @version 1.0
 */
public class BorderlessController {
	
	/** The stage. */
	private Stage stage;
	
	/** The prev size. */
	protected Delta prevSize;
	
	/** The prev pos. */
	protected Delta prevPos;
	
	/** The maximized. */
	private ReadOnlyBooleanWrapper maximized;
	
	/** The resizable **/
	private ReadOnlyBooleanWrapper resizable;
	
	/** The snap enabled */
	private ReadOnlyBooleanWrapper snap;
	
	/** The snapped. */
	private boolean snapped;
	
	/** The left pane. */
	@FXML
	private Pane leftPane;
	
	/** The right pane. */
	@FXML
	private Pane rightPane;
	
	/** The top pane. */
	@FXML
	private Pane topPane;
	
	/** The bottom pane. */
	@FXML
	private Pane bottomPane;
	
	/** The top left pane. */
	@FXML
	private Pane topLeftPane;
	
	/** The top right pane. */
	@FXML
	private Pane topRightPane;
	
	/** The bottom left pane. */
	@FXML
	private Pane bottomLeftPane;
	
	/** The bottom right pane. */
	@FXML
	private Pane bottomRightPane;
	
	/** The bottom. */
	String bottom = "bottom";
	
	/**
	 * Transparent Window used to show how the window will be resized
	 */
	private TransparentWindow transparentWindow;
	
	/**
	 * The constructor.
	 */
	public BorderlessController() {
		prevSize = new Delta();
		prevPos = new Delta();
		maximized = new ReadOnlyBooleanWrapper(false);
		resizable = new ReadOnlyBooleanWrapper(true);
		snap = new ReadOnlyBooleanWrapper(true);
		snapped = false;
		
	}
	
	/**
	 * Creates the Transparent Window
	 * 
	 * @param parentWindow
	 *            The parentWindow of the TransparentWindow
	 */
	public void createTransparentWindow(Stage parentWindow) {
		
		transparentWindow = new TransparentWindow();
		transparentWindow.getWindow().initOwner(parentWindow);
	}
	
	/**
	 * Maximized property.
	 *
	 * @return Maximized property
	 */
	public ReadOnlyBooleanProperty maximizedProperty() {
		return maximized.getReadOnlyProperty();
	}
	
	/**
	 * Resizable property.
	 *
	 * @return Resizable property
	 */
	public ReadOnlyBooleanProperty resizableProperty() {
		return resizable.getReadOnlyProperty();
	}
	
	/**
	 * Aero Snap property.
	 *
	 * @return Aero Snap property
	 */
	public ReadOnlyBooleanProperty snapProperty() {
		return snap.getReadOnlyProperty();
	}
	
	/**
	 * Called after the FXML layout is loaded.
	 */
	@FXML
	private void initialize() {
		setResizeControl(leftPane, "left");
		setResizeControl(rightPane, "right");
		setResizeControl(topPane, "top");
		setResizeControl(bottomPane, bottom);
		setResizeControl(topLeftPane, "top-left");
		setResizeControl(topRightPane, "top-right");
		setResizeControl(bottomLeftPane, bottom + "-left");
		setResizeControl(bottomRightPane, bottom + "-right");
		
		BooleanBinding negateOfResizable = resizable.not();
		leftPane.disableProperty().bind(negateOfResizable);
		rightPane.disableProperty().bind(negateOfResizable);
		topPane.disableProperty().bind(negateOfResizable);
		bottomPane.disableProperty().bind(negateOfResizable);
		topLeftPane.disableProperty().bind(negateOfResizable);
		topRightPane.disableProperty().bind(negateOfResizable);
		bottomLeftPane.disableProperty().bind(negateOfResizable);
		bottomRightPane.disableProperty().bind(negateOfResizable);
	}
	
	/**
	 * Set the Stage of the controller.
	 *
	 * @param primaryStage
	 *            the new stage
	 */
	protected void setStage(Stage primaryStage) {
		this.stage = primaryStage;
	}
	
	/**
	 * Maximize on/off the application.
	 */
	protected void maximize() {
		Rectangle2D screen;
		
		try {
			if (Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth() / 2, stage.getHeight() / 2).isEmpty())
				screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()).get(0).getVisualBounds();
			else
				screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth() / 2, stage.getHeight() / 2).get(0).getVisualBounds();
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return;
		}
		
		if (maximized.get()) {
			stage.setWidth(prevSize.x);
			stage.setHeight(prevSize.y);
			stage.setX(prevPos.x);
			stage.setY(prevPos.y);
			setMaximized(false);
		} else {
			// Record position and size, and maximize.
			if (!snapped) {
				prevSize.x = stage.getWidth();
				prevSize.y = stage.getHeight();
				prevPos.x = stage.getX();
				prevPos.y = stage.getY();
			} else if (!screen.contains(prevPos.x, prevPos.y)) {
				if (prevSize.x > screen.getWidth())
					prevSize.x = screen.getWidth() - 20;
				
				if (prevSize.y > screen.getHeight())
					prevSize.y = screen.getHeight() - 20;
				
				prevPos.x = screen.getMinX() + ( screen.getWidth() - prevSize.x ) / 2;
				prevPos.y = screen.getMinY() + ( screen.getHeight() - prevSize.y ) / 2;
			}
			
			stage.setX(screen.getMinX());
			stage.setY(screen.getMinY());
			stage.setWidth(screen.getWidth());
			stage.setHeight(screen.getHeight());
			
			setMaximized(true);
		}
	}
	
	/**
	 * Minimize the application.
	 */
	protected void minimize() {
		stage.setIconified(true);
	}
	
	/**
	 * Set a node that can be pressed and dragged to move the application around.
	 * 
	 * @param node
	 *            the node.
	 */
	protected void setMoveControl(final Node node) {
		final Delta delta = new Delta();
		final Delta eventSource = new Delta();
		
		// Record drag deltas on press.
		node.setOnMousePressed(m -> {
			if (m.isPrimaryButtonDown()) {
				delta.x = m.getSceneX(); //getX()
				delta.y = m.getSceneY(); //getY()
				
				if (maximized.get() || snapped) {
					delta.x = prevSize.x * ( m.getSceneX() / stage.getWidth() );//(m.getX() / stage.getWidth())
					delta.y = prevSize.y * ( m.getSceneY() / stage.getHeight() );//(m.getY() / stage.getHeight())
				} else {
					prevSize.x = stage.getWidth();
					prevSize.y = stage.getHeight();
					prevPos.x = stage.getX();
					prevPos.y = stage.getY();
				}
				
				eventSource.x = m.getScreenX();
				eventSource.y = node.prefHeight(stage.getHeight());
			}
		});
		
		// Dragging moves the application around.
		node.setOnMouseDragged(m -> {
			if (m.isPrimaryButtonDown()) {
				
				// Move x axis.
				stage.setX(m.getScreenX() - delta.x);
				
				if (snapped) {
					if (m.getScreenY() > eventSource.y) {
						snapOff();
					} else {
						Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getVisualBounds();
						stage.setHeight(screen.getHeight());
					}
				} else {
					// Move y axis.
					stage.setY(m.getScreenY() - delta.y);
				}
				
				// Aero Snap off.
				if (maximized.get()) {
					stage.setWidth(prevSize.x);
					stage.setHeight(prevSize.y);
					setMaximized(false);
				}
				
				boolean toCloseWindow = false;
				if(!snap.get()) {
					toCloseWindow = true;
				} else {
					//--------------------------Check here for Transparent Window--------------------------
					//Rectangle2D wholeScreen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getBounds()
					Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getVisualBounds();
					
					//----------TO BE ADDED IN FUTURE RELEASE , GAVE ME CANCER implementing them ..----------------
					
					//				// Aero Snap Top Right Corner
					//				if (m.getScreenY() <= screen.getMinY() && m.getScreenX() >= screen.getMaxX() - 1) {
					//					double difference;
					//					
					//					//Fix the positioning
					//					if (wholeScreen.getMaxX() > screen.getMaxX())
					//						difference = - ( wholeScreen.getWidth() - screen.getWidth() );
					//					else
					//						difference =  (wholeScreen.getWidth() - screen.getWidth()-15);
					//					
					//					System.out.println(difference);
					//					
					//					transparentWindow.getWindow().setX(wholeScreen.getWidth() / 2 + difference);
					//					transparentWindow.getWindow().setY(screen.getMinY());
					//					transparentWindow.getWindow().setWidth(screen.getWidth() / 2);
					//					transparentWindow.getWindow().setHeight(screen.getHeight() / 2);
					//					
					//					transparentWindow.show();
					//				}
					//				
					//				// Aero Snap Top Left Corner
					//				else if (m.getScreenY() <= screen.getMinY() && m.getScreenX() <= screen.getMinX()) {
					//					
					//					transparentWindow.getWindow().setX(screen.getMinX());
					//					transparentWindow.getWindow().setY(screen.getMinY());
					//					transparentWindow.getWindow().setWidth(screen.getWidth() / 2);
					//					transparentWindow.getWindow().setHeight(screen.getHeight() / 2);
					//					
					//					transparentWindow.show();
					//				}
					//				
					//				// Aero Snap Bottom Right Corner
					//				else if (m.getScreenY() >= screen.getMaxY() - 1 && m.getScreenX() >= screen.getMaxY()) {
					//					
					//					transparentWindow.getWindow().setX(wholeScreen.getWidth() / 2 - ( wholeScreen.getWidth() - screen.getWidth() ));
					//					transparentWindow.getWindow().setY(wholeScreen.getHeight() / 2 - ( wholeScreen.getHeight() - screen.getHeight() ));
					//					transparentWindow.getWindow().setWidth(wholeScreen.getWidth() / 2);
					//					transparentWindow.getWindow().setHeight(wholeScreen.getHeight() / 2);
					//					
					//					transparentWindow.show();
					//				}
					//				
					//				// Aero Snap Bottom Left Corner
					//				else if (m.getScreenY() >= screen.getMaxY() - 1 && m.getScreenX() <= screen.getMinX()) {
					//					
					//					transparentWindow.getWindow().setX(screen.getMinX());
					//					transparentWindow.getWindow().setY(wholeScreen.getHeight() / 2 - ( wholeScreen.getHeight() - screen.getHeight() ));
					//					transparentWindow.getWindow().setWidth(wholeScreen.getWidth() / 2);
					//					transparentWindow.getWindow().setHeight(wholeScreen.getHeight() / 2);
					//					
					//					transparentWindow.show();
					//				}
					
					// Aero Snap Left.
					if (m.getScreenX() <= screen.getMinX()) {
						transparentWindow.getWindow().setY(screen.getMinY());
						transparentWindow.getWindow().setHeight(screen.getHeight());
						
						transparentWindow.getWindow().setX(screen.getMinX());
						if (screen.getWidth() / 2 < transparentWindow.getWindow().getMinWidth()) {
							transparentWindow.getWindow().setWidth(transparentWindow.getWindow().getMinWidth());
						} else {
							transparentWindow.getWindow().setWidth(screen.getWidth() / 2);
						}
						
						transparentWindow.show();
					}
					
					// Aero Snap Right.
					else if (m.getScreenX() >= screen.getMaxX() - 1) {
						transparentWindow.getWindow().setY(screen.getMinY());
						transparentWindow.getWindow().setHeight(screen.getHeight());
						
						if (screen.getWidth() / 2 < transparentWindow.getWindow().getMinWidth()) {
							transparentWindow.getWindow().setWidth(transparentWindow.getWindow().getMinWidth());
						} else {
							transparentWindow.getWindow().setWidth(screen.getWidth() / 2);
						}
						transparentWindow.getWindow().setX(screen.getMaxX() - transparentWindow.getWindow().getWidth());
						
						transparentWindow.show();
					}
					
					// Aero Snap Top. || Aero Snap Bottom.
					else if (m.getScreenY() <= screen.getMinY() || m.getScreenY() >= screen.getMaxY() - 1) {
						
						transparentWindow.getWindow().setX(screen.getMinX());
						transparentWindow.getWindow().setY(screen.getMinY());
						transparentWindow.getWindow().setWidth(screen.getWidth());
						transparentWindow.getWindow().setHeight(screen.getHeight());
						
						transparentWindow.show();
					} else {
						toCloseWindow = true;
					}
					
					//				System.out.println("Mouse Position [ " + m.getScreenX() + "," + m.getScreenY() + " ]")
					//				System.out.println(" " + screen.getMinX() + "," + screen.getMinY() + " ," + screen.getMaxX() + " ," + screen.getMaxY())
					//				System.out.println()
				}
				
				if(toCloseWindow) {
					transparentWindow.close();	
				}
			}
		});
		
		// Maximize on double click.
		node.setOnMouseClicked(m -> {
			if (snap.get() && ( MouseButton.PRIMARY.equals(m.getButton()) ) && ( m.getClickCount() == 2 ))
				maximize();
		});
		
		// Aero Snap on release.
		node.setOnMouseReleased(m -> {
			
			try {
				if(!snap.get()) {
					return;
				}
			
			if ( ( MouseButton.PRIMARY.equals(m.getButton()) ) && ( m.getScreenX() != eventSource.x )) {
				Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getVisualBounds();
				
				// Aero Snap Left.
				if (m.getScreenX() <= screen.getMinX()) {
					stage.setY(screen.getMinY());
					stage.setHeight(screen.getHeight());
					
					stage.setX(screen.getMinX());
					if (screen.getWidth() / 2 < stage.getMinWidth()) {
						stage.setWidth(stage.getMinWidth());
					} else {
						stage.setWidth(screen.getWidth() / 2);
					}
					
					snapped = true;
				}
				
				// Aero Snap Right.
				else if (m.getScreenX() >= screen.getMaxX() - 1) {
					stage.setY(screen.getMinY());
					stage.setHeight(screen.getHeight());
					
					if (screen.getWidth() / 2 < stage.getMinWidth()) {
						stage.setWidth(stage.getMinWidth());
					} else {
						stage.setWidth(screen.getWidth() / 2);
					}
					stage.setX(screen.getMaxX() - stage.getWidth());
					
					snapped = true;
				}
				
				// Aero Snap Top ||  Aero Snap Bottom
				else if (m.getScreenY() <= screen.getMinY() || m.getScreenY() >= screen.getMaxY() - 1) {
					if (!screen.contains(prevPos.x, prevPos.y)) {
						if (prevSize.x > screen.getWidth())
							prevSize.x = screen.getWidth() - 20;
						
						if (prevSize.y > screen.getHeight())
							prevSize.y = screen.getHeight() - 20;
						
						prevPos.x = screen.getMinX() + ( screen.getWidth() - prevSize.x ) / 2;
						prevPos.y = screen.getMinY() + ( screen.getHeight() - prevSize.y ) / 2;
					}
					
					stage.setX(screen.getMinX());
					stage.setY(screen.getMinY());
					stage.setWidth(screen.getWidth());
					stage.setHeight(screen.getHeight());
					setMaximized(true);
				}
				
				//				System.out.println("Mouse Position [ " + m.getScreenX() + "," + m.getScreenY() + " ]")
				//				System.out.println(" " + screen.getMinX() + "," + screen.getMinY() + " ," + screen.getMaxX() + " ," + screen.getMaxY())
				//				System.out.println()
				
			}
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			
			//Hide the transparent window -- close this window no matter what
			transparentWindow.close();
		});
	}
	
	private void snapOff() {
		stage.setWidth(prevSize.x);
		stage.setHeight(prevSize.y);
		snapped = false;
	}
	
	/**
	 * Set pane to resize application when pressed and dragged.
	 * 
	 * @param pane
	 *            the pane the action is set to.
	 * @param direction
	 *            the resize direction. Diagonal: 'top' or 'bottom' + 'right' or 'left'. [[SuppressWarningsSpartan]]
	 */
	private void setResizeControl(Pane pane , final String direction) {
		
		//Record the previous size and previous point
		pane.setOnDragDetected((event) -> {
			prevSize.x = stage.getWidth();
			prevSize.y = stage.getHeight();
			prevPos.x = stage.getX();
			prevPos.y = stage.getY();
		});
		
		pane.setOnMouseDragged(m -> {
			if (m.isPrimaryButtonDown()) {
				double width = stage.getWidth();
				double height = stage.getHeight();
				
				// Horizontal resize.
				if (direction.endsWith("left")) {
					double comingWidth = width - m.getScreenX() + stage.getX();
					
					//Check if it violates minimumWidth
					if (comingWidth > 0 && comingWidth >= stage.getMinWidth()) {
						stage.setWidth(stage.getX() - m.getScreenX() + stage.getWidth());
						stage.setX(m.getScreenX());
					}
					
				} else if (direction.endsWith("right")) {
					double comingWidth = width + m.getX();
					
					//Check if it violates
					if (comingWidth > 0 && comingWidth >= stage.getMinWidth())
						stage.setWidth(m.getSceneX());
				}
				
				// Vertical resize.
				if (direction.startsWith("top")) {
					if (snapped) {
						stage.setHeight(prevSize.y);
						snapped = false;
					} else if ( ( height > stage.getMinHeight() ) || ( m.getY() < 0 )) {
						stage.setHeight(stage.getY() - m.getScreenY() + stage.getHeight());
						stage.setY(m.getScreenY());
					}
				} else if (direction.startsWith(bottom)) {
					if (snapped) {
						stage.setY(prevPos.y);
						snapped = false;
					} else {
						double comingHeight = height + m.getY();
						
						//Check if it violates
						if (comingHeight > 0 && comingHeight >= stage.getMinHeight())
							stage.setHeight(m.getSceneY());
					}
					
				}
			}
		});
		
		// Record application height and y position.
		pane.setOnMousePressed(m -> {
			if ( ( m.isPrimaryButtonDown() ) && ( !snapped )) {
				prevSize.y = stage.getHeight();
				prevPos.y = stage.getY();
			}
			
		});
		
		// Aero Snap Resize.
		pane.setOnMouseReleased(m -> {
			if ( ( MouseButton.PRIMARY.equals(m.getButton()) ) && ( !snapped )) {
				Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getVisualBounds();
				
				if ( ( stage.getY() <= screen.getMinY() ) && ( direction.startsWith("top") )) {
					stage.setHeight(screen.getHeight());
					stage.setY(screen.getMinY());
					snapped = true;
				}
				
				if ( ( m.getScreenY() >= screen.getMaxY() ) && ( direction.startsWith(bottom) )) {
					stage.setHeight(screen.getHeight());
					stage.setY(screen.getMinY());
					snapped = true;
				}
			}
			
		});
		
		// Aero Snap resize on double click.
		pane.setOnMouseClicked(m -> {
			if ( ( MouseButton.PRIMARY.equals(m.getButton()) ) && ( m.getClickCount() == 2 ) && ( "top".equals(direction) || bottom.equals(direction) )) {
				Rectangle2D screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth() / 2, stage.getHeight() / 2).get(0).getVisualBounds();
				
				if (snapped) {
					stage.setHeight(prevSize.y);
					stage.setY(prevPos.y);
					snapped = false;
				} else {
					prevSize.y = stage.getHeight();
					prevPos.y = stage.getY();
					stage.setHeight(screen.getHeight());
					stage.setY(screen.getMinY());
					snapped = true;
				}
			}
			
		});
	}
	
	/**
	 * Determines if the Window is maximized.
	 *
	 * @param maximized
	 *            the new maximized
	 */
	private void setMaximized(boolean maximized) {
		this.maximized.set(maximized);
		setResizable(!maximized);
	}
	
	/**
	 * Disable/enable the resizing of your stage. Enabled by default.
	 * 
	 * @param bool
	 *            false to disable, true to enable.
	 */
	protected void setResizable(boolean bool) {
		resizable.set(bool);
	}
	
	/**
	 * Disable/enable the Aero Snap property of your stage. Enabled by default.
	 * 
	 * @param bool
	 *            false to disable, true to enable.
	 */
	protected void setSnapEnabled(boolean bool) {
		snap.set(bool);
		if(!bool && snapped) {
			snapOff();
		}
	}

	/**
	 * @return the transparentWindow
	 */
	public TransparentWindow getTransparentWindow() {
		return transparentWindow;
	}

}
