/**
 * Holds a grid to be displayed in the development and player GUI. It can have Sprites added to a particular cell or 
 * have all cells updated after something occurs in the game.
 */
package frontend.worldview.grid;

import backend.grid.GameBoard;
import frontend.sprites.Sprite;
import frontend.util.BaseUIManager;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Region;

/**
 * Andreas
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class GridView extends BaseUIManager<Region> {

	private ScrollPane myScrollPane;
	
	public GridView(){
		initialize();
	}

	private void initialize() {
		
		
	}

	@Override
	public Region getObject() {
		return myScrollPane;
	}
}
