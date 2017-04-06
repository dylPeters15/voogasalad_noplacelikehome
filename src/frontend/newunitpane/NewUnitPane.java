package frontend.newunitpane;

import frontend.BaseUIManager;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

/**
 * 
 * @author Dylan Peters
 *
 */
public class NewUnitPane extends BaseUIManager<Region> {
	private AnchorPane anchorPane;

	public NewUnitPane() {
		initialize();
	}

	@Override
	public Region getObject() {
		return anchorPane;
	}

	protected void submit() {

	}

	private void initialize() {
		anchorPane = new AnchorPane();
	}

}
