/**
 * 
 */
package frontend.sprites;

import java.util.List;
import frontend.Displayable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;

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
	void setOnDrag(EventHandler<ActionEvent> event);
	
	List<SpriteCell> getSpriteCells();
	
	void addSpriteCell(SpriteCell cell);
	
	SpriteCell getSpriteCell(int index);
	
	int getSize();
	
	Image getImage();
	
	void setImage(Image image);
	
	void setListType(String type);
	
	String getListType();
}
