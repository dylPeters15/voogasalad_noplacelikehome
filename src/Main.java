import frontend.UI;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	private UI userInterface;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		this.userInterface = new UI(1200.0, 800.0);
		primaryStage.setScene(userInterface.getPrimaryScene());
		primaryStage.setResizable(true);
		primaryStage.show();

	}
}