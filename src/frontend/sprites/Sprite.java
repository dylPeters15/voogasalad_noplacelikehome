/**
 * 
 */
package frontend.sprites;

import java.util.List;
import frontend.Displayable;
import javafx.scene.image.Image;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface Sprite extends Displayable {

	/**
	 * Determines what is done when a Sprite is clicked.
	 */
	void setOnClick();
	
	/**
	 * Determines what is done when a Sprite is clicked and dragged.
	 */
	void setOnDrag();
	
	List<SpriteCell> getSpriteCells();
	
	void addSpriteCell(SpriteCell cell);
	
	SpriteCell getSpriteCell(int index);
	
	int getSize();
	
	Image getImage();
	
	void setImage(Image image);
}
