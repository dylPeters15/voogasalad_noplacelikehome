package frontend.wizards.wizard_2_0.wizard_pages;

import java.util.Arrays;

import backend.cell.ModifiableCell;
import backend.grid.BoundsHandler;
import backend.grid.ModifiableGameBoard;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GridInstantiationPage extends WizardPage {

	private VBox vbox;
	private ComboBox<String> cellChooser;

	public GridInstantiationPage() {
		initialize();
	}

	@Override
	public Region getObject() {
		return vbox;
	}

	public ModifiableGameBoard getGameBoard() {
		System.out.println("Cell: " + getCell().toString());
		return new ModifiableGameBoard("Game Board Name", getCell(), 100, 100,
				BoundsHandler.FINITE_BOUNDS, "Game Board Description", "");
	}
	
	private ModifiableCell getCell(){
		for (ModifiableCell cell : ModifiableCell.getPredefined(ModifiableCell.class)){
			if (cell.getName().equals(cellChooser.getValue())){
				return cell;
			}
		}
		return null;
	}

	private void initialize() {
		vbox = new VBox();
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
