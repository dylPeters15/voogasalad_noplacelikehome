package frontend.wizards.strategies;

import backend.cell.Terrain;
import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import backend.player.Team;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import frontend.wizards.TeamWizard;
import frontend.wizards.TerrainWizard;
import frontend.wizards.UnitWizard;
import frontend.wizards.strategies.wizard_pages.AdditionalWizardsPage;
import frontend.wizards.strategies.wizard_pages.GridInstantiationPage;
import frontend.wizards.strategies.wizard_pages.ImageNameDescriptionPage;

/**
 * GameStrategy implements the SelectionStrategy interface in order to allow the
 * user to instantiate new AuthoringGameStates.
 *
 * @author Dylan Peters, heavily edited by ncp14 4/18/17
 */
public class GameStrategy extends BaseStrategy<AuthoringGameState> implements WizardStrategy<AuthoringGameState> {

	private ImageNameDescriptionPage gameNamePage;
	private ImageNameDescriptionPage boardNamePage;
	private GridInstantiationPage gridInstantiationPage;
	private AdditionalWizardsPage<Team> additionalTeamWizardsPage;
	private AdditionalWizardsPage<Unit> additionalUnitWizardsPage;
	private AdditionalWizardsPage<Terrain> additionalTerrainWizardsPage;

	public GameStrategy() {
		initialize();
	}

	@Override
	public AuthoringGameState finish() {
		ModifiableGameBoard boardBuilder = new ModifiableGameBoard("");
		boardBuilder.setName(boardNamePage.getName());
		boardBuilder.setDescription(boardNamePage.getDescription());
		boardBuilder.setImgPath(boardNamePage.getImagePath());
		boardBuilder.setRows(gridInstantiationPage.getRows());
		boardBuilder.setColumns(gridInstantiationPage.getCols());
		boardBuilder.setTemplateCell(gridInstantiationPage.getTemplateCell());
		boardBuilder.setBoundsHandler(BoundsHandler.INFINITE_BOUNDS);
		AuthoringGameState gameState = new AuthoringGameState(gameNamePage.getName()); //Why is the authoring gamestate initialized here, dylan? Isn't it already initialized on the homepage? -noah
		gameState.setDescription(gameNamePage.getDescription());
		gameState.setImgPath(gameNamePage.getImagePath());
		gameState.setGrid(boardBuilder.build());
		gameState.setTeams(additionalTeamWizardsPage.getObjects());
		return gameState;
	}

	private void initialize() {
		gameNamePage = new ImageNameDescriptionPage(getString("CreateNewGame"), getString("CreateNewGameDesc"));
		boardNamePage = new ImageNameDescriptionPage(getString("CreateNewBoard"), getString("CreateNewBoardDesc"));
		gridInstantiationPage = new GridInstantiationPage();
		additionalUnitWizardsPage = new AdditionalWizardsPage<>("Create Units",
				"Use the wizards below to create new units", UnitWizard.class);
		additionalTeamWizardsPage = new AdditionalWizardsPage<>("Create Teams",
				"Use the wizards below to create new teams", TeamWizard.class);
		additionalTerrainWizardsPage = new AdditionalWizardsPage<>("Create Terrains", 
				"Use the wizards below to create new terrains", TerrainWizard.class);
		getPages().addAll(gameNamePage, boardNamePage, gridInstantiationPage, 
				additionalUnitWizardsPage, additionalTerrainWizardsPage, additionalTeamWizardsPage);
	}
}