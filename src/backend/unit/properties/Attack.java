/**
 * 
 */
package backend.unit.properties;

import backend.unit.Unit;

/**
 * @author Dylan Peters
 *
 */
// ///this should become a class that implements Ability
public interface Attack extends Ability<Unit> {

	double getBaseDamage();

}
