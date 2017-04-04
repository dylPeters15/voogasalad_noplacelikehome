package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import com.sun.istack.internal.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

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

    Collection<CellAbility> getAbilities();

    default void addAbility(CellAbility cellEffect) {
        getAbilities().add(cellEffect);
    }

    default void removeAbility(CellAbility cellEffect) {
        getAbilities().remove(cellEffect);
    }

    @NotNull
    Collection<UnitInstance> getOccupants();

    enum Shape {
        SQUARE(2, new CoordinateTuple(0, 1), new CoordinateTuple(1, 0), new CoordinateTuple(-1, 0), new CoordinateTuple(0, -1)),
        HEXAGONAL(3, new CoordinateTuple(0, 1, -1), new CoordinateTuple(0, -1, 1), new CoordinateTuple(1, 0, -1), new CoordinateTuple(-1, 0, 1), new CoordinateTuple(1, -1, 0), new CoordinateTuple(-1, 1, 0));

        private final int dimension;
        private final Collection<CoordinateTuple> neighbors;
        public Collection<CoordinateTuple> getNeighbors;

        Shape(int dimension, CoordinateTuple... neighbors) {
            this(dimension, Arrays.asList(neighbors));
        }

        Shape(int dimension, Collection<CoordinateTuple> neighbors) {
            this.dimension = dimension;
            this.neighbors = new HashSet<>(neighbors);
        }

        public int getDimension() {
            return dimension;
        }
    }
}
