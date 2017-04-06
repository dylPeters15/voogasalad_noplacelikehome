package scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
//TODO Implement Groovy Engine
class VoogaGroovyEngine extends VoogaScriptEngine {

    protected VoogaGroovyEngine() {
        super("groovy", "groovyshell");
    }

    @Override
    protected Object eval(Map<String, Object> bindings) {
        //TODO
        return null;
    }
}
