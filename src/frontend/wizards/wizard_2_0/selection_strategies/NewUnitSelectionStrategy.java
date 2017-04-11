package frontend.wizards.wizard_2_0.selection_strategies;

import java.util.Arrays;

import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_2_0.wizard_pages.AbilitiesAdderPage;
import frontend.wizards.wizard_2_0.wizard_pages.ImageNameDescriptionPage;
import frontend.wizards.wizard_2_0.wizard_pages.TerrainMovePointPage;

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
		// return new ModifiableUnit(imageNameDescriptionPage.getName(),
		// unitStats, unitFaction, movePattern,
		// terrainMovePointPage.getTerrainMovePoints(),
		// abilitiesAdderPage.getSelectedAbilities(), triggeredAbilities,
		// offensiveModifiers, defensiveModifiers,
		// imageNameDescriptionPage.getDescription(),
		// imageNameDescriptionPage.getImagePath());
		// can change ^ to use a constructor with more options, using
		// information retreived from the user by the pages. For example:
		// "return new ModifiableUnit(imageNamePairView.getName(),
		// imageNamePairView.getImage(), abilitiesAdder.getAbilities());"
	}

	private void initialize(AuthoringGameState gameState) {
		imageNameDescriptionPage = new ImageNameDescriptionPage();
		abilitiesAdderPage = new AbilitiesAdderPage(gameState);
		terrainMovePointPage = new TerrainMovePointPage(gameState);
		getPages().addAll(Arrays.asList(imageNameDescriptionPage, abilitiesAdderPage, terrainMovePointPage));
	}

}
