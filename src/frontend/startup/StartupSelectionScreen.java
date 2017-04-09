package frontend.startup;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import backend.util.*;
import frontend.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class StartupSelectionScreen extends VBox{

	private ResourceBundle SelectionProperties = ResourceBundle.getBundle("frontend/properties/SelectionProperties");
	private StartupScreen ui;


	public StartupSelectionScreen(StartupScreen ui){ //should have some sort of parameter that is passing the UI
		this.setUpPane();
		this.ui = ui;
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
		Button load = new Button(SelectionProperties.getString("Load")){{
			this.setOnAction(e -> load());
		}};
		this.setPadding(new Insets(30, 10, 10, 10));
		this.setSpacing(10);
		this.setMinWidth(450);
		this.setMinHeight(400);
		this.getChildren().addAll(play, create, load);
	}

	private void play(){
		System.out.println("you can load and save files, but it won't do anything");
		read("play");
	}

	private void create(){
		System.out.println("you clicked 'create.' I haven't gotten that far");

	}

	private void load(){
		System.out.println("you can load and save files, but it won't do anything");
		read("load");
	}

	private void read(String saveOrLoad){
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(".xml Files", "*.xml"));
		fileChooser.setTitle("Open Resource File");
		Window stage = null;
		File file = fileChooser.showOpenDialog(stage);
		System.out.println(saveOrLoad);


		try {

			FileInputStream fileIn = new FileInputStream(file);

			ObjectInputStream in = new ObjectInputStream(fileIn);

			//need to do something with the file

			in.close();

			fileIn.close();


			//this part probs doesn't work
			Region pane = ui.getPrimaryPane();
			((BorderPane) pane).setCenter(new View().getObject());

		}catch(IOException i) {

			i.printStackTrace();

			return;

		} catch (NullPointerException e){
			e.printStackTrace();

			Alert alert = new Alert(AlertType.CONFIRMATION);

			alert.setTitle("No file selected");
			
//			alert.setGraphic(graphic); //insert DuvallSalad
			
			if (Objects.equals(saveOrLoad, "save")){
				alert.setHeaderText("Current game will not save");
			}
			if (Objects.equals(saveOrLoad, "load") || Objects.equals(saveOrLoad, "play")){
				alert.setHeaderText("Failed to load game");
			}
			
			alert.setContentText("Would you like to try again?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == new ButtonType("okay")){
				read(saveOrLoad);
			} else{
				return;

			}
		}
		//		}catch(ClassNotFoundException c) {
		//
		//			System.out.println("Employee class not found");
		//
		//			c.printStackTrace();
		//
		//			return;
		//
		//		}
	}
}
