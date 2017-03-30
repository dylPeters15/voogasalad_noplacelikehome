/**
 * 
 */
package frontend;

import java.util.List;

/**
 * @author Stone Mathers Created 3/29/2017
 */
public interface CollapsibleList extends Displayable {

	void collapse();

	List<ListItem> getItems();

	void addItem(ListItem item);

}
