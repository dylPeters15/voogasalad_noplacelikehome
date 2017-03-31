package backend.grid;

import backend.GameObjectImpl;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Andreas
 *
 * @author Created by th174 on 3/28/2017.
 */
public class BoundsHandler extends GameObjectImpl {
    public static final BoundsHandler INFINITEBOUNDS = new BoundsHandler("Infinite Bounds", (grid, input) -> input, "Allows grid to expand to accommodate out of bounds coordinates.");
    public static final BoundsHandler FINITEBOUNDS = new BoundsHandler("Finite Bounds", (grid, input) -> new CoordinateTuple(IntStream.range(0, input.dimension()).map(i -> Math.min(Math.max(input.get(i), grid.getRectangularBounds().getMax(i)), grid.getRectangularBounds().getMin(i))).boxed().collect(Collectors.toList())), "Converts out of bounds coordinates to the closest in bounds coordinate.");
    public static final BoundsHandler TOROIDALBOUNDS = new BoundsHandler("Toroidal Bounds", (grid, input) -> new CoordinateTuple(IntStream.range(0, input.dimension()).map(i -> Math.floorMod(input.get(i) - grid.getRectangularBounds().getMin(i), grid.getRectangularBounds().getMax(i) - grid.getRectangularBounds().getMin(i)) + grid.getRectangularBounds().getMin(i)).boxed().collect(Collectors.toList())), "Wraps out of bounds coordinates to the opposite side of the grid");

    private final BoundsGetter boundsGetter;

    public BoundsHandler(String name, BoundsGetter boundsGetter, String description) {
        this(name, boundsGetter, description, "");
    }

    public BoundsHandler(String name, BoundsGetter boundsGetter, String description, String imgPath) {
        super(name, description, imgPath);
        this.boundsGetter = boundsGetter;
    }

    public CoordinateTuple getMappedCoordinate(Grid grid, CoordinateTuple input) {
        return boundsGetter.mapCoordinate(grid, input);
    }

    @FunctionalInterface
    interface BoundsGetter {
        CoordinateTuple mapCoordinate(Grid grid, CoordinateTuple input);
    }
}
