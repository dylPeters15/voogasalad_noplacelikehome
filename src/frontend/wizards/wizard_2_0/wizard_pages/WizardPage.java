package frontend.wizards.wizard_2_0.wizard_pages;

import frontend.util.BaseUIManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.layout.Region;

public abstract class WizardPage extends BaseUIManager<Region> {

	private BooleanProperty canNext;

	public WizardPage() {
		canNext = new SimpleBooleanProperty(false);
	}

	public ReadOnlyBooleanProperty canNext() {
		return BooleanProperty.readOnlyBooleanProperty(canNext);
	}

	protected BooleanProperty canNextWritable() {
		return canNext;
	}

}
