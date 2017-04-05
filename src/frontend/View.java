/**
 *
 */
package frontend;

import backend.util.ImmutableGameState;
import controller.ClientController;
import frontend.detailpane.DetailPane;
import frontend.templatepane.TemplatePane;
import frontend.toolspane.ToolsPane;
import frontend.voogamenubar.VoogaMenuBar;
import frontend.worldview.WorldView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

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
	private ImmutableGameState myGameState;
	private ClientController myController;
	
	
	
	public View(ImmutableGameState gameState, ClientController controller){
		myGameState = gameState;
		myController = controller;
		initBorderPane();
	}
	
	//TODO: Somewhere the View needs to call something like "controller.sendRequests(this.getRequests());" whenever one of its
	//panes needs to send its new request(s). Whether this is in "update()" or a new method will be figured out once listening
	//is figured out
	
	/**
	 * Updates the display of the GameState. This method is to be called by the GameState whenever changes are made.
	 */
	public void update(){
		worldView.updateGrid(myGameState.getGrid());
		//TODO: something like templatePane.updateSprites(myGameState.getUnits());
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
		//TODO: make View a listener of menuBar
		worldView = new WorldView();
		//TODO: make View a listener of worldView
		//TODO: toolsPane = new ToolsPane(myGameState.getUnits());
		//TODO: make View a listener of toolsPane
		detailPane = new DetailPane();
		//TODO: make View a listener of detailPane
		tempPane = new TemplatePane();
		//TODO: make View a listener of tempPane
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
