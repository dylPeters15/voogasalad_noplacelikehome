/**
 * 
 */
package frontend;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
	
	
	/* (non-Javadoc)
	 * @see frontend.Displayable#getView()
	 */
	@Override
	public Node getView() {
		return myGroup;
	}
	
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
