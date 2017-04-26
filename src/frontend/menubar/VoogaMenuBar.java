/**
 *
 */
package frontend.menubar;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

import backend.grid.GameBoard;
import backend.player.Team;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.AuthoringClickHandler;
import frontend.GameplayClickHandler;
import frontend.View;
import frontend.factory.wizard.WizardFactory;
import frontend.startup.StartupScreen;
import frontend.util.BaseUIManager;
import frontend.util.ComponentFactory;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import polyglot.PolyglotException;

/**
 * @author Stone Mathers Created 4/18/2017
 */
public class VoogaMenuBar extends BaseUIManager<MenuBar> {
	private static final boolean SYSTEM_MENU_BAR = false;

	private Menu file, edit, language, theme, view, help, setLanguageItem, setThemeItem;
	private MenuItem loadItem, saveItem, homeScreenItem, quitItem, newUnitItem, newTerrainItem, newActiveAbilityItem,
			newTriggeredAbilityItem, newInteractionModifierItem, newGridItem, newTeamItem,
			conditionsPaneItem, templatePaneItem, detailsPaneItem, statsPaneItem, editModeItem, playModeItem, helpItem, aboutItem, undoItem;
	private ComponentFactory factory;
	private MenuBar menuBar;
	private View myView;

	public VoogaMenuBar(View view, Controller controller, boolean editable) {
		super(controller);
		myView = view;
		menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(SYSTEM_MENU_BAR);
		factory = new ComponentFactory();
		populateMenuBar();
	}

	public void setEditable(boolean editable) {
		loadItem.setDisable(!editable);
		edit.setDisable(!editable);
		conditionsPaneItem.setDisable(!editable);
		templatePaneItem.setDisable(!editable);
		playModeItem.setDisable(!editable);

		editModeItem.setDisable(editable);
	}

	protected void populateMenuBar() {
		initMenuItems();
		initMenus();
	}

	private void initMenuItems() {
		saveItem = factory.getMenuItem(getPolyglot().get("Save"), e -> save());
		loadItem = factory.getMenuItem(getPolyglot().get("Load"), e -> load());
		homeScreenItem = factory.getMenuItem(getPolyglot().get("HomeScreen"), e -> {

			StartupScreen su = new StartupScreen(myView.getStage(), StartupScreen.DEFAULT_WIDTH,
					StartupScreen.DEFAULT_HEIGHT);
			myView.getStage().setScene(new Scene(su.getPrimaryPane()));

		});
		quitItem = factory.getMenuItem(getPolyglot().get("Quit"), e -> System.exit(0));
		undoItem = factory.getMenuItem(getPolyglot().get("Undo"), e -> getController().undo());
		undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
		newUnitItem = factory.getMenuItem(getPolyglot().get("CreateNewUnit"), e -> create("unit"));
		newTerrainItem = factory.getMenuItem(getPolyglot().get("CreateNewTerrain"), e -> create("terrain"));
		newActiveAbilityItem = factory.getMenuItem(getPolyglot().get("CreateNewActiveAbility"),
				e -> create("activeability"));
		newTriggeredAbilityItem = factory.getMenuItem(getPolyglot().get("CreateNewTriggeredAbility"),
				e -> create("triggeredability"));
		newInteractionModifierItem = factory.getMenuItem(getPolyglot().get("CreateNewInteractionModifier"),
				e -> create("interactionmodifier"));
		newGridItem = factory.getMenuItem(getPolyglot().get("createNewGrid"), e -> {
			WizardFactory.newWizard("grid", getController().getAuthoringGameState()).addObserver((observer,object) -> {
				getController().setGrid((GameBoard)object);
			});
		});
		newTeamItem = factory.getMenuItem(getPolyglot().get("createNewTeam"), e -> {
			WizardFactory.newWizard("team", getController().getAuthoringGameState()).addObserver((observer,object) -> {
				getController().addTeamTemplates((Team)object);
			});
		});

		setLanguageItem = factory.getMenu(getPolyglot().get("SetLanguage"));
		try {
			getPolyglot().languages().stream().forEach(languageName -> {
				MenuItem menuItem = new MenuItem(languageName);
				menuItem.setOnAction(event -> {
					try {
						getPolyglot().setLanguage(languageName);
					} catch (PolyglotException e1) {
						menuItem.setVisible(false);
					}
				});
				setLanguageItem.getItems().add(menuItem);
			});
		} catch (PolyglotException e1) {
			setLanguageItem.setVisible(false);
		}

		setThemeItem = factory.getMenu(getPolyglot().get("SetTheme"));
		getPossibleStyleSheetNamesAndFileNames().forEach((name, fileName) -> {
			MenuItem menuItem = new MenuItem(name);
			menuItem.setOnAction(event -> getStyleSheet().setValue(fileName));
			setThemeItem.getItems().add(menuItem);
		});

		conditionsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideConditions"),
				e -> myView.toggleConditionsPane());
		templatePaneItem = factory.getMenuItem(getPolyglot().get("ShowHideTemplate"), e -> myView.toggleTemplatePane());
		detailsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideDetails"), e -> myView.toggleDetailsPane());
		statsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideStats"), e -> myView.toggleStatsPane());
		editModeItem = factory.getMenuItem(getPolyglot().get("EditMode"), e -> {
			myView.setClickHandler(new AuthoringClickHandler());
			getController().enterAuthoringMode();
		});
		playModeItem = factory.getMenuItem(getPolyglot().get("PlayMode"), e -> {
			myView.setClickHandler(new GameplayClickHandler());
			getController().enterGamePlayMode();
		});

