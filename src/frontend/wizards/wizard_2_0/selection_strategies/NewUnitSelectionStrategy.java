package frontend.wizards.wizard_2_0.selection_strategies;

import java.util.Arrays;

import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.GameState;
import frontend.wizards.wizard_2_0.util.AbilitiesAdder;
import frontend.wizards.wizard_2_0.util.ImageNamePairView;

public class NewUnitSelectionStrategy extends BaseSelectionStrategy<Unit> {

	public NewUnitSelectionStrategy(GameState gameState) {
		initialize(gameState);
	}

	@Override
	public Unit finish() {
		return new ModifiableUnit("Name");
		// can change ^ to use a constructor with more options
	}

	private void initialize(GameState gameState) {
		getPages().addAll(Arrays.asList(new ImageNamePairView(), new AbilitiesAdder(gameState)));
		canNextWritable().setValue(true);
	}

}
