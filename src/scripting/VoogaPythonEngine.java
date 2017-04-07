package scripting;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Map;

/**
 * @author Created by th174 on 4/7/2017.
 */
class VoogaPythonEngine extends VoogaScriptEngine {
    private static final String FUNCTION_FORMAT = "" +
            "def %s(%s):\n" +
            "%s\n";
    private static final String FUNCTION_NAME = "voogaPythonFunction";
    private static final ScriptEngine JYTHON = new ScriptEngineManager().getEngineByName("python");

    VoogaPythonEngine(String scriptContent) {
        super(scriptContent);
    }

    @Override
    public Object eval(Map<String, Object> bindings) throws VoogaScriptException {
        try {
            JYTHON.eval(formatFunction(FUNCTION_FORMAT, FUNCTION_NAME, bindings.keySet()));
            return ((Invocable) JYTHON).invokeFunction(FUNCTION_NAME, bindings.values().toArray());
        } catch (ScriptException | NoSuchMethodException e) {
            throw new VoogaScriptException(e);
        }
    }
}
