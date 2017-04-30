package frontend.factory.wizard.strategies;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import controller.Controller;
import frontend.factory.wizard.strategies.wizard_pages.ImageNameDescriptionPage;
import frontend.factory.wizard.strategies.wizard_pages.UnitMovePointPage;
import javafx.beans.binding.StringBinding;

/**
 * Strategy that extends BaseStrategy to instantiate a new Terrain object.
 * 
 * @author Dylan Peters
 *
 */
class TerrainStrategy extends BaseStrategy<Terrain> {

	private ImageNameDescriptionPage imageNameDescriptionPage;
	private UnitMovePointPage unitMovePointPage;

	/**
	 * Creates a new instance of TerrainStrategy.
	 * 
	 * @param gameState
	 *            the AuthoringGameState that will be used to populate parts of
	 *            the wizard.
	 */
	public TerrainStrategy(Controller controller) {
		super(controller);
		initialize();
	}

	/**
	 * Returns a fully instantiated Terrain object.
	 */
	@Override
	public Terrain finish() {
		ModifiableTerrain terrain = new ModifiableTerrain(imageNameDescriptionPage.getName());
		terrain.setDescription(imageNameDescriptionPage.getDescriptionLabelBinding().getValue());
		terrain.setImgPath(imageNameDescriptionPage.getImagePath());
		terrain.setSoundPath(imageNameDescriptionPage.getSoundPath());
		terrain.setDefaultMoveCost(unitMovePointPage.getValue());
		return terrain;
	}

	@Override
	public StringBinding getTitle() {
		return getPolyglot().get("TerrainTitle");
	}

	private void initialize() {
		imageNameDescriptionPage = new ImageNameDescriptionPage(getController(), "TerrainNameDescription", true);
		unitMovePointPage = new UnitMovePointPage(getController(), "TerrainMovePointDescription");
		getPages().addAll(imageNameDescriptionPage, unitMovePointPage);
	}

}
