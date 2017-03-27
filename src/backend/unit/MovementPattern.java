/**
 * 
 */
package backend.unit;

import java.util.Collection;

import backend.grid.Coordinate;

/**
 * @author Dylan Peters
 *
 */
public interface MovementPattern {

	Collection<Coordinate> legalMoves();

	int numMoves();

}
