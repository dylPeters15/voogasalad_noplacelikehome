package frontend.detailpane;

import java.util.Collection;
import java.util.Map;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.unit.ModifiableUnit;
import backend.util.VoogaEntity;
import frontend.util.BaseUIManager;
import frontend.worldview.WorldView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class DetailPane extends BaseUIManager<Region>{

	VBox pane = new VBox();
	Label spriteInfo;
	String content = "";
	WorldView worldView;
	
//	public DetailPane(WorldView worldView) {
//		this.worldView = worldView;
//		pane.setFillWidth(true);
//		Text title = new Text("Sprite Details");
//		pane.getChildren().add(title);
//		spriteInfo = new Label(content);
//		setLabel();
//		clearContent();
//		
//	}
	
	public void setContent(VoogaEntity sprite, String spriteType) {
		clearContent();
		addString("Name", sprite.getName());
		addString("Description", sprite.getDescription());
		Label newSpriteInfo;
		if (spriteType.equals("unit")) {
			newSpriteInfo = new Label(setUnitContent((ModifiableUnit) sprite));			
		}
		else {
			newSpriteInfo = new Label(setTerrainContent((ModifiableTerrain) sprite));
		}
		pane.getChildren().remove(spriteInfo);
		spriteInfo = newSpriteInfo;
		setLabel();
		Button addButton = new Button("Add");
		pane.getChildren().add(addButton);
		addButton.setOnAction(event -> worldView.setOnCellClick(cellView -> {
			cellView.add(sprite);
		}));
		
	}

	private void setLabel() {
		pane.getChildren().add(spriteInfo);		
		spriteInfo.setWrapText(true);
	}
	
	private String setUnitContent(ModifiableUnit unit) {
		addMoveCosts(unit);
		addCollection("Active Abilities", unit.getActiveAbilities());
		addCollection("DefensiveModifiers", unit.getDefensiveModifiers());
		addString("Hit Points", unit.getHitPoints().toString());
		addString("Move Points", unit.getMovePoints().toString());
		addString("Legal Moves", unit.getMovePattern().toString());
		return content;
	}
	
	private String setTerrainContent(ModifiableTerrain terrain) {
		addString("Default Move Cost", ((Integer) terrain.getDefaultMoveCost()).toString());
		addString("Default Defense Modifier", ((Integer) terrain.getDefaultMoveCost()).toString());
		return content;
	}
	
	
	private void addCollection(String label, Collection<? extends VoogaEntity> collection) {
		content += label + ": \n";
		for (VoogaEntity AA: collection) {
			content += AA.getName() + "\n";
		}
	}
	
	private void addString(String label, String value) {
		content += label + ": " + value + "\n";
	}
	
	private void addMoveCosts(ModifiableUnit unit) {
		content+= "Terrain Move Costs: \n";
		Map<Terrain, Integer> MC = unit.getTerrainMoveCosts();
		for (VoogaEntity t: MC.keySet()) {
			content = content + t.getName() + ": " + MC.get(t).toString() + "\n";
		}
	}
	
	private void clearContent() {
		content = "";
	}
	
	@Override
	public Region getObject() {
		return pane;
	}

}
