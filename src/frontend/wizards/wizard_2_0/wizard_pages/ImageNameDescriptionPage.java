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

		imageView.fitWidthProperty().bind(imageBox.widthProperty());
		imageView.fitHeightProperty().bind(imageView.fitWidthProperty());

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

		// //for debugging:
		// hbox.setBackground(
		// new Background(new BackgroundFill(Paint.valueOf("ff3344"), new
		// CornerRadii(0), new Insets(0))));
		// nameAndDescription.setBackground(
		// new Background(new BackgroundFill(Paint.valueOf("aa3344"), new
		// CornerRadii(0), new Insets(0))));
		// imageBox.setBackground(
		// new Background(new BackgroundFill(Paint.valueOf("ff8844"), new
		// CornerRadii(0), new Insets(0))));
		// nameAndDescription.prefWidthProperty().bind(hbox.widthProperty().multiply(0.8));
		// nameAndDescription.prefHeightProperty().bind(hbox.heightProperty());
		// imageBox.prefWidthProperty().bind(hbox.widthProperty().subtract(nameAndDescription.widthProperty()));
		// imageBox.prefHeightProperty().bind(hbox.heightProperty());

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
