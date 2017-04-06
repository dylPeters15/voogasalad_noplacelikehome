package scripting;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Collection;
import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
public class VoogaJavaScriptEngine extends VoogaScriptEngine {
    private static final String FUNCTION_FORMAT = "function %s(%s) {\n%s\n}";
    private static final String FUNCTION_NAME = "voogaJavascriptFunction";
    private static final ScriptEngine NASHORN = new ScriptEngineManager().getEngineByName("nashorn");

    VoogaJavaScriptEngine() {
        super();
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        try {
            NASHORN.eval(formatScript(getScript(), bindings.keySet()));
            return ((Invocable) NASHORN).invokeFunction(FUNCTION_NAME, bindings.values().toArray());
        } catch (ScriptException | NoSuchMethodException e) {
            throw new VoogaScriptException(e);
        }
    }

    public static String formatScript(String script, Collection<String> paramNames) {
        return String.format(FUNCTION_FORMAT, FUNCTION_NAME, paramNames.toString().substring(1, paramNames.toString().length() - 1), script);
    }
}
