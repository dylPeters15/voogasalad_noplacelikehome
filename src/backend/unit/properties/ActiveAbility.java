package backend.unit.properties;

import backend.game_engine.GameEngine;
import backend.game_engine.GameObject;
import backend.unit.Unit;

/**
 * Timmy
 * @author Created by th174 on 3/29/2017.
 */
public abstract class ActiveAbility<T> extends GameObject {
    public ActiveAbility(String name, String description, String imgPath, GameEngine game) {
        super(name, description, imgPath, game);
    }

    public abstract void affect(Unit user, T target);
}
