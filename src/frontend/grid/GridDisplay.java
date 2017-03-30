/**
 * 
 */
package frontend.grid;

import frontend.Displayable;
import frontend.sprites.Sprite;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface GridDisplay extends Displayable {
	
	void placeInCell(Sprite sprite, Coordinates mouseCoord);
	
	void updateCells();
}
