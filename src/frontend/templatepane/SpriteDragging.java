package frontend.templatepane;

import java.util.ArrayList;
import java.util.List;

import frontend.Displayable;
import frontend.sprites.Sprite;
import frontend.sprites.SpriteCell;
import frontend.sprites.Unit;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;

/**
 * @author Faith Rodriguez
 * Created 4/3/2017
 */

public class SpriteAddPane implements Displayable {

	FlowPane parent = new FlowPane();
	Unit unit;
	List<Sprite> availableSprites;
	
	public SpriteAddPane() {
		unit = new Unit();
		updateSprites();
		parent.setMaxWidth(100);
		parent.setOrientation(Orientation.HORIZONTAL);
		
	}

	private void updateSprites() {
		availableSprites = new ArrayList<Sprite>();
		parent.getChildren().clear();
		for (Sprite sprite: availableSprites) {
			ImageView spriteImage = new ImageView(sprite.getImage());
			parent.getChildren().add(spriteImage);
			 sprite.setOnDragDetected(new EventHandler <MouseEvent>() {
		            public void handle(MouseEvent event) {
		                /* drag was detected, run drag-and-drop gesture*/
		                System.out.println("onDragDetected");
		                
		                /* create dragboard */
		                Dragboard db = (Dragboard) Dragboard.getSystemClipboard();
		                
		                /* put an image on dragboard */
		                ClipboardContent content = new ClipboardContent();
		                content.putImage(sprite.getImage());
		                db.setContent(content);
		                event.consume();
		            }
		        });
		}
		
	}

	@Override
	public Node getView() {
		return parent;
	}
}
