package backend.grid.boundary;

import backend.grid.CoordinateTuple;
import backend.grid.Grid;

/**
 * @author Dylan Peters, Timmy Huang
 */
public interface BoundaryConditions {
    BoundaryConditions INFINITEBOUNDS = (grid, coordinateIn) -> coordinateIn;
    BoundaryConditions FINITEBOUNDS = (grid, coordinateIn) -> {
        Integer[] newCoordinates = new Integer[coordinateIn.dimension()];
        for (int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = Math.min(Math.max(coordinateIn.get(i), grid.getRectangularBounds().getMax(i)), grid.getRectangularBounds().getMin(i));
        }
        return new CoordinateTuple(newCoordinates);
    };
    BoundaryConditions TOROIDALBOUNDS = (grid, coordinateIn) -> {
        Integer[] newCoordintes = new Integer[coordinateIn.dimension()];
        for (int i = 0; i < newCoordintes.length; i++) {
            int range = grid.getRectangularBounds().getMax(i) - grid.getRectangularBounds().getMin(i);
            newCoordintes[i] = (coordinateIn.get(i) - grid.getRectangularBounds().getMin(i) + range * 1000) % range + grid.getRectangularBounds().getMin(i);
        }
        return new CoordinateTuple(newCoordintes);
    };

    CoordinateTuple getMappedCoordinate(Grid grid, CoordinateTuple coordinateIn);
}
