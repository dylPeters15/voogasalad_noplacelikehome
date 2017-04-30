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
			System.out.println("add act");
			System.out.println(getController().getAuthoringGameState().getActiveTurnActions().size());
			getController().activateTurnAction(getName());
			System.out.println(getController().getAuthoringGameState().getActiveTurnActions().size());
		} else {
			System.out.println("remove act");
			System.out.println(getController().getAuthoringGameState().getActiveTurnActions().size());
			getController().deactivateTurnAction(getName());
			System.out.println(getController().getAuthoringGameState().getActiveTurnActions().size());
		}
	}

}
