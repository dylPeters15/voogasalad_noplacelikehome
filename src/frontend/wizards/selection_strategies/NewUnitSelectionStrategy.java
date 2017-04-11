package frontend.wizards.selection_strategies;

import java.util.Arrays;

import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_pages.AbilitiesAdderPage;
import frontend.wizards.wizard_pages.ImageNameDescriptionPage;
import frontend.wizards.wizard_pages.TerrainMovePointPage;

public class NewUnitSelectionStrategy extends BaseSelectionStrategy<Unit> {

	private ImageNameDescriptionPage imageNameDescriptionPage;
	private AbilitiesAdderPage abilitiesAdderPage;
	private TerrainMovePointPage terrainMovePointPage;

	public NewUnitSelectionStrategy(AuthoringGameState gameState) {
		initialize(gameState);
	}

	@Override
	public Unit finish() {
		System.out.println("Image: " + imageNameDescriptionPage.getImage());
		System.out.println("Name: " + imageNameDescriptionPage.getName());
		System.out.println("Description: " + imageNameDescriptionPage.getDescription());
		System.out.println("Abilities: " + abilitiesAdderPage.getSelectedAbilities());
		System.out.println("Terrain Move Points: " + terrainMovePointPage.getTerrainMovePoints());
		return new ModifiableUnit(imageNameDescriptionPage.getName());
	}

	private void initialize(AuthoringGameState gameState) {
		imageNameDescriptionPage = new ImageNameDescriptionPage("Create New Unit");
		abilitiesAdderPage = new AbilitiesAdderPage(gameState);
		terrainMovePointPage = new TerrainMovePointPage(gameState);
		getPages().addAll(Arrays.asList(imageNameDescriptionPage, abilitiesAdderPage, terrainMovePointPage));
	}

}
