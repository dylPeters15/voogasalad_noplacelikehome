/**
 *
 */
package frontend;

import backend.util.Client;
import backend.util.GameState;
import controller.Controller;
import controller.CommunicationController;
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

/**
 * @author Stone Mathers, Dylan Peters
 *         Created 4/3/2017
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
	//private GameState myGameState;
	//private Client mClient;
	//private ObservableClient<GameState> myClient;
	
	public View(){
		this(null);
	}
	
	public View(GameState mGameState)
	{
		myController = new CommunicationController(mGameState, this);
		initBorderPane();
	}
	
	
	
	/**
	public View(CommunicationController myController){
		this.myController = (CommunicationController) myController;
		
		
		//Both of the following lines no longer needed, as can be accessed form controller.
		//mClient = myController.getClient();
		//myGameState = myController.getGameState();
		
		//Instead of using listners, the update() method is called from the controller; check it out.
		//myController.addListener(e -> update()); 
		//client.addListener(e -> update());
		
		initBorderPane();
	}
	****/
	
	/**
	 * Updates the display of the GameState. This method is to be called by the GameState whenever changes are made.
	 */
	public void update(){
		//worldView.updateGrid(myGameState.getGrid());
		//tempPane.updateUnits(myGameState.getUnitTemplates());
		worldView.updateGrid(myController.getGrid());
		tempPane.updateSprites(myController.getUnitTemplates());
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
	
//	/**
//	 * @param Controller to be used by the View to obtain data from the Model and send requests from the GUI.
//	 */
//	public void setController(Controller controller){
//		myController = controller;
//	}
	
	/**
	 * @param True if this View can be switched into "edit" mode, false if it cannot.
	 */
	public void setEditable(boolean editable){
		this.editable = editable;
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
		worldView = new WorldView(myController.getGameState().getGrid());
		//worldView = new WorldView(myController.getGrid());
		worldView.getRequests().passTo(this.getRequests());
		toolsPane = new ToolsPane();
		toolsPane.getRequests().passTo(this.getRequests());
		detailPane = new DetailPane();
		detailPane.getRequests().passTo(this.getRequests());
		tempPane = new TemplatePane(myController.getGameState().getUnitTemplates(), myController.getGameState().getTerrains());
		//tempPane = new TemplatePane(myController.getUnitTemplates(), myController.getModifiableCells());
		tempPane.getRequests().passTo(this.getRequests());
		
		getRequests().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				while (!getRequests().isEmpty()) {
					//myClient.addToOutbox(getRequests().poll());
					//myController.sendRequest(getRequests().poll());
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
	
	/**
	public void setGameState(GameState newGameState)
	{
		this.myGameState = newGameState;
	}
	
	public GameState getGameState()
	{
		this.myGameState = myController.getGameState();
	}
	**/
	
	public void sendAlert(String s) {
		Alert myAlert;
		myAlert = new Alert(AlertType.INFORMATION);
		myAlert.setTitle("Information Dialog");
		myAlert.setHeaderText(null);
		myAlert.setContentText(s);
		myAlert.showAndWait();
	}
	
	public void setController(Controller newController)
	{
		this.myController = newController;
	}
}