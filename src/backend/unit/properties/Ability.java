/**
 *
 */
package backend.unit.properties;

import backend.Game;
import backend.GameObject;
import backend.unit.Unit;

/**
 * @author Dylan Peters, Timmy Huang
 */
public abstract class Ability<T> extends GameObject {

    public Ability(String name, String description, String imgPath, Game game) {
        super(name, description, imgPath, game);
    }

    public abstract void affect(Unit user, T target);
}
