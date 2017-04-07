package scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
class VoogaJavaEngine extends VoogaScriptEngine {

    protected VoogaJavaEngine(String script) {
        super(script);
    }

    @Override
    protected Object eval(Map<String, Object> bindings) throws VoogaScriptException {
        //TODO
        return null;
    }
}
