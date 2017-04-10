package frontend.wizards.wizard_2_0.util;

import java.io.File;

import frontend.util.BaseUIManager;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class ImageNamePairView extends BaseUIManager<Region>{
	
	private BorderPane myBorderPane;
	private Button uploadImage;
	private Group imageWindow;
	private ImageView imageView;
	private ObjectProperty<Image> image;
	private TextField myTextField;
	
	public ImageNamePairView(){
		initialize();
	}
	
	private void initialize(){
		myBorderPane = new BorderPane();
		
		imageWindow = new Group();
		imageView = new ImageView();
		image = new SimpleObjectProperty<Image>();
		imageView.setImage(image.get());
		imageWindow.getChildren().add(imageView);
		//magic numbers here
		imageView.setFitHeight(100);
		imageView.setFitWidth(100);
		
		uploadImage = new Button("upload image...");
		uploadImage.setOnAction(e -> setImage());
		
		myTextField = new TextField();
		myTextField.setPromptText("name...");
		myTextField.setAlignment(Pos.CENTER_RIGHT);
		//myTextArea.setMaxWidth(100);
		
		VBox vBox = new VBox(imageWindow, uploadImage);
		myBorderPane.setLeft(vBox);
		myBorderPane.setRight(myTextField);
	}
	
	private void setImage() {
		FileChooser choose = new FileChooser();
		choose.setInitialDirectory(new File(System.getProperty("user.dir")));
		choose.getExtensionFilters().setAll(
				new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		File file = choose.showOpenDialog(null);
		if (file != null) {
			Image image = new Image(file.toURI().toString());
			this.image.setValue(image);
			imageView.setImage(this.image.get());
		}
	}

	@Override
	public Region getObject() {
		return myBorderPane;
	}
	
	public ObjectProperty<Image> getImage() {
		return image;
	}

}
