import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import frontend.startup.StartupScreen;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		new CoordinateTuple(1, 2, 3);
		@SuppressWarnings("unused")
		GridPattern pattern = GridPattern.HEXAGONAL_ADJACENT;
		StartupScreen starter = new StartupScreen(primaryStage, 1200.0, 800.0);
		primaryStage.setScene(starter.getPrimaryScene());
		primaryStage.setResizable(true);
		primaryStage.show();

	}
}