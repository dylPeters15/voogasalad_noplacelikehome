package frontend.factory.templatepane;

import backend.grid.BoundsHandler;
import backend.util.HasLocation;
import backend.util.HasSound;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.ClickHandler;
import frontend.ClickableUIComponent;
import frontend.interfaces.worldview.CellViewExternal;
import frontend.util.AddRemoveButton;
import frontend.util.GameBoardObjectView;
import frontend.util.VoogaEntityButton;
import javafx.event.Event;

/**
 * @author Created by th174 on 4/22/17.
 */
public class TemplateButton extends VoogaEntityButton implements GameBoardObjectView {
	private final String templateCategory;

	public TemplateButton(VoogaEntity entity, String templateCategory, int size, Controller controller, ClickHandler clickHandler) {
		super(entity, size, controller, clickHandler);
		this.templateCategory = templateCategory;
	}

	@Override
	public void actInAuthoringMode(ClickableUIComponent target, Object additionalInfo, ClickHandler clickHandler, Event event) {
		if (target instanceof AddRemoveButton) {
			getController().removeTemplatesByCategory(templateCategory, getEntity().getName());
			clickHandler.cancel();
		} else if (getEntity() instanceof BoundsHandler) {
			getController().setBoundsHandler(getEntity().getName());
		} else if (target instanceof GameBoardObjectView && ((GameBoardObjectView) target).getEntity() instanceof HasLocation) {
			getController().copyTemplateToGrid(getEntity(), (HasLocation) ((GameBoardObjectView) target).getEntity());
			if (target instanceof CellViewExternal) {
				playMedia(((CellViewExternal) target).getEntity().getTerrain().getSoundPath());
			}
		}
		super.actInAuthoringMode(target, additionalInfo, clickHandler, event);
	}

	@Override
	public void actInGameplayMode(ClickableUIComponent target, Object additionalInfo, ClickHandler clickHandler, Event event) {
		if (getController().isMyTeam()) {
			actInAuthoringMode(target, null, clickHandler, event);
		}
	}

	@Override
	public String toString() {
		return getEntity().toString();
	}
//	
//	@Override
//	public void select(ClickHandler clickHandler){
//		super.select(clickHandler);
//		//playMedia(getEntity().get)
//	}
}
