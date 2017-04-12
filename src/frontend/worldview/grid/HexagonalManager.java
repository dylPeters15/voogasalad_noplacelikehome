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
//	
//	private double xOffset;
//	private double yOffset;
//	private CellView cell;
//	
//	@Override
//	public void layoutCell(CellView cellIn, double scaleFactor, double min, double max) {
//		cell = cellIn;
//		double size = (max - min) * scaleFactor + min;
//		convertToRect(size);
//		addVertex(xOffset, yOffset);
//		addVertex(xOffset + size, yOffset);
//		addVertex(xOffset + size, yOffset + size);
//		addVertex(xOffset, yOffset + size);
//	}
//	
//	private void convertToRect(double size) {
//		xOffset = cell.getCoordinateTuple().get(0) * size;
//		yOffset = cell.getCoordinateTuple().get(1) * size;
//	}
//	
//	private void addVertex(double xV, double yV) {
//		cell.getPolygon().getPoints().add(xV);
//		cell.getPolygon().getPoints().add(yV);
//	}
	@Override
	public void layoutCell(CellView cell, double scaleFactor, double minWidth, double maxWidth) {
		if(scaleFactor <= 0 || scaleFactor > 1 || minWidth <= 0){
			throw new RuntimeException();
		}
		cell.setPolygon(new Hexagon(0, 0, 0));
		resizeHexagon((Hexagon)cell.getPolygon(), scaleFactor, minWidth, maxWidth);
		double width = minWidth + ((maxWidth - minWidth) * scaleFactor);
		cell.setX(cell.getCoordinateTuple().convertToRectangular().get(X_INDEX) * width);
		cell.setY(cell.getCoordinateTuple().convertToRectangular().get(Y_INDEX) * width);	
	}
	
	private void resizeHexagon(Hexagon hexagon, double scale, double min, double max){
		hexagon.setPoints(scale, min, max);
	}
}
