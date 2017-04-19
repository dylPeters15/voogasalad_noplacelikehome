package frontend.worldview;

import controller.Controller;
import frontend.util.BaseUIManager;
import frontend.worldview.grid.CellView;
import frontend.worldview.grid.GridView;
import javafx.event.EventHandler;
import javafx.scene.input.ZoomEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

import java.util.function.Consumer;

/**
 * WorldView sets up and displays a Region object that contains a grid of
 * CellViews that represents the state of the grid received from the Controller
 * that is passed to the WorldView when instantiated.
 * 
 * The WorldView extends the BaseUIManager class so that it can change
 * languages, css stylesheets, and can be updated by the controller through the
 * updatable interface.
 * 
 * @author Dylan Peters
 *
 */
public class WorldView extends BaseUIManager<Region> {

	private GridView myGrid;
	private BorderPane borderPane;

	/**
	 * Instantiates a new instance of WorldView. Sets all values to default.
	 * 
	 * @param controller
	 *            the controller whose state will be displayed within the
	 *            WorldView
	 */
	public WorldView(Controller controller) {
		setController(controller);
		initialize();
	}

	/**
	 * Returns
	 * 
	 * @return Region object that shows the user a visual representation of the
	 *         grid, which can be interacted with to manipulate the back end
	 */
	@Override
	public Region getObject() {
		return borderPane;
	}

	/**
	 * Sets the action that is performed when a cell is clicked.
	 * 
	 * @param consumer
	 *            consumer to execute when the cell is clicked
	 */
	public void setOnCellClick(Consumer<CellView> consumer) {
		myGrid.setOnCellClick(consumer);
	}

	private void initialize() {
		borderPane = new BorderPane();
		myGrid = new GridView(getController());
		borderPane.setCenter(myGrid.getObject());
	}
}
