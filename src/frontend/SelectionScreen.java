package frontend;

import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class SelectionScreen {
	
	private ResourceBundle SelectionProperties = ResourceBundle.getBundle("properties/Selections.properties");
	//path is probably wrong
	Pane selectionPane =  new Pane();
	
	public SelectionScreen(){ //should have some sort of parameter that is passing the UI
		
	}
	
	public void setUpPane(){
		Button play = new Button(SelectionProperties.getString("Play")){{
			this.setOnAction(e -> play());
		}};
		
		Button create = new Button(SelectionProperties.getString("Create")){{
			this.setOnAction(e -> create());
		}};
		Button edit = new Button(SelectionProperties.getString("Edit/Load")){{
			this.setOnAction(e -> edit());;
		}};
		selectionPane.getChildren().addAll(play, create, edit);
	}
	
	private void play(){
		
	}
	
	private void create(){
		
	}
	
	private void edit(){
		
	}
}
