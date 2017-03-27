/**
 *
 */
package backend.unit;

import backend.XMLsavable;
import backend.grid.Cell;
import backend.grid.Coordinate;
import backend.grid.Grid;
import backend.grid.Terrain;
import backend.unit.properties.Ability;
import backend.unit.properties.HitPoints;

import java.nio.file.Path;
import java.util.Collection;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author Dylan Peters, Timmy Huang
 */
public interface Unit extends XMLsavable {

    Cell getCurrentCell();

    Grid getGrid();

    default Coordinate getCurrentLocation() {
        return getCurrentCell().getCoordinates();
    }

    default Collection<Cell> getNeighbors() {
        return getGrid().getNeighbors(getCurrentCell());
    }

    Map<String, Ability> getAbilities();

    void moveTo(Cell cell);

    default double getCurrentHitChance() {
        return getHitChance().get(getCurrentCell().getTerrain());
    }

    Map<Terrain, Double> getHitChance();

    UnaryOperator<Double> getAttackModifier();

    UnaryOperator<Double> getDefenseModifier();

    default void moveTo(Coordinate coordinate) {
        moveTo(getGrid().getCells().get(coordinate));
    }

    HitPoints getHitPoints();

    int movePointsTo(Coordinate other);

    MovementPattern getMovementPattern();

    String getDescription();

    Path imagePath();

}
