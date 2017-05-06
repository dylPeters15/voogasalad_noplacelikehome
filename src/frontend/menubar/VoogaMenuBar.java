package frontend.menubar;

import backend.grid.GameBoard;
import backend.player.Team;
import backend.util.VoogaEntity;
import controller.Controller;
import frontend.View;
import frontend.factory.wizard.Wizard;
import frontend.factory.wizard.WizardFactory;
import frontend.startup.StartupScreen;
import frontend.util.BaseUIManager;
import frontend.util.ComponentFactory;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
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
import util.polyglot.PolyglotException;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * A VoogaMenuBar object creates a MenuBar with the functionality necessary for
 * both the Authoring and Gameplay environments. The only difference found
 * between Authoring and Gameplay is the functionality provided, which is
 * accomplished by enabling/disabling environment-specific buttons. VoogaMenuBar
 * extends BaseUIManage so that it can be updated, displayed, styled, and
 * translated.
 * 
 * @author Stone Mathers Created 4/18/2017
 */
public class VoogaMenuBar extends BaseUIManager<MenuBar> {
	private static final boolean SYSTEM_MENU_BAR = false;

	private Menu file, edit, language, theme, view, help, setLanguageItem, setThemeItem, play, team;
	private MenuItem loadItem, saveItem, homeScreenItem, quitItem, newUnitItem, newTerrainItem, newActiveAbilityItem,
			newGridItem, newTeamItem, conditionsPaneItem, templatePaneItem, detailsPaneItem, statsPaneItem,
			editModeItem, playModeItem, helpItem, aboutItem, undoItem, joinTeam;
	private ComponentFactory factory;
	private MenuBar menuBar;
	private View myView;

	/**
	 * Constructs a VoogaMenuBar given the passed parameters.
	 * 
	 * @param view
	 *            View that is communicated with for the functionality of
	 *            certain buttons.
	 * @param controller
	 *            Controller used to communicate with the Model.
	 * @param editable
	 *            true if editing functionality is provided, false if not.
	 */
	public VoogaMenuBar(View view, Controller controller, boolean editable) {
		super(controller);
		myView = view;
		menuBar = new MenuBar();
		menuBar.setUseSystemMenuBar(SYSTEM_MENU_BAR);
		factory = new ComponentFactory();
		populateMenuBar();
		getStyleSheet().setValue(getPossibleStyleSheetNamesAndFileNames().get("NoTheme"));
	}

	/**
	 * Set whether or not this menuBar provides the functionality for editing.
	 * 
	 * @param editable
	 *            true if editing functionality is provided, false if not.
	 */
	public void setEditable(boolean editable) {
		loadItem.setDisable(!editable);
		edit.setDisable(!editable);
		conditionsPaneItem.setDisable(!editable);
		templatePaneItem.setDisable(!editable);
		playModeItem.setDisable(!editable);

		editModeItem.setDisable(editable);
		joinTeam.setDisable(!editable);
		newTeamItem.setDisable(!editable);
	}

	@Override
	public MenuBar getNode() {
		return menuBar;
	}

	/**
	 * Get the View object passed to the VoogaMenuBar at instantiation.
	 * 
	 * @return View
	 */
	public View getView() {
		return myView;

	}

	@Override
	public void update() {
		setEditable(getController().isAuthoringMode());
	}

	private void populateMenuBar() {
		initMenuItems();
		initMenus();
	}

