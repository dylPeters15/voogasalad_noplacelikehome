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
		anchor = createAnchorPane();
	}
	
	private AnchorPane createAnchorPane(){
		VBox settings = createSettingsBox();
		HBox buttons = createButtonsBox();
		anchor = new AnchorPane(settings, buttons);
		
		AnchorPane.setTopAnchor(settings, EDGE_OFFSET);
		AnchorPane.setLeftAnchor(settings, EDGE_OFFSET);
		AnchorPane.setBottomAnchor(buttons, EDGE_OFFSET);
		AnchorPane.setRightAnchor(buttons, EDGE_OFFSET);
		
		return anchor;
	}
	
	private VBox createSettingsBox(){
		return new VBox(new NameSetting().getObject(), new AuthorSetting().getObject(), new CellShapeSetting().getObject(),
							new GameTypeSetting().getObject(), new GridSizeSetting().getObject());
	}
	
	private HBox createButtonsBox(){
		Button cancelButton = new Button("Cancel"); //TODO get string from resource file depending on language
		cancelButton.setOnMouseClicked(e -> {}); //TODO: Implement cancel action
		
		Button continueButton = new Button("Continue"); //TODO get string from resource file depending on language
		continueButton.setOnMouseClicked(e -> {}); //TODO: Implement continue action
		
		
		return new HBox(BUTTON_SPACING, cancelButton, continueButton);
	}

	@Override
	public Region getObject() {
		return anchor;
	}
}
