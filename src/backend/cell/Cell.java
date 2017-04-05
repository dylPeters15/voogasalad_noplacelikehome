package backend.cell;

import backend.grid.CoordinateTuple;
import backend.unit.UnitInstance;
import backend.util.TriggeredEffectInstance;
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

    Collection<TriggeredEffectInstance> getTriggeredAbilities();

    default void addAbility(TriggeredEffectInstance cellEffect) {
        getTriggeredAbilities().add(cellEffect);
    }

    default void removeAbility(TriggeredEffectInstance cellEffect) {
        getTriggeredAbilities().remove(cellEffect);
    }

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
