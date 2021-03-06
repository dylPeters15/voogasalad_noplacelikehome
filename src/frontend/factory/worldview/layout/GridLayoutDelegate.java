package frontend.factory.worldview.layout;

import backend.grid.CoordinateTuple;
import backend.grid.GameBoard;
import javafx.scene.shape.Polygon;

/**
 * GridLayoutDelegate is a functional interface that is used to translate the
 * relative coordinates in a CellView's CoordinateTuple to absolute coordinates
 * at which it is to be displayed in a grid. The CellView's setX() and setY()
 * methods are then called with these absolute coordinates as parameters.
 *
 * A different GridLayoutDelegate should be implemented for all different
 * layouts. Primarily, different LayoutManagers are made for different shapes
 * and different orientations of a particular shape.
 *
 * The purpose of a GridLayoutDelegate is to encapsulate the math behind
 * arranging shapes into a grid. It also makes it easy to quickly swap out
 * different grid layouts and use the same layout strategies for multiple grids.
 *
 * @author Stone Mathers Created 4/11/2017
 */
public interface GridLayoutDelegate {

	/**
	 * Takes in a CellView and sets the coordinates at which it should be
	 * displayed according to the set of bounding constraints that are passed
	 * in.
	 * 
	 * @param scaleFactor
	 *            A scaling factor to determine where within the min-max range
	 *            the CellView image's size will fall.
	 * @param min
	 *            Minimum size of a CellView
	 * @param max
	 * @param location
	 * @param gameBoard
	 */
	Polygon layoutCell(double scaleFactor, double min, double max, CoordinateTuple location, GameBoard gameBoard);
}
