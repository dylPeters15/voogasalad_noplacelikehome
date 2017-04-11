/**
 * 
 */
package frontend.wizards.new_game_wizard;

import frontend.util.GameType;
import frontend.wizards.wizard_2_0.util.SettingNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

/**
 * @author Stone Mathers
 * Created 4/5/2017
 */
public class GameTypeSetting extends SettingNode {

	private ComboBox<String> inputBox;

	public GameTypeSetting() { //TODO Make this constructor take in something to determine language
		super();
	}


	@Override
	public String getData() {
		return inputBox.getValue();
	}


	@Override
	protected HBox fillSettingBox(HBox box) {
		Text type = new Text(getResources().getString("GameType"));

		
		ObservableList<String> options = FXCollections.observableArrayList(); //TODO: Find a way to do this with a stream
		for(GameType shape: GameType.values()){
			options.add(shape.toString());
		}
		
		inputBox = new ComboBox<String>(options);

		box.getChildren().addAll(type, inputBox);
		return box;
	}

}
