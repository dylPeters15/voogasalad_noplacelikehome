package frontend.wizards.new_voogaobject_wizard.test;

import backend.util.AuthoringGameState;
import frontend.wizards.new_voogaobject_wizard.ModalNewUnitPane;
import javafx.application.Application;
import javafx.stage.Stage;

public class NewUnitPaneTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
//		primaryStage.setScene(new Scene(new NewUnitPane(null).getObject()));
//		primaryStage.show();
//		
		new ModalNewUnitPane(new AuthoringGameState("AuthoringGameStateTest"));
	}

	public static void main(String[] args){
		launch(args);
	}
	
}
