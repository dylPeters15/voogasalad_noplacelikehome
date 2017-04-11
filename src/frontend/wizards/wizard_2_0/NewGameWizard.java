package frontend.wizards.wizard_2_0;

import backend.util.GameState;
import frontend.wizards.wizard_2_0.selection_strategies.NewGameSelectionStrategy;
import javafx.stage.Stage;

public class NewGameWizard extends Wizard<GameState> {

	public NewGameWizard() {
		this(new Stage());
	}

	public NewGameWizard(Stage stage) {
		super(stage, new NewGameSelectionStrategy());
	}

}
