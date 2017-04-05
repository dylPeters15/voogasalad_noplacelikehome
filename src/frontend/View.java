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

/**
 * @author Stone Mathers, Dylan Peters
 *         Created 4/3/2017
 */
public class View extends BaseUIManager {

	private VoogaMenuBar menuBar;
	private WorldView worldView;
	private ToolsPane toolsPane;
	private DetailPane infoPane;
	private TemplatePane tempPane;
	private ImmutableGameState myGameState;
	private ClientController myController;
	
	
	
	public View(ImmutableGameState gameState, ClientController controller){
		myGameState = gameState;
		myController = controller;
		initPanesAndListeners();
	}
	
	
	
	/**
	 * Updates the display of the GameState. This method is to be called by the GameState whenever changes are made.
	 */
	public void update(){
		worldView.updateGrid(myGameState.getGrid());
	}
	
	/**
	 * Instantiates all panes in the GUI and makes View a listener to all necessary panes.
	 */
	private void initPanesAndListeners(){
		
	}

	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
