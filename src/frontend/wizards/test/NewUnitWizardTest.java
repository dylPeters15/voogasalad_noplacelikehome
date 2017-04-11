package frontend.wizards.test;

import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import backend.util.AuthoringGameState;
import frontend.wizards.NewUnitWizard;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewUnitWizardTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		new CoordinateTuple(1, 2, 3);
		@SuppressWarnings("unused")
		GridPattern pattern = GridPattern.HEXAGONAL_ADJACENT;
		NewUnitWizard newUnitWizard = new NewUnitWizard(primaryStage, new AuthoringGameState("Test Game State"));
		newUnitWizard.addObserver((observable, object) -> {
			System.out.println("Observable: " + observable.toString());
			System.out.println("Object: " + object.toString());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
