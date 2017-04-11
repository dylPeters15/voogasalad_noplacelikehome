package frontend.worldview;

import backend.grid.GameBoard;
import frontend.util.BaseUIManager;
import frontend.worldview.grid.GridDisplay;
import frontend.worldview.grid.SquareGridDisplay;
import javafx.scene.layout.Region;

public class WorldView extends BaseUIManager<Region>{

	private GridDisplay myGrid;
	
	public WorldView(GameBoard grid){
		myGrid = initGrid(grid);
	}
	
	
	public void updateGrid(GameBoard grid){
		myGrid.updateCells(grid);
	}

	@Override
	public Region getObject() {
		return myGrid.getObject();
	}
	
	private GridDisplay initGrid(GameBoard grid){
		return new SquareGridDisplay(grid);
		// TODO create a GridDisplay using the backend ImmutableGrid structure and return it.
				//Don't forget to account for the cell shape.
				//"grid.getTemplateCell().getShape()" will return the cell shape being used by this particular grid.
	}
	
}
