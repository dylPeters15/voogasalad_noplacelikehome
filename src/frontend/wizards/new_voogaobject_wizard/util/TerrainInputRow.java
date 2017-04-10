package frontend.wizards.new_voogaobject_wizard.util;

import backend.cell.ModifiableTerrain;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(ModifiableTerrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
