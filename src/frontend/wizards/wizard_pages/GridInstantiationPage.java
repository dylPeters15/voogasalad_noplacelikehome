package frontend.wizards.wizard_pages;

import java.util.Arrays;

import backend.cell.ModifiableCell;
import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GridInstantiationPage extends WizardPage {
	private static final String DEFAULT_TITLE = "Set Grid Attributes";
	private static final String DEFAULT_DESCRIPTION = "Choose the default cell type for the grid.";

	private VBox vbox;
	private ComboBox<String> cellChooser;

	public GridInstantiationPage() {
		this(DEFAULT_TITLE);
	}

	public GridInstantiationPage(String title) {
		this(title, DEFAULT_DESCRIPTION);
	}

	public GridInstantiationPage(String title, String description) {
		super(title, description);
		initialize();
	}

	@Override
	public Region getObject() {
		return vbox;
	}

	public ModifiableGameBoard getGameBoard() {
		System.out.println("Cell: " + getCell().toString());
		return new ModifiableGameBoard("Game Board Name", getCell(), 100, 100, BoundsHandler.FINITE_BOUNDS,
				"Game Board Description", "");
	}

	private ModifiableCell getCell() {
		for (ModifiableCell cell : ModifiableCell.getPredefined(ModifiableCell.class)) {
			if (cell.getName().equals(cellChooser.getValue())) {
				return cell;
			}
		}
		return null;
	}

	private void initialize() {
		vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		cellChooser = new ComboBox<String>(FXCollections.observableArrayList(
				Arrays.asList(ModifiableCell.BASIC_SQUARE_EMPTY.getName(), ModifiableCell.BASIC_SQUARE_FLAT.getName(),
						ModifiableCell.BASIC_SQUARE_FOREST.getName(), ModifiableCell.BASIC_SQUARE_FORTIFIED.getName(),
						ModifiableCell.BASIC_SQUARE_MOUNTAIN.getName(), ModifiableCell.BASIC_SQUARE_WATER.getName())));
		cellChooser.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				canNextWritable().setValue(!cellChooser.getValue().isEmpty());
			}
		});
		vbox.getChildren().add(cellChooser);
	}

}
