/**
 * A Unit object is an movable or immovable character/object that can be placed on top of and move across Terrains.
 */
package frontend.sprites;

import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class Unit implements Sprite {

	/* (non-Javadoc)
	 * @see frontend.Displayable#getView()
	 */
	@Override
	public Node getView() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#setOnClick()
	 */
	@Override
	public void setOnClick(EventHandler<ActionEvent> event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#setOnDrag()
	 */
	@Override
	public void setOnDragDetected(EventHandler<MouseEvent> event) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#getSpriteCells()
	 */
	@Override
	public List<SpriteCell> getSpriteCells() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#addSpriteCell(frontend.sprites.SpriteCell)
	 */
	@Override
	public void addSpriteCell(SpriteCell cell) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#getSpriteCell(int)
	 */
	@Override
	public SpriteCell getSpriteCell(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#getImage()
	 */
	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#setImage(javafx.scene.image.Image)
	 */
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
