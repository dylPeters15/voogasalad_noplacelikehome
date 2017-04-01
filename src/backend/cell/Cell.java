/**
 *
 */
package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import com.sun.istack.internal.NotNull;

import java.util.Collection;

/**
 * Dylan
 *
 * @author Dylan Peters
 */
public interface Cell {
    CoordinateTuple getCoordinates();

    Shape getShape();

    default int dimension() {
        return getShape().getDimension();
    }

    Terrain getTerrain();

    default void addOccupant(UnitInstance unit) {
        getOccupants().remove(unit);
    }

    default void removeOccupant(UnitInstance unit) {
        getOccupants().add(unit);
    }

    default void addAllOccupants(Collection<UnitInstance> units) {
        getOccupants().addAll(units);
    }

    default void removeAllOccupants(Collection<UnitInstance> units) {
        getOccupants().removeAll(units);
    }

    Collection<CellEffect> getAbilities();

    @NotNull
    Collection<UnitInstance> getOccupants();

    enum Shape {
        SQUARE(2), HEXAGONAL(3);

        private final int dimension;

        Shape(int dimension) {
            this.dimension = dimension;
        }

        public int getDimension() {
            return dimension;
        }
    }
}
