package frontend;

import java.util.ResourceBundle;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class FileMenu extends VBox {
	private ResourceBundle SelectionProperties = ResourceBundle.getBundle("properties/Selections.properties");
	//path is probably wrong
	Pane selectionPane =  new Pane();
	
	MenuBar menuBar;
	public FileMenu(){
		this.initMenu();
	}
	
	private void initMenu(){
		MenuItem playItem = new MenuItem(SelectionProperties.getString("Play")){{
        	setOnAction(e -> play());
        }};
        
		MenuItem saveItem = new MenuItem(SelectionProperties.getString("Save")){{
            setOnAction(e -> save());
        }};

        MenuItem loadItem = new MenuItem(SelectionProperties.getString("Edit/Load")){{
            setOnAction(e -> editOrLoad());
        }};
        
        MenuItem newGameItem = new MenuItem(SelectionProperties.getString("Create")){{
            setOnAction(e -> newGame());
        }};
        
        Menu helpMenu = new Menu(SelectionProperties.getString("Help")){{
        	setOnAction(e -> System.out.println("haven't written help yet"));
        }};
        
        Menu fileMenu = new Menu(SelectionProperties.getString("File")) {{
        	getItems().addAll(playItem, saveItem, loadItem, newGameItem);
        }};
        
        this.menuBar = new MenuBar() {{
            getMenus().addAll(helpMenu, fileMenu);
        }};
        
        this.getChildren().addAll(menuBar);
        
	}
	
	private void play(){
		System.out.println("you clicked Play, which means you are ahead of me");
	}
	
	private void save(){
		System.out.println("you clicked Save, which means you are ahead of me");
	}
	
	private void editOrLoad(){
		System.out.println("you clicked Edit/Load, which means you are ahead of me");
	}
	
	private void newGame(){
		System.out.println("you clicked New Game, which means you are ahead of me");
	}
	
	
}
