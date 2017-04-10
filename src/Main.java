import frontend.startup.StartupScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private StartupScreen userInterface;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		this.userInterface = new StartupScreen(1200.0, 800.0);
		primaryStage.setScene(userInterface.getPrimaryScene());
		primaryStage.setResizable(true);
		primaryStage.show();

	}
}