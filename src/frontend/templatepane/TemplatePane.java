package frontend.templatepane;

import java.util.Collection;

import backend.unit.UnitTemplate;
import frontend.BaseUIManager;
import frontend.sprites.Sprite;
import frontend.sprites.Terrain;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class TemplatePane extends BaseUIManager<Region>{

	Pane pane;
	Collection<UnitTemplate> units;
	Collection<Terrain> terrains;
	
	public TemplatePane(Collection<UnitTemplate> availableUnits) {
			//, Collection<Terrain> availableTerrains) {
		units = availableUnits;
		//terrains = availableTerrains;
		pane = new Pane();
		createCollabsible("Terrain", terrains);
		createCollabsible("Unit", units);
		
	}
	
	public void createCollabsible(String label, Collection sprites) {
		TitledPane spritePane = new TitledPane();
		spritePane.setText(label);
		spritePane.setContent(createContent(sprites));
		spritePane.setCollapsible(true);
		pane.getChildren().add(spritePane);
	}
	
	public VBox createContent(Collection sprites) {
		VBox contentPane = new VBox();
		for (Object sprite: sprites) {
			VBox spriteContent = new VBox();
			// fix getName and getImage once communication sorted
			Text spriteName = new Text(sprite.getName());
			spriteContent.getChildren().add(spriteName);
			ImageView spriteImage = new ImageView(sprite.getImage()); 
			spriteContent.getChildren().add(spriteImage);
			contentPane.getChildren().add(spriteContent);
		}
		return contentPane;
		
	}
	
	public void updateSprites(Collection<UnitTemplate> sprites){
		//TODO
		//sprites will (I am fairly certain) contain all available sprites, not just the new ones
	} 
	
	@Override
	public Region getObject() {
		return pane;
	}

}
