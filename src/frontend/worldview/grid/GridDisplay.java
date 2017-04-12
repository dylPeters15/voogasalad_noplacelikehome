/**
 * Holds a grid to be displayed in the development and player GUI. It can have Sprites added to a particular cell or 
 * have all cells updated after something occurs in the game.
 */
package frontend.worldview.grid;

import backend.grid.GameBoard;
import frontend.sprites.Sprite;
import frontend.util.BaseUIManager;
import javafx.scene.layout.Region;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class GridDisplay extends BaseUIManager<Region> {

	
	/**
	 * Place a Sprite in the cell that contains the given coordinates.
	 * 
	 * @param Sprite to be placed in the cell.
	 * @param DisplayCoordinates that the mouse is located at.
	 */
	public void placeInCell(Sprite sprite, DisplayCoordinates mouseCoord) {
		
	};
	
	/**
	 * Updates all cells in the grid using the given GameBoard.
	 */
	public void updateCells(GameBoard grid) {;
}

	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}
}
