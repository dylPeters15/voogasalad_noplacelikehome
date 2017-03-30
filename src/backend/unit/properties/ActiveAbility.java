package backend.unit.properties;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.game_engine.GameState;
import backend.unit.Unit;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class ActiveAbility<T extends GameObject> extends GameObjectImpl {
    private final AbilityEffect<T> effect;

    public ActiveAbility(String name, AbilityEffect<T> effect, String description, String imgPath) {
        super(name, description, imgPath);
        this.effect = effect;
    }

    public void affect(Unit user, T target, GameState game) {
        effect.useAbility(user, target, game);
    }

    public interface AbilityEffect<T extends GameObject> {
        void useAbility(Unit user, T target, GameState game);
    }
}
