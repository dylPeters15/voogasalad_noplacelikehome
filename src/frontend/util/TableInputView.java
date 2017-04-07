package frontend.util;

import java.util.ArrayList;
import java.util.Collection;

import frontend.BaseUIManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

public abstract class TableInputView extends BaseUIManager<Region> {

	private ObservableList<BaseUIManager<Parent>> children;
	private ScrollPane scrollPane;

	public TableInputView() {
		this(new ArrayList<BaseUIManager<Parent>>());
	}

	public TableInputView(Collection<BaseUIManager<Parent>> childrenToAdd) {
		children = FXCollections.observableArrayList();
		children.addListener(new ListChangeListener<BaseUIManager<Parent>>() {
			@Override
			public void onChanged(
					javafx.collections.ListChangeListener.Change<? extends BaseUIManager<Parent>> change) {
				if (change.wasAdded()) {
					change.getAddedSubList().stream()
							.forEachOrdered(child -> child.getRequests().passTo(getRequests()));
				}
			}
		});
		children.addAll(childrenToAdd);
		scrollPane = new ScrollPane();
	}

	public ObservableList<BaseUIManager<Parent>> getChildren() {
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
