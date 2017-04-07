package frontend.util;

import java.io.Serializable;

import frontend.BaseUIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;
import util.net.Request;

public class ButtonManager extends BaseUIManager<Region> {
	private Button button;
	private int index;

	public ButtonManager(String string) {
		button = new Button(string);
		index = 0;
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				getRequests().add(new Request<Serializable>("test" + index++));
			}
		});
	}

	@Override
	public Region getObject() {
		return button;
	}

}