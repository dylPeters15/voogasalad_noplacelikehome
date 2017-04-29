package frontend.factory.wizard;

import frontend.factory.wizard.wizards.strategies.WizardStrategy;
import frontend.util.BaseUIManager;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import util.polyglot.PolyglotException;

/**
 * The Wizard class presents a basic API for allowing the user to create new
 * instances of objects while specifying initial information about the objects.
 * The Wizard extends BaseUIManager so that it can be styleable and can change
 * languages with the rest of the program. It is also updatable so that changes
 * in the rest of the program can be reflected immediately in the Wizard pages.
 * 
 * The Wizard uses the Strategy design pattern; it uses WizardStrategy objects
 * to populate the screens that the user interacts with. The Wizard itself
 * simply creates a next, previous, cancel, and finish dialog, and uses the
 * observable pattern to alert any listeners that are waiting for the object to
 * be instantiated.
 * 
 * @author Dylan Peters
 *
 * @param <T>
 *            The object type that the wizard will return after instantiation.
 */
public class Wizard<T> extends BaseUIManager<Region> {

	private WizardStrategy<T> selectionStrategy;
	private Dialog<Scene> dialog;
	private BorderPane borderPane;

	/**
	 * Creates a new Wizard object using the SelectionStrategy specified and
	 * displaying on the stage provided. Sets all values to default.
	 * 
	 * @param stage
	 *            the Stage on which to display the wizard
	 * @param selectionStrategy
	 *            WizardStrategy to use when populating the wizard's pages.
	 */
	Wizard(WizardStrategy<T> selectionStrategy) {
		initialize(selectionStrategy);
	}

	/**
	 * Returns the object that this wizard uses to display to the user.
	 */
	@Override
	public Region getNode() {
		return borderPane;
	}

	public void show() {
		dialog.show();
	}

	public void hide() {
		dialog.hide();
	}

	private void previous() {
		selectionStrategy.previous();
	}

	private void next() {
		selectionStrategy.next();
	}

	private void cancel() {
		dialog.close();
	}

	private void finish(Object object) {
		setChanged();
		notifyObservers(object);
		clearChanged();
		dialog.close();
	}

	private void initialize(WizardStrategy<T> selectionStrategy) {
		this.selectionStrategy = selectionStrategy;

		getPolyglot().setOnLanguageChange(event -> {
			try {
				selectionStrategy.getPolyglot().setLanguage(getPolyglot().getLanguage());
			} catch (PolyglotException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		dialog = new Dialog<>();
		DialogPane dialogPane = new DialogPane();
		borderPane = new BorderPane(selectionStrategy.getNode());
		WizardMenuBar<T> menuBar = new WizardMenuBar<>();
		menuBar.addObserver((observable,object) -> finish(menuBar.finish()));
		borderPane.setTop(menuBar.getNode());
		dialogPane.setContent(borderPane);
		

		dialog.setDialogPane(dialogPane);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.setResizable(true);
		dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.NEXT, ButtonType.PREVIOUS,
				ButtonType.FINISH);
		dialog.titleProperty().bind(selectionStrategy.getTitle());

		dialog.getDialogPane().lookupButton(ButtonType.NEXT).addEventFilter(ActionEvent.ACTION,
				event -> event.consume());
		dialog.getDialogPane().lookupButton(ButtonType.PREVIOUS).addEventFilter(ActionEvent.ACTION,
				event -> event.consume());
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).addEventFilter(ActionEvent.ACTION,
				event -> event.consume());
		dialog.getDialogPane().lookupButton(ButtonType.FINISH).addEventFilter(ActionEvent.ACTION, event -> finish(selectionStrategy.finish()));

		dialog.getDialogPane().lookupButton(ButtonType.NEXT).setOnMouseClicked(event -> next());
		dialog.getDialogPane().lookupButton(ButtonType.PREVIOUS).setOnMouseClicked(event -> previous());
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).setOnMouseClicked(event -> cancel());
		dialog.getDialogPane().lookupButton(ButtonType.FINISH).setOnMouseClicked(event -> finish(selectionStrategy.finish()));

		dialog.getDialogPane().lookupButton(ButtonType.NEXT).disableProperty().bind(selectionStrategy.canNext().not());
		dialog.getDialogPane().lookupButton(ButtonType.PREVIOUS).disableProperty()
				.bind(selectionStrategy.canPrevious().not());
		dialog.getDialogPane().lookupButton(ButtonType.FINISH).disableProperty()
				.bind(selectionStrategy.canFinish().not());
		((Button) (dialog.getDialogPane().lookupButton(ButtonType.NEXT))).defaultButtonProperty()
				.bind(selectionStrategy.canNext());
		((Button) (dialog.getDialogPane().lookupButton(ButtonType.FINISH))).defaultButtonProperty()
				.bind(selectionStrategy.canFinish());

		dialog.getDialogPane().lookupButton(ButtonType.NEXT).accessibleTextProperty().bind(getPolyglot().get("Next"));
		dialog.getDialogPane().lookupButton(ButtonType.PREVIOUS).accessibleTextProperty()
				.bind(getPolyglot().get("Previous"));
		dialog.getDialogPane().lookupButton(ButtonType.FINISH).accessibleTextProperty()
				.bind(getPolyglot().get("Finish"));
		dialog.getDialogPane().lookupButton(ButtonType.CANCEL).accessibleTextProperty()
				.bind(getPolyglot().get("Cancel"));

		getStyleSheet().setValue(getPossibleStyleSheetNamesAndFileNames().get("DefaultTheme"));
		show();
	}

}