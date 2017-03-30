package backend.grid;

/**
 * Andreas
 * @author Created by th174 on 3/28/2017.
 */
@FunctionalInterface
public interface BoundaryConditions {
    BoundaryConditions INFINITEBOUNDS = (grid, input) -> input;
    BoundaryConditions FINITEBOUNDS = (grid, input) -> {
        int[] newCoordinates = new int[input.dimension()];
        for (int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = Math.min(Math.max(input.get(i), grid.getRectangularBounds().getMax(i)), grid.getRectangularBounds().getMin(i));
        }
        return new CoordinateTuple(newCoordinates);
    };
    BoundaryConditions TOROIDALBOUNDS = (grid, input) -> {
        int[] newCoordinates = new int[input.dimension()];
        for (int i = 0; i < newCoordinates.length; i++) {
            int range = grid.getRectangularBounds().getMax(i) - grid.getRectangularBounds().getMin(i);
            newCoordinates[i] = (input.get(i) - grid.getRectangularBounds().getMin(i) + range * 1000) % range + grid.getRectangularBounds().getMin(i);
        }
        return new CoordinateTuple(newCoordinates);
    };

    CoordinateTuple getMappedCoordinate(Grid grid, CoordinateTuple input);
}
