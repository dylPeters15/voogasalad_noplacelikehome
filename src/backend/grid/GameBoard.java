package backend.grid;

import backend.GameObjectImpl;
import backend.cell.Cell;
import backend.cell.CellImpl;
import backend.game_engine.GameState;
import backend.io.XMLsavable;
import backend.player.Player;
import backend.unit.UnitInstance;
import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;
import javafx.util.Pair;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Andreas
 *
 * @author Created by th174 on 3/28/2017.
 */
public class GameBoard extends GameObjectImpl implements Grid, XMLsavable, Iterable {
    private final ObservableMap<CoordinateTuple, Cell> gameBoard;
    private BoundsHandler currentBoundsMode;

    public GameBoard(String name, CellImpl templateCell, int rows, int columns, BoundsHandler currentBoundsMode, String description, String imgPath, GameState game) {
        super(name, description, imgPath, game);
        this.currentBoundsMode = currentBoundsMode;
        gameBoard = FXCollections.observableMap(
                IntStream.range(0, rows).boxed()
                        .flatMap(i -> IntStream.range(0, columns).mapToObj(j -> new CoordinateTuple(i, j)))
                        .parallel()
                        .map(e -> (templateCell.getCoordinates().dimension() == 3) ? e.convertToHexagonal() : e)
                        .map(e -> new Pair<CoordinateTuple, Cell>(e, new CellImpl(e, templateCell, getGame())))
                        .collect(Collectors.toMap(Pair::getKey, Pair::getValue)));
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
    public Collection<UnitInstance> getUnits() {
        return parallelStream().map(Cell::getOccupants).flatMap(Collection::stream).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Map<CoordinateTuple, Cell> getNeighbors(CoordinateTuple coordinate) {
        return CoordinateTuple
                .getOrigin(coordinate.dimension())
                .getNeighbors()
                .parallelStream()
                .map(e -> new Pair<>(e, gameBoard.get(coordinate.sum(e))))
                .filter(e -> Objects.nonNull(e.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public GridBounds getRectangularBounds() {
        List<CoordinateTuple> rectCoordinates = gameBoard.keySet().parallelStream().map(CoordinateTuple::convertToRectangular).collect(Collectors.toList());
        int[] xBounds = new int[]{rectCoordinates.parallelStream().mapToInt(e -> e.get(0)).min().getAsInt(), rectCoordinates.parallelStream().mapToInt(e -> e.get(1)).max().getAsInt()};
        int[] yBounds = new int[]{rectCoordinates.parallelStream().mapToInt(e -> e.get(1)).min().getAsInt(), rectCoordinates.parallelStream().mapToInt(e -> e.get(1)).max().getAsInt()};
        return new GridBounds(xBounds, yBounds);
    }

    @Override
    public void setBoundaryConditions(BoundsHandler boundaryConditions) {
        this.currentBoundsMode = boundaryConditions;
    }

    @Override
    public Collection<Cell> filterCells(Player player, BiPredicate<Player, Cell> visibilityPredicate) {
        return parallelStream().filter(c -> visibilityPredicate.test(player, c)).collect(Collectors.toList());
    }

    @Override
    public Iterator iterator() {
        return gameBoard.entrySet().iterator();
    }

    public Stream<Cell> stream() {
        return gameBoard.values().stream();
    }

    private Stream<Cell> parallelStream() {
        return gameBoard.values().parallelStream();
    }

    @Override
    public String toXml() {
        return null;
    }

    @Override
    public void setGridSize(int x, int y) {
        // TODO Implement this
    }
}
