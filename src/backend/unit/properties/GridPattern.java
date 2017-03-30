package backend.unit.properties;

import backend.grid.CoordinateTuple;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class GridPattern {
    private final Collection<CoordinateTuple> legalMoves;

    public GridPattern(Collection<CoordinateTuple> legalMoves) {
        this.legalMoves = legalMoves;
    }

    public Collection<CoordinateTuple> getLegalMoves() {
        return Collections.unmodifiableCollection(legalMoves);
    }

    public static GridPattern getNeighborPattern(int dimensions) {
        return new GridPattern(CoordinateTuple.getOrigin(dimensions).getNeighbors());
    }
}
