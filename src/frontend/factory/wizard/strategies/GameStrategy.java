package frontend.factory.wizard.strategies;

import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import backend.util.AuthoringGameState;
import controller.Controller;
import frontend.factory.wizard.strategies.wizard_pages.GridInstantiationPage;
import frontend.factory.wizard.strategies.wizard_pages.ImageNameDescriptionPage;
import javafx.beans.binding.StringBinding;

/**
 * GameStrategy implements the SelectionStrategy interface in order to allow the
 * user to instantiate new AuthoringGameStates.
 *
 * @author Dylan Peters, heavily edited by ncp14 4/18/17
 */
class GameStrategy extends BaseStrategy<AuthoringGameState> implements WizardStrategy<AuthoringGameState> {

	private ImageNameDescriptionPage gameNamePage;
	private ImageNameDescriptionPage boardNamePage;
	private GridInstantiationPage gridInstantiationPage;

	/**
	 * Creates a new instance of GameStrategy
	 */
	public GameStrategy(Controller controller) {
		super(controller);
		initialize();
	}

	/**
	 * Returns a fully instantiated AuthoringGameState
	 */
	@Override
	public AuthoringGameState finish() {
		ModifiableGameBoard boardBuilder = new ModifiableGameBoard("");
		boardBuilder.setName(boardNamePage.getName());
		boardBuilder.setDescription(boardNamePage.getDescriptionBoxText());
		boardBuilder.setImgPath(boardNamePage.getImagePath());
		boardBuilder.setRows(gridInstantiationPage.getRows());
		boardBuilder.setColumns(gridInstantiationPage.getCols());
		boardBuilder.setTemplateCell(gridInstantiationPage.getTemplateCell());
		boardBuilder.setBoundsHandler(BoundsHandler.INFINITE_BOUNDS);
		AuthoringGameState gameState = new AuthoringGameState(gameNamePage.getName());
		gameState.setDescription(gameNamePage.getDescriptionBoxText());
		gameState.setImgPath(gameNamePage.getImagePath());
		gameState.setGrid(boardBuilder.build());
		return gameState;
	}

	private void initialize() {
		gameNamePage = new ImageNameDescriptionPage(getController(), "GameStrategyGameNameDescription");
		boardNamePage = new ImageNameDescriptionPage(getController(), "GameStrategyBoardNameDescription");
		gridInstantiationPage = new GridInstantiationPage(getController(), "GameStrategyGridInstantiationDescription");

		getPages().addAll(gameNamePage, boardNamePage, gridInstantiationPage);
	}

	@Override
	public StringBinding getTitle() {
		return getPolyglot().get("GameStrategyTitle");
	}
}