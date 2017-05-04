package frontend.factory.worldview;

import backend.cell.Cell;
import backend.grid.CoordinateTuple;
import backend.unit.Unit;
import backend.unit.properties.UnitStat;
import controller.Controller;
import frontend.ClickHandler;
import frontend.ClickableUIComponent;
import frontend.interfaces.worldview.CellViewExternal;
import frontend.interfaces.worldview.UnitViewExternal;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The CellView class creates a visual representation of the back end Cell
 * object.
 * 
 * @author Dylan Peters
 *
 */
class CellView extends ClickableUIComponent<Group> implements CellViewExternal {
	private static final Paint CELL_OUTLINE = Color.BLACK;
	private static final double CELL_STROKE = 1.5;
	private final CoordinateTuple cellLocation;
	private final Polygon polygon;
	private final Polygon polygonMask;
	private final Group updateGroup;
	private final ContextMenu contextMenu;
	private final Map<String, UnitView> unitViews;

	private String terrainCache;
	private int unitCount;

	/**
	 * Creates a new CellView instance. Sets all values to default.
	 *
	 * @param cellLocation
	 *            The Cell object that this CellView will visually represent.
	 * @param controller
	 *            the controller object that this CellView will send information
	 * @param clickHandler
	 *            The ClickHandler Object that determines the program's behavior
	 *            when the cell is clicked.
	 * @param polygonCellView
	 *            the polygon that will be manipulated to represent the Cell.
	 */
	public CellView(CoordinateTuple cellLocation, Controller controller, ClickHandler clickHandler,
			Polygon polygonCellView) {
		super(controller, clickHandler);
		this.unitViews = new HashMap<>();
		this.cellLocation = cellLocation;
		contextMenu = new ContextMenu();
		updateGroup = new Group();
		this.polygon = polygonCellView;
		polygon.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED,
				event -> contextMenu.show(polygon, event.getScreenX(), event.getScreenY()));
		polygon.setOnMouseClicked(event -> handleClick(event, null));
		polygon.setOnMouseDragReleased(event -> {
			handleClick(event, null);
			getClickHandler().cancel();
		});
		polygon.setStroke(CELL_OUTLINE);
		polygon.setStrokeWidth(CELL_STROKE);
		polygonMask = new Polygon(polygon.getPoints().stream().mapToDouble(e -> e).toArray());
		polygonMask.setFill(Color.TRANSPARENT);
		polygonMask.layoutXProperty().bind(polygon.layoutXProperty());
		polygonMask.layoutYProperty().bind(polygon.layoutYProperty());
		polygonMask.setMouseTransparent(true);
		getPolyglot().addLanguageChangeHandler(change -> {
			setContextMenu();
			installToolTips();
		});
		this.unitCount = -2;
		update();
	}

	/**
	 * Returns an object that can be displayed to the user to show the Cell
	 */
	@Override
	public Group getNode() {
		return new Group(polygon, polygonMask, updateGroup);
	}

	/**
	 * passes a cell's location to the controller for the backend to use, and
	 * determine its validity
	 */
	@Override
	public void update() {
		if (Objects.nonNull(getEntity())) {
			if (unitCount < 0 || !getEntity().getTerrain().getImgPath().equals(terrainCache)) {
				if (getController().getGrid().getImgPath().length() < 1) {
					polygon.setFill(new ImagePattern(getImg(getEntity().getTerrain().getImgPath())));
				} else {
					polygon.setFill(Color.TRANSPARENT);
				}
				terrainCache = getEntity().getTerrain().getImgPath();
			}
			if (unitCount != getEntity().getOccupants().size() || !getEntity().getOccupants().stream()
					.map(Unit::getName).collect(Collectors.toSet()).equals(unitViews.keySet())) {
				unitCount = unitCount < 0 ? getEntity().getOccupants().size() : unitCount + 1;
				unitViews.values().forEach(e -> getController().removeListener(e));
				unitViews.clear();
				updateGroup.getChildren().clear();
				getEntity().getOccupants().forEach(unit -> {
					UnitView unitView = new UnitView(unit.getName(), unit.getLocation(),
							polygon.boundsInParentProperty(), getController(), getClickHandler());
					unitViews.put(unit.getName(), unitView);
					updateGroup.getChildren().add(unitView.getNode());
				});
				unitViews.values().forEach(unitView -> unitView.getNode().setOnMouseClicked(event -> {
					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (getEntity().getOccupants().size() <= 1) {
							unitView.handleClick(event, null);
						} else {
							handleClick(event, null);
						}
					} else {
						contextMenu.show(polygon, event.getScreenX(), event.getScreenY());
					}
				}));
				if (getEntity().getOccupants().size() > 1) {
					Text numOccupants = new Text(getEntity().getOccupants().size() + "");
					numOccupants.setFont(new Font(13));
					numOccupants.setLayoutX(
							polygon.getBoundsInParent().getMaxX() - numOccupants.getLayoutBounds().getWidth() - 4);
					numOccupants.setLayoutY(
							polygon.getBoundsInParent().getMaxY() - numOccupants.getLayoutBounds().getHeight() + 9);
					updateGroup.getChildren().add(numOccupants);
					numOccupants.toFront();
				}
				setContextMenu();
			}
			updateGroup.toFront();
			installToolTips();
		} else {
			getController().removeListener(this);
		}

	}

	@Override
	public CoordinateTuple getLocation() {
		return cellLocation;
	}

	@Override
	public Cell getEntity() {
		return getController().getCell(cellLocation);
	}

	@Override
	public void setClickHandler(ClickHandler clickHandler) {
		super.setClickHandler(clickHandler);
		unitViews.values().forEach(e -> e.setClickHandler(clickHandler));
	}

	@Override
	public String toString() {
		return getEntity().toString();
	}

	@Override
	public void darken() {
		polygonMask.setFill(new Color(0, 0, 0, .7));
	}

	@Override
	public void unDarken() {
		polygonMask.setFill(Color.TRANSPARENT);
	}

	@Override
	public void playSound() {
		playMedia(getEntity().getTerrain().getSoundPath());
	}

	private void installToolTips() {
		unitViews.values().forEach(uv -> Tooltip.install(uv.getNode(), new Tooltip(getToolTipString(uv))));
	}

	private void setContextMenu() {
		contextMenu.getItems().clear();
		getEntity().getOccupants().forEach(e -> {
			MenuItem item = new MenuItem(e.getName());
			contextMenu.getItems().add(item);
			item.setOnAction(event -> unitViews.get(e.getName()).handleClick(event, null));
		});
	}

	private String getToolTipString(UnitViewExternal uv) {
		String hp = "";
		UnitStat<Double> hitpoints = uv.getEntity().getHitPoints();
		if (Objects.nonNull(hitpoints)) {
			String formatString = "\n" + getPolyglot().get("Hitpoints").getValueSafe() + ": %2.0f/%2.0f";
			hp = String.format(formatString, hitpoints.getCurrentValue(), hitpoints.getMaxValue());
		}
		String formatString = getPolyglot().get("Name").getValueSafe() + ": %s\n"
				+ getPolyglot().get("Position").getValueSafe() + ": %s%s";
		return String.format(formatString, uv.getUnitName(), uv.getEntity().getLocation().toString(), hp);
	}
}
