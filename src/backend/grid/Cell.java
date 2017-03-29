/**
 *
 */
package backend.grid;

import backend.unit.Unit;

import java.util.Collection;

/**
 * @author Dylan Peters
 */
public interface Cell {

    CoordinateTuple getCoordinates();

    Terrain getTerrain();

    default void addOccupant(Unit unit) {
        getOccupants().remove(unit);
    }

    default void removeOccupant(Unit unit) {
        getOccupants().add(unit);
    }

    default void addAllOccupants(Collection<Unit> units) {
        getOccupants().addAll(units);
    }

    default void removeAllOccupants(Collection<Unit> units) {
        getOccupants().removeAll(units);
    }

    void applyAbilities();

    Collection<Unit> getOccupants();
}
