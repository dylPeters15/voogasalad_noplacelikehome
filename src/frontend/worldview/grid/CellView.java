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
		update(cellModel);
	}
	
	/**
	 * gets the tuple coordinate of the cell
	 * @return DisplayCoordinates at which the CellView is displayed.
	 */
	public CoordinateTuple getCoordinateTuple(){
		return cellModel.getLocation();
	}
	
	
	/**
	 * 
	 * @return 
	 */
	public double getX(){
		return polygon.getLayoutX();
	}
	
	/**
	 * 
	 * @param x
	 */
	public void setX(double x){
		polygon.setLayoutX(x);
	}
	
	/**
	 * 
	 * @return
	 */
	public double getY(){
		return polygon.getLayoutY();
	}
	
	/**
	 * 
	 * @param y
	 */
	public void setY(double y){
		polygon.setLayoutY(y);
	}
	
	/**
	 * 
	 * @return
	 */
	public Polygon getPolygon(){
		return polygon;
	}
	
	/**
	 * 
	 * @param cellModel
	 */
	public void update(Cell cellModel){
		cellModel = cellModel;
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
