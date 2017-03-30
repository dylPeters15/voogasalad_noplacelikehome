/**
 * 
 */
package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import frontend.sprites.Sprite;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * @author Stone Mathers
 *
 */
public class SpriteMenu implements Displayable {

	private Group myGroup = new Group();
	private Map<String, CollapsibleList> myLists;
	
	public SpriteMenu(){
		myLists = new HashMap<String, CollapsibleList>();
	}
	
	public CollapsibleList getListForSprite(Sprite sprite){
		return myLists.get(sprite.getListType());
	}
	
	/* (non-Javadoc)
	 * @see frontend.Displayable#getView()
	 */
	@Override
	public Node getView() {
		return myGroup;
	}

}
