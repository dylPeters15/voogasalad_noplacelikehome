package backend.cell;

import backend.grid.CoordinateTuple;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author Created by th174 on 3/28/2017.
 */
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