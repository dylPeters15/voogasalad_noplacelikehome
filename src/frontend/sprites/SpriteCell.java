/**
 * SpriteCells are the individual pieces that make up a cell. 
 */
package frontend.sprites;

import frontend.grid.Coordinates;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface SpriteCell {
	
	/**
	 * @return Relative Coordinates at which the SpriteCell is located.
	 */
	Coordinates getCoordinates();
	
}
