/**
 * A cell within the frontend GridDisplay.
 */
package frontend.worldview.grid;

import java.util.function.Consumer;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.unit.Unit;
import backend.util.GameplayState;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import util.net.Modifier;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class CellView extends BaseUIManager<Parent>{
	
	Cell cellModel;
	Polygon polygon;
	Group group;
	
	public CellView(Cell cellModel, Controller controller){
		setController(controller);
		this.cellModel = cellModel;
		polygon = new Polygon();
		group = new Group();
		update(cellModel);
	}
	
	/**
	 * @return DisplayCoordinates at which the CellView is displayed.
	 */
	public CoordinateTuple getCoordinateTuple(){
		return cellModel.getLocation();
	}
	
	public double getX(){
		return polygon.getLayoutX();
	}
	
	public void setX(double x){
		polygon.setLayoutX(x);
	}
	
	public double getY(){
		return polygon.getLayoutY();
	}
	
	public void setY(double y){
		polygon.setLayoutY(y);
	}
	
	public Polygon getPolygon(){
		return polygon;
	}
	
	public void setPolygon(Polygon polygon){
		if (group.getChildren().contains(polygon)){
			group.getChildren().remove(polygon);
		}
		this.polygon = polygon;
		update(cellModel);
	}
	
	public void update(Cell cellModel){
		this.cellModel = cellModel;
		group.getChildren().clear();
		group.getChildren().add(polygon);
		Image polygonImage = new Image(cellModel.getTerrain().getImgPath());
		Paint polygonFill = new ImagePattern(polygonImage);
		polygon.setFill(polygonFill);
		polygon.setStrokeWidth(10);
		cellModel.getOccupants().stream().forEach(unit -> {
			group.getChildren().add(new UnitView(unit).getObject());
			System.out.println("added unit");
		});
	}

	public void setOnCellClick(Consumer<CellView> consumer){
		polygon.setOnMouseClicked(event -> consumer.accept(this));
	}
	
	public void add(VoogaEntity sprite){
		System.out.println("Creating modifier");
		Modifier<GameplayState> toSend = game -> {
			game.getGrid().get(cellModel.getLocation()).arrive((Unit) sprite.copy(), game);
			System.out.println("Executing modifier");
			return game;
		};
		getController().sendModifier(toSend);
	}
	
	@Override
	public Parent getObject() {
		return group;
	}
	
}
