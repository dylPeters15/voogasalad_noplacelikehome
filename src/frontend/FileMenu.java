package frontend;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ResourceBundle;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class FileMenu extends VBox {
	private ResourceBundle SelectionProperties = ResourceBundle.getBundle("frontend/properties/SelectionProperties");

	Pane selectionPane =  new Pane();

	MenuBar menuBar;
	private MenuItem playItem, saveItem, loadItem, newGameItem, editItem;
	Menu fileMenu, helpMenu;

	public FileMenu(){
		this.initMenu();
	}

	private void initMenu(){
		this.playItem = new MenuItem(SelectionProperties.getString("Play")){{
			setOnAction(e -> play());
		}};

		this.saveItem = new MenuItem(SelectionProperties.getString("Save")){{
			setOnAction(e -> save());
		}};

		this.editItem = new MenuItem(SelectionProperties.getString("Edit")){{
			setOnAction(e -> edit());
		}};
		
		this.loadItem = new MenuItem(SelectionProperties.getString("Load")){{
			setOnAction(e -> edit());
		}};


		this.newGameItem = new MenuItem(SelectionProperties.getString("Create")){{
			setOnAction(e -> newGame());
		}};



		this.helpMenu = new Menu(SelectionProperties.getString("Help")){{
		}};

		this.fileMenu = new Menu(SelectionProperties.getString("File")) {{
			getItems().addAll(playItem, saveItem, editItem, loadItem, newGameItem);
		}};

		this.menuBar = new MenuBar() {{
			getMenus().addAll(fileMenu, helpMenu);
		}};

		this.getChildren().addAll(menuBar);

	}

	private void play(){
		System.out.println("you can load and save files, but it won't do anything");
		read();

	}

	private void save(){
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
		}catch(IOException i) {
			i.printStackTrace();
		}
	}

	private void edit(){

	}

	private void load(){
		read();
	}

	private void newGame(){
		System.out.println("you clicked New Game, which means you are ahead of me");
	}

	public void read(){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
		fileChooser.setTitle("Open Resource File");
		Window stage = null;
		File file = fileChooser.showOpenDialog(stage);

		try {

			FileInputStream fileIn = new FileInputStream(file);

			ObjectInputStream in = new ObjectInputStream(fileIn);

			Object e =  in.readObject();

			in.close();

			fileIn.close();

		}catch(IOException i) {

			i.printStackTrace();

			return;

		}catch(ClassNotFoundException c) {

			System.out.println("Employee class not found");

			c.printStackTrace();

			return;

		}

	}
}

