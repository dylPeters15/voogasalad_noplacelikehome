package backend.grid;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Andreas
 *
 * @author Created by th174 on 3/28/2017.
 */
@FunctionalInterface
public interface BoundaryConditions {
    BoundaryConditions INFINITEBOUNDS = (grid, input) -> input;
    BoundaryConditions FINITEBOUNDS = (grid, input) -> new CoordinateTuple(IntStream.range(0, input.dimension()).map(i -> Math.min(Math.max(input.get(i), grid.getRectangularBounds().getMax(i)), grid.getRectangularBounds().getMin(i))).boxed().collect(Collectors.toList()));
    BoundaryConditions TOROIDALBOUNDS = (grid, input) -> new CoordinateTuple(IntStream.range(0, input.dimension()).map(i -> {
        int range = grid.getRectangularBounds().getMax(i) - grid.getRectangularBounds().getMin(i);
        return (input.get(i) - grid.getRectangularBounds().getMin(i) + range * 1000) % range + grid.getRectangularBounds().getMin(i);
    }).boxed().collect(Collectors.toList()));

    CoordinateTuple getMappedCoordinate(Grid grid, CoordinateTuple input);
}
