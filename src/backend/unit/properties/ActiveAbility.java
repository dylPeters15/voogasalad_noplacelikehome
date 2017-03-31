package backend.unit.properties;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.game_engine.GameState;
import backend.unit.UnitInstance;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class ActiveAbility<T extends GameObject> extends GameObjectImpl {
    //All hexagonal and only effects adjacent neighbors
    public static final ActiveAbility<UnitInstance> PUNCH = new ActiveAbility<>("Punch", new Attack(6, 2), GridPattern.getNeighborPattern(3), "Hits 2 times for 6 damage each in any hexagonal direction.", "Fist.png");
    public static final ActiveAbility<UnitInstance> FULL_HEAL = new ActiveAbility<>("Full Heal", (user, target, game) -> target.getHitPoints().resetValue(), GridPattern.getNeighborPattern(3), "Fully heals any unit", "Red_Cross.png");
    public static final ActiveAbility<UnitInstance> BLIND = new ActiveAbility<>("Blind", (user, target, game) -> target.addOffensiveModifier(InteractionModifier.BLINDED), GridPattern.getNeighborPattern(3), "Gives a unit the Blinded modifier", "Helen_Keller.png");
    public static final ActiveAbility<UnitInstance> SILENCE = new ActiveAbility<>("Silence", (user, target, game) -> Stream.of(target.getOffensiveModifiers(), target.getDefensiveModifiers(), target.getAllPassiveAbilities()).forEach(Collection::clear), GridPattern.getNeighborPattern(3), "Removes all offensive, defensive, and passive modifiers from a unit", "Silencer.png");

    private final AbilityEffect<T> effect;
    private final GridPattern range;

    public ActiveAbility(String name, AbilityEffect<T> effect, GridPattern range, String description, String imgPath) {
        super(name, description, imgPath);
        this.range = range;
        this.effect = effect;
    }

    public GridPattern getRange() {
        return range;
    }

    public void affect(UnitInstance user, T target, GameState game) {
        effect.useAbility(user, target, game);
    }

    interface AbilityEffect<T extends GameObject> {
        void useAbility(UnitInstance user, T target, GameState game);
    }
}
