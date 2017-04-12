/**
 * A cell within the frontend GridDisplay.
 */
package frontend.worldview.grid;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.grid.GameBoard;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.Image;
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
	
	public void update(GameBoard grid){
		cellModel = grid.get(cellModel.getLocation());
		group.getChildren().clear();
		group.getChildren().add(polygon);
		Image polygonImage = new Image(cellModel.getImgPath());
		Paint polygonFill = new ImagePattern(polygonImage);
		polygon.setFill(polygonFill);
		cellModel.getOccupants().stream().forEach(unit -> {
			group.getChildren().add(new UnitView(unit).getObject());
		});
	}

	
	@Override
	public Parent getObject() {
		return group;
	}
	
}
