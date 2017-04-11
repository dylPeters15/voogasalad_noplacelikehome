/**
 * 
 */
package frontend.worldview.grid;

import backend.grid.GameBoard;
import frontend.sprites.Sprite;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

/**
 * @author 
 *
 */
public class SquareGridDisplay extends GridDisplay {

	private GridPane gridPane;
	//private ________ myGrid; //TODO
	
	public SquareGridDisplay(GameBoard grid){
		//myGrid = new _______;
		updateCells(grid);
	}

	@Override
	public void placeInCell(Sprite sprite, Coordinates mouseCoord) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateCells(GameBoard grid) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Region getObject() {
		return gridPane;
	}

}
