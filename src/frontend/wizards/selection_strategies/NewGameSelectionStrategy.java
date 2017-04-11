package frontend.wizards.selection_strategies;

import backend.util.AuthoringGameState;
import frontend.wizards.wizard_pages.GridInstantiationPage;
import frontend.wizards.wizard_pages.ImageNameDescriptionPage;

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
		imageNameDescriptionPage = new ImageNameDescriptionPage("Create New Game","Enter the icon, name, and description");
		gridInstantiationPage = new GridInstantiationPage();
		getPages().addAll(imageNameDescriptionPage, gridInstantiationPage);
	}
}