package frontend.wizards.wizard_2_0;

import javafx.beans.property.BooleanProperty;
import javafx.scene.Node;

public interface WizardSelectionStrategy<T> {

	BooleanProperty canPrevious();

	BooleanProperty canNext();

	BooleanProperty canFinish();

	Node getPage();

	void previous();

	void next();

	T finish();

}
