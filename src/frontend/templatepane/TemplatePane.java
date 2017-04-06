package frontend.templatepane;

import java.util.Collection;

import backend.unit.UnitTemplate;
import frontend.BaseUIManager;
import javafx.scene.layout.Region;

public class TemplatePane extends BaseUIManager<Region>{

	public TemplatePane(Collection<UnitTemplate> availableSprites) {
		SpriteAddPane spritepane = new SpriteAddPane();
		updateSprites(availableSprites);
	}
	
	public void updateSprites(Collection<UnitTemplate> sprites){
		//TODO
		//sprites will (I am fairly certain) contain all available sprites, not just the new ones
	} 
	
	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
