package frontend.wizards.wizard_2_0.wizard_pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class TerrainMovePointPage extends WizardPage {

	Button button;

	public TerrainMovePointPage() {
		button = new Button("TerrainMovePointPage");
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				canNextWritable().setValue(true);
			}
		});
	}

	@Override
	public Region getObject() {
		return button;
	}

}
