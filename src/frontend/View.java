/**
 * The View is responsible for displaying the data held within the Model. It is also responsible
 * for providing an interface with which the user can interact to change the GameState.
 * View does not do so directly, instead sending modifiers and getting updated through the Controller.
 * <p>
 * The View organizes the GUI, instantiating and placing all of the necessary panes. Requests are made through a
 * Modifier class, a wrapper holding lambda expressions. The panes are expected to extend BaseUIManager, so that
 * View can access getObject(), the pane can send Modifiers, and the pane can update itself. They are all given
 * an instance of Controller to do so, specifically by adding itself as an observer (so that it’s update method
 * is called whenever a change in the GameState occurs) and calling a method from the controller to send Modifiers.
 * <p>
 * The View also has a boolean editable that determines which mode it is in, edit or play. This determines which
 * GUI components are made available to the user. The methods that instantiate the GUI should use this boolean to
 * determine which components are displayed.
 *
 * @author Stone Mathers, Dylan Peters
 * Created 4/3/2017
 */
package frontend;

import backend.util.AuthoringGameState;
import backend.util.GameplayState;
import controller.Controller;
import frontend.factory.abilitypane.AbilityPane;
import frontend.factory.conditionspane.ConditionsPaneFactory;
import frontend.factory.detailpane.DetailPaneFactory;
import frontend.factory.templatepane.TemplatePaneFactory;
import frontend.factory.worldview.MinimapPane;
import frontend.factory.worldview.WorldViewFactory;
import frontend.interfaces.conditionspane.ConditionsPaneExternal;
import frontend.interfaces.detailpane.DetailPaneExternal;
import frontend.interfaces.templatepane.TemplatePaneExternal;
import frontend.interfaces.worldview.WorldViewExternal;
import frontend.menubar.VoogaMenuBar;
import javafx.geometry.Orientation;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.SplitPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import polyglot.PolyglotException;

import java.util.HashMap;
import java.util.Map;

public class View extends ClickableUIComponent<Region> {
	private static final Map<String, Image> IMAGE_CACHE = new HashMap<>();
	private Stage myStage;

	static {
		IMAGE_CACHE.put("", new Image("resources/images/transparent.png"));
	}

	private boolean editable;
	private SplitPane outerSplitPane;
	private VoogaMenuBar menuBar;
	private WorldViewExternal worldView;
	private DetailPaneExternal detailPane;
	private AbilityPane abilityPane;
	private TemplatePaneExternal tempPane;
	private ConditionsPaneExternal conditionsPane;

	public View(Controller controller) {
		this(controller, new Stage(), true);
	}

	public View(Controller controller, Stage stage) {
		this(controller, stage, true);
	}

	public View(Controller controller, Stage stage, boolean editable) {
		super(controller, new AuthoringClickHandler());
		myStage = stage;
		this.editable = editable;
		placePanes();
		setEditable(editable);
		getStyleSheet().setValue(getPossibleStyleSheetNamesAndFileNames().get("Default Theme"));
	}

	/**
	 * @param editable True if this View can be switched into "edit" mode, false if
	 *                 it cannot.
	 */
	public void setEditable(boolean editable) {
		this.editable = editable;  //TODO do through controller
		if (editable) {
			enterAuthorMode();
			menuBar.setEditable(true);
		} else {
			enterPlayMode();
			menuBar.setEditable(false);
		}
	}

	public void toggleConditionsPane() {
//		if(myBorder.getLeft() == null){
//			//myBorder.setLeft(rulesPane.getObject());				//TODO For when rules pane is created
//		} else {
//			myBorder.setLeft(null);
//		}
	}

	public void toggleTemplatePane() {
		//TODO
//		if(myBorder.getRight() == null){
//			myBorder.setRight(tempPane.getObject());
//		} else {
//			myBorder.setRight(null);
//		}
	}

	public void toggleDetailsPane() {
		//TODO
//		if(myBorder.getBottom() == null){
//			myBorder.setBottom(detailPane.getObject());
//		} else {
//			myBorder.setBottom(null);
//		}
	}

	public void toggleStatsPane() {
		//TODO
	}

	@Override
	public Region getObject() {
		return outerSplitPane;
	}

	/**
	 * @return The Stage that the View is being displayed on
	 */
	public Stage getStage() {
		return myStage;
	}

