/**
 * 
 */
package frontend.wizard;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Stone Mathers
 *
 */
public class NameSetting extends SettingNode {

	private TextField inputField;

	public NameSetting() {  //TODO Make this constructor take in something to determine language
		super();
	}

	@Override
	public String getData() {
		return inputField.getCharacters().toString();
	}

	@Override
	protected HBox fillSettingBox(HBox box) {
		Text type = new Text(getResources().getString("Name"));
		inputField = new TextField(getResources().getString("NewGame"));
		box.getChildren().addAll(type, inputField);
		return box;
	}

}
