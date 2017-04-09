package frontend.newunitpane;

import backend.cell.ModifiableTerrain;
import frontend.util.NumericInputRow;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(ModifiableTerrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
