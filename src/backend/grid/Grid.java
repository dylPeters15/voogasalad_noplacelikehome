/**
 * 
 */
package backend.grid;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import backend.Player;
import backend.grid.boundary.BoundaryConditions;

/**
 * @author Dylan Peters
 *
 */
public interface Grid {

	Map<List<Integer>, Cell> getCells();

	default Collection<Cell> getNeighbors(Cell cell) {
		return getNeighbors(cell.getCoordinate());
	}

	Collection<Cell> getNeighbors(Coordinate coordinate);

	/*
	 * this should be either protected or private to prevent dynamic changing of
	 * boundary conditions (in the first sprint at least.)
	 */
	void setBoundaryConditions(BoundaryConditions boundaryConditions);

	Collection<Cell> getVisibleCells(Player player,
			Predicate<Cell> visibilityPredicate);

	Collection<Cell> getExploredCells(Player player,
			Predicate<Cell> visibilityPredicate);

}
