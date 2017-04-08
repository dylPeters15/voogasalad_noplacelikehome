package backend.grid;

import backend.util.VoogaObject;

import java.util.Collection;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class BoundsHandler extends VoogaObject {
    //TODO ResourceBundlify this
    public static final BoundsHandler INFINITE_BOUNDS = new BoundsHandler("Infinite Bounds", (input, grid) -> input, "Allows grid to expand to accommodate out of bounds coordinates.");
    public static final BoundsHandler FINITE_BOUNDS = new BoundsHandler("Finite Bounds",
            (input, grid) -> {
                MutableGrid.GridBounds bounds = grid.getBounds();
                return new CoordinateTuple(
                        input.parallelStream()
                                .map(i -> Math.min(Math.max(input.get(i), bounds.getMax(i)), bounds.getMin(i)))
                                .collect(Collectors.toList())
                );
            }, "Converts out of bounds coordinates to the closest inbounds coordinate on the grid.");
    public static final BoundsHandler SQUARE_FINITE_BOUNDS = new BoundsHandler("Square Finite Bounds",
            (input, grid) -> {
                MutableGrid.GridBounds bounds = grid.getRectangularBounds();
                return new CoordinateTuple(
                        input.convertToRectangular().parallelStream()
                                .map(i -> Math.min(Math.max(input.get(i), bounds.getMax(i)), bounds.getMin(i)))
                                .collect(Collectors.toList())
                ).convertToDimension(input.dimension());
            }, "Converts out of bounds coordinates to the closest inbounds coordinate on a square grid.");
    public static final BoundsHandler TOROIDAL_BOUNDS = new BoundsHandler("Toroidal Bounds",
            (input, grid) -> {
                MutableGrid.GridBounds bounds = grid.getBounds();
                return new CoordinateTuple(
                        input.parallelStream()
                                .map(i -> Math.floorMod(input.get(i) - bounds.getMin(i), bounds.getMax(i) - bounds.getMin(i)) + bounds.getMin(i))
                                .collect(Collectors.toList())
                );
            }, "Wraps out of bounds coordinates to the opposite side of the grid", "Torus.png");
    public static final BoundsHandler SQUARE_TOROIDAL_BOUNDS = new BoundsHandler("Square Toroidal Bounds",
            (input, grid) -> {
                MutableGrid.GridBounds bounds = grid.getRectangularBounds();
                return new CoordinateTuple(
                        input.convertToRectangular().parallelStream()
                                .map(i -> Math.floorMod(input.get(i) - bounds.getMin(i), bounds.getMax(i) - bounds.getMin(i)) + bounds.getMin(i))
                                .collect(Collectors.toList())
                ).convertToDimension(input.dimension());
            }, "Wraps out of bounds coordinates to the opposite side of a square grid.", "Torus.png");
    private final BiFunction<CoordinateTuple, MutableGrid, CoordinateTuple> boundsGetter;

    public BoundsHandler(String name, BiFunction<CoordinateTuple, MutableGrid, CoordinateTuple> boundsGetter, String description) {
        this(name, boundsGetter, description, "");
    }

    public BoundsHandler(String name, BiFunction<CoordinateTuple, MutableGrid, CoordinateTuple> boundsGetter, String description, String imgPath) {
        super(name, description, imgPath);
        this.boundsGetter = boundsGetter;
    }

    public static Collection<BoundsHandler> getPredefinedBoundsHandlers() {
        return getPredefined(BoundsHandler.class);
    }

    public CoordinateTuple getMappedCoordinate(MutableGrid grid, CoordinateTuple input) {
        return boundsGetter.apply(input, grid);
    }
}
