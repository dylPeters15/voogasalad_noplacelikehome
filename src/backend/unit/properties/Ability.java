/**
 * 
 */
package backend.unit.properties;

/**
 * @author Dylan Peters
 *
 */
public interface Ability<T> {

	String getName();

	String getDescription();

	void affect(T target);

}
