/**
 * 
 */
package frontend.worldview.grid;

import backend.grid.CoordinateTuple;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Stone Mathers
 *
 */
public class HexagonalManager implements LayoutManager {

	@Override
	public void layoutCell(CellView cell, double scaleFactor, double min, double max) {
		if(scaleFactor <= 0 || scaleFactor > 1 || min <= 0){
			throw new RuntimeException();
		}
		
		CoordinateTuple rectCoord = cell.getCoordinateTuple().convertToRectangular();
		Image image = cell.getImage();
		ImageView iv = new ImageView();
		double length = min + ((max-min)*scaleFactor);
		
		
	}

}
