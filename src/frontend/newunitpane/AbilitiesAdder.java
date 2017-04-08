package frontend.newunitpane;

import java.util.ArrayList;
import java.util.Collection;

import backend.unit.properties.ActiveAbility;
import backend.util.GameState;
import backend.util.VoogaInstance;
import frontend.util.VerticalTableInputView;

public class AbilitiesAdder extends VerticalTableInputView{
	
	Collection<ActiveAbility<? extends VoogaInstance<?>>> activeAbilities;
	
	public AbilitiesAdder(GameState gameState){
		if (gameState != null){
			activeAbilities = gameState.getActiveAbilities();
		} else {
			activeAbilities = new ArrayList<>();
		}
		activeAbilities.stream().forEachOrdered(ability -> getChildren().add(new AbilityInputRow(ability)));
	}

}
