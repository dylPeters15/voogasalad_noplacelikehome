package frontend.wizards.new_voogaobject_wizard.util;

import backend.cell.ModifiableTerrain;
import frontend.wizards.wizard_2_0.util.NumericInputRow;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(ModifiableTerrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
