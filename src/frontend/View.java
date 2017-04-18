/**
 * The View is responsible for displaying the data held within the Model. It is also responsible
 * for providing an interface with which the user can interact to change the GameState.
 * View does not do so directly, instead sending modifiers and getting updated through the Controller.
 * <p>
 * The View organizes the GUI, instantiating and placing all of the necessary panes. Requests are made through a
 * Modifier class, a wrapper holding lambda expressions. The panes are expected to extend BaseUIManager, so that
 * View can access getObject(), the pane can send Modifiers, and the pane can update itself. They are all given
 * an instance of Controller to do so, specifically by adding itself as an observer (so that it’s update method
 * is called whenever a change in the GameState occurs) and calling a method from the controller to send Modifiers.
 * <p>
 * The View also has a boolean editable that determines which mode it is in, edit or play. This determines which
 * GUI components are made available to the user. The methods that instantiate the GUI should use this boolean to
 * determine which components are displayed.
 *
 * @author Stone Mathers, Dylan Peters
 * Created 4/3/2017
 */
package frontend;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import backend.util.AuthoringGameState;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.detailpane.DetailPane;
import frontend.menubar.VoogaMenuBar;
import frontend.templatepane.TemplatePane;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class View extends BaseUIManager<Region> implements Observer{
	private static final Map<String, Image> IMAGE_CACHE = new HashMap<>();

	private boolean editable;
	private BorderPane myBorder;
	private VoogaMenuBar menuBar;
	private WorldView worldView;
	private DetailPane detailPane;
	private TemplatePane tempPane;

	public View(Controller controller) {
		super(controller);
		initBorderPane();
	}

	/**
	 * Performs all necessary actions to convert the View into development mode.
	 * If the View is already in development mode, then nothing visually
	 * changes.
	 */
	public void enterDevMode() {
		addSidePanes();
	}

	/**
	 * Performs all necessary actions to convert the View into play mode. If the
	 * View is already in play mode, then nothing visually changes.
	 */
	public void enterPlayMode() {
		removeSidePanes();
	}

	/**
	 * @param editable True if this View can be switched into "edit" mode, false if
	 *                 it cannot.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	@Override
	public Region getObject() {
		return myBorder;
	}

	/**
	 * Sets the GameState that the View accesses its data from.
	 *
	 * @param newGameState AuthoringGameState that the View will now access its data from
	 */
	public void setGameState(AuthoringGameState newGameState) {
		getController().setGameState(newGameState);
	}

	/**
	 * Displays an Alert to the user containing the given message.
	 *
	 * @param s String alert message
	 */
	public void sendAlert(String s) {
		Alert myAlert;
		myAlert = new Alert(AlertType.INFORMATION);
		myAlert.setTitle("Information Dialog");
		myAlert.setHeaderText(null);
		myAlert.setContentText(s);
		myAlert.showAndWait();
	}

	private void initBorderPane() {
		initPanesAndListeners();
		myBorder = new BorderPane(worldView.getObject(), menuBar.getObject(), tempPane.getObject(),
				detailPane.getObject(), null);
	}

	/**
	 * Initializes all panes in the GUI and makes View a listener to all
	 * necessary panes.
	 */
	private void initPanesAndListeners() {
		menuBar = new VoogaMenuBar();
		menuBar.getStyleSheet().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				getObject().getStylesheets().clear();
				getObject().getStylesheets().add(newValue);
			}
		});
		worldView = new WorldView(getController());
		detailPane = new DetailPane(worldView);
		tempPane = new TemplatePane(detailPane, worldView, getController());
		tempPane.addObserver(this);
	}

	/**
	 * Adds the ToolsPane and TemplatePane to the sides of the View's GUI.
	 */
	private void addSidePanes() {
		myBorder.setRight(tempPane.getObject());
	}

	/**
	 * Removes the ToolsPane and TemplatePane from the sides of the View's GUI.
	 */
	private void removeSidePanes() {
		myBorder.setLeft(null);
		myBorder.setRight(null);
	}

	public static Image getImg(String imgPath) {
		if (!IMAGE_CACHE.containsKey(imgPath)) {
			IMAGE_CACHE.put(imgPath, new Image(imgPath));
		}
		return IMAGE_CACHE.get(imgPath);
	}

	@Override
	public void update(Observable observable, Object object) {
		if (observable == tempPane){
			detailPane.setContent((VoogaEntity)object, "");
			worldView.templateClicked((VoogaEntity)object);
		}
	}

	
}