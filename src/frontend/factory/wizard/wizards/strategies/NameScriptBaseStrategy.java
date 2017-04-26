package frontend.factory.wizard.wizards.strategies;

import frontend.factory.wizard.wizards.strategies.wizard_pages.ImageNameDescriptionPage;
import frontend.factory.wizard.wizards.strategies.wizard_pages.ScriptingPage;
import javafx.beans.binding.StringBinding;
import util.scripting.VoogaScriptEngine;

public abstract class NameScriptBaseStrategy<T> extends BaseStrategy<T> {

	private ImageNameDescriptionPage namePage;
	private ScriptingPage scriptingPage;

	public NameScriptBaseStrategy() {
		initialize();
	}

	protected void setTitle(StringBinding title) {
		namePage.setTitle(title);
	}

	protected void setDescription(StringBinding description) {
		namePage.setDescription(description);
	}

	protected String getName() {
		return namePage.getName();
	}

	protected String getDescription() {
		return namePage.getDescription();
	}

	protected String getImgPath() {
		return namePage.getImagePath();
	}
	
	protected void setScriptPrompt(String prompt){
		scriptingPage.setPrompt(prompt);
	}

	protected VoogaScriptEngine getScriptEngine() {
		return scriptingPage.getScriptEngine().isPresent() ? scriptingPage.getScriptEngine().get() : null;
	}

	private void initialize() {
		namePage = new ImageNameDescriptionPage();
		scriptingPage = new ScriptingPage();
		getPages().addAll(namePage, scriptingPage);
	}

}
