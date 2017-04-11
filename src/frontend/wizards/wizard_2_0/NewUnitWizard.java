package frontend.wizards.wizard_2_0;

import backend.unit.Unit;
import backend.util.AuthorGameState;
import frontend.wizards.wizard_2_0.selection_strategies.NewUnitSelectionStrategy;
import javafx.stage.Stage;

public class NewUnitWizard extends Wizard<Unit> {

	public NewUnitWizard(AuthorGameState gameState) {
		this(new Stage(), gameState);
	}

	public NewUnitWizard(Stage stage, AuthorGameState gameState) {
		super(stage, new NewUnitSelectionStrategy(gameState));
	}

}
