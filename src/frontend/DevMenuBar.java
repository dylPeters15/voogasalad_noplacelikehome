/**
 * The DevMenuBar is the menu bar at the top of the development environment. It is meant to allow the user to 
 * perform all actions that do not involve directly changing the game board, such as placing new Sprites or using any tools.
 */
package frontend;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class DevMenuBar implements MenuBar {
	
	private Stage myStage = new Stage();
	private Group myGroup = new Group();
	private SpriteMenu availableSprites;

	public DevMenuBar(SpriteMenu sprites){
		availableSprites = sprites;
		initButtons();
	}
	
	@Override
	public Node getView() {
		return myGroup;
	}
	
	/**
	 * Initializes all Buttons in the menu.
	 */
	private void initButtons(){
		Button createUnit = new Button("Create Unit");
		createUnit.setOnMouseClicked(e -> {
			Scene s = new Scene(myGroup, 100, 100);
			NewUnitScreen us = new NewUnitScreen(availableSprites);
			myGroup.getChildren().add(us.getView());
			myStage.setScene(s);
			myStage.showAndWait();
			myGroup.getChildren().remove(us);
		});
		myGroup.getChildren().add(createUnit);
		
	}
	

}
