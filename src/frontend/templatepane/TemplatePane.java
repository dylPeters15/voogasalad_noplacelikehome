package frontend.templatepane;

import java.util.Collection;

import frontend.sprites.Sprite;
import frontend.sprites.Terrain;
import javafx.event.EventHandler;
import javafx.scene.Node;
import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import frontend.util.BaseUIManager;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * @author Faith Rodriguez
 * Created 3/29/2017
 */

public class TemplatePane extends BaseUIManager<Region>{

	Pane pane;
	Collection<? extends VoogaObject> units;
	Collection<? extends VoogaObject> terrains;
	

	public TemplatePane(Collection<VoogaObject> availableUnits, 
			Collection<VoogaObject> availableTerrains) {
	Collection<ModifiableUnit> units;
	Collection<ModifiableCell> terrains;
	

//	public TemplatePane(Collection<ModifiableUnit> availableUnits,
//			Collection<ModifiableCell> availableTerrains) {
//		units = availableUnits;
//		terrains = availableTerrains;
//		pane = new Pane();
//		
//	}
	
	private void createCollabsible(String label, Collection<? extends VoogaObject> sprites) {
		TitledPane spritePane = new TitledPane();
		spritePane.setText(label);
		VBox content = createContent(sprites);
		spritePane.setContent(content);
		spritePane.setCollapsible(true);
		pane.getChildren().add(spritePane);
	}
	
	private VBox createContent(Collection<? extends VoogaObject> sprites) {
		VBox contentPane = new VBox();
		for (VoogaObject sprite: sprites) {
			VBox spriteContent = new VBox();
			// fix getName and getImage once communication sorted
			Text spriteName = new Text(sprite.getName());
			spriteContent.getChildren().add(spriteName);
			Image tempImage = new Image(sprite.getImgPath());
			ImageView spriteImage = new ImageView(tempImage); 
			spriteContent.getChildren().add(spriteImage);
			contentPane.getChildren().add(spriteContent);
		}
		return contentPane;
	}
	
	private void setOnDrag(Node o) {
		//ImageView spriteImage = new ImageView(getImage(o));
		 o.setOnDragDetected(new EventHandler <MouseEvent>() {
	            public void handle(MouseEvent event) {
	                /* drag was detected, run drag-and-drop gesture*/
	                System.out.println("onDragDetected");
	                
	                /* create dragboard */
	                Dragboard db = (Dragboard) Dragboard.getSystemClipboard();
	                
	                /* put an image on dragboard */
	                ClipboardContent content = new ClipboardContent();
	                content.putString(o.toString());
	                db.setContent(content);
	                event.consume();
	            }
	        });
	}
	
	private void updatePane() {
		pane.getChildren().clear();
		createCollabsible("Terrain", terrains);
		createCollabsible("Unit", units);
	}
	
	public void updateSprites(Collection<ModifiableUnit> sprites){
		//TODO
		//sprites will (I am fairly certain) contain all available sprites, not just the new ones
		units = unitsIn;
		updatePane();
	} 
	
	public void updateTerrains(Collection<backend.cell.Terrain> terrainsIn) {
		terrains = terrainsIn;
		updatePane();
	}
	
	@Override
	public Region getObject() {
		return pane;
	}

}
