/**
 * 
 */
package frontend.wizard;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Stone Mathers
 * Created 4/5/2017
 */
public class AuthorSetting extends SettingNode {

	private TextField inputField;

	public AuthorSetting() {  //TODO Make this constructor take in something to determine language
		super();
	}

	@Override
	public String getData() {
		return inputField.getCharacters().toString();
	}

	@Override
	protected HBox fillSettingBox(HBox box) {
		Text type = new Text(getResources().getString("Author"));
		inputField = new TextField(getResources().getString("Me"));
		box.getChildren().addAll(type, inputField);
		return box;
	}

}
