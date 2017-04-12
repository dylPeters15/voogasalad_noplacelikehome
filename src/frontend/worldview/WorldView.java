package frontend.worldview;
import backend.grid.GameBoard;
import backend.util.VoogaEntity;
import frontend.util.BaseUIManager;
import frontend.worldview.grid.GridView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
public class WorldView extends BaseUIManager<Region> {


	private GridView myGrid;
	private BorderPane borderPane;
	public WorldView(GameBoard gameBoard) {
		initialize(gameBoard);
		update(gameBoard);
	}
	public void update(GameBoard grid) {
		myGrid.update(grid);
	}
	@Override
	public Region getObject() {
		return borderPane;
	}
	public void addSprite(VoogaEntity sprite, String spriteType) {
		// TODO add sprite to cell that it is dragged over
		// spriteType will either be "unit" or "terrain"
		System.out.print(sprite.getName());
	}
	private void initialize(GameBoard gameBoard) {
		borderPane = new BorderPane();
		myGrid = new GridView(gameBoard);
		borderPane.setCenter(myGrid.getObject());
		borderPane.setOnDragDetected(event -> System.out.println("WorldView drag drop" + event));
		borderPane.setOnMouseClicked(event -> System.out.println("WorldView click" + event));
	}
}
