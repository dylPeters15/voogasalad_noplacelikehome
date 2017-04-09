package frontend.newunitpane;

import backend.cell.Terrain;
import backend.util.GameState;
import frontend.util.VerticalTableInputView;

public class TerrainMovePointView extends VerticalTableInputView{
	
	public TerrainMovePointView(GameState gameState){
//		if (gameState != null){
			Terrain.getPredefinedTerrain().stream().forEachOrdered(terrain -> getChildren().add(new TerrainInputRow(terrain)));
//		}
	}

}
