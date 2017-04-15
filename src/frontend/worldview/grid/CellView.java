package frontend.worldview.grid;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.util.GameplayState;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.View;
import frontend.util.BaseUIManager;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import util.net.Modifier;

import java.util.function.Consumer;

/**
 * A Cell object is an immovable object on which Terrains and Units can be
 * placed.
 * <p>
 * The CellView is the UI representation of the backend Cell class. It allows
 * the user to interact with the Cell by dragging and dropping Units, Abilities,
 * or Terrains onto it, as well as selecting the items on it.
 * <p>
 * The CellView extends BaseUIManager so that it can change css stylesheets,
 * languages, and be updated by the controller.
 *
 * @author Dylan Peters
 */
public class CellView extends BaseUIManager<Parent> {
	private Cell cellModel;
	private Polygon polygon;
	private Group group;

	/**
	 * Creates a new CellView instance. Sets all values to default.
	 *
	 * @param cellModel  The Cell object that this CellView will visually represent.
	 * @param controller the controller object that this CellView will send information
	 *                   to when the user interacts with the CellView
	 */
	public CellView(Cell cellModel, Controller controller) {
		setController(controller);
		this.cellModel = cellModel;
		polygon = new Polygon();
		group = new Group();
		group.translateXProperty().bind(polygon.translateXProperty());
		group.translateYProperty().bind(polygon.translateYProperty());
		update(cellModel);
	}

	/**
	 * Sets the action that is performed when a cell is clicked.
	 *
	 * @param consumer consumer to execute when the cell is clicked
	 */
	public void setOnCellClick(Consumer<CellView> consumer) {
		polygon.setOnMouseClicked(event -> consumer.accept(this));
	}

	/**
	 * Adds a copy of the Sprite to the cell and sends the request to the
	 * controller.
	 *
	 * @param sprite sprite to copy and add to the cell
	 */
	public void add(VoogaEntity sprite) {
		CoordinateTuple location = cellModel.getLocation();
		Modifier<? extends GameplayState> toSend = game -> {
			game.getGrid().get(location).arrive(sprite.copy(), game);
			return game;
		};
		getController().sendModifier(toSend);
	}

	/**
	 * Returns an object that can be displayed to the user to show the Cell
	 */
	@Override
	public Parent getObject() {
		return group;
	}

	/**
	 * sets the x position of a polygon
	 *
	 * @param x
	 */
	public void setX(double x) {
		polygon.setLayoutX(x);
	}

	/**
	 * sets the y position of a polygon
	 *
	 * @param y
	 */
	public void setY(double y) {
		polygon.setLayoutY(y);
	}

	/**
	 * returns the polygon that serves as the shape of the cell
	 *
	 * @return polygon
	 */
	public Polygon getPolygon() {
		return polygon;
	}

	/**
	 * sets the group to contain a different polygon
	 *
	 * @param polygon Shape of cellview
	 *                an instance of a cell
	 */
	public void setPolygon(Polygon polygon) {
		group.getChildren().remove(polygon);
		this.polygon = polygon;
		update(cellModel);
	}

	/**
	 * passes a cell's location to the controller for the backend to use, and determine its validity
	 */
	public void update() {
		update(getController().getGameState().getGrid().get(cellModel.getLocation()));
	}

	/**
	 * sets a cell's visual shape to a polygon as described in the following method
	 *
	 * @param cellModel an instance of a cell
	 */
	private void update(Cell cellModel) {
		this.cellModel = cellModel;
		group.getChildren().clear();
		polygon.setFill(new ImagePattern(View.getImg(cellModel.getTerrain().getImgPath())));
		polygon.setStrokeWidth(10);
		group.getChildren().add(polygon);
		cellModel.getOccupants().forEach(unit -> {
			ImageView imageView = new ImageView(View.getImg(unit.getImgPath()));
			imageView.setFitWidth(75);
			imageView.setFitHeight(75);
			if (polygon.getPoints().size() >= 2) {
				imageView.setX(polygon.getPoints().get(0));
				imageView.setY(polygon.getPoints().get(1));
			}
			group.getChildren().add(imageView);
		});

	}

	/**
	 * Returns the coordinateTuple at which the CellView is displayed
	 *
	 * @return DisplayCoordinates at which the CellView is displayed.
	 */
	CoordinateTuple getCoordinateTuple() {
		return cellModel.getLocation();
	}
}

