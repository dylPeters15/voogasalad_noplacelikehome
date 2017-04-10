package frontend.wizards.wizard_2_0.wizard_pages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import backend.unit.properties.ActiveAbility;
import backend.util.GameState;
import frontend.wizards.new_voogaobject_wizard.util.VerticalTableInputView;
import frontend.wizards.wizard_2_0.util.SelectableInputRow;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

public class AbilitiesAdderPage extends WizardPage {

	private VerticalTableInputView table;
	private Map<SelectableInputRow, ActiveAbility<?>> rowToAbility;

	public AbilitiesAdderPage(GameState gameState) {
		initialize(gameState);
	}

	@Override
	public Region getObject() {
		return table.getObject();
	}

	private void initialize(GameState gameState) {
		table = new VerticalTableInputView();
		rowToAbility = new HashMap<>();
		gameState.getActiveAbilities().stream().forEachOrdered(ability -> {
			Image image;
			try {
				image = new Image(ability.getImgPath());
			} catch (Exception e){
				image = null;
			}
			SelectableInputRow row = new SelectableInputRow(image, ability.getName(),
					ability.getDescription());
			rowToAbility.put(row, ability);
			table.getChildren().add(row);
		});
		canNextWritable().setValue(true);
	}

	public Collection<ActiveAbility<?>> getSelectedAbilities() {
		return rowToAbility.keySet().stream().filter(row -> row.getSelected()).map(row -> rowToAbility.get(row)).collect(Collectors.toList());
	}

}
