package frontend.wizards.wizard_2_0.util;

import java.util.function.UnaryOperator;

import frontend.util.BaseUIManager;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class NumericInputRow extends BaseUIManager<Region>{
	
	private HBox myNumericInputRow;
	private TextField myNumericalInputField, myNameField, myLabelField;
	
	public NumericInputRow(String name, String label){
		initialize(name, label);
	}
	
	private void initialize(String name, String label){
		myNumericInputRow = new HBox();
		
		myNameField = new TextField(name);
		myNameField.setEditable(false);
		
		myNumericalInputField = new TextField("0");
		
		myLabelField = new TextField(label);
		myLabelField.setEditable(false);
		
		myNumericInputRow.getChildren().addAll(myNameField, myNumericalInputField, myLabelField);
	}
	
	//can implement here
	 void setName(String name){
		 
	 }
	 
	 String getName(){
		 return null;
	 }
	 
	 void setValue(Double value){
		 
	 }
	 
	 double getValue(){
		 return 0.0;
	 }
	 
	 void setLabel(String label){
		 
	 }
	 
	 String getLabel(){
		 return null;
	 }
	 
	 void setOnValueEntry(UnaryOperator<Object> action){
		 
	 }
	
	 @Override
	public Region getObject() {
		return myNumericInputRow;
	}
	
}
