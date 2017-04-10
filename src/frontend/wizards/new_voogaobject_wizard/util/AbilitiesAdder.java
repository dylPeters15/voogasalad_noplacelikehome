package frontend.wizards.new_voogaobject_wizard.util;

import backend.unit.properties.ActiveAbility;
import backend.util.GameState;
import javafx.scene.image.Image;

public class AbilitiesAdder extends VerticalTableInputView {

	public AbilitiesAdder(GameState gameState) {
//		if (gameState != null) {
			ActiveAbility.getPredefinedActiveAbilities().stream()
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
//		}
	}

}
