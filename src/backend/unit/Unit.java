/**
 *
 */
package backend.unit;

import backend.Game;
import backend.XMLsavable;
import backend.grid.Cell;
import backend.grid.CoordinateTuple;
import backend.unit.properties.Ability;
import backend.unit.properties.HitPoints;
import backend.unit.properties.MovePoints;

import java.util.Map;

/**
 * @author Dylan Peters, Timmy Huang
 */
public interface Unit extends XMLsavable {

    Game getGame();

    Cell getCurrentCell();

    default CoordinateTuple getCurrentLocation() {
        return getCurrentCell().getCoordinates();
    }

    default Map<CoordinateTuple, Cell> getNeighbors() {
        return getGame().getGrid().getNeighbors(getCurrentCell());
    }

    Map<String, Ability> getAbilities();

    void moveTo(Cell cell);

    InteractionModifier<Double> getAttackModifier();

    InteractionModifier<Double> getDefenseModifier();

    default void moveTo(CoordinateTuple coordinate) {
        moveTo(getGame().getGrid().getCells().get(coordinate));
    }

    HitPoints getHitPoints();

    int movePointsTo(CoordinateTuple other);

    MovePoints getMovePoints();

    MovementPattern getMovementPattern();
}
