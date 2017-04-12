/**
 * Data structure holding the coordinates of an object on a Grid.
 */
package frontend.worldview.grid;

import java.util.List;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface DisplayCoordinates {

	/**
	 * Returns a list of coordinates, as they could be on any coordinate system.
	 * 
	 * @return List of Integer coordinates the the object contains.
	 */
	List<Integer> getCoordinates();
	
}
