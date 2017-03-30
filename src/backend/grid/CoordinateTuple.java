package backend.grid;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Andreas
 * @author Dylan Peters, Timmy Huang
 */
public final class CoordinateTuple implements Iterable<Integer> {
    private final List<Integer> coordinates;

    public CoordinateTuple(int... coordinates) {
        this.coordinates = Arrays.stream(coordinates).boxed().collect(Collectors.toList());
    }

    private CoordinateTuple(List<Integer> coordinates) {
        this.coordinates = new ArrayList<>(coordinates);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof CoordinateTuple && ((CoordinateTuple) other).dimension() == this.dimension())) {
            return false;
        }
        for (int i = 0; i < this.dimension(); i++) {
            if (this.get(i) != ((CoordinateTuple) other).get(i)) {
                return false;
            }
        }
        return true;
    }

    public int dimension() {
        return coordinates.size();
    }

    public Set<CoordinateTuple> getNeighbors() {
        Set<CoordinateTuple> neighboringCoordinates = new HashSet<>();
        CoordinateTuple origin = CoordinateTuple.getOrigin(this.dimension());
        for (int i = 0; i < this.dimension(); i++) {
            CoordinateTuple neighbor1 = origin.replace(i, -1);
            CoordinateTuple neighbor2 = origin.replace(i, 1);
            neighboringCoordinates.add(this.sum(neighbor1));
            neighboringCoordinates.add(this.sum(neighbor2));
        }
        return neighboringCoordinates;
    }

    public double euclideanDistanceTo(CoordinateTuple other) {
        double sum = 0;
        for (int i = 0; i < this.dimension(); i++) {
            sum += Math.pow(this.get(0) - other.get(0), 2);
        }
        return Math.sqrt(sum);
    }

    public double manhattanDistanceTo(CoordinateTuple other) {
        double sum = 0;
        for (int i = 0; i < this.dimension(); i++) {
            sum += this.get(0) - other.get(0);
        }
        return sum;
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
        for (int i = 0; i < this.dimension(); i++) {
            newCoordinates[i] = this.get(i) + other.get(i);
        }
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

    public CoordinateTuple convertToHexagonal() {
        if (dimension() == 3) {
            return this;
        }
        int x = get(1);
        int z = get(0) - (get(1) + (get(1) & 1)) / 2;
        return new CoordinateTuple(x, -(x + z), z);
    }

    public static CoordinateTuple getOrigin(int dimension) {
        return new CoordinateTuple(new int[dimension]);
    }

    public static class DimensionMismatchException extends RuntimeException {
        DimensionMismatchException(int dim1, int dim2) {
            super(String.format("Coordinate Tuple Dimension Mismatch: %d,%d", dim1, dim2));
        }
    }

    @Override
    public String toString() {
        return coordinates.toString().replace("[", "(").replace("]", ")");
    }
}