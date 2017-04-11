package backend.grid;

import backend.cell.Cell;

/**
 * @author Created by th174 on 4/8/2017.
 */
public interface GameBoardBuilder {
	GameBoardBuilder setTemplateCell(Cell cell);

	GameBoardBuilder setRows(int rows);

	GameBoardBuilder setColumns(int columns);

	GameBoardBuilder setBoundsHandler(BoundsHandler boundsHandler);

	GameBoardBuilder setCell(CoordinateTuple coordinates, Cell cell);

	GameBoard build();
}
