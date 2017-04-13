package frontend.wizards.selection_strategies;

import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.scene.Node;

public interface WizardSelectionStrategy<T> {

	ReadOnlyBooleanProperty canPrevious();

	ReadOnlyBooleanProperty canNext();

	ReadOnlyBooleanProperty canFinish();
	
	Node getObject();

	void previous();

	void next();

	T finish();

}
