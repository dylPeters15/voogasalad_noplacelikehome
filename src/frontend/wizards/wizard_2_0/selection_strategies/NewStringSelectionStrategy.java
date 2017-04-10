package frontend.wizards.wizard_2_0.selection_strategies;

import java.util.Arrays;

import backend.util.GameState;
import frontend.wizards.wizard_2_0.util.AbilitiesAdder;
import frontend.wizards.wizard_2_0.util.ImageNamePairView;
import frontend.wizards.wizard_2_0.util.TerrainMovePointView;

public class NewStringSelectionStrategy extends BaseSelectionStrategy<String> {

	private ImageNamePairView imageNamePairView;
	private AbilitiesAdder abilitiesAdder;
	private TerrainMovePointView terrainMovePointView;

	public NewStringSelectionStrategy(GameState gameState) {
		initialize(gameState);
	}

	@Override
	public void next() {
		super.next();
		if (getCurrentPage() == terrainMovePointView) {
			canFinishWritable().setValue(true);
		}
	}

	@Override
	public String finish() {
		return "StringTest!";
		// can change ^ to use a constructor with more options, using
		// information retreived from the user by the pages. For example:
		// "return new ModifiableUnit(imageNamePairView.getName(),
		// imageNamePairView.getImage(), abilitiesAdder.getAbilities());"
	}

	private void initialize(GameState gameState) {
		imageNamePairView = new ImageNamePairView();
		abilitiesAdder = new AbilitiesAdder(gameState);
		terrainMovePointView = new TerrainMovePointView(gameState);
		getPages().addAll(Arrays.asList(imageNamePairView, abilitiesAdder, terrainMovePointView));
		canNextWritable().setValue(true);
	}

}
