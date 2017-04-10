package frontend.wizards.wizard_2_0.wizard_pages;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Region;

public class ImageNameDescriptionPage extends WizardPage {

	Button button;

	public ImageNameDescriptionPage() {
		button = new Button("ImageNameDescriptionPage");
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
