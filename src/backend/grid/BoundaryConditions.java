package backend.grid;

/**
 * @author Dylan Peters, Timmy Huang
 */
@FunctionalInterface
public interface BoundaryConditions {
    BoundaryConditions INFINITEBOUNDS = (grid, coordinateIn) -> coordinateIn;
    BoundaryConditions FINITEBOUNDS = (grid, coordinateIn) -> {
        int[] newCoordinates = new int[coordinateIn.dimension()];
        for (int i = 0; i < newCoordinates.length; i++) {
            newCoordinates[i] = Math.min(Math.max(coordinateIn.get(i), grid.getRectangularBounds().getMax(i)), grid.getRectangularBounds().getMin(i));
        }
        return new CoordinateTuple(newCoordinates);
    };
    BoundaryConditions TOROIDALBOUNDS = (grid, coordinateIn) -> {
        int[] newCoordinates = new int[coordinateIn.dimension()];
        for (int i = 0; i < newCoordinates.length; i++) {
            int range = grid.getRectangularBounds().getMax(i) - grid.getRectangularBounds().getMin(i);
            newCoordinates[i] = (coordinateIn.get(i) - grid.getRectangularBounds().getMin(i) + range * 1000) % range + grid.getRectangularBounds().getMin(i);
        }
        return new CoordinateTuple(newCoordinates);
    };

    CoordinateTuple getMappedCoordinate(Grid grid, CoordinateTuple coordinateIn);
}
