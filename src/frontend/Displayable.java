/**
 * A Displayable class is one that has graphical components that need to be displayed in the GUI.
 */
package frontend;

import javafx.scene.Node;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface Displayable {

	/**
	 * Returns a single Node containing all elements to be displayed.
	 * 
	 * @return Node to be displayed
	 */
	Node getView();
	
}
