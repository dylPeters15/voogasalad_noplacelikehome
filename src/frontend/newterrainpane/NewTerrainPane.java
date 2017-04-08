package frontend.newterrainpane;

import frontend.BaseUIManager;
import frontend.util.CancelSaveView;
import frontend.util.ImageNamePairView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;

public class NewTerrainPane extends BaseUIManager<Region>{
	
	private AnchorPane myAnchorPane;
	private CancelSaveView myCancelSaveView;
	private ImageNamePairView myImageNamePairView;
	
	public NewTerrainPane(){
		initialize();
	}

	private void initialize(){
		myAnchorPane = new AnchorPane();
		
		myCancelSaveView = new CancelSaveView();
		
		myImageNamePairView = new ImageNamePairView();
		
		AnchorPane.setTopAnchor(myImageNamePairView.getObject(), 0.0);
		AnchorPane.setLeftAnchor(myImageNamePairView.getObject(), 0.0);
		AnchorPane.setBottomAnchor(myCancelSaveView.getObject(), 0.0);
		AnchorPane.setRightAnchor(myCancelSaveView.getObject(), 0.0);
		
		myAnchorPane.getChildren().addAll(myImageNamePairView.getObject(), myCancelSaveView.getObject());
	}
	
	@Override
	public Region getObject() {
		return myAnchorPane;
	}

}
