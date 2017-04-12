/**
 * 
 */
package frontend.worldview.grid;

/**
 * @author Stone Mathers
 * Created 4/11/2017
 */
public class HexagonalManager implements LayoutManager {

	public static final int X_INDEX = 0;
	public static final int Y_INDEX = 1;
	
	@Override
	public void layoutCell(CellView cell, double scaleFactor, double minWidth, double maxWidth) {
		if(scaleFactor <= 0 || scaleFactor > 1 || minWidth <= 0){
			throw new RuntimeException();
		}
		resizeHexagon((Hexagon)cell.getPolygon(), scaleFactor, minWidth, maxWidth);
		double width = minWidth + ((maxWidth - minWidth) * scaleFactor);
		cell.setX(cell.getCoordinateTuple().convertToRectangular().get(X_INDEX) * width);
		cell.setX(cell.getCoordinateTuple().convertToRectangular().get(Y_INDEX) * width);	
	}
	
	private void resizeHexagon(Hexagon hexagon, double scale, double min, double max){
		hexagon.setPoints(scale, min, max);
	}
}
