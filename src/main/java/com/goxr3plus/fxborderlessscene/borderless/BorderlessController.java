/*
 * 
 */
package main.java.com.goxr3plus.fxborderlessscene.borderless;

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
 * @author Nicolas Senet-Larson
 * @author GOXR3PLUS STUDIO
 * @version 1.0
 */
public class BorderlessController{

	/** The stage. */
	private Stage stage;

	/** The prev size. */
	protected Delta prevSize;

	/** The prev pos. */
	protected Delta prevPos;

	/** The maximized. */
	private ReadOnlyBooleanWrapper maximized;

	/** The snapped. */
	private boolean snapped;

	/** The left pane. */
	@FXML private Pane leftPane;

	/** The right pane. */
	@FXML private Pane rightPane;

	/** The top pane. */
	@FXML private Pane topPane;

	/** The bottom pane. */
	@FXML private Pane bottomPane;

	/** The top left pane. */
	@FXML private Pane topLeftPane;

	/** The top right pane. */
	@FXML private Pane topRightPane;

	/** The bottom left pane. */
	@FXML private Pane bottomLeftPane;

	/** The bottom right pane. */
	@FXML private Pane bottomRightPane;

	/** The bottom. */
	String bottom = "bottom";

	private static final double BUFFER_SPACE = 100;

	private double aeroSnapStartX;
	private double aeroSnapStartY;
	private double aeroSnapWidth;
	private double aeroSnapHeight;
	private boolean isAeroSnap;

	/**
	 * Transparent Window used to show how the window will be resized
	 */
	private TransparentWindow transparentWindow;

	/**
	 * The constructor.
	 */
	public BorderlessController(){
		prevSize = new Delta();
		prevPos = new Delta();
		maximized = new ReadOnlyBooleanWrapper(false);
		snapped = false;

	}

	/**
	 * Creates the Transparent Window
	 * @param parentWindow The parentWindow of the TransparentWindow
	 */
	public void createTransparentWindow(Stage parentWindow){

		transparentWindow = new TransparentWindow();
		transparentWindow.getWindow().initOwner(parentWindow);
	}

	/**
	 * Maximized property.
	 * @return True is the window is maximized or False if it is not
	 */
	public ReadOnlyBooleanProperty maximizedProperty(){
		return maximized.getReadOnlyProperty();
	}

	/**
	 * Called after the FXML layout is loaded.
	 */
	@FXML
	private void initialize(){

		setResizeControl(leftPane, "left");
		setResizeControl(rightPane, "right");
		setResizeControl(topPane, "top");
		setResizeControl(bottomPane, bottom);
		setResizeControl(topLeftPane, "top-left");
		setResizeControl(topRightPane, "top-right");
		setResizeControl(bottomLeftPane, bottom + "-left");
		setResizeControl(bottomRightPane, bottom + "-right");
	}

	/**
	 * Set the Stage of the controller.
	 * @param primaryStage the new stage
	 */
	protected void setStage(Stage primaryStage){
		this.stage = primaryStage;
	}

