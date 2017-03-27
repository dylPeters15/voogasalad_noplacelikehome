/**
 *
 */
package backend.unit.properties;

import backend.XMLsavable;

/**
 * @author Dylan Peters
 */
public interface HitPoints extends XMLsavable{

    double getCurrentHP();

    double getMaxHP();

    void setHP(double numPoints);

    default void takeDamage(double numPoints) {
        setHP(getCurrentHP() - numPoints);
    }

    default void fullHeal() {
        setHP(getMaxHP());
    }
}
