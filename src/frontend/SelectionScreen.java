package frontend;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class SelectionScreen extends VBox{
	
	private ResourceBundle SelectionProperties = ResourceBundle.getBundle("frontend/properties/SelectionProperties");
	//path is probably wrong
	
	public SelectionScreen(){ //should have some sort of parameter that is passing the UI
		this.setUpPane();
		System.out.println(this.getChildren());
	}
	
	public void setUpPane(){
		System.out.println("setUpPane");
		Button play = new Button(SelectionProperties.getString("Play")){{
			this.setOnAction(e -> play());
		}};
		
		Button create = new Button(SelectionProperties.getString("Create")){{
			this.setOnAction(e -> create());
		}};
		Button edit = new Button(SelectionProperties.getString("Edit/Load")){{
			this.setOnAction(e -> edit());;
		}};
		this.getChildren().addAll(play, create, edit);
		
	}
	
	private void play(){
		System.out.println("you can load and save files, but it won't do anything");
		read();
	}
	
	private void create(){
		System.out.println("you clicked 'create.' I haven't gotten that far");
		
	}
	
	private void edit(){
		System.out.println("you can load and save files, but it won't do anything");
		read();
	}
	
	private void read(){
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
