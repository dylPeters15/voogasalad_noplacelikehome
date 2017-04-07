package frontend.newunitpane;

import backend.unit.properties.ActiveAbility;
import backend.util.VoogaInstance;
import frontend.util.SelectableInputRow;
import javafx.scene.image.Image;

public class AbilityInputRow extends SelectableInputRow{
	
	public AbilityInputRow(ActiveAbility<? extends VoogaInstance<?>> ability){
		super(new Image(ability.getImgPath()), ability.getName());
	}
	
}
