package backend.grid;

import backend.cell.CellInstance;
import backend.cell.CellTemplate;
import backend.player.Player;
import backend.unit.UnitInstance;
import backend.util.GameState;
import backend.util.VoogaObject;
import javafx.util.Pair;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class GameBoard extends VoogaObject implements Grid, Iterable {
    private final Map<CoordinateTuple, CellInstance> gameBoard;
    private CellTemplate templateCell;
    private BoundsHandler currentBoundsMode;

    public GameBoard(String name, CellTemplate templateCell, int rows, int columns, BoundsHandler currentBoundsMode, String description, String imgPath, GameState game) {
        super(name, description, imgPath);
        this.currentBoundsMode = currentBoundsMode;
        this.templateCell = templateCell;
        gameBoard = IntStream.range(0, rows).boxed()
                .flatMap(i -> IntStream.range(0, columns).mapToObj(j -> new CoordinateTuple(i, j)))
                .parallel()
                .map(e -> e.convertToDimension(templateCell.dimension()))
                .map(e -> new Pair<>(e, templateCell.createInstance(e)))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public CellInstance get(CoordinateTuple coordinateTuple) {
        return gameBoard.getOrDefault(currentBoundsMode.getMappedCoordinate(this, coordinateTuple), null);
    }

    @Override
    public CellTemplate getTemplateCell() {
        return templateCell;
    }

    @Override
    public Map<CoordinateTuple, CellInstance> getCells() {
        return Collections.unmodifiableMap(gameBoard);
    }

    @Override
    public Collection<UnitInstance> getUnits() {
        return parallelStream().map(CellInstance::getOccupants).flatMap(Collection::stream).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override
    public Map<CoordinateTuple, CellInstance> getNeighbors(CoordinateTuple coordinate) {
        return CoordinateTuple
                .getOrigin(coordinate.dimension())
                .getNeighbors()
                .parallelStream()
                .map(e -> new Pair<>(e, gameBoard.get(coordinate.sum(e))))
                .filter(e -> Objects.nonNull(e.getValue()))
                .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    @Override
    public GridBounds getBounds() {
        return new GridBounds(getMinMax(gameBoard.keySet().parallelStream()));
    }

    @Override
    public GridBounds getRectangularBounds() {
        return new GridBounds(getMinMax(gameBoard.keySet().parallelStream().map(CoordinateTuple::convertToRectangular)));
    }

    private int[][] getMinMax(Stream<CoordinateTuple> coordinateTuples) {
        int[][] minMax = new int[coordinateTuples.findAny().orElse(CoordinateTuple.EMPTY).dimension()][2];
        coordinateTuples.forEach(e -> {
            for (int[] ints : minMax) {
                ints[0] = Math.min(e.get(0), ints[0]);
                ints[1] = Math.max(e.get(0), ints[1]);
            }
        });
        return minMax;
    }

    @Override
    public void setBoundaryConditions(BoundsHandler boundaryConditions) {
        this.currentBoundsMode = boundaryConditions;
    }

    @Override
    public Collection<CellInstance> filterCells(Player player, BiPredicate<Player, CellInstance> visibilityPredicate) {
        return parallelStream().filter(c -> visibilityPredicate.test(player, c)).collect(Collectors.toList());
    }

    @Override
    public Iterator iterator() {
        return gameBoard.entrySet().iterator();
    }

    public Stream<CellInstance> stream() {
        return gameBoard.values().stream();
    }

    private Stream<CellInstance> parallelStream() {
        return gameBoard.values().parallelStream();
    }

    @Override
    public void setGridSize(int x, int y) {
        // TODO Implement this
    }
}
