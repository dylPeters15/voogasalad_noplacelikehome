package frontend.worldview;

import backend.grid.GameBoard;
import frontend.BaseUIManager;
import frontend.worldview.grid.GridDisplay;
import javafx.scene.layout.Region;

public class WorldView extends BaseUIManager<Region>{

	private GridDisplay myGrid;
	
	public WorldView(GameBoard grid){
		myGrid = createGrid(grid);
	}
	
	
	public void updateGrid(GameBoard grid){
		myGrid.updateCells(createGrid(grid));
	}

	private GridDisplay createGrid(GameBoard grid){
		// TODO create a GridDisplay using the backend ImmutableGrid structure and return it.
				//Don't forget to account for the cell shape.
				//"grid.getTemplateCell().getShape()" will return the cell shape being used by this particular grid.
		return null; //TODO This obviously shouldn't be the return type
	}

	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
