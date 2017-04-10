package frontend.menubar;

import java.util.ResourceBundle;

import frontend.wizards.new_voogaobject_wizard.util.ButtonManager;
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
		VoogaMenuBar voogaMenuBar = new VoogaMenuBar();
		bp.setTop(voogaMenuBar.getObject());
		ButtonManager buttonManager = new ButtonManager();
//		buttonManager.getLanguage().bind(voogaMenuBar.getLanguage());
//		buttonManager.getStyleSheet().bind(voogaMenuBar.getStyleSheet());
		voogaMenuBar.getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldValue,
					ResourceBundle newValue) {
				buttonManager.getLanguage().setValue(newValue);
			}
		});
		voogaMenuBar.getStyleSheet().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				buttonManager.getStyleSheet().setValue(newValue);
			}
		});
		bp.setCenter(buttonManager.getObject());
		primaryStage.setScene(new Scene(bp));
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

}
