package frontend.newunitpane;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class NewUnitPaneTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setScene(new Scene(new NewUnitPane(null).getObject()));
		primaryStage.show();
		
	}

	public static void main(String[] args){
		launch(args);
	}
	
}
