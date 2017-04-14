package frontend.wizards.util;

import java.util.ArrayList;
import java.util.Collection;

import frontend.util.BaseUIManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * TableInputView extends the BaseUIManager and is a UI structure used in the creation of
 * many wizard pages used to create objects that creates a table that other UI utils can go into in a
 * table format
 * @author Andreas
 *
 */
public abstract class TableInputView extends BaseUIManager<Region> {

	private ObservableList<BaseUIManager<? extends Parent>> children;
	private ScrollPane scrollPane;

	public TableInputView() {
		this(new ArrayList<BaseUIManager<? extends Parent>>());
	}

	public TableInputView(Collection<BaseUIManager<? extends Parent>> childrenToAdd) {
		children = FXCollections.observableArrayList();
		children.addListener(new ListChangeListener<BaseUIManager<? extends Parent>>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends BaseUIManager<? extends Parent>> change) {
				if (change.next()) {
					if (change.wasAdded()) {
//						change.getAddedSubList().stream()
//								.forEachOrdered(child -> child.getRequests().passTo(getRequests()));
					}
				}
			}
		});
		children.addAll(childrenToAdd);
		scrollPane = new ScrollPane();
	}

	public ObservableList<BaseUIManager<? extends Parent>> getChildren() {
		return children;
	}

	@Override
	public Region getObject() {
		return scrollPane;
	}

	protected void setContent(Node value) {
		scrollPane.setContent(value);
	}

	protected Node getContent() {
		return scrollPane.getContent();
	}

}
