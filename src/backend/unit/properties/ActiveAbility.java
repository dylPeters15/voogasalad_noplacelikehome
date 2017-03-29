package backend.unit.properties;

import backend.Game;
import backend.GameObject;
import backend.unit.Unit;

/**
 * @author Created by th174 on 3/29/2017.
 */
public abstract class ActiveAbility<T> extends GameObject {
    public ActiveAbility(String name, String description, String imgPath, Game game) {
        super(name, description, imgPath, game);
    }

    public abstract void affect(Unit user, T target);
}
