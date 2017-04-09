package frontend.newunitpane;

import backend.cell.Terrain;
import frontend.util.NumericInputRow;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(Terrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
