/**
 * 
 */
package backend.grid;

import java.util.Collection;
import java.util.List;

import backend.unit.Unit;

/**
 * @author Dylan Peters
 *
 */
public interface Cell {

	List<Integer> getCoordinate();

	Terrain getTerrain();

	/**
	 * 
	 * @return empty collection if there are no units
	 */
	Collection<Unit> getOccupyingUnits();

}
