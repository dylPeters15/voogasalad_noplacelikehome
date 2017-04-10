package frontend.wizards.wizard_2_0.util;

import frontend.util.BaseUIManager;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class CancelSaveView extends BaseUIManager<Region>{
	
	private HBox myCancelSaveView;
	private Button createNew, cancel, save;
	
	public CancelSaveView(){
		initialize();
	}
	
	private void initialize() {
		myCancelSaveView = new HBox();
		createNew = new Button("Create New...");
		cancel = new Button("Cancel");
		save = new Button("Save");
		
		myCancelSaveView.getChildren().addAll(createNew, cancel, save);
	}

	public void setOnCreateNew(EventHandler<ActionEvent> eventHandler){
		createNew.setOnAction(eventHandler);
	}
	
	public void setOnCancel(EventHandler<ActionEvent> eventHandler){
		cancel.setOnAction(eventHandler);
	}
	
	public void setOnSave(EventHandler<ActionEvent> eventHandler){
		save.setOnAction(eventHandler);
	}
	
	@Override
	public Region getObject() {
		return myCancelSaveView;
	}
}
