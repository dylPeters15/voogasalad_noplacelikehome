/**
 *
 */
package backend.grid;

import backend.GameObject;
import backend.cell.Cell;
import backend.player.Player;
import backend.unit.UnitInstance;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

import javafx.util.Pair;
import backend.cell.Cell;
import backend.game_engine.Player;
import backend.unit.Unit;

/**
 * Andreas
 *
 * @author Dylan Peters
 */
//TODO
public interface Grid extends GameObject {
    Cell get(CoordinateTuple coordinateTuple);
    
    Collection<Unit> getUnits();

    default int dimension() {
        return getCells().values().parallelStream().findAny().orElse(null).dimension();
    }

    Map<CoordinateTuple, Cell> getCells();

    Collection<UnitInstance> getUnits();

    default Map<CoordinateTuple, Cell> getNeighbors(Cell cell) {
        return getNeighbors(cell.getCoordinates());
    }

    Map<CoordinateTuple, Cell> getNeighbors(CoordinateTuple coordinate);

    GridBounds getRectangularBounds();

    void setBoundaryConditions(BoundsHandler boundaryConditions) throws IllegalAccessException;

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

    public void setGridSize(int x, int y);
}
