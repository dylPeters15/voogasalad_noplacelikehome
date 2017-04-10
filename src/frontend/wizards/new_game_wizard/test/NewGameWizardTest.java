package frontend.wizards.new_game_wizard.test;

import frontend.wizards.new_game_wizard.NewGameWizard;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewGameWizardTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(new NewGameWizard().getObject()));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
