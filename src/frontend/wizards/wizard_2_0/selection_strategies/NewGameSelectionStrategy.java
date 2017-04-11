package frontend.wizards.wizard_2_0.selection_strategies;

import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.grid.BoundsHandler;
import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import backend.grid.ModifiableGameBoard;
import backend.util.GameState;
import frontend.wizards.wizard_2_0.wizard_pages.GridInstantiationPage;
import frontend.wizards.wizard_2_0.wizard_pages.ImageNameDescriptionPage;

public class NewGameSelectionStrategy extends BaseSelectionStrategy<GameState> {

	private ImageNameDescriptionPage imageNameDescriptionPage;
	private GridInstantiationPage gridInstantiationPage;
	
	public NewGameSelectionStrategy(){
		initialize();
	}
	
	@Override
	public GameState finish() {
		new CoordinateTuple(1,2,3);
		GridPattern pattern = GridPattern.HEXAGONAL_ADJACENT;
		Cell template = ModifiableCell.BASIC_SQUARE_FLAT;
		ModifiableGameBoard board = new ModifiableGameBoard("testBoard", template, 5, 5, BoundsHandler.TOROIDAL_BOUNDS, "", "").copy();
		GameState gameState = new GameState(board);
		return gameState;
	}
	
	private void initialize(){
		imageNameDescriptionPage = new ImageNameDescriptionPage();
		gridInstantiationPage = new GridInstantiationPage();
		getPages().addAll(imageNameDescriptionPage);//,gridInstantiationPage);
	}

}
