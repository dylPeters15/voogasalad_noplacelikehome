package frontend.wizards.wizard_2_0.wizard_pages;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImageNameDescriptionPage extends WizardPage {
	private static final double IMAGE_VIEW_WIDTH = 100;
	private static final double IMAGE_VIEW_HEIGHT = 100;

	private HBox hbox;
	private Button uploadButton;
	private ImageView imageView;
	private TextField nameField;
	private TextArea descriptionField;

	public ImageNameDescriptionPage() {
		initialize();
	}

	@Override
	public Region getObject() {
		return hbox;
	}

	public Image getImage() {
		return imageView.getImage();
	}

	public String getName() {
		return nameField.getText();
	}

	public String getDescription() {
		return descriptionField.getText();
	}

	private void initialize() {
		hbox = new HBox();
		uploadButton = new Button("Upload Image");
		imageView = new ImageView();
		nameField = new TextField();
		nameField.setPromptText("Name");
		descriptionField = new TextArea();
		descriptionField.setPromptText("Description (optional)");

		VBox imageBox = new VBox();
		imageBox.getChildren().addAll(imageView, uploadButton);
		VBox nameAndDescription = new VBox();
		nameAndDescription.getChildren().addAll(nameField, descriptionField);
		hbox.getChildren().addAll(imageBox, nameAndDescription);

		uploadButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setImage();
			}
		});

		imageView.setFitWidth(IMAGE_VIEW_WIDTH);
		imageView.setFitHeight(IMAGE_VIEW_HEIGHT);

		imageView.imageProperty().addListener(new ChangeListener<Image>() {
			@Override
			public void changed(ObservableValue<? extends Image> observable, Image oldValue, Image newValue) {
				checkIfCanNext();
			}
		});
		nameField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				checkIfCanNext();
			}
		});
		descriptionField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				checkIfCanNext();
			}
		});

	}

	private void setImage() {
		FileChooser choose = new FileChooser();
		choose.setInitialDirectory(new File(System.getProperty("user.dir")));
		choose.getExtensionFilters().setAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File file = choose.showOpenDialog(null);
		if (file != null) {
			imageView.setImage(new Image(file.toURI().toString()));
		}
	}

	private void checkIfCanNext() {
		canNextWritable().setValue(imageView.getImage() != null && !nameField.getText().isEmpty());
	}

}
