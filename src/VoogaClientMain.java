import javafx.stage.Stage;
import scripting.VoogaScriptEngine;
import scripting.VoogaScriptEngineManager;

import java.util.HashMap;

/**
 * @author Created by th174 on 4/4/2017.
 */
public class VoogaClientMain {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        VoogaScriptEngine engine = VoogaScriptEngineManager.read("python", "" +
                "import sys\n" +
                "list = []\n" +
                "list.append(a)\n" +
                "list.append(b)\n" +
                "ret = list\n" +
                "print ret");
        for (int i = 0; i < 1; i++) {
            Object result = engine.eval(new HashMap<String, Object>() {{
                put("a", "hello");
                put("b", " world");
            }});
        }
        System.out.println((System.currentTimeMillis() - start) / 1000.0);
//        launch(args);
    }

//    @Override
    public void start(Stage primaryStage) {
//        primaryStage.setTitle(ResourceBundle.getBundle("resources/Selections", Locale.getDefault()).getString("Title"));
//        UI userInterface = new UI();
//        primaryStage.setScene(userInterface.getPrimaryScene());
//        primaryStage.setResizable(true);
//        primaryStage.show();
    }
}
