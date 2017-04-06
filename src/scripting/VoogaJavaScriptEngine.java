package scripting;

import javax.script.*;
import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
public class VoogaJavaScriptEngine extends VoogaScriptEngine {
    private final ScriptEngine nashornEngine;

    VoogaJavaScriptEngine() {
        super();
        nashornEngine = new ScriptEngineManager().getEngineByName("nashorn");
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        try {
            return nashornEngine.eval(getScript(), new SimpleBindings(bindings));
        } catch (ScriptException e) {
            throw new VoogaScriptException(e);
        }
    }
}
