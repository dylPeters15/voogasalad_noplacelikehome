/**
 * A Terrain object is any landscape piece that can be placed on the grid. These make up the board or ground
 * that movable pieces can traverse and immovable pieces can be placed on. A Terrain may or may not have attributes
 * that affect a piece located on it.
 */
package frontend.sprites;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class Terrain implements Sprite {

	@Override
	public Node getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setOnClick(EventHandler<ActionEvent> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOnDrag(EventHandler<ActionEvent> event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<SpriteCell> getSpriteCells() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addSpriteCell(SpriteCell cell) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public SpriteCell getSpriteCell(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setImage(Image image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setListType(String type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getListType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
