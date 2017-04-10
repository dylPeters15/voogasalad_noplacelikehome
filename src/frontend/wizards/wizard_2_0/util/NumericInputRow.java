package frontend.wizards.wizard_2_0.util;

import java.util.function.UnaryOperator;

import frontend.util.BaseUIManager;
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

		myNumericalInputField = new TextField("0");

		myLabelField = new Label(label);

		myNumericInputRow.getChildren().addAll(new ImageView(image), myNameField, myNumericalInputField, myLabelField);
	}

	// can implement here
	void setName(String name) {

	}

	String getName() {
		return null;
	}

	void setValue(Double value) {

	}

	public double getValue() {
		return 0.0;
	}

	void setLabel(String label) {

	}

	String getLabel() {
		return null;
	}

	void setOnValueEntry(UnaryOperator<Object> action) {

	}

	@Override
	public Region getObject() {
		return myNumericInputRow;
	}

}
