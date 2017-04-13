/**
 * 
 */
package frontend.worldview.grid;

/**
 * @author Stone Mathers
 * Created 4/11/2017
 */
public interface LayoutManager {
	
	/**
	 * Takes in a CellView and sets the coordinates at which it should be displayed according to
	 * the set of bounding constraints that are passed in.
	 * 
	 * @param CellView whose layout is to be set.
	 * @param A scaling factor to determine where within the min-max range the CellView image's size will fall.
	 * @param Minimum size of a CellView
	 * @param Maximum size of a CellView
	 */
	void layoutCell(CellView cell, double scaleFactor, double min, double max);
	
}
