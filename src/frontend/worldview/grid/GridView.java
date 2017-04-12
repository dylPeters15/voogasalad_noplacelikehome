/**
 * Holds a grid to be displayed in the development and player GUI. It can have Sprites added to a particular cell or 
 * have all cells updated after something occurs in the game.
 */
package frontend.worldview.grid;

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

	private ScrollPane myScrollPane;
	private Group cellViews;
	private LayoutManager myLayoutManager;
	
	public GridView(GameBoard gameBoard){
		initialize(gameBoard);
	}

	private void initialize(GameBoard gameBoard) {
		myScrollPane = new ScrollPane();
		cellViews = new Group();
		
		if (gameBoard.dimension() == 2){
			myLayoutManager = new SquareLayout();
		} else {
			myLayoutManager = new HexagonalLayout();
		}
		
		Map<CoordinateTuple, Cell> backendCells = gameBoard.getCells();
		backendCells.values().stream().forEach(cell -> {
			cellViews.getChildren().add(new CellView(cell).getObject());
		});
		
		myScrollPane.setContent(cellViews);
	}
	
	public void update(GameBoard gameBoard){
		cellViews.getChildren();
	}

	@Override
	public Region getObject() {
		return myScrollPane;
	}
}
