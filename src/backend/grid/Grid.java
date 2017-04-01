/**
 *
 */
package backend.grid;

import backend.cell.CellInstance;
import backend.cell.CellTemplate;
import backend.player.Player;
import backend.unit.UnitInstance;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

/**
 * Andreas
 *
 * @author Dylan Peters
 */
public interface Grid {
    CellInstance get(CoordinateTuple coordinateTuple);

    CellTemplate getTemplateCell();

    default int dimension() {
        return getTemplateCell().dimension();
    }

    Map<CoordinateTuple, CellInstance> getCells();

    Collection<UnitInstance> getUnits();

    default Map<CoordinateTuple, CellInstance> getNeighbors(CellInstance cell) {
        return getNeighbors(cell.getCoordinates());
    }

    Map<CoordinateTuple, CellInstance> getNeighbors(CoordinateTuple coordinate);

    GridBounds getRectangularBounds();

    void setBoundaryConditions(BoundsHandler boundaryConditions) throws IllegalAccessException;

    Collection<CellInstance> filterCells(Player currentPlayer, BiPredicate<Player, CellInstance> visibilityPredicate);

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
