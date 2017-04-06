package frontend.toolspane;

import java.util.Collection;

import backend.unit.UnitTemplate;
import frontend.BaseUIManager;
import frontend.sprites.Unit;
import javafx.scene.layout.Region;

public class ToolsPane extends BaseUIManager<Region>{

	
	public ToolsPane(Collection<UnitTemplate> availableSprites){
		updateSprites(availableSprites);
	}
	
	public void updateSprites(Collection<UnitTemplate> sprites){
		//TODO
	}
	
	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
