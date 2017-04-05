package frontend.worldview;

import backend.grid.ImmutableGrid;
import frontend.worldview.grid.GridDisplay;

public class WorldView {

	private GridDisplay myGrid;
	
	
	public void updateGrid(ImmutableGrid grid){
		myGrid.updateCells(grid);
	}
	
}
