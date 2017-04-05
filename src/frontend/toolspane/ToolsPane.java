package frontend.toolspane;

import java.util.Collection;

import backend.unit.Unit;
import frontend.BaseUIManager;
import javafx.scene.layout.Region;

public class ToolsPane extends BaseUIManager<Region>{

	
	public ToolsPane(Collection<Unit> availableSprites){
		updateSprites(availableSprites);
	}
	
	public void updateSprites(Collection<Unit> sprites){
		//TODO
	}
	
	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
