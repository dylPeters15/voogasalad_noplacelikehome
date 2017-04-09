package frontend.wizards.new_voogaobject_wizard;

import backend.unit.UnitTemplate;
import backend.util.GameState;
import frontend.util.BaseUIManager;
import frontend.wizards.new_voogaobject_wizard.util.AbilitiesAdder;
import frontend.wizards.new_voogaobject_wizard.util.CancelSaveView;
import frontend.wizards.new_voogaobject_wizard.util.ImageNamePairView;
import frontend.wizards.new_voogaobject_wizard.util.TerrainMovePointView;
import frontend.wizards.new_voogaobject_wizard.util.VerticalTableInputView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import util.net.Modifier;

/**
 * 
 * @author Dylan Peters
 *
 */
public class NewUnitPane extends BaseUIManager<Region> {
	private static final double TOP_INSET = 10.0;
	private static final double BOTTOM_INSET = 10.0;
	private static final double LEFT_INSET = 10.0;
	private static final double RIGHT_INSET = 10.0;

	private AnchorPane anchorPane;

	private ImageNamePairView imageNamePairView;
	private VerticalTableInputView movePointView;
	private CancelSaveView cancelSaveView;
	private AbilitiesAdder abilitiesAdder;

	public NewUnitPane(GameState gameState) {
		initialize(gameState);
	}

	@Override
	public Region getObject() {
		return anchorPane;
	}

	protected void submit() {
//		imageNamePairView.getRequests().passTo(getRequests());
//		movePointView.getRequests().passTo(getRequests());
//		abilitiesAdder.getRequests().passTo(getRequests());
		Modifier<GameState> modifier = gameState -> {gameState.getUnitTemplates().add(new UnitTemplate("NewUnitName"));
		return gameState;
		};
		getRequests().add(modifier);
		System.out.println(getRequests().toString());
	}

	protected void cancel() {
		
	}

	protected void newNewUnitPane() {

	}

	private void initialize(GameState gameState) {

		anchorPane = new AnchorPane();
		imageNamePairView = new ImageNamePairView();
		movePointView = new TerrainMovePointView(gameState);
		cancelSaveView = new CancelSaveView();
		abilitiesAdder = new AbilitiesAdder(gameState);
		
		cancelSaveView.setOnSave(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				submit();
			}
		});
		
		cancelSaveView.setOnCancel(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				cancel();
			}
		});
		
		cancelSaveView.setOnCreateNew(new EventHandler<ActionEvent>(){
			@Override
			public void handle(ActionEvent event) {
				newNewUnitPane();
			}
		});
		
		AnchorPane.setTopAnchor(imageNamePairView.getObject(), TOP_INSET);
		AnchorPane.setLeftAnchor(imageNamePairView.getObject(), LEFT_INSET);
		AnchorPane.setTopAnchor(movePointView.getObject(), TOP_INSET);
		AnchorPane.setRightAnchor(movePointView.getObject(), RIGHT_INSET);
		AnchorPane.setBottomAnchor(abilitiesAdder.getObject(), BOTTOM_INSET);
		AnchorPane.setLeftAnchor(abilitiesAdder.getObject(), LEFT_INSET);
		AnchorPane.setBottomAnchor(cancelSaveView.getObject(), BOTTOM_INSET);
		AnchorPane.setRightAnchor(cancelSaveView.getObject(), RIGHT_INSET);

		setBounds();

		anchorPane.getChildren().addAll(imageNamePairView.getObject(), movePointView.getObject(),
				cancelSaveView.getObject(), abilitiesAdder.getObject());

	}

	void setBounds() {
		imageNamePairView.getObject().prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.3));
		imageNamePairView.getObject().prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.2));
		movePointView.getObject().prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.475));
		movePointView.getObject().prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.85));
		abilitiesAdder.getObject().prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.475));
		abilitiesAdder.getObject().prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.65));
		cancelSaveView.getObject().prefWidthProperty().bind(anchorPane.widthProperty().multiply(0.3));
		cancelSaveView.getObject().prefHeightProperty().bind(anchorPane.heightProperty().multiply(0.1));
	}

}
