/**
 *
 */
package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.Unit;
import com.sun.istack.internal.NotNull;

import java.util.Collection;
import java.util.Map;

/**
 * Dylan
 *
 * @author Dylan Peters
 */
public interface Cell {
    CoordinateTuple getCoordinates();

    Map<CoordinateTuple, Cell> getNeighbors();

    default int dimension() {
        return getCoordinates().dimension();
    }

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

    @NotNull
    Collection<Unit> getOccupants();
}
