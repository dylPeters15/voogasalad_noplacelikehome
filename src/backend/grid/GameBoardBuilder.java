package backend.grid;

import backend.cell.ModifiableCell;

/**
 * @author Created by th174 on 4/8/2017.
 */
public interface GameBoardBuilder {
	GameBoard copy();

	GameBoardBuilder setTemplateCell(ModifiableCell cell);

	GameBoardBuilder setRows(int rows);

	GameBoardBuilder setColumns(int columns);

	GameBoardBuilder setBoundsHandler(BoundsHandler boundsHandler);

	GameBoardBuilder addCell(CoordinateTuple coordinates, ModifiableCell cell);
}
