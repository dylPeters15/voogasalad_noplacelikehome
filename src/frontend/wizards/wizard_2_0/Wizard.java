package frontend.wizards.wizard_2_0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import frontend.util.BaseUIManager;
import frontend.wizards.wizard_2_0.util.ButtonBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;

public class Wizard<T> extends BaseUIManager<Region> {
	private static final Collection<String> buttonNames = new ArrayList<>(
			Arrays.asList("Previous", "Next", "Cancel", "Finish"));
	private WizardSelectionStrategy<T> selectionStrategy;
	private BorderPane borderPane;

	public Wizard(WizardSelectionStrategy<T> selectionStrategy) {
		initialize(selectionStrategy);
	}

	@Override
	public Region getObject() {
		return borderPane;
	}

	protected void previous() {
		selectionStrategy.previous();
	}

	protected void next() {
		selectionStrategy.next();
	}

	protected void cancel() {

	}

	protected void finish() {
		selectionStrategy.finish();
	}

	private void initialize(WizardSelectionStrategy<T> selectionStrategy) {
		this.selectionStrategy = selectionStrategy;
		borderPane = new BorderPane();

		ButtonBar buttonBar = new ButtonBar(buttonNames);

		buttonBar.getButton("Previous").disableProperty().bind(selectionStrategy.canPrevious().not());
		buttonBar.getButton("Next").disableProperty().bind(selectionStrategy.canNext().not());
		buttonBar.getButton("Finish").disableProperty().bind(selectionStrategy.canFinish().not());

		buttonBar.getButton("Previous").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				previous();
			}
		});
		buttonBar.getButton("Next").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				next();
			}
		});
		buttonBar.getButton("Cancel").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				cancel();
			}
		});
		buttonBar.getButton("Finish").setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				finish();
			}
		});

		borderPane.setCenter(selectionStrategy.getPage());
		borderPane.setBottom(buttonBar.getObject());
	}

}
