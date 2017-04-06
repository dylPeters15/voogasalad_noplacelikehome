/**
 * 
 */
package frontend.wizard;

import frontend.BaseUIManager;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

/**
 * @author Stone Mathers
 * Created 4/5/2017
 */
public class Wizard extends BaseUIManager<Region>{
	
	public static final double EDGE_OFFSET = 5.0;
	public static final double BUTTON_SPACING = 10.0;
	
	private AnchorPane anchor;
	
	public Wizard(){
		initAnchorPane();
	}
	
	private void initAnchorPane(){
		VBox settings = initSettings();
		HBox buttons = initButtons();
		anchor = new AnchorPane(settings, buttons);
		
		AnchorPane.setTopAnchor(settings, EDGE_OFFSET);
		AnchorPane.setLeftAnchor(settings, EDGE_OFFSET);
		AnchorPane.setBottomAnchor(buttons, EDGE_OFFSET);
		AnchorPane.setRightAnchor(buttons, EDGE_OFFSET);
	}
	
	private VBox initSettings(){
		//TODO
		
		return new VBox();
	}
	
	private HBox initButtons(){
		Button cancelButton = new Button("Cancel");
		cancelButton.setOnMouseClicked(e -> {}); //TODO: Implement cancel action
		
		Button continueButton = new Button("Continue");
		continueButton.setOnMouseClicked(e -> {}); //TODO: Implement continue action
		
		
		return new HBox(BUTTON_SPACING, cancelButton, continueButton);
	}

	@Override
	public Region getObject() {
		return anchor;
	}
}
