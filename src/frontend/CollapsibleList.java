/**
 * 
 */
package frontend;

import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;

/**
 * @author Stone Mathers Created 3/29/2017
 */
public class CollapsibleList implements Displayable {

	private Group myGroup = new Group();
	private List<ListItem> myList;
	Displayable myParent;
	
	public CollapsibleList(Displayable parent){
		myParent = parent;
	}
	
	
	public void collapse(){
		
	}
	
	public void expand(){
		
	}

	public List<ListItem> getItems(){
		return myList;
	}

	public void add(ListItem item){
		myList.add(item);
	}
	
	public Node getView(){
		return myGroup;
	}

}
