/**
 * 
 */
package frontend.sprites;

import java.util.List;

import javafx.scene.Node;
import javafx.scene.image.Image;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public abstract class Unit implements Sprite {

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
	public void setOnClick() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.sprites.Sprite#setOnDrag()
	 */
	@Override
	public void setOnDrag() {
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

}
