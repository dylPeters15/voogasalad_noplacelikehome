package frontend.wizards.wizard_2_0.test;

import java.util.Observable;
import java.util.Observer;

import backend.unit.Unit;
import backend.util.GameState;
import frontend.wizards.wizard_2_0.Wizard;
import frontend.wizards.wizard_2_0.selection_strategies.NewUnitSelectionStrategy;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewUnitWizardTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Wizard<Unit> newUnitWizard = new Wizard<Unit>(primaryStage, new NewUnitSelectionStrategy(new GameState()));
		newUnitWizard.addObserver(new Observer() {
			@Override
			public void update(Observable observable, Object object) {
				System.out.println("Observable: " + observable.toString());
				System.out.println("Object: " + object.toString());
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

}
