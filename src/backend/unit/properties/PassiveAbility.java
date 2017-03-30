package backend.unit.properties;

import backend.game_engine.GameEngine;
import backend.game_engine.GameObject;
import backend.unit.Unit;

/**
 * Timmy
 * @author Created by th174 on 3/29/2017.
 */
public abstract class PassiveAbility extends GameObject {
    public PassiveAbility(String name, String description, String imgPath, GameEngine currentGame) {
        super(name, description, imgPath, currentGame);
    }

    public abstract void activate(Unit user, GameEngine game);
}
