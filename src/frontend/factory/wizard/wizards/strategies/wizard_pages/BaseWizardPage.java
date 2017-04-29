package frontend.factory.wizard.wizards.strategies.wizard_pages;

import frontend.util.BaseUIManager;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;

/**
 * BaseWizardPage is an abstract class whose sub-classes implement the full UIs
 * of a single page dialogue in a specific wizard. It is composed with
 * wizards.util package classes and also knows if it can Next.
 * 
 * @author Andreas
 *
 */
abstract class BaseWizardPage extends BaseUIManager<Region> implements WizardPage {

	private BooleanProperty canNext;
	private StringBinding description;

	public BaseWizardPage() {
		this("");
	}

	public BaseWizardPage(String descriptionKey) {
		this.description = getPolyglot().get(descriptionKey);
		canNext = new SimpleBooleanProperty(false);
	}

	@Override
	public ReadOnlyBooleanProperty canNext() {
		return BooleanProperty.readOnlyBooleanProperty(canNext);
	}

	@Override
	public StringBinding getDescription() {
		return description;
	}

	void setDescription(StringBinding description) {
		this.description = description;
	}

	protected BooleanProperty canNextWritable() {
		return canNext;
	}

}
