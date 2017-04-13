/**
 *
 */
package frontend;

import java.util.Collection;
import java.util.stream.Collectors;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.unit.ModifiableUnit;
import backend.util.AuthoringGameState;
import controller.Controller;
import frontend.detailpane.DetailPane;
import frontend.menubar.VoogaMenuBar;
import frontend.templatepane.TemplatePane;
import frontend.toolspane.ToolsPane;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

/**
 * @author Stone Mathers, Dylan Peters Created 4/3/2017
 */
public class View extends BaseUIManager<Region> {
	private boolean editable;
	private BorderPane myBorder;
	private VoogaMenuBar menuBar;
	private WorldView worldView;
	private ToolsPane toolsPane;
	private DetailPane detailPane;
	private TemplatePane tempPane;
	private Controller myController;

	public View(Controller controller) {
		myController = controller;
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
	 * Sets Controller and updates View.
	 * 
	 * @param Controller
	 *            to be used by the View to obtain data from the Model and send
	 *            requests from the GUI.
	 */
	public void setController(Controller controller) {
		myController = controller;
		update();
	}

	/**
	 * @param True
	 *            if this View can be switched into "edit" mode, false if it
	 *            cannot.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	private void initBorderPane() {
		initPanesAndListeners();
		myBorder = new BorderPane(worldView.getObject(), menuBar.getObject(), tempPane.getObject(),
				detailPane.getObject(), toolsPane.getObject());
	}

	/**
	 * Initializes all panes in the GUI and makes View a listener to all
	 * necessary panes.
	 */
	private void initPanesAndListeners() {
		menuBar = new VoogaMenuBar();
		worldView = new WorldView();
		toolsPane = new ToolsPane();
		detailPane = new DetailPane(worldView);
		tempPane = new TemplatePane(detailPane, worldView, myController);
		// tempPane = new TemplatePane(myController.getUnitTemplates(),
		// myController.getModifiableCells());
	}

	/**
	 * Adds the ToolsPane and TemplatePane to the sides of the View's GUI.
	 */
	private void addSidePanes() {
		myBorder.setLeft(toolsPane.getObject());
		myBorder.setRight(tempPane.getObject());
	}

	/**
	 * Removes the ToolsPane and TemplatePane from the sides of the View's GUI.
	 */
	private void removeSidePanes() {
		myBorder.setLeft(null);
		myBorder.setRight(null);
	}

	@Override
	public Region getObject() {
		return myBorder;
	}

	public void setGameState(AuthoringGameState newGameState) {
		myController.setGameState(newGameState);
	}

	public void sendAlert(String s) {
		Alert myAlert;
		myAlert = new Alert(AlertType.INFORMATION);
		myAlert.setTitle("Information Dialog");
		myAlert.setHeaderText(null);
		myAlert.setContentText(s);
		myAlert.showAndWait();
	}
}