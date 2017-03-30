package frontend;

import frontend.sprites.Sprite;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 *
 */
public interface SpriteListItem extends ListItem {
	
	Sprite getSprite();
	
	void setSprite(Sprite sprite);
	
	String getName();
	
	void setName(String name);
	
}
