package frontend.wizards.wizard_2_0.selection_strategies;

import backend.util.GameState;
import frontend.wizards.wizard_2_0.wizard_pages.GridInstantiationPage;
import frontend.wizards.wizard_2_0.wizard_pages.ImageNameDescriptionPage;

public class NewGameSelectionStrategy extends BaseSelectionStrategy<GameState> {

	private ImageNameDescriptionPage imageNameDescriptionPage;
	private GridInstantiationPage gridInstantiationPage;

	public NewGameSelectionStrategy() {
		initialize();
	}

	@Override
	public GameState finish() {
		GameState gameState = new GameState(gridInstantiationPage.getGameBoard());
		return gameState;
	}

	private void initialize() {
		imageNameDescriptionPage = new ImageNameDescriptionPage();
		gridInstantiationPage = new GridInstantiationPage();
		getPages().addAll(imageNameDescriptionPage, gridInstantiationPage);
	}

}
