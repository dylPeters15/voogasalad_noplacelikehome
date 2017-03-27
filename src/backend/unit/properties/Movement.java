/**
 * 
 */
package backend.unit.properties;

import backend.grid.Coordinate;
import backend.grid.Grid;

/**
 * @author Dylan Peters
 *
 */
public interface Movement {

	int getMovePoints();

	int getMaxMovePoints();

	int movePointsRequired(Grid grid, Coordinate from, Coordinate to);

	void setMovePoints(int numPoints);

}
