/**
 *
 */
package backend.unit.properties;

import backend.XMLsavable;
import backend.unit.Unit;

import java.util.ResourceBundle;

/**
 * @author Dylan Peters, Timmy Huang
 */
public abstract class Ability<T> implements XMLsavable {
    private final String name;
    private final String description;

    Ability(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public abstract void affect(Unit user, T target);
}
