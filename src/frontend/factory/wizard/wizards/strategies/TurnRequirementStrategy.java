package frontend.factory.wizard.wizards.strategies;

import backend.util.Requirement;

public class TurnRequirementStrategy extends NameScriptBaseStrategy<Requirement> {

	public TurnRequirementStrategy() {
		setTitle(getPolyglot().get("TurnRequirementStrategyTitle"));
		setDescription(getPolyglot().get("TurnRequirementStrategyDescription"));
		setScriptPrompt("this is a prompt");
	}
	
	@Override
	public Requirement finish() {
		return new Requirement(getScriptEngine(), getName(), getDescription(), getImgPath());
	}

}
