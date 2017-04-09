/**
 *
 */
package frontend;

import backend.util.GameState;
import frontend.detailpane.DetailPane;
import frontend.menubar.VoogaMenuBar;
import frontend.templatepane.TemplatePane;
import frontend.toolspane.ToolsPane;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import util.net.ObservableClient;

/**
 * @author Stone Mathers, Dylan Peters
 *         Created 4/3/2017
 */
public class View extends BaseUIManager<Region> {

	private BorderPane myBorder;
	private VoogaMenuBar menuBar;
	private WorldView worldView;
	private ToolsPane toolsPane;
	private DetailPane detailPane;
	private TemplatePane tempPane;
	private GameState myGameState;
	private ObservableClient myClient; //TODO: What should this generic be?
	
	
	
	public View(GameState gameState, ObservableClient client){
		myGameState = gameState;
		myClient = client;
		client.addListener(e -> update());
		initBorderPane();
	}
	
	/**
	 * Updates the display of the GameState. This method is to be called by the GameState whenever changes are made.
	 */
	public void update(){
		worldView.updateGrid(myGameState.getGrid());
		tempPane.updateSprites(myGameState.getUnitTemplates());
	}
	
	/**
	 * Performs all necessary actions to convert the View into development mode.
	 * If the View is already in development mode, then nothing visually changes.
	 */
	public void enterDevMode(){
		addSidePanes();
	}
	
	/**
	 * Performs all necessary actions to convert the View into play mode.
	 * If the View is already in play mode, then nothing visually changes.
	 */
	public void enterPlayMode(){
		removeSidePanes();
	}
	
	private void initBorderPane(){
		initPanesAndListeners();
		myBorder = new BorderPane(worldView.getObject(), menuBar.getObject(), tempPane.getObject(), 
									detailPane.getObject(), toolsPane.getObject());
	}
	
	/**
	 * Initializes all panes in the GUI and makes View a listener to all necessary panes.
	 */
	private void initPanesAndListeners(){
		menuBar = new VoogaMenuBar();
		menuBar.getRequests().passTo(this.getRequests());
		worldView = new WorldView(myGameState.getGrid());
		worldView.getRequests().passTo(this.getRequests());
		toolsPane = new ToolsPane();
		toolsPane.getRequests().passTo(this.getRequests());
		detailPane = new DetailPane();
		detailPane.getRequests().passTo(this.getRequests());
		tempPane = new TemplatePane(myGameState.getUnitTemplates(), myGameState.getModifiableCells());
		tempPane.getRequests().passTo(this.getRequests());
		
		getRequests().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				while (!getRequests().isEmpty()) {
					myClient.handleRequest(getRequests().poll());
				}
			}
		});
	}
	
	/**
	 * Adds the ToolsPane and TemplatePane to the sides of the View's GUI.
	 */
	private void addSidePanes(){
		myBorder.setLeft(toolsPane.getObject());
		myBorder.setRight(tempPane.getObject());
	}
	
	/**
	 * Removes the ToolsPane and TemplatePane from the sides of the View's GUI.
	 */
	private void removeSidePanes(){
		myBorder.setLeft(null);
		myBorder.setRight(null);
	}

	@Override
	public Region getObject() {
		return myBorder;
	}
}