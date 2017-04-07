package frontend.util;

import java.util.function.UnaryOperator;

import frontend.BaseUIManager;
import javafx.scene.layout.Region;

public class NumericInputRow extends BaseUIManager<Region>{

	//can implement here
	 void setName(String name){
		 
	 }
	 String getName();
	 void setValue(Double value);
	 double getValue();
	 void setLabel(String label);
	 String getLabel();
	 void setOnValueEntry(UnaryOperator<Object> action);
	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
