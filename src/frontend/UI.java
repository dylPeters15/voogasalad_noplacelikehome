package frontend;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class UI {
	private Scene primaryScene;
	private BorderPane primaryPane;
	private double width, height;
	private FileMenu fileMenu;
	private SelectionScreen selectionScreen;
	
	public UI(double width, double height){
		this.initPrimaryScene();
		this.width = width;
		this.height = height;
	}
	
	private void initPrimaryScene(){
		this.primaryScene = new Scene(this.initPrimaryPane());
	}
	
	private BorderPane initPrimaryPane(){ 
		System.out.println("here");
		this.fileMenu = new FileMenu();
		this.selectionScreen = new SelectionScreen();
		this.primaryPane = new BorderPane() {{
			setMinSize(width, height);
            setTop(fileMenu);
            setCenter(selectionScreen);
            selectionScreen.setAlignment(Pos.CENTER);
//            this.getChildren().addAll(fileMenu, selectionScreen);
//            this.setCenter(getCenter());

//            setCenter(); //where game or development should be initiated
        }};
//        System.out.println(selectionScreen.getChildren());
        return primaryPane;
	}
	
	public Scene getPrimaryScene(){
		return primaryScene;
	}
	
	
}
