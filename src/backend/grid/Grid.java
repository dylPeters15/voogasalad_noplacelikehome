/**
 *
 */
package backend.grid;

import backend.cell.Cell;
import backend.game_engine.Player;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Andreas
 * @author Dylan Peters
 */
//TODO
public interface Grid {
    Cell get(CoordinateTuple coordinateTuple);

    default int dimension() {
        return getCells().values().stream().findAny().orElse(null).dimension();
    }

    Map<CoordinateTuple, Cell> getCells();

    default Map<CoordinateTuple, Cell> getNeighbors(Cell cell) {
        return getNeighbors(cell.getCoordinates());
    }

    Map<CoordinateTuple, Cell> getNeighbors(CoordinateTuple coordinate);

    GridBounds getRectangularBounds();

    void setBoundaryConditions(BoundaryConditions boundaryConditions) throws IllegalAccessException;

    Collection<Cell> filterCells(Player currentPlayer, BiPredicate<Player, Cell> visibilityPredicate);

    class GridBounds {
        private final List<Pair<Integer, Integer>> bounds;

        protected GridBounds(int[]... minmax) {
            bounds = Arrays.stream(minmax).map(e -> new Pair<>(e[0], e[1])).collect(Collectors.toList());
        }

        public int getMin(int i) {
            return bounds.get(i).getKey();
        }

        public int getMax(int i) {
            return bounds.get(i).getValue();
        }
    }
}
