package backend.grid;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Andreas
 *
 * @author Dylan Peters, Timmy Huang
 */
public class CoordinateTuple implements Iterable<Integer> {
    public static final CoordinateTuple EMPTY = new CoordinateTuple();

    private final List<Integer> coordinates;

    public CoordinateTuple(int... coordinates) {
        this.coordinates = Collections.unmodifiableList(Arrays.stream(coordinates).boxed().collect(Collectors.toList()));
    }

    public CoordinateTuple(List<Integer> coordinates) {
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    public static CoordinateTuple getOrigin(int dimension) {
        return new CoordinateTuple(new int[dimension]);
    }

    public int dimension() {
        return coordinates.size();
    }

    public Collection<CoordinateTuple> getNeighbors() {
        return getRays(1);
    }

    public Collection<CoordinateTuple> getRays(int radius) {
        return IntStream.rangeClosed(1, radius).boxed()
                .flatMap(i -> IntStream.range(0, dimension()).boxed()
                        .flatMap(j -> {
                            List<Integer> temp1 = Collections.nCopies(dimension(), i);
                            List<Integer> temp2 = Collections.nCopies(dimension(), -i);
                            temp1.set(j, 0);
                            temp2.set(j, 0);
                            return Stream.of(new CoordinateTuple(temp1), new CoordinateTuple(temp2));
                        }))
                .parallel().collect(Collectors.toList());
    }

    public double euclideanDistanceTo(CoordinateTuple other) {
        return Math.sqrt(
                IntStream.range(0, dimension())
                        .mapToDouble(i -> Math.pow(this.get(i) - other.get(i), 2))
                        .sum());
    }

    public double manhattanDistanceTo(CoordinateTuple other) {
        return ((dimension() == 3) ? 0.5 : 1) *
                IntStream.range(0, dimension())
                        .map(i -> this.get(i) - other.get(i))
                        .sum();
    }

    public int get(int index) {
        return coordinates.get(index);
    }

    public CoordinateTuple replace(int index, int newValue) {
        List<Integer> newCoordinates = new ArrayList<>(coordinates);
        coordinates.set(index, newValue);
        return new CoordinateTuple(newCoordinates);
    }

    @Override
    public Iterator<Integer> iterator() {
        return coordinates.iterator();
    }

    public CoordinateTuple sum(CoordinateTuple other) {
        if (this.dimension() != other.dimension()) {
            throw new DimensionMismatchException(this.dimension(), other.dimension());
        }
        int[] newCoordinates = new int[dimension()];
        IntStream.range(0, dimension()).forEach(i -> newCoordinates[i] = this.get(i) + other.get(i));
        return new CoordinateTuple(newCoordinates);
    }

    public CoordinateTuple convertToRectangular() {
        if (dimension() == 2) {
            return this;
        }
        int col = get(0);
        int row = get(2) + (get(0) + (get(0) & 1)) / 2;
        return new CoordinateTuple(row, col);
    }

    public CoordinateTuple convertToDimension(int dimension) {
        switch (dimension) {
            case 2:
                return convertToRectangular();
            case 3:
                return convertToHexagonal();
            default:
                throw new RuntimeException("Not Implemented Yet");
        }
    }

    private CoordinateTuple convertToHexagonal() {
        if (dimension() == 3) {
            return this;
        }
        int x = get(1);
        int z = get(0) - (get(1) + (get(1) & 1)) / 2;
        return new CoordinateTuple(x, -(x + z), z);
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof CoordinateTuple && this.coordinates.equals(((CoordinateTuple) other).coordinates);
    }

    @Override
    public int hashCode() {
        return coordinates.hashCode();
    }

    @Override
    public String toString() {
        return coordinates.toString().replace("[", "(").replace("]", ")");
    }

    public Stream<Integer> stream() {
        return coordinates.stream();
    }

    public static class DimensionMismatchException extends RuntimeException {
        DimensionMismatchException(int dim1, int dim2) {
            super(String.format("Coordinate Tuple Dimension Mismatch: %d,%d", dim1, dim2));
        }
    }
}