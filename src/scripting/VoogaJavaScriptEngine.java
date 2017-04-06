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
class VoogaJavaScriptEngine extends VoogaScriptEngine {
    private static final String FUNCTION_FORMAT = "" +
            "var console = { \n" +
            "    log: print,\n" +
            "    warn: print,\n" +
            "    error: print\n" +
            "};\n\n" +
            "var %s = function(%s) {\n" +
            "%s\n" +
            "};";
    private static final String FUNCTION_NAME = "voogaJavascriptFunction";
    private static final ScriptEngine NASHORN = new ScriptEngineManager().getEngineByName("nashorn");

    VoogaJavaScriptEngine() {
        super();
    }

    @Override
    public Object eval(Map<String, Object> bindings) {
        try {
            NASHORN.eval(getFormattedScript(bindings.keySet()));
            return ((Invocable) NASHORN).invokeFunction(FUNCTION_NAME, bindings.values().toArray());
        } catch (ScriptException | NoSuchMethodException e) {
            throw new VoogaScriptException(e);
        }
    }

    public String getFormattedScript(Collection<String> paramNames) {
        return String.format(FUNCTION_FORMAT, FUNCTION_NAME, paramNames.toString().substring(1, paramNames.toString().length() - 1), getScript().replaceAll("(?m)^", "\t"));
    }
}
