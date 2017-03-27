/**
 * 
 */
package backend.grid.boundary;

import backend.grid.Coordinate;

/**
 * @author Dylan Peters
 *
 */
public interface BoundaryConditions {
	
	Coordinate getMappedCoordinate(Coordinate coordinateIn);

}
