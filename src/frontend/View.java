/**
 *
 */
package frontend;

import backend.util.ImmutableGameState;
import controller.Controller;
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
	
	
	public View(ImmutableGameState gameState, ClientController controller){
		
	}
	
	
	
	/**
	 * Notifies when GameState changes and displays the new GameState.
	 */
	public void update(){
		
	}
	
	/**
	 * Sends requests to ClientController when the user makes any changes.
	 */
	private void instantiateListeners(){
		
	}

	@Override
	public Object getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}