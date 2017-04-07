package scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
//TODO Implement Groovy Engine
class VoogaGroovyEngine extends VoogaScriptEngine {
    protected VoogaGroovyEngine(String script) {
        super(script);
    }

    @Override
    protected Object eval(Map<String, Object> bindings) {
        //TODO
        return null;
    }
}