	/**
	 * Sets the GameState that the View accesses its data from.
	 *
	 * @param newGameState GameplayState that the View will now access its data from
	 */
	public void setGameState(GameplayState newGameState) {
		getController().setGameState(newGameState);
	}

	/**
	 * Displays an Alert to the user containing the given message.
	 *
	 * @param s String alert message
	 */
	public void sendAlert(String s) {
		Alert myAlert;
		myAlert = new Alert(AlertType.INFORMATION);
		myAlert.titleProperty().bind(getPolyglot().get("informationdialog"));
		myAlert.setHeaderText(null);
		myAlert.setContentText(s);
		myAlert.showAndWait();
	}

	private void placePanes() {
		initPanes();
		SplitPane innerSplitPane = new SplitPane(conditionsPane.getObject(), worldView.getObject(), new VBox(new MinimapPane(worldView.getGridPane(), getController()).getObject(), tempPane.getObject()));
		innerSplitPane.setDividerPositions(0, 1);
		innerSplitPane.setOrientation(Orientation.HORIZONTAL);
		SplitPane bottomPane = new SplitPane(new SplitPane(detailPane.getObject(), abilityPane.getObject()));
		bottomPane.setDividerPosition(0, .8);
		outerSplitPane = new SplitPane(menuBar.getObject(), innerSplitPane, bottomPane);
		outerSplitPane.setDividerPositions(0, 1);
		outerSplitPane.setOrientation(Orientation.VERTICAL);
		SplitPane.setResizableWithParent(menuBar.getObject(), false);        //In case user is on Windows and MenuBar is in the View
		getClickHandler().setAbilityPane(abilityPane);
		getClickHandler().setDetailPane(detailPane);
	}

	/**
	 * Initializes all panes in the GUI and makes View a listener to all
	 * necessary panes.
	 */
	private void initPanes() {
		menuBar = new VoogaMenuBar(this, getController(), editable);
		menuBar.getStyleSheet().addListener((observable, oldValue, newValue) -> {
			getObject().getStylesheets().clear();
			getObject().getStylesheets().add(newValue);
		});
		worldView = WorldViewFactory.newWorldView(getController(), getClickHandler());
		detailPane = DetailPaneFactory.newDetailPane(getClickHandler());
		abilityPane = new AbilityPane(getController(), getClickHandler());
		tempPane = TemplatePaneFactory.newTemplatePane(getController(),
				getClickHandler());
		conditionsPane = ConditionsPaneFactory.newConditionsPane(getController(), getClickHandler());
		menuBar.getPolyglot().setOnLanguageChange(event -> {
			System.out.println("Languagechange detcted in menu bar");
			try {
				worldView.getPolyglot().setLanguage(menuBar.getPolyglot().getLanguage());
				detailPane.getPolyglot().setLanguage(menuBar.getPolyglot().getLanguage());
				abilityPane.getPolyglot().setLanguage(menuBar.getPolyglot().getLanguage());
				tempPane.getPolyglot().setLanguage(menuBar.getPolyglot().getLanguage());
				conditionsPane.getPolyglot().setLanguage(menuBar.getPolyglot().getLanguage());
				System.out.println("Language change applied in view");
			} catch (PolyglotException e) {
				//TODO display dialog that we could not change language
				e.printStackTrace();
			}
		});
	}

	/**
	 * Performs all necessary actions to convert the View into development mode.
	 * If the View is already in development mode, then nothing visually
	 * changes.
	 */
	private void enterAuthorMode() {
		// addSidePanes();
	}

	/**
	 * Performs all necessary actions to convert the View into play mode. If the
	 * View is already in play mode, then nothing visually changes.
	 */
	private void enterPlayMode() {

		// removeSidePanes();
	}

	@Override
	public void setClickHandler(ClickHandler clickHandler) {
		super.setClickHandler(clickHandler);
		abilityPane.setClickHandler(clickHandler);
		worldView.setClickHandler(clickHandler);
		detailPane.setClickHandler(clickHandler);
	}

	public static Image getImg(String imgPath) {
		if (!IMAGE_CACHE.containsKey(imgPath)) {
			try {
				IMAGE_CACHE.put(imgPath, new Image(imgPath));
			} catch (Exception e) {
				System.out.println("Error opening image: " + imgPath);
				IMAGE_CACHE.put(imgPath, IMAGE_CACHE.get(""));
			}
		}
		return IMAGE_CACHE.get(imgPath);
	}

	@Override
	public void update() {
		this.setEditable(getController().getGameState().isAuthoringMode());
	}
}