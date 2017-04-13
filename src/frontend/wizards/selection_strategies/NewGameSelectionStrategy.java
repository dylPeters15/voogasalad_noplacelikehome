package frontend.wizards.selection_strategies;

import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import backend.util.AuthoringGameState;
import frontend.wizards.wizard_pages.GridInstantiationPage;
import frontend.wizards.wizard_pages.ImageNameDescriptionPage;

public class NewGameSelectionStrategy extends BaseSelectionStrategy<AuthoringGameState>
		implements WizardSelectionStrategy<AuthoringGameState> {

	private ImageNameDescriptionPage gameNamePage;
	private ImageNameDescriptionPage boardNamePage;
	private GridInstantiationPage gridInstantiationPage;

	public NewGameSelectionStrategy() {
		initialize();
	}

	@Override
	public AuthoringGameState finish() {
		ModifiableGameBoard boardBuilder = new ModifiableGameBoard(boardNamePage.getName());
		boardBuilder.setDescription(boardNamePage.getDescription());
		boardBuilder.setImgPath(boardNamePage.getImagePath());
		boardBuilder.setRows(gridInstantiationPage.getRows());
		boardBuilder.setColumns(gridInstantiationPage.getCols());
		boardBuilder.setTemplateCell(gridInstantiationPage.getTemplateCell());
		boardBuilder.setBoundsHandler(BoundsHandler.FINITE_BOUNDS);
		AuthoringGameState gameState = new AuthoringGameState(gameNamePage.getName());
		gameState.setDescription(gameNamePage.getDescription());
		gameState.setImgPath(gameNamePage.getImagePath());
		gameState.setGrid(boardBuilder.build());
		System.out.println(boardBuilder.build());
		return gameState;
	}

	private void initialize() {
		gameNamePage = new ImageNameDescriptionPage("Create New Game", "Enter the icon, name, and description");
		boardNamePage = new ImageNameDescriptionPage("Create New Board",
				"Enter the background image, board name, and board description.");
		gridInstantiationPage = new GridInstantiationPage();
		getPages().addAll(gameNamePage, boardNamePage, gridInstantiationPage);
	}
}