package frontend.menubar;

import java.util.ResourceBundle;

import frontend.util.BaseUIManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

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

		load = new MenuItem(getLanguage().getValue().getString("Load"));
		save = new MenuItem(getLanguage().getValue().getString("Save"));
		quit = new MenuItem(getLanguage().getValue().getString("Quit"));
		setLanguage = new Menu(getLanguage().getValue().getString("SetLanguage"));
		setTheme = new Menu(getLanguage().getValue().getString("SetTheme"));
		helpItem = new MenuItem(getLanguage().getValue().getString("Help"));

		file.getItems().add(load);
		file.getItems().add(save);
		file.getItems().add(quit);

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

}
