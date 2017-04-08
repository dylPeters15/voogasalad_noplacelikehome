import javafx.application.Application;
import javafx.stage.Stage;
import scripting.VoogaScriptEngine;
import scripting.VoogaScriptEngineManager;

import javax.script.ScriptEngineManager;
import java.util.HashMap;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaClientMain extends Application {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        System.out.println(new ScriptEngineManager().getEngineFactories());
        VoogaScriptEngine engine = VoogaScriptEngineManager.read("Java", "System.out.println(a+b);\nreturn b+a;");
        for (int i = 0; i < 100; i++) {
            Object result = engine.eval(new HashMap<String, Object>() {{
                put("a", "hello");
                put("b", " world");
            }});
            System.out.println(result);
        }
        System.out.println((System.currentTimeMillis() - start) / 1000.0);
//        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
//        primaryStage.setTitle(ResourceBundle.getBundle("resources/Selections", Locale.getDefault()).getString("Title"));
//        UI userInterface = new UI();
//        primaryStage.setScene(userInterface.getPrimaryScene());
//        primaryStage.setResizable(true);
//        primaryStage.show();
    }
}
