package frontend.wizards.new_voogaobject_wizard.util;

import java.util.Collection;

import backend.unit.properties.ActiveAbility;
import backend.util.GameState;
import frontend.wizards.wizard_2_0.util.SelectableInputRow;
import frontend.wizards.wizard_2_0.util.VerticalTableInputView;
import javafx.scene.image.Image;

public class AbilitiesAdder extends VerticalTableInputView {

	public AbilitiesAdder(GameState gameState) {
		if (gameState != null) {
			gameState.getActiveAbilities().stream()
					.forEachOrdered(ability -> {
						Image image = null;
						try {
							image = new Image(ability.getImgPath());
						} catch (Exception e) {
//							e.printStackTrace();
						}
						String description = "";
						if (ability.getDescription() != null){
							description = ability.getDescription();
						}
						SelectableInputRow row = new SelectableInputRow(image,description);
						getChildren().add(row);
					});
		}
	}
	
	public Collection<ActiveAbility<?>> getSelectedAbilities(){
		return get
	}

}
