package frontend.wizards.wizard_2_0.test;

import java.util.Observable;
import java.util.Observer;

import frontend.wizards.wizard_2_0.NewGameWizard;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewGameWizardTest extends Application{

	@Override
	public void start(Stage primaryStage) throws Exception {
		NewGameWizard newGameWizard = new NewGameWizard(primaryStage);
		newGameWizard.addObserver(new Observer() {
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
