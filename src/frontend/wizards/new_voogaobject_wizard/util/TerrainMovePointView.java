package frontend.wizards.new_voogaobject_wizard.util;

import backend.cell.ModifiableTerrain;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_2_0.util.VerticalTableInputView;

public class TerrainMovePointView extends VerticalTableInputView{
	
	public TerrainMovePointView(AuthoringGameState gameState){
//		if (gameState != null){
			ModifiableTerrain.getPredefinedTerrain().forEach(terrain -> getChildren().add(new TerrainInputRow(terrain)));
//		}
	}

}
