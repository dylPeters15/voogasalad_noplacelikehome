package frontend.wizards.wizard_2_0.test;

import java.util.Observable;
import java.util.Observer;

import backend.util.GameState;
import frontend.wizards.wizard_2_0.NewUnitWizard;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewUnitWizardTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		NewUnitWizard newUnitWizard = new NewUnitWizard(primaryStage, new GameState());
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
