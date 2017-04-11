package frontend.wizards.wizard_2_0;

import backend.unit.Unit;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_2_0.selection_strategies.NewUnitSelectionStrategy;
import javafx.stage.Stage;

public class NewUnitWizard extends Wizard<Unit> {

	public NewUnitWizard(AuthoringGameState gameState) {
		this(new Stage(), gameState);
	}

	public NewUnitWizard(Stage stage, AuthoringGameState gameState) {
		super(stage, new NewUnitSelectionStrategy(gameState));
	}

}
