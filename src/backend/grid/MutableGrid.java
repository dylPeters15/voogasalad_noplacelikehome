package backend.grid;

public interface MutableGrid extends ImmutableGrid {

	void setBoundaryConditions(BoundsHandler boundaryConditions) throws IllegalAccessException;

	void setGridSize(int x, int y);

}
