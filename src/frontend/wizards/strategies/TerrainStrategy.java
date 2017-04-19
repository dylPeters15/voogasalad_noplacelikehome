package frontend.wizards.strategies;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.unit.ModifiableUnit;
import backend.util.AuthoringGameState;
import frontend.wizards.strategies.wizard_pages.AbilitiesAdderPage;
import frontend.wizards.strategies.wizard_pages.ImageNameDescriptionPage;
import frontend.wizards.strategies.wizard_pages.TerrainMovePointPage;
import frontend.wizards.strategies.wizard_pages.UnitMovePointPage;

/**
 * Strategy that extends BaseStrategy to instantiate a new Terrain object.
 * 
 * @author Dylan Peters
 *
 */
public class TerrainStrategy extends BaseStrategy<Terrain> {
	
	private ImageNameDescriptionPage imageNameDescriptionPage;
	//private AbilitiesAdderPage abilitiesAdderPage;
	private UnitMovePointPage unitMovePointPage;
	
	public TerrainStrategy(AuthoringGameState gameState){
		initialize(gameState);
	}
	
	private void initialize(AuthoringGameState gameState) {
		imageNameDescriptionPage = new ImageNameDescriptionPage("Create New Unit");
		unitMovePointPage = new UnitMovePointPage(gameState);
		getPages().addAll(imageNameDescriptionPage, unitMovePointPage);
	}

	@Override
	public Terrain finish() {
		ModifiableTerrain terrain = new ModifiableTerrain(imageNameDescriptionPage.getName());
		terrain.setDescription(imageNameDescriptionPage.getDescription());
		terrain.setImgPath(imageNameDescriptionPage.getImagePath());
		terrain.setDefaultMoveCost(unitMovePointPage.getValue());
		return terrain;
	}

}
