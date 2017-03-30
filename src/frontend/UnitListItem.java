/**
 * 
 */
package frontend;

import frontend.sprites.Sprite;
import javafx.scene.Node;

/**
 * @author Stone Mathers
 * Created 3/30/2017
 */
public class UnitListItem implements SpriteListItem {

	Sprite mySprite;
	
	public UnitListItem(Sprite sprite){
		mySprite = sprite;
	}
	
	/* (non-Javadoc)
	 * @see frontend.ListItem#setOnClick()
	 */
	@Override
	public void setOnClick() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.Displayable#getView()
	 */
	@Override
	public Node getView() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.SpriteListItem#getSprite()
	 */
	@Override
	public Sprite getSprite() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.SpriteListItem#setSprite(frontend.sprites.Sprite)
	 */
	@Override
	public void setSprite(Sprite sprite) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see frontend.SpriteListItem#getName()
	 */
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see frontend.SpriteListItem#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub

	}

}
