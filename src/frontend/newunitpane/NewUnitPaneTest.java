package frontend.newunitpane;

import backend.util.GameState;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewUnitPaneTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage.setScene(new Scene(new NewUnitPane(null).getObject()));
//		primaryStage.show();
//		
		new ModalNewUnitPane(new GameState());
	}

	public static void main(String[] args){
		launch(args);
	}
	
}
