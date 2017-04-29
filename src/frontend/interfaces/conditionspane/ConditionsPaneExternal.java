/**
 * 
 */
package frontend.interfaces.conditionspane;

import javafx.beans.property.ObjectProperty;
import javafx.scene.layout.Region;
import util.polyglot.Polyglot;

/**
 * @author Stone Mathers Created 4/20/2017
 */
public interface ConditionsPaneExternal {

	Region getNode();

	Polyglot getPolyglot();

	ObjectProperty<String> getStyleSheet();

}
