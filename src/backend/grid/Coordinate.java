/**
 * 
 */
package backend.grid;

/**
 * @author Dylan Peters
 *
 */
public interface Coordinate {

	boolean equals(Coordinate other);

	double euclideanDistanceTo(Coordinate other);

	Integer getCoordinate(int index);

}
