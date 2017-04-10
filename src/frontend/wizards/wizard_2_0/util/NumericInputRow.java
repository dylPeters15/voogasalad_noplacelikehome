package frontend.wizards.wizard_2_0.util;

import frontend.util.BaseUIManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class NumericInputRow extends BaseUIManager<Region> {

	private HBox myNumericInputRow;
	private TextField myNumericalInputField;
	Label myNameField, myLabelField;

	public NumericInputRow(Image image, String name, String label) {
		initialize(image, name, label);
	}

	private void initialize(Image image, String name, String label) {
		myNumericInputRow = new HBox();

		myNameField = new Label(name);

		myNumericalInputField = new TextField("0.0");
		myNumericalInputField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				try {
					if (!newValue.isEmpty()) {
						Double.parseDouble(newValue);
					}
				} catch (Exception e) {
					myNumericalInputField.setText("0.0");
				}
			}
		});

		myLabelField = new Label(label);

		myNumericInputRow.getChildren().addAll(new ImageView(image), myNameField, myNumericalInputField, myLabelField);
	}

	void setName(String name) {
		myNameField.setText(name);
	}

	String getName() {
		return myNameField.getText();
	}

	void setValue(Double value) {
		myNumericalInputField.setText(value.toString());
	}

	public double getValue() {
		return myNumericalInputField.getText().isEmpty() ? 0.0 : Double.parseDouble(myNumericalInputField.getText());
	}

	void setLabel(String label) {
		myLabelField.setText(label);
	}

	String getLabel() {
		return myLabelField.getText();
	}

	@Override
	public Region getObject() {
		return myNumericInputRow;
	}

}
