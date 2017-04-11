package frontend.wizards.new_voogaobject_wizard.util;

import backend.cell.ModifiableTerrain;
import backend.util.AuthorGameState;
import frontend.wizards.wizard_2_0.util.VerticalTableInputView;

public class TerrainMovePointView extends VerticalTableInputView{
	
	public TerrainMovePointView(AuthorGameState gameState){
//		if (gameState != null){
			ModifiableTerrain.getPredefinedTerrain().stream().forEachOrdered(terrain -> getChildren().add(new TerrainInputRow(terrain)));
//		}
	}

}
