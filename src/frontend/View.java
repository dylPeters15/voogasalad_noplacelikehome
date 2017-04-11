/**
 *
 */
package frontend;

import backend.cell.ModifiableTerrain;
import backend.unit.ModifiableUnit;
import backend.util.AuthoringGameState;
import controller.CommunicationController;
import controller.Controller;
import frontend.detailpane.DetailPane;
import frontend.menubar.VoogaMenuBar;
import frontend.templatepane.TemplatePane;
import frontend.toolspane.ToolsPane;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import util.net.ObservableClient;

import java.util.Collection;
import java.util.stream.Collectors;
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
	
	public View(){
		this(null);
	}
	
	public View(Controller controller) {
		myController = controller;
		initBorderPane();
	}
	/**
	 * Updates the display of the GameState. This method is to be called by the
	 * GameState whenever changes are made.
	 */
	public void update() {
		@SuppressWarnings("unchecked")
		Collection<ModifiableUnit> units = (Collection<ModifiableUnit>) myController.getGameState()
				.getTemplateByCategory(AuthoringGameState.UNIT).getAll().stream()
				.filter(voogaEntity -> voogaEntity instanceof ModifiableUnit).collect(Collectors.toList());
		tempPane.updateUnits(units);
		@SuppressWarnings("unchecked")
		Collection<ModifiableTerrain> terrains = (Collection<ModifiableTerrain>) myController.getGameState()
				.getTemplateByCategory(AuthoringGameState.TERRAIN).getAll().stream()
				.filter(voogaEntity -> voogaEntity instanceof ModifiableTerrain).collect(Collectors.toList());
		tempPane.updateTerrains(terrains);
		//tempPane.updateUnits(myController.getUnits());  //TODO add this method to controller
		//tempPane.updateTerrains(myController.getTerrains()); //TODO add this method to controller
		worldView.updateGrid(myController.getGrid());

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
	 * @param Controller to be used by the View to obtain data from the Model
	 and send requests from the GUI.
	 */
	 public void setController(Controller controller){
	 myController = controller;
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
		menuBar.getRequests().passTo(this.getRequests());
		worldView = new WorldView(myController.getGameState().getGrid());
		// worldView = new WorldView(myController.getGrid());
		worldView.getRequests().passTo(this.getRequests());
		toolsPane = new ToolsPane();
		toolsPane.getRequests().passTo(this.getRequests());
		detailPane = new DetailPane();
		detailPane.getRequests().passTo(this.getRequests());
		@SuppressWarnings("unchecked")
		Collection<ModifiableUnit> units = (Collection<ModifiableUnit>) myController.getGameState()
				.getTemplateByCategory(AuthoringGameState.UNIT).getAll().stream()
				.filter(voogaEntity -> voogaEntity instanceof ModifiableUnit).collect(Collectors.toList());
		@SuppressWarnings("unchecked")
		Collection<ModifiableTerrain> terrains = (Collection<ModifiableTerrain>) myController.getGameState()
				.getTemplateByCategory(AuthoringGameState.TERRAIN).getAll().stream()
				.filter(voogaEntity -> voogaEntity instanceof ModifiableTerrain).collect(Collectors.toList());
		tempPane = new TemplatePane(myController.getGameState(), detailPane, worldView);
		// tempPane = new TemplatePane(myController.getUnitTemplates(),
		// myController.getModifiableCells());
		tempPane.getRequests().passTo(this.getRequests());
		getRequests().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				while (!getRequests().isEmpty()) {
					// myClient.addToOutbox(getRequests().poll());
					// myController.sendRequest(getRequests().poll());
				}
			}
		});
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