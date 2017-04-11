package frontend.detailpane;

import java.util.Collection;
import java.util.Map;

import backend.cell.ModifiableTerrain;
import backend.cell.Terrain;
import backend.unit.ModifiableUnit;
import backend.unit.properties.ActiveAbility;
import backend.util.VoogaEntity;
import frontend.util.BaseUIManager;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class DetailPane extends BaseUIManager<Region>{

	VBox pane;
	Label spriteInfo;
	String content = "";
	
	public DetailPane() {
		pane = new VBox();
		pane.setFillWidth(true);
		spriteInfo = new Label(content);
		spriteInfo.setWrapText(true);
		pane.getChildren().add(spriteInfo);
		
	}
	
	public void setUnitContent(ModifiableUnit unit) {
		setGeneralContent(unit);
		addMoveCosts(unit);
		addCollection("Active Abilities", unit.getActiveAbilities());
		addCollection("DefensiveModifiers", unit.getDefensiveModifiers());
		addString("Hit Points", unit.getHitPoints().toString());
		addString("Move Points", unit.getMovePoints().toString());
		addString("Legal Moves", unit.getMovePattern().toString());
	}
	
	public void setTerrainContent(ModifiableTerrain terrain) {
		setGeneralContent(terrain);
		addString("Default Move Cost", ((Integer) terrain.getDefaultMoveCost()).toString());
		addString("Default Defense Modifier", ((Integer) terrain.getDefaultMoveCost()).toString());
	}
	
	private void setGeneralContent(VoogaEntity sprite) {
		clearContent();
		addString("Name", sprite.getName());
		addString("Description", sprite.getDescription());
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
