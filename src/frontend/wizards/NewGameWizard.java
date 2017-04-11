package frontend.wizards;

import backend.util.AuthoringGameState;
import frontend.wizards.selection_strategies.NewGameSelectionStrategy;
import javafx.stage.Stage;

public class NewGameWizard extends Wizard<AuthoringGameState> {

	public NewGameWizard() {
		this(new Stage());
	}

	public NewGameWizard(Stage stage) {
		super(stage, new NewGameSelectionStrategy());
	}

}
