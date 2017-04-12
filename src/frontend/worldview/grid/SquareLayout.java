package frontend.worldview.grid;

import java.util.Arrays;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.shape.Polygon;

public class SquareLayout implements LayoutManager {

	private double size;
	private CellView cell;
	private Double[] cellPosition;
	private Polygon shape;

	@Override
	public void layoutCell(CellView cellIn, double scaleFactor, double min, double max) {

//		cellIn.getPolygon().getPoints().clear();
//		cellIn.getPolygon().getPoints().addAll(FXCollections.observableArrayList(Arrays.asList(0.0,0.0,100.0,0.0,100.0,100.0,0.0,100.0)));

		cell = cellIn;
		shape = cell.getPolygon();
		setCoordinates();
		setCellSize(scaleFactor, min, max);

	}

	private void setCellSize(double scaleFactor, double min, double max) {
		double difference = max - min;
		size = scaleFactor * difference + min;
		ObservableList<Double> vertices = shape.getPoints();
		vertices.clear();
		System.out.println(cellPosition[0] + ", " + cellPosition[1]);

		vertices.add(cellPosition[0] - size / 2);
		vertices.add(cellPosition[1] - size / 2);
		vertices.add(cellPosition[0] - size / 2);
		vertices.add(cellPosition[1] + size / 2);
		vertices.add(cellPosition[0] + size / 2);
		vertices.add(cellPosition[1] + size / 2);
		vertices.add(cellPosition[0] + size / 2);
		vertices.add(cellPosition[1] - size / 2);

	}

	private void setCoordinates() {
		cellPosition = new Double[2];

		Double col = (double) cell.getCoordinateTuple().get(0);
		Double row = (double) (cell.getCoordinateTuple().get(1)
				+ (cell.getCoordinateTuple().get(0) + (cell.getCoordinateTuple().get(0) & 1)) / 2);
		cellPosition[0] = row;
		cellPosition[1] = col;
	}

}
