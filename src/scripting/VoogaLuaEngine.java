package scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/7/2017.
 */
public class VoogaLuaEngine extends VoogaScriptEngine {
    protected VoogaLuaEngine(String scriptContent) {
        super(scriptContent);
    }

    @Override
    protected Object eval(Map<String, Object> bindings) throws VoogaScriptException {
        return null;
    }
}
