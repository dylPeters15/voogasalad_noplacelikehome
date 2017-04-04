/**
 * @author Created by th174 on 4/4/2017.
 */

import frontend.UI;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.Locale;
import java.util.ResourceBundle;

public class VoogaClientMain extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ResourceBundle bundle = ResourceBundle.getBundle("resources/Selections", Locale.getDefault());
        primaryStage.setTitle(bundle.getString("Title"));
        UI userInterface = new UI();
        primaryStage.setScene(userInterface.getPrimaryScene());
        primaryStage.setResizable(true);
        primaryStage.show();
    }
}
