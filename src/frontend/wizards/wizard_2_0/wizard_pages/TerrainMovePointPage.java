package frontend.wizards.wizard_2_0.wizard_pages;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_2_0.util.NumericInputRow;
import frontend.wizards.wizard_2_0.util.VerticalTableInputView;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class TerrainMovePointPage extends WizardPage {

	private VerticalTableInputView table;
	private Map<NumericInputRow, Terrain> rowToTerrain;

	public TerrainMovePointPage(AuthoringGameState gameState) {
		initialize(gameState);
	}

	@Override
	public Region getObject() {
		return table.getObject();
	}

	private void initialize(AuthoringGameState gameState) {
		table = new VerticalTableInputView();
		rowToTerrain = new HashMap<>();
		ModifiableTerrain.getPredefinedTerrain().forEach(terrain -> {
			Image image;
			try {
				image = new Image(terrain.getImgPath());
			} catch (Exception e) {
				image = null;
			}
			NumericInputRow row = new NumericInputRow(image, terrain.getName(), terrain.getDescription());
			rowToTerrain.put(row, terrain);
			table.getChildren().add(row);
		});
		canNextWritable().setValue(true);
	}

	public Map<Terrain, Integer> getTerrainMovePoints() {
		return rowToTerrain.keySet().stream()
				.collect(Collectors.toMap(row -> rowToTerrain.get(row), NumericInputRow::getValue));
	}

}
