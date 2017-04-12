package frontend.wizards.wizard_pages;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import backend.cell.Cell;
import backend.cell.ModifiableCell;
import backend.util.AuthoringGameState;
import frontend.wizards.util.NumericInputRow;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class GridInstantiationPage extends WizardPage {
	private static final String DEFAULT_TITLE = "Set Grid Attributes";
	private static final String DEFAULT_DESCRIPTION = "Choose the default cell type for the grid.";

	private VBox vbox;
	private NumericInputRow rows, cols;
	private ComboBox<String> cellChooser;
	private Map<String, ModifiableCell> cellMap;

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

	public int getRows() {
		return rows.getValue();
	}

	public int getCols() {
		return cols.getValue();
	}

	public Cell getTemplateCell() {
		return cellMap.get(cellChooser.getValue());
	}

	private void initialize() {
		vbox = new VBox();
		vbox.setAlignment(Pos.CENTER);
		rows = new NumericInputRow(null, "Number of Grid Rows: ", "");
		cols = new NumericInputRow(null, "Number of Grid Columns: ", "");
		Collection<ModifiableCell> cells = AuthoringGameState.getPredefined(ModifiableCell.class);
		cellMap = new HashMap<>();
		cells.stream().forEach(cell -> cellMap.put(cell.getName(), cell));

		cellChooser = new ComboBox<String>(FXCollections.observableArrayList(cellMap.keySet()));
		cellChooser.setOnAction(event -> checkCanNext());
		rows.setOnAction(event -> checkCanNext());
		cols.setOnAction(event -> checkCanNext());
		vbox.getChildren().addAll(rows.getObject(), cols.getObject(), cellChooser);
	}

	private void checkCanNext() {
		canNextWritable().setValue(!cellChooser.getValue().isEmpty() && rows.getValue() != 0 && cols.getValue() != 0);
	}

}
