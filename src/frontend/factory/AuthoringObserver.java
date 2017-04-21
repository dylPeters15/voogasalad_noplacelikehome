package frontend.factory;

import backend.grid.CoordinateTuple;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.interfaces.GameObserver;
import frontend.interfaces.detailpane.DetailPaneExternal;
import frontend.interfaces.templatepane.TemplatePaneExternal;
import frontend.interfaces.worldview.CellViewExternal;
import frontend.interfaces.worldview.UnitViewExternal;
import frontend.interfaces.worldview.WorldViewExternal;

class AuthoringObserver implements GameObserver {

	private String unitClickedName;
	private CoordinateTuple unitClickedLocation;
	private boolean shouldCopy = true;
	private Controller controller;
	private WorldViewExternal worldView;
	private DetailPaneExternal detailPane;
	private TemplatePaneExternal templatePane;

	public AuthoringObserver(Controller controller, WorldViewExternal worldView, DetailPaneExternal detailPane,
			TemplatePaneExternal templatePane) {
		this.controller = controller;
		this.worldView = worldView;
		this.detailPane = detailPane;
		this.templatePane = templatePane;
	}

	@Override
	public void didClickCellViewExternalInterface(CellViewExternal cell) {
		// TODO Auto-generated method stub
//		System.out.println("CellViewExternalCell: " + cell + "\n");
		if (unitClickedName != null) {
			CoordinateTuple unitClickedLocation = this.unitClickedLocation;
			String unitClickedName = this.unitClickedName;
			if (shouldCopy) {
				CoordinateTuple location = cell.getLocation();
				controller.sendModifier((AuthoringGameState gameState) -> {
					gameState.getGrid().get(location).addVoogaEntity(gameState.getTemplateByName(unitClickedName).copy());
					return gameState;
				});
			} else {
				CoordinateTuple location = cell.getLocation();
				controller.sendModifier((GameplayState gameState) -> {
					Unit unitToMove = gameState.getGrid().get(unitClickedLocation).getOccupantByName(unitClickedName);
					unitToMove.moveTo(gameState.getGrid().get(location), gameState);
					return gameState;
				});
			}
			this.shouldCopy = false;
			this.unitClickedName = null;
			this.unitClickedLocation = null;
		}
	}

	@Override
	public void didClickUnitViewExternalInterface(UnitViewExternal unit) {
		// TODO Auto-generated method stub
		System.out.println("UnitViewExternalCell: " + unit + "\n");

		unitClickedName = unit.getUnitName();
		unitClickedLocation = unit.getUnitLocation();
	}

	@Override
	public void didClickVoogaEntity(TemplatePaneExternal templatePane, VoogaEntity entity) {
		// TODO Auto-generated method stub
		System.out.println("TemplatePane: " + templatePane);
		System.out.println("VoogaEntity: " + entity + "\n");

		detailPane.setContent(entity, "");

		unitClickedName = entity.getName();
		unitClickedLocation = null;
		shouldCopy = true;
	}

}
