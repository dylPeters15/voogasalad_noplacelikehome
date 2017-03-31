/**
 * Holds a grid to be displayed in the development and player GUI. It can have Sprites added to a particular cell or 
 * have all cells updated after something occurs in the game.
 */
package frontend.grid;

import frontend.Displayable;
import frontend.sprites.Sprite;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface GridDisplay extends Displayable {
	
	/**
	 * Place a Sprite in the cell that contains the given coordinates.
	 * 
	 * @param Sprite to be placed in the cell.
	 * @param Coordinates that the mouse is located at.
	 */
	void placeInCell(Sprite sprite, Coordinates mouseCoord);
	
	/**
	 * Updates all cells in the grid. This method will generally access the grid in the game engine's model
	 * and make any changes that have resulted from an action within the game.
	 */
	void updateCells();
}
