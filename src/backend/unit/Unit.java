/**
 * 
 */
package backend.unit;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;

import backend.grid.Cell;
import backend.grid.Coordinate;
import backend.unit.properties.Ability;
import backend.unit.properties.HP;

/**
 * @author Dylan Peters
 *
 */
public interface Unit {

	Collection<Unit> getNeighbors();

	Map<String, Ability<Object>> getAbilities();

	default void moveTo(Cell cell) {
		moveTo(cell.getCoordinate());
	}

	void moveTo(Coordinate coordinate);

	HP getHP();

	int movePointsTo(Coordinate other);

	MovementPattern getMovementPattern();

	String getDescription();
	
	Path imagePath();

}
