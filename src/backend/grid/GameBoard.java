package backend.grid;

import backend.Player;
import backend.XMLsavable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class GameBoard implements Grid, XMLsavable, Iterable {
    private final ObservableMap<CoordinateTuple, Cell> gameBoard;
    private BoundaryConditions currentBoundsMode;

    //TODO: Actual constructor
    private GameBoard() {
        gameBoard = FXCollections.observableHashMap();
        currentBoundsMode = BoundaryConditions.FINITEBOUNDS;
    }

    @Override
    public Cell get(CoordinateTuple coordinateTuple) {
        return gameBoard.getOrDefault(currentBoundsMode.getMappedCoordinate(this, coordinateTuple), null);
    }

    @Override
    public Map<CoordinateTuple, Cell> getCells() {
        return Collections.unmodifiableMap(gameBoard);
    }

    @Override
    public Map<CoordinateTuple, Cell> getNeighbors(CoordinateTuple coordinate) {
        Map<CoordinateTuple, Cell> neighbors = new HashMap<>();
        CoordinateTuple origin = CoordinateTuple.getOrigin(coordinate.dimension());
        for (int i = 0; i < coordinate.dimension(); i++) {
            CoordinateTuple neighbor1 = origin.replace(i, -1);
            CoordinateTuple neighbor2 = origin.replace(i, 1);
            neighbors.put(neighbor1, gameBoard.get(coordinate.sum(neighbor1)));
            neighbors.put(neighbor2, gameBoard.get(coordinate.sum(neighbor2)));
        }
        return neighbors;
    }

    @Override
    public GridBounds getRectangularBounds() {
        List<CoordinateTuple> rectCoordinates = gameBoard.keySet().stream().map(CoordinateTuple::convertToRectangular).collect(Collectors.toList());
        int[] xBounds = new int[]{rectCoordinates.stream().mapToInt(e -> e.get(0)).min().getAsInt(), rectCoordinates.stream().mapToInt(e -> e.get(1)).max().getAsInt()};
        int[] yBounds = new int[]{rectCoordinates.stream().mapToInt(e -> e.get(1)).min().getAsInt(), rectCoordinates.stream().mapToInt(e -> e.get(1)).max().getAsInt()};
        return new GridBounds(xBounds, yBounds);
    }

    @Override
    public void setBoundaryConditions(BoundaryConditions boundaryConditions) {
        this.currentBoundsMode = boundaryConditions;
    }

    @Override
    public Collection<Cell> filterCells(Player currentPlayer, BiPredicate<Player, Cell> visibilityPredicate) {
        return stream().filter(c -> visibilityPredicate.test(currentPlayer,c)).collect(Collectors.toList());
    }

    @Override
    public Collection<Cell> getExploredCells(Player currentPlayer, BiPredicate<Player, Cell> visibilityPredicate) {
        //TODO: How do we want to keep track of explored cells?
        return stream().filter(c -> visibilityPredicate.test(currentPlayer,c)).collect(Collectors.toList());
    }

    @Override
    public Iterator iterator() {
        return gameBoard.entrySet().iterator();
    }

    public Stream<Cell> stream() {
        return gameBoard.values().stream();
    }

    @Override
    public String toXml() {
        return null;
    }
}
