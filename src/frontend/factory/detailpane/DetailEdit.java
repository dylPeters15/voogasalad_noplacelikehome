package frontend.factory.detailpane;

import backend.cell.Terrain;
import backend.grid.CoordinateTuple;
import backend.grid.GridPattern;
import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.unit.properties.ModifiableUnitStat;
import backend.unit.properties.UnitStat;
import backend.util.AuthoringGameState;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.util.BaseUIManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.*;
import java.util.stream.Collectors;

public class DetailEdit extends BaseUIManager {

	private Pane pane;
	private Unit unit;
	Terrain terrain;
	private GridPane sceneView;
	private Map<Terrain, TextField> moveCosts;
	private Map<String, List<TextField>> unitStats;
	private String movePattern;
	private Stage myStage;
	private int rows = 0;


	public DetailEdit(VoogaEntity sprite, String spriteType, Controller controller) {
		super(controller);
		myStage = new Stage();
		pane = new Pane();
		if (spriteType.equals("Unit")) {
			unit = (Unit) sprite;
			createUnitScene();
		} else {
			//createTerrainScene();
		}
		Scene edittingScene = new Scene(pane);
		myStage.setScene(edittingScene);
		myStage.show();
	}

	private void createUnitScene() {
		sceneView = new GridPane();
		Label label = addText("Move Costs");
		sceneView.addRow(rows++, label);
		Collection<Terrain> terrains = (Collection<Terrain>) getController().getAuthoringGameState().getTemplateByCategory("terrain").getAll();
		moveCosts = new HashMap<>();
		for (Terrain t : terrains) {
			TextField feature = createUserInput(t.getFormattedName(), ((Integer) unit.getMoveCostByTerrain(t)).toString()).get(0);
			moveCosts.put(t, feature);
		}
		sceneView.addRow(rows++, new Label("Stats"));
		unitStats = new HashMap<>();
		for (UnitStat stat : unit.getUnitStats()) {
			unitStats.put(stat.getName(), createUserInput(stat.getName(), stat.getMinValue(), stat.getCurrentValue(), stat.getMaxValue()));
		}
		ObservableList<String> options = FXCollections.observableArrayList(getController()
				.getAuthoringGameState()
				.getTemplateByCategory("Grid Pattern").getAll().stream()
				.map(GridPattern.class::cast)
				.filter(e -> e.getShape().equals(getController().getShape()))
				.map(VoogaEntity::getName)
				.collect(Collectors.toList()));

		createDropDown("Move Pattern", options);
		pane.getChildren().add(sceneView);
		createUnitSubmitBtn();
	}

	private List<TextField> createUserInput(String feature, Object... values) {
		Label label = addText(feature);
		List<Node> featureInfo = new ArrayList<>();
		featureInfo.add(label);
		List<TextField> featureValues = Arrays.stream(values).map(e -> new TextField(e.toString())).collect(Collectors.toList());
		featureInfo.addAll(featureValues);
		sceneView.addRow(rows++, featureInfo.toArray(new Node[0]));
		return featureValues;
	}

	@SuppressWarnings("unchecked")
	private ComboBox createDropDown(String feature, ObservableList<String> choices) {
//		HBox featureInfo = new HBox();
//		featureInfo.setPadding(new Insets(5, 5, 5, 5));
		Label label = addText(feature);
		ComboBox options = new ComboBox(choices);
		options.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> movePattern = (String) newValue);
//		featureInfo.getChildren().addAll(label, options);
		sceneView.addRow(rows++, label, options);
		return options;
	}

	private Label addText(String feature) {
		Label label = new Label();
		label.setText(feature + ": ");
		return label;
	}

	@SuppressWarnings("unchecked")
	private void createUnitSubmitBtn() {
		Button submit = new Button("Submit Changes");
		sceneView.addRow(rows++, submit);
		Map<Terrain, Integer> finalCosts = new HashMap<>();
		submit.setOnAction(e -> {
			for (Terrain key : moveCosts.keySet()) {
				finalCosts.put(key, Integer.parseInt(moveCosts.get(key).getText()));
			}
//			modifiable.setTerrainMoveCosts(finalCosts);
//			((ModifiableUnitStat) modifiable.getHitPoints()).setMaxValue(Double.parseDouble(unitStats[0].getText()));
//			((ModifiableUnitStat) modifiable.getMovePoints()).setMaxValue(Integer.parseInt(unitStats[1].getText()));
//			if (movePattern!= null) {
//				modifiable.setMovePattern((GridPattern) getController()
//					.getAuthoringGameState()
//					.getTemplateByCategory("Grid Pattern")
//					.getByName(movePattern));
			myStage.close();

			Map<String, List<? extends Number>> stats = unitStats.entrySet().stream()
					.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().stream()
							.map(field -> {
								try {
									return Integer.parseInt(field.getText());
								} catch (NumberFormatException nfe) {
									return Double.parseDouble(field.getText());
								}
							}).collect(Collectors.toList())));

			CoordinateTuple unitLocation = unit.getLocation();
			String unitName = unit.getName();
			;
			String moveP = movePattern;
			getController().sendModifier((AuthoringGameState state) -> {
				ModifiableUnit newUnit = (ModifiableUnit) state.getGrid().get(unitLocation).getOccupantByName(unitName);
				newUnit.setTerrainMoveCosts(finalCosts);
				stats.forEach((names, values) -> ((ModifiableUnitStat) newUnit
						.getUnitStat(names))
						.setMinValue(values.get(0))
						.setCurrentValue(values.get(1))
						.setMaxValue(values.get(2)));
				if (moveP != null) {
					newUnit.setMovePattern((GridPattern) state
							.getTemplateByCategory("Grid Pattern")
							.getByName(moveP));
				}
				return state;
			});

		});
	}

	@Override
	public Object getObject() {
		return pane;
	}
}
