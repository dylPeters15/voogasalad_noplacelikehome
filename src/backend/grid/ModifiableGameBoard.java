package backend.grid;

import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.util.ModifiableVoogaObject;

import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Created by th174 on 3/28/2017.
 */
public class ModifiableGameBoard extends ModifiableVoogaObject<ModifiableGameBoard> implements GameBoard, GameBoardBuilder {
	private final Map<CoordinateTuple, Cell> gameBoard;
	private ModifiableCell templateCell;
	private BoundsHandler boundsHandler;
	private int rows;
	private int columns;

	protected ModifiableGameBoard(String name, ModifiableCell templateCell, int rows, int columns, BoundsHandler boundsHandler, String description, String imgPath) {
		super(name, description, imgPath);
		this.boundsHandler = boundsHandler;
		this.templateCell = templateCell;
		gameBoard = IntStream.range(0, rows).boxed()
				.flatMap(i -> IntStream.range(0, columns).mapToObj(j -> new CoordinateTuple(i, j)))
				.parallel()
				.map(e -> e.convertToDimension(templateCell.dimension()))
				.collect(Collectors.toMap(e -> e, e -> templateCell.copy().setLocation(e)));
	}

	@Override
	public ModifiableGameBoard copy() {
		return new ModifiableGameBoard(getName(), getTemplateCell(), rows, columns, getBoundsHandler(), getDescription(), getImgPath());
	}

	@Override
	public BoundsHandler getBoundsHandler() {
		return boundsHandler;
	}

	@Override
	public ModifiableGameBoard setBoundsHandler(BoundsHandler boundsHandler) {
		this.boundsHandler = boundsHandler;
		return this;
	}

	@Override
	public ModifiableGameBoard addCell(CoordinateTuple coordinateTuple, ModifiableCell cell) {
		gameBoard.put(coordinateTuple, cell.copy().setLocation(coordinateTuple));
		return this;
	}

	@Override
	public ModifiableCell getTemplateCell() {
		return templateCell;
	}

	@Override
	public ModifiableGameBoard setTemplateCell(ModifiableCell cell) {
		this.templateCell = cell;
		return this;
	}

	@Override
	public ModifiableGameBoard setRows(int rows) {
		this.rows = rows;
		return this;
	}

	@Override
	public ModifiableGameBoard setColumns(int columns) {
		this.columns = columns;
		return this;
	}

	@Override
	public Map<CoordinateTuple, Cell> getCells() {
		return Collections.unmodifiableMap(gameBoard);
	}

	@Override
	public Cell get(CoordinateTuple coordinateTuple) {
		return gameBoard.getOrDefault(boundsHandler.getMappedCoordinate(this, coordinateTuple), null);
	}

	@Override
	public Iterator<Map.Entry<CoordinateTuple, Cell>> iterator() {
		return gameBoard.entrySet().iterator();
	}

	public static class InvalidGridException extends RuntimeException {
		InvalidGridException() {
			super("Incorrect Grid parameters! Could not generate grid!");
		}
	}
}
