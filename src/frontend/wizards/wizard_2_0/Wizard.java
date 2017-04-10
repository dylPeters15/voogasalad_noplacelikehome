package frontend.wizards.wizard_2_0;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import frontend.util.BaseUIManager;
import frontend.wizards.new_voogaobject_wizard.util.ButtonBar;
import frontend.wizards.wizard_2_0.selection_strategies.WizardSelectionStrategy;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class Wizard<T> extends BaseUIManager<Region> {
	private static final Collection<String> buttonNames = new ArrayList<>(
			Arrays.asList("Previous", "Next", "Cancel", "Finish"));
	private WizardSelectionStrategy<T> selectionStrategy;
	private BorderPane borderPane;
	private Stage stage;

	public Wizard(WizardSelectionStrategy<T> selectionStrategy) {
		this(new Stage(), selectionStrategy);
	}

	public Wizard(Stage stage, WizardSelectionStrategy<T> selectionStrategy) {
		initialize(stage, selectionStrategy);
	}

	protected void previous() {
		selectionStrategy.previous();
	}

	protected void next() {
		selectionStrategy.next();
	}

	protected void cancel() {
		stage.close();
	}

	protected void finish() {
		setChanged();
		notifyObservers(selectionStrategy.finish());
		clearChanged();
		stage.close();
	}

	private void initialize(Stage stage, WizardSelectionStrategy<T> selectionStrategy) {
		this.stage = stage;
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

		borderPane.setCenter(selectionStrategy.getObject());
		borderPane.setBottom(buttonBar.getObject());

		stage.setScene(new Scene(borderPane));
		stage.show();
	}

	@Override
	public Region getObject() {
		return null;
	}

}
