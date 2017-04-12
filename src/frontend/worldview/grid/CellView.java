/**
 * A cell within the frontend GridDisplay.
 */
package frontend.worldview.grid;

import backend.grid.CoordinateTuple;
import javafx.scene.image.Image;

/**
 * @author Stone Mathers
 * Created 3/29/2017
 */
public interface CellView {
	
	/**
	 * @return DisplayCoordinates at which the CellView is displayed.
	 */
	DisplayCoordinates getDisplayCoordinates();
	
	/**
	 * @param DisplayCoordinates at which the CellView is displayed.
	 */
	void setDisplayCoordinates(DisplayCoordinates coordinates);
	
	/**
	 * @return CoordinateTuple at which the CellView is held.
	 */
	CoordinateTuple getCoordinateTuple();
	
	/**
	 * @param CoordinateTuple at which the CellView is held.
	 */
	void setCoordinateTuple(CoordinateTuple coordinates);
	
	/**
	 * @return Image representing the CellView.
	 */
	Image getImage();
	
	/**
	 * @param Image representing the CellView.
	 */
	void setImage(Image image);
	
}
