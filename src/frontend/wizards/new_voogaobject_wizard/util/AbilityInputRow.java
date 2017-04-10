package frontend.wizards.new_voogaobject_wizard.util;

import backend.unit.properties.ActiveAbility;
import backend.util.ImmutableVoogaObject;
import javafx.scene.image.Image;

public class AbilityInputRow extends SelectableInputRow{
	
	public AbilityInputRow(ActiveAbility<? extends ImmutableVoogaObject<?>> ability){
		super(new Image(ability.getImgPath()), ability.getName());
	}
	
}
