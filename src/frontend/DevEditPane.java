package frontend;

import javafx.scene.Group;
import javafx.scene.Node;

public class DevEditPane implements Displayable {

	Group parent = new Group();
	
	public DevEditPane() {
		SpriteAddPane spritepane = new SpriteAddPane();
		
	}
	
	@Override
	public Node getView() {
		return parent;
	}

}
