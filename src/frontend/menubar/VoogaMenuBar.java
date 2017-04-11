package frontend.menubar;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Observable;
import java.util.Observer;
import java.util.Optional;
import java.util.ResourceBundle;

import backend.util.AuthoringGameState;
import frontend.View;
import frontend.util.BaseUIManager;
import frontend.wizards.NewGameWizard;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class VoogaMenuBar extends BaseUIManager<MenuBar> {

	private MenuBar menuBar;
	private Menu file, language, theme, help, setLanguage, setTheme;
	private MenuItem load, save, quit, helpItem;

	public VoogaMenuBar() {
		menuBar = new MenuBar();
		populateMenuBar();
		getLanguage().addListener(new ChangeListener<ResourceBundle>() {
			@Override
			public void changed(ObservableValue<? extends ResourceBundle> observable, ResourceBundle oldLanguage,
					ResourceBundle newLanguage) {
				populateMenuBar();
			}
		});
	}

	@Override
	public MenuBar getObject() {
		return menuBar;
	}

	private void populateMenuBar() {
		menuBar.getMenus().clear();

		file = new Menu(getLanguage().getValue().getString("File"));
		language = new Menu(getLanguage().getValue().getString("Language"));
		theme = new Menu(getLanguage().getValue().getString("Theme"));
		help = new Menu(getLanguage().getValue().getString("Help"));

		menuBar.getMenus().add(file);
		menuBar.getMenus().add(language);
		menuBar.getMenus().add(theme);
		menuBar.getMenus().add(help);

		load = new MenuItem(getLanguage().getValue().getString("Load")){{
			setOnAction(e -> read());
		}};
		save = new MenuItem(getLanguage().getValue().getString("Save")){{
			setOnAction(e -> save());
		}};
		
		MenuItem newGameItem =  new MenuItem(getLanguage().getValue().getString("Create")){{
			setOnAction(e -> create());
		}};
		quit = new MenuItem(getLanguage().getValue().getString("Quit"));
		setLanguage = new Menu(getLanguage().getValue().getString("SetLanguage"));
		setTheme = new Menu(getLanguage().getValue().getString("SetTheme"));
		helpItem = new MenuItem(getLanguage().getValue().getString("Help"));

		file.getItems().add(load);
		file.getItems().add(save);
		file.getItems().add(quit);
		file.getItems().add(newGameItem);

		language.getItems().add(setLanguage);

		theme.getItems().add(setTheme);

		help.getItems().add(helpItem);

		getPossibleResourceBundleNamesAndResourceBundles().forEach((name, bundle) -> {
			MenuItem menuItem = new MenuItem(name);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getLanguage().setValue(bundle);
				}
			});
			setLanguage.getItems().add(menuItem);
		});

		getPossibleStyleSheetNamesAndFileNames().forEach((name, fileName) -> {
			MenuItem menuItem = new MenuItem(name);
			menuItem.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					getStyleSheet().setValue(fileName);
				}
			});
			setTheme.getItems().add(menuItem);
		});
		

	}
	private void save() {
		System.out.println("you can load and save files, but it won't do anything");
		try {
			System.out.println("here");
			FileChooser chooser = new FileChooser();
			chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
			Window ownerWindow = null;
			File file = chooser.showSaveDialog(ownerWindow);
			FileOutputStream fileOut =
					new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(new Object());  //need to pass in an object
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + file);

		} catch (IOException i) {
			i.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("No file selected");
			//			alert.setGraphic(graphic); //insert DuvallSalad
			alert.setHeaderText("Current game will not save");
			alert.setContentText("Would you like to try again?");
			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == new ButtonType("okay")) {
				save();
			} else {
				return;
			}
		}
	}
	
	private void read() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
		fileChooser.setTitle("Open Resource File");
		Window stage = null;
		File file = fileChooser.showOpenDialog(stage);

		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);

			//need to do something with the file

			in.close();
			fileIn.close();

			//this part probs doesn't work
//			Region pane = ui.getPrimaryPane();
//			((BorderPane) pane).setCenter(new View(null, null).getObject());

		} catch (IOException i) {
			i.printStackTrace();
			return;
		} catch (NullPointerException e) {
			e.printStackTrace();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("No file selected");
			
			/*
			 * failed attempt to set DuvallSalad as graphic
			 */
//			ImageView graphic = new ImageView(new Image("frontend/properties/DuvallSalad.png"));
//			graphic.setScaleX(.25);
//			graphic.setScaleY(.25);
//			
//			alert.setGraphic(graphic); //insert DuvallSalad


				alert.setHeaderText("Failed to load game");
			

			alert.setContentText("Would you like to try again?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == new ButtonType("okay")) {
				read();
			} else {
				return;

			}
		}
	}
	
	private void create() {
		NewGameWizard wiz = new NewGameWizard();
		wiz.addObserver(new Observer() {

			@Override
			public void update(Observable o, Object arg) {
				createGame((AuthoringGameState) arg, true);
//				stage.close();
			}
		});

	}
	private void createGame(AuthoringGameState state, boolean editable) {
		//Controller control = new CommunicationController();
		View view = new View(state,null);
		//myClient.setGameState(state);
		//control.setClient(myClient);
		//control.setGameState(state);
		view.setEditable(editable);
		//view.setController(control);
		//control.setView(view);
		Stage stage = new Stage();
		Scene scene = new Scene(view.getObject());
		stage.setScene(scene);
		stage.show();

	}

}
