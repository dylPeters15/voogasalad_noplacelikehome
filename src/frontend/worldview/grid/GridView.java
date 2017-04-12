/**
 * Holds a grid to be displayed in the development and player GUI. It can have Sprites added to a particular cell or 
 * have all cells updated after something occurs in the game.
 */
package frontend.worldview.grid;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.grid.GameBoard;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * Andreas
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class GridView extends BaseUIManager<Region> {
	private static final double MIN = 10, MAX = 100, SCALE = 0.50;

	private ScrollPane myScrollPane;
	private Group cellViewObjects;
	private LayoutManager myLayoutManager;
	private Collection<CellView> cellViews;
	
	public GridView(GameBoard gameBoard){
		initialize(gameBoard);
		update(gameBoard);
	}

	private void initialize(GameBoard gameBoard) {
		myScrollPane = new ScrollPane();
		cellViewObjects = new Group();
		cellViews = new ArrayList<CellView>();
	}
	
	public void update(GameBoard gameBoard){
		cellViewObjects.getChildren().clear();

		if (gameBoard.dimension() == 2){
			myLayoutManager = new SquareLayout();
		} else {
			myLayoutManager = new HexagonalManager();
		}
		
		Map<CoordinateTuple, Cell> backendCells = gameBoard.getCells();
		backendCells.values().stream().forEach(cell -> {
			CellView cl = new CellView(cell);
			myLayoutManager.layoutCell(cl, SCALE, MIN, MAX);
			cellViews.add(cl);
			cellViewObjects.getChildren().add(cl.getObject());
		});
		
		myScrollPane.setContent(cellViewObjects);
	}

	@Override
	public Region getObject() {
		return myScrollPane;
	}
}