	private void initMenuItems() {
		saveItem = factory.getMenuItem(getPolyglot().get("Save"), e -> save());
		saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN));
		loadItem = factory.getMenuItem(getPolyglot().get("Load"), e -> load());
		homeScreenItem = factory.getMenuItem(getPolyglot().get("HomeScreen"), e -> {

			myView.getStage().setScene(new Scene(new StartupScreen(myView.getStage()).getNode()));

		});
		homeScreenItem.setAccelerator(
				new KeyCodeCombination(KeyCode.H, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
		quitItem = factory.getMenuItem(getPolyglot().get("Quit"), e -> System.exit(0));
		quitItem.setAccelerator(new KeyCodeCombination(KeyCode.Q, KeyCombination.SHORTCUT_DOWN));
		undoItem = factory.getMenuItem(getPolyglot().get("Undo"), e -> getController().undo());
		undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.SHORTCUT_DOWN));
		newUnitItem = factory.getMenuItem(getPolyglot().get("CreateNewUnit"), e -> create("unit"));
		newTerrainItem = factory.getMenuItem(getPolyglot().get("CreateNewTerrain"), e -> create("terrain"));
		newActiveAbilityItem = factory.getMenuItem(getPolyglot().get("CreateNewActiveAbility"),
				e -> create("activeability"));
		newGridItem = factory.getMenuItem(getPolyglot().get("createNewGrid"), e -> {
			WizardFactory.newWizard("grid", getController(), getPolyglot().getLanguage(), getStyleSheet().getValue())
					.addObserver((observer, object) -> {
				getController().setGrid((GameBoard) object);
			});
		});
		newTeamItem = factory.getMenuItem(getPolyglot().get("createNewTeam"), e -> {
			WizardFactory.newWizard("team", getController(), getPolyglot().getLanguage(), getStyleSheet().getValue())
					.addObserver((observer, object) -> {
				getController().addTeams((Team) object);
			});
		});
		joinTeam = factory.getMenuItem(getPolyglot().get("JoinTeam"), e -> myView.joinTeam());
		setLanguageItem = factory.getMenu(getPolyglot().get("SetLanguage"));
		try {
			getPolyglot().languages().stream().forEach(languageName -> {
				MenuItem menuItem = new MenuItem(languageName);
				menuItem.setOnAction(event -> {
					getPolyglot().setLanguage(languageName);
				});
				setLanguageItem.getItems().add(menuItem);
			});
		} catch (PolyglotException e1) {
			setLanguageItem.setVisible(false);
		}
		setThemeItem = factory.getMenu(getPolyglot().get("SetTheme"));
		getPossibleStyleSheetNamesAndFileNames().forEach((name, fileName) -> {
			MenuItem menuItem = new MenuItem();
			menuItem.textProperty().bind(getPolyglot().get(name));
			menuItem.setOnAction(event -> getStyleSheet().setValue(fileName));
			setThemeItem.getItems().add(menuItem);
		});
		conditionsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideConditions"),
				e -> myView.toggleConditionsPane());
		templatePaneItem = factory.getMenuItem(getPolyglot().get("ShowHideTemplate"), e -> myView.toggleTemplatePane());
		detailsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideDetails"), e -> myView.toggleDetailsPane());
		statsPaneItem = factory.getMenuItem(getPolyglot().get("ShowHideStats"), e -> myView.toggleStatsPane());
		editModeItem = factory.getMenuItem(getPolyglot().get("EditMode"), e -> getController().enterAuthoringMode());
		editModeItem.setAccelerator(new KeyCodeCombination(KeyCode.E, KeyCombination.SHORTCUT_DOWN));
		playModeItem = factory.getMenuItem(getPolyglot().get("PlayMode"), e -> enterGamePlayMode());
		playModeItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN));

		helpItem = factory.getMenuItem(getPolyglot().get("Help"),
				e -> showBrowser(getResourceBundle().getString("HelpPage")));
		aboutItem = factory.getMenuItem(getPolyglot().get("About"),
				e -> showBrowser(getResourceBundle().getString("AboutPage")));

	}

	/**
	 * @author Faith Rodriguez
	 * Browser for loading help and about screens from menu bar
	 * @param url
	 * A string value that represents the url of the page to be loaded
	 */
	
	private void showBrowser(String url) {
		try {
			Desktop.getDesktop().browse(getClass().getClassLoader().getResource(url).toURI());
		} catch (URISyntaxException | IOException e) {
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
		edit.getItems().add(newGridItem);

		language = factory.getMenu(getPolyglot().get("Language"));
		language.getItems().add(setLanguageItem);

		theme = factory.getMenu(getPolyglot().get("Theme"));
		theme.getItems().add(setThemeItem);

		view = factory.getMenu(getPolyglot().get("View"));
		view.getItems().add(conditionsPaneItem);
		view.getItems().add(templatePaneItem);
		view.getItems().add(detailsPaneItem);
		view.getItems().add(statsPaneItem);
		play = factory.getMenu(getPolyglot().get("Play"));
		play.getItems().add(editModeItem);
		play.getItems().add(playModeItem);

		team = factory.getMenu(getPolyglot().get("Teams"));
		team.getItems().add(joinTeam);
		team.getItems().add(newTeamItem);

		help = factory.getMenu(getPolyglot().get("Help"));
		help.getItems().add(helpItem);
		help.getItems().add(aboutItem);
		getNode().getMenus().addAll(file, edit, view, play, language, theme, team, help);
	}

	private void save() {
		try {
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
			chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
			File file = chooser.showSaveDialog(null);
			getController().saveState(Paths.get(file.getPath()));
		} catch (Exception i) {
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
			getController()
					.setGameState(getController().load(Paths.get(fileChooser.showOpenDialog(null).getAbsolutePath())));
		} catch (NullPointerException | IOException e) {
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
		Wizard<?> wiz = WizardFactory.newWizard(categoryName, getController(), getPolyglot().getLanguage(),
				getStyleSheet().getValue());
		wiz.getPolyglot().setLanguage(getPolyglot().getLanguage());
		wiz.addObserver((wizard, template) -> getController().getAuthoringGameState()
				.getTemplateByCategory(categoryName).addAll((VoogaEntity) template));
	}

	private void enterGamePlayMode() {
		if (getController().getTeamTemplates().size() == 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.titleProperty().bind(getPolyglot().get("NoTeamsTitle"));
			alert.headerTextProperty().bind(getPolyglot().get("NoTeamsHeader"));
			alert.contentTextProperty().bind(getPolyglot().get("NoTeamsContent"));
			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				newTeamItem.fire();
			}
		} else {
			getController().enterGamePlayMode();
		}
	}

}