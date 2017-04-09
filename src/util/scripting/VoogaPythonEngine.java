package util.scripting;

import java.util.Map;

/**
 * @author Created by th174 on 4/7/2017.
 */
public class VoogaPythonEngine extends VoogaJSR_223Engine {
	@Override
	protected String getEngineName() {
		return "jython";
	}

	@Override
	public Object eval(Map<String, Object> bindings) throws VoogaScriptException {
		super.eval(bindings);
		System.out.println(getEngine().get(RESOURCES.getString("ReturnVarName")));
		return getEngine().get(RESOURCES.getString("ReturnVarName"));
	}
}
