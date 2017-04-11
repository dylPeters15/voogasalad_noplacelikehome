package frontend.wizards.wizard_2_0.selection_strategies;

import backend.util.AuthoringGameState;
import frontend.wizards.wizard_2_0.wizard_pages.GridInstantiationPage;
import frontend.wizards.wizard_2_0.wizard_pages.ImageNameDescriptionPage;

public class NewGameSelectionStrategy extends BaseSelectionStrategy<AuthoringGameState> implements WizardSelectionStrategy<AuthoringGameState> {

	private ImageNameDescriptionPage imageNameDescriptionPage;
	private GridInstantiationPage gridInstantiationPage;

	public NewGameSelectionStrategy() {
		initialize();
	}

	@Override
	public AuthoringGameState finish() {
		AuthoringGameState gameState = new AuthoringGameState("TestGameState").setGrid(gridInstantiationPage.getGameBoard());
		return gameState;
	}

	private void initialize() {
		imageNameDescriptionPage = new ImageNameDescriptionPage();
		gridInstantiationPage = new GridInstantiationPage();
		getPages().addAll(imageNameDescriptionPage, gridInstantiationPage);
	}
}