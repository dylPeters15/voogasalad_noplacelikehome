package frontend.wizards.wizard_2_0.test;

import java.util.Observable;
import java.util.Observer;

import backend.util.GameState;
import frontend.wizards.wizard_2_0.Wizard;
import frontend.wizards.wizard_2_0.selection_strategies.NewStringSelectionStrategy;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewStringWizardTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Wizard<String> newStringWizard = new Wizard<String>(primaryStage, new NewStringSelectionStrategy(new GameState()));
		newStringWizard.addObserver(new Observer() {
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