		helpItem = factory.getMenuItem(getPolyglot().get("Help"), e -> {
			showBrowser("frontend/menubar/help.html");
		});
		aboutItem = factory.getMenuItem(getPolyglot().get("About"), e -> {
			showBrowser("frontend/menubar/about.html");
		});
	}

	private void showBrowser(String url) {
		try {
			Desktop.getDesktop().browse(getClass().getClassLoader().getResource(url).toURI());
		} catch (URISyntaxException | IOException e) {
			System.err.print("Invalid url: " + url);
			WebView browser = new WebView();
			WebEngine webEngine = browser.getEngine();
			Stage s = new Stage();
			Scene scene = new Scene(browser);
			url = this.getClass().getClassLoader().getResource(url).toExternalForm();
			webEngine.load(url);
			s.setScene(scene);
			s.show();
		}
	}

	private void initMenus() {
		file = factory.getMenu(getPolyglot().get("File"));
		file.getItems().add(loadItem);
		file.getItems().add(saveItem);
		file.getItems().add(homeScreenItem);
		file.getItems().add(quitItem);

		edit = factory.getMenu(getPolyglot().get("Edit"));
		edit.getItems().add(undoItem);
		edit.getItems().add(newUnitItem);
		edit.getItems().add(newTerrainItem);
		edit.getItems().add(newActiveAbilityItem);
//		edit.getItems().add(newTriggeredAbilityItem);
//		edit.getItems().add(newInteractionModifierItem);
		edit.getItems().add(newGridItem);
		edit.getItems().add(newTeamItem);

		language = factory.getMenu(getPolyglot().get("Language"));
		language.getItems().add(setLanguageItem);

		theme = factory.getMenu(getPolyglot().get("Theme"));
		theme.getItems().add(setThemeItem);

		view = factory.getMenu(getPolyglot().get("View"));
		view.getItems().add(conditionsPaneItem);
		view.getItems().add(templatePaneItem);
		view.getItems().add(detailsPaneItem);
		view.getItems().add(statsPaneItem);
		view.getItems().add(editModeItem);
		view.getItems().add(playModeItem);

		help = factory.getMenu(getPolyglot().get("Help"));
		help.getItems().add(helpItem);
		help.getItems().add(aboutItem);

		getObject().getMenus().add(file);
		getObject().getMenus().add(edit);
		getObject().getMenus().add(language);
		getObject().getMenus().add(theme);
		getObject().getMenus().add(view);
		getObject().getMenus().add(help);
	}

	private void save() {
		try {
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
			File file = chooser.showSaveDialog(null);
			getController().saveFile(Paths.get(file.getPath()));
		} catch (Exception i) {
			i.printStackTrace();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.titleProperty().bind(getPolyglot().get("NoFileSelected"));
			alert.headerTextProperty().bind(getPolyglot().get("CurrentGameWillNotSave"));
			alert.contentTextProperty().bind(getPolyglot().get("TryAgain"));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				save();
			}
		}
	}

	private void load() {
		try {
			FileChooser fileChooser = new FileChooser();
			fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
			getController().setGameState(
					getController().loadFile(Paths.get(fileChooser.showOpenDialog(null).getAbsolutePath())));
		} catch (IOException i) {
			i.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.titleProperty().bind(getPolyglot().get("NoFileSelected"));
			alert.headerTextProperty().bind(getPolyglot().get("FailedToLoad"));
			alert.contentTextProperty().bind(getPolyglot().get("TryAgain"));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				load();
			}
		}
	}

	private void create(String categoryName) {
		WizardFactory.newWizard(categoryName, getController().getAuthoringGameState()).addObserver((wizard, template) -> getController().getAuthoringGameState().getTemplateByCategory(categoryName).addAll((VoogaEntity) template));
	}

	@Override
	public MenuBar getObject() {
		return menuBar;
	}

	public View getView() {
		return myView;

	}

	@Override
	public void update() {
		setEditable(getController().isAuthoringMode());
	}

}
