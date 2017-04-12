package frontend.worldview.grid;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import backend.grid.CoordinateTuple;
import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

public class SquareLayout implements LayoutManager {

	private double size;
	private CellView cell;
	private Double[] cellPosition;
	private Polygon shape;
	
	@Override
	public void layoutCell(CellView cellIn, double scaleFactor, double min, double max) {
		cell = cellIn;
		setCellSize(scaleFactor, min, max);
		setCoordinates();
		shape = cell.getPolygon();
		
	}
	
	private void setCellSize(double scaleFactor, double min, double max) {
		double difference = max - min;
		size = scaleFactor*difference + min;
		ObservableList<Double> vertices = shape.getPoints();
		vertices.set(0, cellPosition[0]- size/2);
		vertices.set(1, cellPosition[0] + size/2);
		vertices.set(2, cellPosition[1]- size/2);
		vertices.set(3, cellPosition[1] + size/2);
		
	}
	
	private void setCoordinates() {
		cellPosition = new Double[1];
		Double col = (double) cell.getCoordinateTuple().get(0);
		Double row = (double) (cell.getCoordinateTuple().get(2) + (cell.getCoordinateTuple().get(0) + (cell.getCoordinateTuple().get(0) & 1)) / 2);
		cellPosition[0] = row;
		cellPosition[1] = col;
	}


}
