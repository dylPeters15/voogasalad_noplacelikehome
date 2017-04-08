package frontend.newunitpane;

import backend.unit.properties.ActiveAbility;
import backend.util.GameState;
import frontend.util.VerticalTableInputView;

public class AbilitiesAdder extends VerticalTableInputView {

	public AbilitiesAdder(GameState gameState) {
//		if (gameState != null) {
			ActiveAbility.getPredefinedActiveAbilities();//.stream()
					//.forEachOrdered(ability -> getChildren().add(new AbilityInputRow(ability)));
//		}
	}

}
