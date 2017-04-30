/**
 * 
 */
package frontend.factory.conditionspane;

import controller.Controller;
import frontend.ClickHandler;
import javafx.beans.value.ObservableValue;

/**
 * @author Stone Mathers
 * Created 4/25/2017
 */
public class TurnActionBox extends ConditionBox {
	
	/**
	 * @param turnActionName
	 * @param controller
	 * @param clickHandler
	 */
	public TurnActionBox(String turnActionName, String category, Controller controller, ClickHandler clickHandler) {
		super(turnActionName, category, controller, clickHandler);
	}

	@Override
	protected void checkBoxAction(ObservableValue<? extends Boolean> o, Boolean oldValue, Boolean newValue) {
		this.actInAuthoringMode(this, null, getClickHandler(), null);
		if(newValue){
			getController().activateTurnAction(getName());
		} else {
			getController().deactivateTurnAction(getName());
		}
	}

}
