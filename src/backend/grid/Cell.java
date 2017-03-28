/**
 * 
 */
package backend.grid;

import java.util.Collection;

import backend.unit.Unit;

/**
 * @author Dylan Peters
 *
 */
public interface Cell {

	Grid getGrid();

	CoordinateTuple getCoordinates();

	Terrain getTerrain();

	/**
	 * 
	 * @return empty collection if there are no units
	 */
	Collection<Unit> getOccupyingUnits();

}
