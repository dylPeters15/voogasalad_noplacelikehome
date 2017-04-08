package frontend.util;

import java.util.ResourceBundle;

import frontend.BaseUIManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

/**
 * A placeholder class to test and demonstrate the BaseUIManager
 * 
 * @author dylanpeters
 *
 */
public class ButtonManager extends BaseUIManager<Region> {
	private Button button;

	public ButtonManager() {
		button = new Button();
		setButtonText();

		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldLanguage,
					ResourceBundle newLanguage) {
				setButtonText();
			}
		});
	}

	private void setButtonText() {
		button.setText(getLanguage().getValue().getString("Play"));
	}

	@Override
	public Region getObject() {
		return button;
	}

}