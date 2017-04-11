package frontend.wizards.wizard_2_0.wizard_pages;

import backend.cell.ModifiableCell;
import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GridInstantiationPage extends WizardPage{

	private VBox vbox;
	
	public GridInstantiationPage(){
		initialize();
	}
	
	@Override
	public Region getObject() {
		return vbox;
	}
	
	public ModifiableGameBoard getGameBoard(){
		return new ModifiableGameBoard("Game Board Name", new ModifiableCell("CellName"), 100, 100, BoundsHandler.FINITE_BOUNDS, "Game Board Description", "imgpath");
	}
	
	private void initialize(){
		vbox = new VBox();
		canNextWritable().setValue(true);
	}

}
