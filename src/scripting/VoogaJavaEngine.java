package scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/6/2017.
 */
public class VoogaJavaEngine extends VoogaScriptEngine{
    static {
        VoogaScriptEngine.addEngine(new VoogaJavaEngine(), "java");
    }

    @Override
    protected Object eval(Map<String, Object> bindings) throws VoogaScriptException {
        //TODO
        return null;
    }
}
