package backend.unit.properties;

import backend.cell.CellInstance;
import backend.grid.GridPattern;
import backend.unit.UnitInstance;
import backend.util.ImmutableGameState;
import backend.util.ImmutableVoogaObject;
import backend.util.TriggeredEffectTemplate;
import backend.util.VoogaObject;

import java.util.Collection;
import java.util.stream.Stream;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class ActiveAbility<T extends VoogaObject> extends ImmutableVoogaObject<ActiveAbility<T>> {
	//All hexagonal
	public static final ActiveAbility<UnitInstance> SWORD = new ActiveAbility<>("Sword", new Attack(5, 3), GridPattern.HEXAGONAL_ADJACENT, "The attacker hits 3 times for 5 damage on any neighboring unit", "Sword.png");
	public static final ActiveAbility<UnitInstance> BOW = new ActiveAbility<>("Bow", new Attack(7, 2), GridPattern.HEXAGONAL_RAYS, "The attacker hits 2 times for 7 dmage on any unit in a straight line away from the attacker", "Bow.png");
	public static final ActiveAbility<UnitInstance> SUICIDE_SQUAD = new ActiveAbility<>("Suicide Squad, Attack!", (user, target, game) -> {
		user.getAllNeighboringUnits(game.getGrid()).parallelStream().filter(e -> e.getTeam() != user.getTeam()).forEach(u -> u.takeDamage(10));
		user.takeDamage(Integer.MAX_VALUE);
	}, GridPattern.HEXAGONAL_ADJACENT, "The attacker sacrifices itself to deal massive damage to all neighboring enemy units.", "Allahu_Akbar.png");
	public static final ActiveAbility<UnitInstance> FULL_HEAL = new ActiveAbility<>("Full Heal", (user, target, game) -> target.getHitPoints().resetValue(), GridPattern.HEXAGONAL_ADJACENT, "The attacker fully heals any neighboring unit", "Red_Cross.png");
	public static final ActiveAbility<UnitInstance> BLIND = new ActiveAbility<>("Blind", (user, target, game) -> target.addOffensiveModifiers(InteractionModifier.BLINDED), GridPattern.HEXAGONAL_ADJACENT, "The attacker gives any neighboring unit the Blinded modifier", "Helen_Keller.png");
	public static final ActiveAbility<UnitInstance> SILENCE = new ActiveAbility<>("Silence", (user, target, game) -> Stream.of(target.getOffensiveModifiers(), target.getDefensiveModifiers(), target.getTriggeredAbilities()).forEach(Collection::clear), GridPattern.HEXAGONAL_ADJACENT, "Removes all offensive, defensive, and passive modifiers from any neighboring unit", "Silencer.png");
	public static final ActiveAbility<CellInstance> DROP_MIXTAPE = new ActiveAbility<>("Set Fire to Cell", (user, target, game) -> target.addTriggeredAbilities(TriggeredEffectTemplate.ON_FIRE), GridPattern.HEXAGONAL_SINGLE_CELL, "The attacker sets fire to the cell they are occupying.", "My_mixtape.png");

	private final AbilityEffect<T> effect;
	private final GridPattern range;

	public ActiveAbility(String name, AbilityEffect<T> effect, GridPattern range, String description, String imgPath) {
		super(name, description, imgPath);
		this.range = range;
		this.effect = effect;
	}

	public AbilityEffect<T> getAbilityEffect() {
		return effect;
	}

	public GridPattern getRange() {
		return range;
	}

	public void affect(UnitInstance user, T target, ImmutableGameState game) {
		effect.useAbility(user, target, game);
	}

	@Override
	public ActiveAbility<T> copy() {
		return new ActiveAbility<>(getName(), getAbilityEffect(), getRange(), getDescription(), getImgPath());
	}

	public static Collection<ActiveAbility> getPredefinedActiveAbilities() {
		return getPredefined(ActiveAbility.class);
	}

	@FunctionalInterface
	public interface AbilityEffect<T extends VoogaObject> {
		void useAbility(UnitInstance user, T target, ImmutableGameState game);
	}
}
