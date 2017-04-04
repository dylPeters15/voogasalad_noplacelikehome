/**
 * A visual representation of anything that can be placed in the game, such as a playable piece, an immovable object,
 * or terrain. Can also be acted upon with mouse click and drags. A Sprite is composed of a List of SpriteCells so
 * that it can take up multiple Cells on the grid, but still is represented with one image.
 */
package frontend.sprites;

import java.util.List;
import frontend.Displayable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface Sprite extends Displayable {

	/**
	 * Determines what is done when a Sprite is clicked.
	 */
	void setOnClick(EventHandler<ActionEvent> event);
	
	/**
	 * Determines what is done when a Sprite is clicked and dragged.
	 */
	void setOnDragDetected(EventHandler<MouseEvent> event);
	
	/**
	 * @return List of SpriteCells composing the Sprite.
	 */
	List<SpriteCell> getSpriteCells();
	
	/**
	 * Adds cell to the Sprite's list of SpriteCells.
	 * 
	 * @param SpriteCell to be added.
	 */
	void addSpriteCell(SpriteCell cell);
	
	/**
	 * @param index in the list to search for a SpriteCell.
	 * @return SpriteCell at the given index.
	 */
	SpriteCell getSpriteCell(int index);
	
	/**
	 * @return integer size of the list of SpriteCells.
	 */
	int getSize();
	
	/**
	 * @return Image representing the Sprite.
	 */
	Image getImage();
	
	/**
	 * @param Image to be set to represent the Sprite.
	 */
	void setImage(Image image);
	
	/**
	 * @param String that represents the type of list the Sprite belongs to.
	 */
	void setListType(String type);
	
	/**
	 * @return String holding the type of list the Sprite belongs to.
	 */
	String getListType();
}
