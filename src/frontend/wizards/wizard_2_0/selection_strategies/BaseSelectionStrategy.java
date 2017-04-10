package frontend.wizards.wizard_2_0.selection_strategies;

import java.util.ArrayList;
import java.util.Collection;

import frontend.util.BaseUIManager;
import frontend.wizards.wizard_2_0.wizard_pages.WizardPage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public abstract class BaseSelectionStrategy<T> extends BaseUIManager<Region> implements WizardSelectionStrategy<T> {

	private BooleanProperty canPrevious, canNext, canFinish;
	private StackPane stackPane;
	private ObservableList<WizardPage> pages;

	public BaseSelectionStrategy() {
		this(new ArrayList<>());
	}

	public BaseSelectionStrategy(Collection<WizardPage> pages) {
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
	public Region getObject() {
		// if (getCurrentPageNum() == -1) {
		// tryToGoToPageNum(0);
		// }
		return stackPane;
	}

	@Override
	public void previous() {
		tryToGoToPageNum(getCurrentPageNum() - 1);
	}

	@Override
	public void next() {
		tryToGoToPageNum(getCurrentPageNum() + 1);
	}

	// protected BooleanProperty canPreviousWritable() {
	// return canPrevious;
	// }
	//
	// protected BooleanProperty canNextWritable() {
	// return canNext;
	// }
	//
	// protected BooleanProperty canFinishWritable() {
	// return canFinish;
	// }

	protected ObservableList<WizardPage> getPages() {
		return pages;
	}

	protected int getCurrentPageNum() {
		int i = 0;
		for (WizardPage page : pages) {
			if (stackPane.getChildren().contains(page.getObject())) {
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
			removeAllPagesFromView();
			stackPane.getChildren().add(pages.get(newPageNum).getObject());

			canPrevious.unbind();
			canPrevious.setValue(newPageNum != 0);

			canNext.unbind();
			if (newPageNum == pages.size() - 1) {
				canNext.setValue(false);
			} else {
				canNext.bind(pages.get(newPageNum).canNext());
			}
			canFinish.unbind();
			if (newPageNum == pages.size() - 1) {
				canFinish.bind(pages.get(newPageNum).canNext());
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

	private void removeAllPagesFromView() {
		pages.stream().forEach(page -> {
			if (stackPane.getChildren().contains(page.getObject())) {
				stackPane.getChildren().remove(page.getObject());
			}
		});
	}

	private void initialize(Collection<WizardPage> pages) {
		canPrevious = new SimpleBooleanProperty(false);
		canNext = new SimpleBooleanProperty(false);
		canFinish = new SimpleBooleanProperty(false);
		stackPane = new StackPane();
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
	}

}
