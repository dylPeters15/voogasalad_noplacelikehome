package frontend.newunitpane;

import backend.cell.ModifiableTerrain;
import frontend.wizards.new_voogaobject_wizard.util.NumericInputRow;

public class TerrainInputRow extends NumericInputRow {

	public TerrainInputRow(ModifiableTerrain terrain) {
		super(terrain.getName(), terrain.getDescription());
	}

}
