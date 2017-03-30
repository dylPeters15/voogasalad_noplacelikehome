package backend.unit.properties;

import backend.Game;
import backend.GameObject;
import backend.unit.Unit;

/**
 * @author Created by th174 on 3/29/2017.
 */
public abstract class PassiveAbility extends GameObject {
    public PassiveAbility(String name, String description, String imgPath, Game currentGame) {
        super(name, description, imgPath, currentGame);
    }

    public abstract void activate(Unit user, Game game);
}
