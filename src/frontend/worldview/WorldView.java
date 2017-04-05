package frontend.worldview;

import backend.grid.ImmutableGrid;
import frontend.BaseUIManager;
import frontend.worldview.grid.GridDisplay;
import javafx.scene.layout.Region;

public class WorldView extends BaseUIManager<Region>{

	private GridDisplay myGrid;
	
	
	public void updateGrid(ImmutableGrid grid){
		myGrid.updateCells(grid);
	}


	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
