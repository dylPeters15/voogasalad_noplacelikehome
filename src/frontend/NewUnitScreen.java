/**
 * Popup window that appears when the user wishes to create a new Unit.
 * The window allows the user to upload an image, name the unit, and determine any attributes.
 */
package frontend;

import frontend.sprites.Sprite;
import frontend.sprites.Unit;
import frontend.templatepane.SpriteMenu;
import frontend.templatepane.UnitListItem;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Button;

/**
 * @author Stone Mathers
 * Created 3/30/2017
 */
public class NewUnitScreen implements Displayable {

	private Group myGroup = new Group();
	private SpriteMenu mySpriteMenu;
	
	public NewUnitScreen(SpriteMenu menu){
		mySpriteMenu = menu;
		initButtons();
	}
	

	private void initButtons(){
		Button create = new Button("Create");
		create.setOnMouseClicked(e -> {
			Sprite sprite = new Unit(); //It needs to be determined how the type of Sprite is
										//determined and how it is given its data
			UnitListItem unit = new UnitListItem(sprite); //Needs to be determined how we know which CollapsibleList to add this to
			mySpriteMenu.getListForSprite(sprite).add(unit);
		});
	}
	
	@Override
	public Node getView() {
		return myGroup;
	}

}
