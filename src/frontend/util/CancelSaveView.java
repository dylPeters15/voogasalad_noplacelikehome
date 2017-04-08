package frontend.util;

import frontend.BaseUIManager;
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
		createNew.setOnAction(e -> setOnCreateNew());
		
		cancel = new Button("Cancel");
		cancel.setOnAction(e -> setOnCancel());
		
		save = new Button("Save");
		save.setOnAction(e -> setOnSave());
		
		myCancelSaveView.getChildren().addAll(createNew, cancel, save);
	}

	void setOnCreateNew(){
		
	}
	
	void setOnCancel(){
		
	}
	
	void setOnSave(){
		
	}
	
	@Override
	public Region getObject() {
		return myCancelSaveView;
	}
}
