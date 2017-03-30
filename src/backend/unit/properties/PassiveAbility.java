package backend.unit.properties;

import backend.Game;
import backend.GameObject;

/**
 * @author Created by th174 on 3/29/2017.
 */
public abstract class PassiveAbility extends GameObject{
    public PassiveAbility(String name, String description, String imgPath, Game currentGame) {
        super(name, description, imgPath, currentGame);
    }
    //TODO how the fuck does this even work lol
}
