package frontend.wizards.wizard_2_0.selection_strategies;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import frontend.util.BaseUIManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Parent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public abstract class BaseSelectionStrategy<T> extends BaseUIManager<Region> implements WizardSelectionStrategy<T> {

	private BooleanProperty canPrevious, canNext, canFinish;
	private StackPane stackPane;
	private List<BaseUIManager<? extends Parent>> pages;

	public BaseSelectionStrategy() {
		this(new ArrayList<>());
	}

	public BaseSelectionStrategy(Collection<BaseUIManager<? extends Parent>> pages) {
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
		if (getCurrentPageNum() == -1) {
			goToPageNum(0);
		}
		return stackPane;
	}

	@Override
	public void previous() {
		goToPageNum(getCurrentPageNum() - 1);
	}

	@Override
	public void next() {
		goToPageNum(getCurrentPageNum() + 1);
	}

	protected BooleanProperty canPreviousWritable() {
		return canPrevious;
	}

	protected BooleanProperty canNextWritable() {
		return canNext;
	}

	protected BooleanProperty canFinishWritable() {
		return canFinish;
	}

	protected List<BaseUIManager<? extends Parent>> getPages() {
		return pages;
	}

	protected int getCurrentPageNum() {
		int i = 0;
		for (BaseUIManager<? extends Parent> page : pages) {
			if (stackPane.getChildren().contains(page.getObject())) {
				return i;
			}
			i++;
		}
		return -1;
	}

	protected BaseUIManager<? extends Parent> getCurrentPage() {
		return getCurrentPageNum() == -1 ? null : pages.get(getCurrentPageNum());
	}

	private void goToPageNum(int newPageNum) {
		if (newPageNum >= 0 && newPageNum < pages.size()) {
			hideAllPages();
			stackPane.getChildren().add(pages.get(newPageNum).getObject());
		} else if (!pages.isEmpty()) {
			goToPageNum(0);
		}
	}

	private void hideAllPages() {
		pages.stream().forEach(page -> {
			if (stackPane.getChildren().contains(page.getObject())) {
				stackPane.getChildren().remove(page.getObject());
			}
		});
	}

	private void initialize(Collection<BaseUIManager<? extends Parent>> pages) {
		canPrevious = new SimpleBooleanProperty(false);
		canNext = new SimpleBooleanProperty(false);
		canFinish = new SimpleBooleanProperty(false);
		stackPane = new StackPane();
		this.pages = new ArrayList<>(pages);
		goToPageNum(0);
	}

}
