/**
 * A cell within the frontend GridDisplay.
 */
package frontend.worldview.grid;

import java.util.function.Consumer;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.util.VoogaEntity;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public class CellView extends BaseUIManager<Parent>{
	
	Cell cellModel;
	Polygon polygon;
	Group group;
	
	public CellView(Cell cellModel){
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
		cellModel.getOccupants().stream().forEach(unit -> {
			group.getChildren().add(new UnitView(unit).getObject());
		});
	}

	public void setOnCellClick(Consumer<CellView> consumer){
		polygon.setOnMouseClicked(event -> consumer.accept(this));
	}
	
	public void add(VoogaEntity sprite){
		ImageView imageView = new ImageView(new Image(sprite.getImgPath()));
		imageView.setX(0);
		imageView.setY(0);
		group.getChildren().add(imageView);
	}
	
	@Override
	public Parent getObject() {
		return group;
	}
	
}
