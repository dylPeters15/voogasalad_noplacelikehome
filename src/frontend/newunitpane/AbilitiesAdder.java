package frontend.newunitpane;

import backend.util.GameState;
import frontend.util.VerticalTableInputView;

public class AbilitiesAdder extends VerticalTableInputView {

	public AbilitiesAdder(GameState gameState) {
		if (gameState != null) {
			gameState.getActiveAbilities().stream()
					.forEachOrdered(ability -> getChildren().add(new AbilityInputRow(ability)));
		}
	}

}
