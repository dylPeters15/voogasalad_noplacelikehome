package backend.unit.properties;

import backend.unit.UnitInstance;
import backend.util.GameState;
import backend.util.VoogaInstance;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class ActiveAbility<T extends VoogaObject> extends VoogaObject implements Ability {
    //All hexagonal and only effects adjacent neighbors
    public static final ActiveAbility<UnitInstance> PUNCH = new ActiveAbility<>("Punch", new Attack(6, 2), GridPattern.HEXAGONAL_ADJACENT, "The attacker hits 2 times for 6 damage each in any hexagonal direction.", "Fist.png");
    public static final ActiveAbility<UnitInstance> SUICIDE_SQUAD = new ActiveAbility<>("Suicide Squad, Attack!", (user, target, game) -> {
        user.getAllNeighboringUnits().stream().filter(e -> e.getTeam() != user.getTeam()).forEach(u -> u.takeDamage(10));
        user.takeDamage(Integer.MAX_VALUE);
    }, GridPattern.HEXAGONAL_ADJACENT, "The attacker sacrifices itself to deal massive damage to all neighboring enemy units.", "Allahu_Akbar.png");
    public static final ActiveAbility<UnitInstance> FULL_HEAL = new ActiveAbility<>("Full Heal", (user, target, game) -> target.getHitPoints().resetValue(), GridPattern.HEXAGONAL_ADJACENT, "The attacker fully heals any unit", "Red_Cross.png");
    public static final ActiveAbility<UnitInstance> BLIND = new ActiveAbility<>("Blind", (user, target, game) -> target.addOffensiveModifier(InteractionModifier.BLINDED), GridPattern.HEXAGONAL_ADJACENT, "The attacker gives a unit the Blinded modifier", "Helen_Keller.png");
    public static final ActiveAbility<UnitInstance> SILENCE = new ActiveAbility<>("Silence", (user, target, game) -> Stream.of(target.getOffensiveModifiers(), target.getDefensiveModifiers(), target.getAllTriggeredAbilities()).forEach(Collection::clear), GridPattern.HEXAGONAL_ADJACENT, "Removes all offensive, defensive, and passive modifiers from a unit", "Silencer.png");

    private final AbilityEffect<T> effect;
    private final GridPattern range;

    public ActiveAbility(String name, AbilityEffect<T> effect, GridPattern range, String description, String imgPath) {
        super(name, description, imgPath);
        this.range = range;
        this.effect = effect;
    }

    public static Collection<ActiveAbility> getPredefinedActiveAbilities() {
        return getPredefined(ActiveAbility.class);
    }

    public AbilityEffect<T> getAbilityEffect() {
        return effect;
    }

    public GridPattern getRange() {
        return range;
    }

    public void affect(UnitInstance user, T target, GameState game) {
        effect.useAbility(user, target, game);
    }

    @FunctionalInterface
    public interface AbilityEffect<T extends VoogaObject> {
        void useAbility(UnitInstance user, T target, GameState game);
    }
}
