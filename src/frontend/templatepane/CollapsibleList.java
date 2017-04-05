/**
 * A CollapsibleList is a GUI component that holds a list of ListItems. This list should be collapsible and expandable,
 * meaning that, visually, the ListItems can disappear and reappear.
 */
package frontend.templatepane;

import java.util.List;

import frontend.Displayable;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * @author Stone Mathers 
 * Created 3/29/2017
 */
public class CollapsibleList implements Displayable {

	private Group myGroup = new Group();
	private List<ListItem> myList;
	Displayable myParent;
	
	public CollapsibleList(Displayable parent){
		myParent = parent;
	}
	
	
	/**
	 * Makes ListItems no longer visible, as they "collapse" down and only a header label remains.
	 */
	public void collapse(){
		
	}
	
	/**
	 * Makes ListItems once again visible.
	 */
	public void expand(){
		
	}

	/**
	 * @return List of ListItems
	 */
	public List<ListItem> getItems(){
		return myList;
	}

	/**
	 * @param ListItem to be added to the CollapsibleList.
	 */
	public void add(ListItem item){
		myList.add(item);
	}
	
	@Override
	public Node getView(){
		return myGroup;
	}

}
