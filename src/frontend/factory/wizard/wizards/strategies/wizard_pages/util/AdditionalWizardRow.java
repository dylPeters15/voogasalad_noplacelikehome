package frontend.factory.wizard.wizards.strategies.wizard_pages.util;

import frontend.factory.wizard.Wizard;
import frontend.util.BaseUIManager;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class AdditionalWizardRow<T> extends BaseUIManager<Region>{
	
	private HBox hbox;
	private CheckBox checkbox;
	private Label label;
	private Button button;
	private ObjectProperty<T> objectProperty;
	
	public AdditionalWizardRow(String description, Wizard<T> wizard){
		this(new StringBinding() {

			@Override
			protected String computeValue() {
				return description;
			}
			
		}, wizard);
	}
	
	public AdditionalWizardRow(StringBinding description, Wizard<T> wizard){
		initialize(description,wizard);
	}

	@Override
	public Region getNode() {
		return hbox;
	}
	
	public ReadOnlyObjectProperty<T> getObjectProperty(){
		return objectProperty;
	}
	
	private void initialize(StringBinding description,Wizard<T> wizard){
		hbox = new HBox();
		checkbox = new CheckBox();
		checkbox.setDisable(true);
		label = new Label();
		label.textProperty().bind(description);
		objectProperty = new SimpleObjectProperty<>(null);
		button = new Button();
		button.textProperty().bind(getPolyglot().get("CreateNew"));
		button.setOnAction(event -> {
			wizard.addObserver((observable,object) -> {
				objectProperty.setValue((T)object);
				checkbox.setSelected(true);
			});
		});
		hbox.getChildren().addAll(checkbox,label,button);
	}

}