	/**
	 * Maximize on/off the application.
	 */
	protected void maximize(){
		Rectangle2D screen;

		try{
			if(Screen
				.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth() / 2, stage.getHeight() / 2)
				.isEmpty())
				screen = Screen
					.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth(), stage.getHeight()).get(0)
					.getVisualBounds();
			else
				screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(), stage.getWidth() / 2,
					stage.getHeight() / 2).get(0).getVisualBounds();

		} catch(Exception ex){
			ex.printStackTrace();
			return;
		}

		if(maximized.get()){
			stage.setWidth(prevSize.x);
			stage.setHeight(prevSize.y);
			stage.setX(prevPos.x);
			stage.setY(prevPos.y);
			setMaximized(false);
		} else{
			// Record position and size, and maximize.
			if(!snapped){
				prevSize.x = stage.getWidth();
				prevSize.y = stage.getHeight();
				prevPos.x = stage.getX();
				prevPos.y = stage.getY();
			} else if(!screen.contains(prevPos.x, prevPos.y)){
				if(prevSize.x > screen.getWidth())
					prevSize.x = screen.getWidth() - 20;

				if(prevSize.y > screen.getHeight())
					prevSize.y = screen.getHeight() - 20;

				prevPos.x = screen.getMinX() + (screen.getWidth() - prevSize.x) / 2;
				prevPos.y = screen.getMinY() + (screen.getHeight() - prevSize.y) / 2;
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
	protected void minimize(){
		stage.setIconified(true);
	}

	/**
	 * Set a node that can be pressed and dragged to move the application around.
	 * @param node the node.
	 */
	protected void setMoveControl(final Node node){
		final Delta delta = new Delta();
		final Delta eventSource = new Delta();

		// Record drag deltas on press.
		node.setOnMousePressed(m -> {
			if(m.isPrimaryButtonDown()){
				delta.x = m.getSceneX(); // getX()
				delta.y = m.getSceneY(); // getY()

				if(maximized.get() || snapped){
					delta.x = prevSize.x * (m.getSceneX() / stage.getWidth());// (m.getX() / stage.getWidth())
					delta.y = prevSize.y * (m.getSceneY() / stage.getHeight());// (m.getY() / stage.getHeight())
				} else{
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
			if(m.isPrimaryButtonDown()){

				// Move x axis.
				stage.setX(m.getScreenX() - delta.x);
				if(snapped){
					// Aero Snap off.
					Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0)
						.getVisualBounds();
					stage.setHeight(screen.getHeight());
					if(m.getScreenY() > eventSource.y){
						stage.setWidth(prevSize.x);
						stage.setHeight(prevSize.y);
						snapped = false;
					}
				} else{
					// Move y axis.
					stage.setY(m.getScreenY() - delta.y);
				}

				// Aero Snap off.
				if(maximized.get()){
					stage.setWidth(prevSize.x);
					stage.setHeight(prevSize.y);
					setMaximized(false);
				}

				// --------------------------Check here for Transparent Window--------------------------
				// System.out.println("WHOLE SCREEN:"
				// + Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0).getBounds());
				// System.out.println("SCREEN:" + Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1,
				// 1)
				// .get(0).getVisualBounds());
				// System.out.println("SCREEN X:" + m.getScreenX() + " Y:" + m.getScreenY());
				Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0)
					.getVisualBounds();

				double startX = screen.getMinX();
				double startY = screen.getMinY();
				double mouseX = m.getScreenX();
				double mouseY = m.getScreenY();
				double halfX;
				double halfY;
				Direction leftRightCenter;
				Direction topBottomCenter;
				double width = screen.getWidth() / 2;
				double height = screen.getHeight() / 2;

				if((mouseX - BUFFER_SPACE <= startX) || (mouseX + BUFFER_SPACE <= startX)){// Left
					halfX = screen.getMinX() / 2;
					leftRightCenter = Direction.LEFT;
				} else if((mouseX + BUFFER_SPACE >= screen.getMaxX() - 1)
					|| (mouseX - BUFFER_SPACE >= screen.getMaxX() - 1)){// Right
					halfX = screen.getMaxX() / 2 + startX / 2;
					leftRightCenter = Direction.RIGHT;
				} else{// Center X
					halfX = screen.getMaxX() / 2;
					leftRightCenter = Direction.CENTER;
				}

				if((mouseY - BUFFER_SPACE <= startY) || (mouseY + BUFFER_SPACE <= startY)){// Top
					halfY = screen.getMinY() / 2;
					topBottomCenter = Direction.TOP;
				} else if((mouseY + BUFFER_SPACE >= startY + height * 2)
					|| (mouseY - BUFFER_SPACE >= startY + height * 2)){// Bottom
					halfY = screen.getMaxY() / 2 + startY / 2;
					topBottomCenter = Direction.BOTTOM;
				} else{// Center Y
					halfY = screen.getMaxY() / 2;
					topBottomCenter = Direction.CENTER;
				}

				// System.out.println("StartX: " + startX + " StartY: " + startY);
				// System.out.println("MouseX: " + mouseX + " MouseY: " + mouseY);
				// System.out.println(leftRightCenter + " " + topBottomCenter + " " + isCorner);/
				isAeroSnap = true;
				if(Direction.RIGHT.equals(leftRightCenter) && Direction.TOP.equals(topBottomCenter)){
					// System.out.println("RIGHT TOP");
					aeroSnapStartX = halfX;
					aeroSnapStartY = startY;
					aeroSnapWidth = width;
					aeroSnapHeight = height;

					transparentWindow.getWindow().setX(aeroSnapStartX);
					transparentWindow.getWindow().setY(aeroSnapStartY);
					transparentWindow.getWindow().setWidth(aeroSnapWidth);
					transparentWindow.getWindow().setHeight(aeroSnapHeight);
					transparentWindow.show();
				} else if(Direction.LEFT.equals(leftRightCenter) && Direction.TOP.equals(topBottomCenter)){
					// System.out.println("LEFT TOP");
					aeroSnapStartX = startX;
					aeroSnapStartY = startY;
					aeroSnapWidth = width;
					aeroSnapHeight = height;

					transparentWindow.getWindow().setX(startX);
					transparentWindow.getWindow().setY(startY);
					transparentWindow.getWindow().setWidth(width);
					transparentWindow.getWindow().setHeight(height);
					transparentWindow.show();
				} else if(Direction.RIGHT.equals(leftRightCenter) && Direction.BOTTOM.equals(topBottomCenter)){
					// System.out.println("RIGHT BOTTOM");
					aeroSnapStartX = halfX;
					aeroSnapStartY = halfY;
					aeroSnapWidth = width;
					aeroSnapHeight = height;

					transparentWindow.getWindow().setX(halfX);
					transparentWindow.getWindow().setY(halfY);
					transparentWindow.getWindow().setWidth(width);
					transparentWindow.getWindow().setHeight(height);
					transparentWindow.show();
				} else if(Direction.LEFT.equals(leftRightCenter) && Direction.BOTTOM.equals(topBottomCenter)){
					// System.out.println("LEFT BOTTOM");
					aeroSnapStartX = startX;
					aeroSnapStartY = halfY;
					aeroSnapWidth = width;
					aeroSnapHeight = height;

					transparentWindow.getWindow().setX(startX);
					transparentWindow.getWindow().setY(halfY);
					transparentWindow.getWindow().setWidth(width);
					transparentWindow.getWindow().setHeight(height);
					transparentWindow.show();
				} else if(Direction.LEFT.equals(leftRightCenter)){
					// System.out.println("LEFT");
					aeroSnapStartX = startX;
					aeroSnapStartY = startY;
					aeroSnapWidth = width;
					aeroSnapHeight = height * 2;

					transparentWindow.getWindow().setX(startX);
					transparentWindow.getWindow().setY(startY);
					transparentWindow.getWindow().setWidth(width);
					transparentWindow.getWindow().setHeight(height * 2);
					transparentWindow.show();
				} else if(Direction.RIGHT.equals(leftRightCenter)){
					// System.out.println("RIGHT");
					aeroSnapStartX = halfX;
					aeroSnapStartY = startY;
					aeroSnapWidth = width;
					aeroSnapHeight = height * 2;

					transparentWindow.getWindow().setX(halfX);
					transparentWindow.getWindow().setY(startY);
					transparentWindow.getWindow().setWidth(width);
					transparentWindow.getWindow().setHeight(height * 2);
					transparentWindow.show();
				} else if((Direction.TOP.equals(topBottomCenter) || Direction.BOTTOM.equals(topBottomCenter))
					&& Direction.CENTER.equals(leftRightCenter)){
					// System.out.println("TOP OR BOTTOM");
					aeroSnapStartX = startX;
					aeroSnapStartY = startY;
					aeroSnapWidth = width * 2;
					aeroSnapHeight = height * 2;

					transparentWindow.getWindow().setX(startX);
					transparentWindow.getWindow().setY(startY);
					transparentWindow.getWindow().setWidth(width * 2);
					transparentWindow.getWindow().setHeight(height * 2);
					transparentWindow.show();
				} else{// Center Center
					isAeroSnap = false;
					transparentWindow.close();
				}
				// System.out.println("\n");
			}
		});

		// Maximize on double click.
		node.setOnMouseClicked(m -> {
			if((MouseButton.PRIMARY.equals(m.getButton())) && (m.getClickCount() == 2))
				maximize();
		});

		// Aero Snap on release.
		node.setOnMouseReleased(m -> {

			try{

				if((MouseButton.PRIMARY.equals(m.getButton()))){
					if(isAeroSnap){
						stage.setX(aeroSnapStartX);
						stage.setY(aeroSnapStartY);
						stage.setWidth(aeroSnapWidth);
						stage.setHeight(aeroSnapHeight);
						snapped = true;

						if((aeroSnapStartX + aeroSnapWidth == 0 || aeroSnapStartX - aeroSnapWidth == 0)
							&& (aeroSnapStartY + aeroSnapHeight == 0 || aeroSnapStartY - aeroSnapHeight == 0)){
							setMaximized(true);
						} else{
							setMaximized(false);
						}
					} else{
						snapped = false;
					}
				}
			} catch(Exception ex){
				ex.printStackTrace();
			}

			// Hide the transparent window -- close this window no matter what
			transparentWindow.close();
		});

	}

	/**
	 * Set pane to resize application when pressed and dragged.
	 * @param pane      the pane the action is set to.
	 * @param direction the resize direction. Diagonal: 'top' or 'bottom' + 'right' or 'left'.
	 *                  [[SuppressWarningsSpartan]]
	 */
	private void setResizeControl(Pane pane, final String direction){

		// Record the previous size and previous point
		pane.setOnDragDetected((event) -> {
			prevSize.x = stage.getWidth();
			prevSize.y = stage.getHeight();
			prevPos.x = stage.getX();
			prevPos.y = stage.getY();
		});

		pane.setOnMouseDragged(m -> {
			if(m.isPrimaryButtonDown()){
				double width = stage.getWidth();
				double height = stage.getHeight();

				// Horizontal resize.
				if(direction.endsWith("left")){
					double comingWidth = width - m.getScreenX() + stage.getX();

					// Check if it violates minimumWidth
					if(comingWidth > 0 && comingWidth >= stage.getMinWidth()){
						stage.setWidth(stage.getX() - m.getScreenX() + stage.getWidth());
						stage.setX(m.getScreenX());
					}

				} else if(direction.endsWith("right")){
					double comingWidth = width + m.getX();

					// Check if it violates
					if(comingWidth > 0 && comingWidth >= stage.getMinWidth())
						stage.setWidth(m.getSceneX());
				}

				// Vertical resize.
				if(direction.startsWith("top")){
					if(snapped){
						stage.setHeight(prevSize.y);
						snapped = false;
					} else if((height > stage.getMinHeight()) || (m.getY() < 0)){
						stage.setHeight(stage.getY() - m.getScreenY() + stage.getHeight());
						stage.setY(m.getScreenY());
					}
				} else if(direction.startsWith(bottom)){
					if(snapped){
						stage.setY(prevPos.y);
						snapped = false;
					} else{
						double comingHeight = height + m.getY();

						// Check if it violates
						if(comingHeight > 0 && comingHeight >= stage.getMinHeight())
							stage.setHeight(m.getSceneY());
					}

				}
			}
		});

		// Record application height and y position.
		pane.setOnMousePressed(m -> {
			if((m.isPrimaryButtonDown()) && (!snapped)){
				prevSize.y = stage.getHeight();
				prevPos.y = stage.getY();
			}

		});

		// Aero Snap Resize.
		pane.setOnMouseReleased(m -> {
			if((MouseButton.PRIMARY.equals(m.getButton())) && (!snapped)){
				Rectangle2D screen = Screen.getScreensForRectangle(m.getScreenX(), m.getScreenY(), 1, 1).get(0)
					.getVisualBounds();

				if((stage.getY() <= screen.getMinY()) && (direction.startsWith("top"))){
					stage.setHeight(screen.getHeight());
					stage.setY(screen.getMinY());
					snapped = true;
				}

				if((m.getScreenY() >= screen.getMaxY()) && (direction.startsWith(bottom))){
					stage.setHeight(screen.getHeight());
					stage.setY(screen.getMinY());
					snapped = true;
				}
			}

		});

		// Aero Snap resize on double click.
		pane.setOnMouseClicked(m -> {
			if((MouseButton.PRIMARY.equals(m.getButton())) && (m.getClickCount() == 2)
				&& ("top".equals(direction) || bottom.equals(direction))){
				Rectangle2D screen = Screen.getScreensForRectangle(stage.getX(), stage.getY(),
					stage.getWidth() / 2, stage.getHeight() / 2).get(0).getVisualBounds();

				if(snapped){
					stage.setHeight(prevSize.y);
					stage.setY(prevPos.y);
					snapped = false;
				} else{
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
	 * @param maximized the new maximized
	 */
	private void setMaximized(boolean maximized){
		this.maximized.set(maximized);
		setResizable(!maximized);
	}

	/**
	 * Disable/enable the resizing of your stage. Enabled by default.
	 * @param bool false to disable, true to enable.
	 */
	protected void setResizable(boolean bool){
		leftPane.setDisable(!bool);
		rightPane.setDisable(!bool);
		topPane.setDisable(!bool);
		bottomPane.setDisable(!bool);
		topLeftPane.setDisable(!bool);
		topRightPane.setDisable(!bool);
		bottomLeftPane.setDisable(!bool);
		bottomRightPane.setDisable(!bool);
	}

	/**
	 * @return the transparentWindow
	 */
	public TransparentWindow getTransparentWindow(){
		return transparentWindow;
	}

	public enum Direction{
		LEFT, CENTER, RIGHT, TOP, BOTTOM
	}
}
