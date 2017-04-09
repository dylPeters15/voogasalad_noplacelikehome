package backend.grid;

import backend.cell.CellTemplate;

/**
 * @author Created by th174 on 4/8/2017.
 */
public interface GameBoardBuilder {
	GameBoard copy();

	GameBoardBuilder setTemplateCell(CellTemplate cell);

	GameBoardBuilder setRows(int rows);

	GameBoardBuilder setColumns(int columns);

	GameBoardBuilder setBoundsHandler(BoundsHandler boundsHandler);

	GameBoardBuilder addCell(CoordinateTuple coordinates, CellTemplate cell);
}
