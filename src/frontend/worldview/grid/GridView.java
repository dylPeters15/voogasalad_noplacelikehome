package frontend.worldview.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

import controller.Controller;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * Holds a grid to be displayed in the development and player GUI inside a ScrollPane. 
 * It can have Sprites added to a particular cell or have all cells updated after something occurs in the game.
 * Contains a group of CellView objects and a collection of CellView's, when update method is called, it can tell
 * all of the units/cells to update themselves
 * 
 * @author Andreas Santos
 * Created 3/29/2017
 */
public class GridView extends BaseUIManager<Region> {
	private static final double MIN = 10, MAX = 100, SCALE = 0.750;

	private ScrollPane myScrollPane;
	private Group cellViewObjects;
	private LayoutManager myLayoutManager;
	private Collection<CellView> cellViews;
	
	public GridView(Controller controller){
		setController(controller);
		initialize();
		update();
	}

	private void initialize() {
		myScrollPane = new ScrollPane();
		cellViewObjects = new Group();
		cellViews = new ArrayList<CellView>();
		myLayoutManager = new LayoutManagerFactory();
		
		populateCellViews();
		
		myScrollPane.setContent(cellViewObjects);
	}
	
	/**
	 * set on clicked method for each cell
	 * @param consumer
	 */
	public void setOnCellClick(Consumer<CellView> consumer){
		cellViews.forEach(cellView -> cellView.setOnCellClick(consumer));
	}

	@Override
	public Region getObject() {
		return myScrollPane;
	}
	
	private void populateCellViews(){
		getController().getGrid().getCells().values().forEach(cell -> {
			CellView cl = new CellView(cell,getController());
			myLayoutManager.layoutCell(cl, SCALE, MIN, MAX);
			cl.update();
			cellViews.add(cl);
			cellViewObjects.getChildren().add(cl.getObject());
		});
	}
}
