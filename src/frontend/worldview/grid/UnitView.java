/**
 * A Unit object is an movable or immovable character/object that can be placed on top of and move across Terrains.
 */
package frontend.worldview.grid;

import java.util.List;

import backend.unit.Unit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

/**
 * @author Stone Mathers Created 3/29/2017
 */
public class UnitView extends Sprite {
	
	public UnitView(Unit unitModel){
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#setOnClick()
	 */
	@Override
	public void setOnClick(EventHandler<ActionEvent> event) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#getSpriteCells()
	 */
	@Override
	public List<CellView> getSpriteCells() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#addSpriteCell(frontend.sprites.SpriteCell)
	 */
	@Override
	public void addSpriteCell(CellView cell) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#getSpriteCell(int)
	 */
	@Override
	public CellView getSpriteCell(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#getSize()
	 */
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see frontend.sprites.Sprite#setImage(javafx.scene.image.Image)
	 */
	@Override
	public void setImage(Image image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setListType(String type) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getListType() {
		return "Unit";
	}

	@Override
	public Region getObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
