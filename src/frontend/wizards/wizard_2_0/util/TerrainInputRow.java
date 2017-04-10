package frontend.wizards.wizard_2_0.util;

import backend.cell.ModifiableTerrain;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(ModifiableTerrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
