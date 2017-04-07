package scripting;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
class VoogaJavaScriptEngine extends VoogaScriptEngine {
    private static final String FUNCTION_FORMAT = "" +
            "var console = { \n" +
            "\tlog: print,\n" +
            "\twarn: print,\n" +
            "\terror: print\n" +
            "};\n\n" +
            "var %s = function(%s) {\n" +
            "%s\n" +
            "};";
    private static final String FUNCTION_NAME = "voogaJavascriptFunction";
    private static final ScriptEngine NASHORN = new ScriptEngineManager().getEngineByName("nashorn");

    VoogaJavaScriptEngine(String scriptContent) {
        super(scriptContent);
    }

    @Override
    public Object eval(Map<String, Object> bindings) throws VoogaScriptException {
        try {
            NASHORN.eval(formatFunction(FUNCTION_FORMAT, FUNCTION_NAME, bindings.keySet()));
            return ((Invocable) NASHORN).invokeFunction(FUNCTION_NAME, bindings.values().toArray());
        } catch (ScriptException | NoSuchMethodException e) {
            throw new VoogaScriptException(e);
        }
    }
}
