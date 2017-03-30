package backend.unit;

import backend.grid.CoordinateTuple;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Dylan Peters, Timmy Huang
 */
public class MovementPattern {
    private final Set<CoordinateTuple> legalMoves;
    private final int numSteps;

    public MovementPattern(int numSteps, Collection<CoordinateTuple> legalMoves) {
        this.numSteps = numSteps;
        this.legalMoves = new HashSet<>(legalMoves);
    }

    public Collection<CoordinateTuple> getLegalMoves() {
        return Collections.unmodifiableSet(legalMoves);
    }

    public int getNumSteps() {
        return numSteps;
    }
}
