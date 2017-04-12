package frontend.menubar;

import java.util.ResourceBundle;

import backend.util.AuthoringGameState;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class VoogaMenuBarTest extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane bp = new BorderPane();
		VoogaMenuBar voogaMenuBar = new VoogaMenuBar(new AuthoringGameState(""));
		bp.setTop(voogaMenuBar.getObject());
		ButtonManager buttonManager = new ButtonManager();
//		buttonManager.getLanguage().bind(voogaMenuBar.getLanguage());
//		buttonManager.getStyleSheet().bind(voogaMenuBar.getStyleSheet());
		voogaMenuBar.getLanguage().addListener((observable, oldValue, newValue) -> buttonManager.setLanguage(newValue));
		voogaMenuBar.getStyleSheet().addListener((observable, oldValue, newValue) -> buttonManager.setStyleSheet(newValue));
		bp.setCenter(buttonManager.getObject());
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
