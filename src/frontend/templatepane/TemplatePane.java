package frontend.templatepane;

import java.util.Collection;

import frontend.detailpane.DetailPane;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.AuthoringGameState;
import backend.util.ModifiableVoogaObject;
import backend.util.VoogaEntity;
import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import frontend.worldview.grid.Sprite;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * @author Faith Rodriguez
 * Created 3/29/2017
 */

public class TemplatePane extends BaseUIManager<Region>{

	VBox pane = new VBox();
	Collection<? extends ModifiableUnit> units;
	Collection<? extends ModifiableTerrain> terrains;
	DetailPane detailPane;
	WorldView worldView;
	

	public TemplatePane(AuthoringGameState gameState, DetailPane detailPaneIn, WorldView worldViewIn) {
		
	
		units = (Collection<? extends ModifiableUnit>) gameState.getTemplateByCategory(AuthoringGameState.UNIT).getAll();
		terrains = (Collection<? extends ModifiableTerrain>) gameState.getTemplateByCategory(AuthoringGameState.TERRAIN).getAll();
		detailPane = detailPaneIn;
		worldView = worldViewIn;
		
		createCollabsible("unit", units);
		createCollabsible("terrain", terrains);
	
	
	}
	
	private void createCollabsible(String label, Collection<? extends VoogaEntity> sprites) {
		TitledPane spritePane = new TitledPane();
		spritePane.setText(label);
		VBox content = createContent(sprites, label);
		spritePane.setContent(content);
		spritePane.setCollapsible(true);
		pane.getChildren().add(spritePane);
	}
	
	private VBox createContent(Collection<? extends VoogaEntity> sprites, String spriteType) {
		VBox contentPane = new VBox();
		for (VoogaEntity sprite: sprites) {
			VBox spriteContent = new VBox();
			// fix getName and getImage once communication sorted
			Text spriteName = new Text(sprite.getName());
			spriteContent.getChildren().add(spriteName);
			//Image tempImage = new Image(sprite.getImgPath());
		//	ImageView spriteImage = new ImageView(tempImage); 
			//spriteContent.getChildren().add(spriteImage);
			setOnDrag(spriteContent, sprite, spriteType);
			setOnClick(spriteContent, sprite, spriteType);
			contentPane.getChildren().add(spriteContent);
		}
		return contentPane;
	}
	
	private void setOnDrag(Node o, VoogaEntity sprite, String spriteType) {
		//ImageView spriteImage = new ImageView(getImage(o));   
		 o.setOnDragDetected(new EventHandler <MouseEvent>() {
	            public void handle(MouseEvent event) {
	               worldView.addSprite(sprite, spriteType);
	            }
	        });
	}
	
	private void setOnClick(Node o, VoogaEntity sprite, String spriteType) {
		o.setOnMouseClicked(new EventHandler <MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				detailPane.setContent(sprite, spriteType);	
			}		
		});
	}
	
	private void updatePane() {
		pane.getChildren().clear();
		createCollabsible("Terrain", terrains);
		createCollabsible("Unit", units);
	}
	
	public void updateUnits(Collection<ModifiableUnit> unitsIn){
		//sprites will (I am fairly certain) contain all available sprites, not just the new ones
		units = unitsIn;
		updatePane();
	} 
	
	public void updateTerrains(Collection<ModifiableTerrain> terrainsIn) {
		terrains = terrainsIn;
		updatePane();
	}
	
	@Override
	public Region getObject() {
		return pane;
	}

}
