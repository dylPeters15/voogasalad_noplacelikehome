package frontend.worldview.grid;

public class SquareLayout implements LayoutManager {

	@Override
	public void layoutCell(CellView cellIn, double scaleFactor, double min, double max) {
		double width = (max - min) * scaleFactor + min;
		double height = (max - min) * scaleFactor + min;
		double xOffset = cellIn.getCoordinateTuple().get(0) * width;
		double yOffset = cellIn.getCoordinateTuple().get(1) * height;
		cellIn.getPolygon().getPoints().clear();
		cellIn.getPolygon().getPoints().add(xOffset);
		cellIn.getPolygon().getPoints().add(yOffset);
		cellIn.getPolygon().getPoints().add(xOffset + width);
		cellIn.getPolygon().getPoints().add(yOffset);
		cellIn.getPolygon().getPoints().add(xOffset + width);
		cellIn.getPolygon().getPoints().add(yOffset + height);
		cellIn.getPolygon().getPoints().add(xOffset);
		cellIn.getPolygon().getPoints().add(yOffset + height);

	}

}
