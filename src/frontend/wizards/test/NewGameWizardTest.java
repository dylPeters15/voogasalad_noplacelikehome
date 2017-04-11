package frontend.wizards.test;

import java.util.Observable;
import java.util.Observer;

import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import frontend.wizards.NewGameWizard;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewGameWizardTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		new CoordinateTuple(1, 2, 3);
		@SuppressWarnings("unused")
		GridPattern pattern = GridPattern.HEXAGONAL_ADJACENT;
		NewGameWizard newGameWizard = new NewGameWizard(primaryStage);
		newGameWizard.addObserver((observable, object) -> {
			System.out.println("Observable: " + observable.toString());
			System.out.println("Object: " + object.toString());
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
