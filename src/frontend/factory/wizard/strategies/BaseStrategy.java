package frontend.factory.wizard.strategies;

import java.util.ArrayList;
import java.util.Collection;

import controller.Controller;
import frontend.factory.wizard.strategies.wizard_pages.WizardPage;
import frontend.util.BaseUIManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * BaseStrategy implements default behavior for the SelectionStrategy interface.
 * It prevents duplicated code in the classes that extend it by implementing the
 * behavior that is common to all of them: having multiple pages that are
 * flipped through when the user clicks the next, previous, and finish button.
 * 
 * @author Dylan Peters
 *
 * @param <T>
 *            type of Object instantiated by BaseStrategy
 */
abstract class BaseStrategy<T> extends BaseUIManager<Region> implements WizardStrategy<T> {

	private BooleanProperty canPrevious, canNext, canFinish;
	private BorderPane borderPane;
	private ScrollPane scrollPane;
	private VBox descriptionBox;
	private Label descriptionLabel;
	private ObservableList<WizardPage> pages;

	/**
	 * Instantiates the BaseStrategy with an empty set of pages.
	 */
	BaseStrategy(Controller controller) {
		this(controller,new ArrayList<>());
	}

	/**
	 * Instantiates the BaseStrategy with the pages specified.
	 */
	BaseStrategy(Controller controller,Collection<WizardPage> pages) {
		super(controller);
		initialize(pages);
	}

	@Override
	public ReadOnlyBooleanProperty canPrevious() {
		return BooleanProperty.readOnlyBooleanProperty(canPrevious);
	}

	@Override
	public ReadOnlyBooleanProperty canNext() {
		return BooleanProperty.readOnlyBooleanProperty(canNext);
	}

	@Override
	public ReadOnlyBooleanProperty canFinish() {
		return BooleanProperty.readOnlyBooleanProperty(canFinish);
	}

	@Override
	final public Region getNode() {
		return borderPane;
	}

	@Override
	public void previous() {
		tryToGoToPageNum(getCurrentPageNum() - 1);
	}

	@Override
	public void next() {
		tryToGoToPageNum(getCurrentPageNum() + 1);
	}

	protected ObservableList<WizardPage> getPages() {
		return pages;
	}

	protected int getCurrentPageNum() {
		int i = 0;
		for (WizardPage page : pages) {
			if (scrollPane.getContent() == page.getNode()) {
				return i;
			}
			i++;
		}
		return -1;
	}

	protected WizardPage getCurrentPage() {
		return getCurrentPageNum() == -1 ? null : pages.get(getCurrentPageNum());
	}

	private void tryToGoToPageNum(int newPageNum) {
		if (canGoToPage(newPageNum)) {
			WizardPage page = pages.get(newPageNum);
			scrollPane.setContent(page.getNode());
			descriptionLabel.textProperty().unbind();
			descriptionLabel.textProperty().bind(page.getDescriptionLabelBinding());

			canPrevious.unbind();
			canPrevious.setValue(newPageNum != 0);

			canNext.unbind();
			if (newPageNum == pages.size() - 1) {
				canNext.setValue(false);
			} else {
				canNext.bind(page.canNext());
			}
			canFinish.unbind();
			if (newPageNum == pages.size() - 1) {
				canFinish.bind(page.canNext());
			} else {
				canFinish.setValue(false);
			}
		} else if (!pages.isEmpty()) {
			tryToGoToPageNum(0);
		}
	}

	private boolean canGoToPage(int newPageNum) {
		return newPageNum >= 0 && newPageNum < pages.size();
	}

	private void initialize(Collection<WizardPage> pages) {
		canPrevious = new SimpleBooleanProperty(false);
		canNext = new SimpleBooleanProperty(false);
		canFinish = new SimpleBooleanProperty(false);
		borderPane = new BorderPane();
		scrollPane = new ScrollPane();
		descriptionLabel = new Label();
		descriptionBox = new VBox();
		descriptionBox.getChildren().add(descriptionLabel);
		descriptionBox.setAlignment(Pos.CENTER);
		borderPane.setTop(descriptionBox);
		borderPane.setCenter(scrollPane);
		this.pages = FXCollections.observableArrayList();
		this.pages.addListener(new ListChangeListener<WizardPage>() {
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends WizardPage> c) {
				if (getCurrentPageNum() == -1) {
					tryToGoToPageNum(0);
				}
			}
		});

		this.pages.addAll(pages);
		getPolyglot().setOnLanguageChange(
				event -> this.pages.stream().forEach(page -> page.setLanguage(getPolyglot().getLanguage())));
	}
}
