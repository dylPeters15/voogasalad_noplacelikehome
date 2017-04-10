package frontend.wizards.wizard_2_0.util;

import frontend.util.BaseUIManager;
import javafx.beans.property.BooleanProperty;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class SelectableInputRow extends BaseUIManager<Region> {

	HBox hbox;
	CheckBox checkbox;
	ImageView icon;
	Label name;

	public SelectableInputRow() {
		this(null, null);
	}

	public SelectableInputRow(Image image, String name) {
		checkbox = new CheckBox();
		icon = new ImageView();
		this.name = new Label();
		hbox = new HBox();
		setImage(image);
		setName(name);
		hbox.getChildren().addAll(checkbox,icon,this.name);
	}

	public void setImage(Image image) {
		icon.setImage(image);
	}

	public Image getImage() {
		return icon.getImage();
	}

	public void setName(String name) {
		this.name.setText(name);
	}

	public String getName() {
		return name.getText();
	}

	@Override
	public Region getObject() {
		return hbox;
	}
	
	public BooleanProperty getSelectedProperty(){
		return checkbox.selectedProperty();
	}
	
	public boolean getSelected(){
		return checkbox.isSelected();
	}
	
	public void setSelected(boolean selected){
		checkbox.setSelected(selected);
	}

}
