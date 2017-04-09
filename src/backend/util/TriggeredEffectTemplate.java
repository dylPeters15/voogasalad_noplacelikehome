package backend.util;

import backend.unit.UnitInstance;
import backend.unit.properties.InteractionModifier;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class TriggeredEffectTemplate extends VoogaTemplate<TriggeredEffectTemplate> implements TriggeredEffectInstance {
	//Unit passive abilities
	public static final TriggeredEffectTemplate REGENERATION = new TriggeredEffectTemplate("Regeneration")
			.setEffect((unit, event, game) -> unit.takeDamage(-6))
			.setDescription("This unit regenerates 6 HP at the run of each turn.")
			.setImgPath("Regenerate.png")
			.addActivationTriggers(Event.TURN_START);
	public static final TriggeredEffectTemplate HEALER = new TriggeredEffectTemplate("Healer")
			.setEffect((unit, event, game) -> unit.getNeighboringUnits(game.getGrid()).values().forEach(c -> c.forEach(u -> u.takeDamage(-6))))
			.setDescription("This unit heals nearby units for 6 HP at the run of each turn.")
			.setImgPath("Red_Cross.png")
			.addActivationTriggers(Event.TURN_START);
	public static final TriggeredEffectTemplate SHADOWSTALKER = new TriggeredEffectTemplate("Shadowstalker")
			.setEffect((unit, event, game) -> unit.setVisible(event.equals(Event.UNIT_POST_ABILITY_USE) || game.getTurnNumber() % 6 != 4 || game.getTurnNumber() % 6 != 5))
			.setDescription("This unit hides in the shadows, rendering it invisible in nighttime")
			.setImgPath("Ninja.png")
			.addActivationTriggers(Event.values());
	public static final TriggeredEffectTemplate POISONED = new TriggeredEffectTemplate("Poisoned")
			.setEffect((unit, event, game) -> unit.getHitPoints().setCurrentValue(Math.min(1, unit.getHitPoints().getCurrentValue() - 4)))
			.setDuration(6)
			.setDescription("This unit is poisoned, and will lose 4 damage at the run and end of each turn, to a minimum of 1 HP. Lasts for 6 turns")
			.setImgPath("Drink_bleach.png")
			.addActivationTriggers(Event.TURN_START, Event.TURN_END);
	//Cell passive abilities
	public static final TriggeredEffectTemplate FULL_HEAL = new TriggeredEffectTemplate("Full Heal")
			.setEffect((occupant, event, game) -> occupant.getHitPoints().resetValue())
			.setDescription("Units occupying this cell have their HP fully restored at the run of each turn.")
			.setImgPath("Red_Cross.png")
			.addActivationTriggers(Event.TURN_START);
	public static final TriggeredEffectTemplate POISON = new TriggeredEffectTemplate("Poison")
			.setEffect((occupant, event, game) -> occupant.addTriggeredAbilities(TriggeredEffectTemplate.POISONED))
			.setDuration(3)
			.setDescription("For the next three turns, units occupying this cell are poisoned at the end of their turn. Lasts for 3 turns.")
			.setImgPath("Drink_bleach.png")
			.addActivationTriggers(Event.TURN_END);
	public static final TriggeredEffectTemplate ON_FIRE = new TriggeredEffectTemplate("On Fire")
			.setEffect((occupant, event, game) -> occupant.takeDamage(10))
			.setDuration(2)
			.setDescription("For the next two turns, units that move through this cell at the start of their turn take 8 damage.")
			.setImgPath("My_mixtape.png")
			.addActivationTriggers(Event.UNIT_POST_MOVEMENT);
	public static final TriggeredEffectTemplate STRONG_ATTACK = new TriggeredEffectTemplate("Strong Attack")
			.setEffect((occupant, event, game) -> occupant.addOffensiveModifiers(InteractionModifier.STRONG_ATTACK))
			.setDuration(1)
			.setDescription("For the next turn, units that move through this cell deal additional damage.")
			.setImgPath("Extra_damage.png")
			.addActivationTriggers(Event.UNIT_POST_MOVEMENT);

	private static final int DEFAULT_DURATION = Integer.MAX_VALUE;
	private Collection<Event> activationTriggers;
	private Effect effect;
	private int duration;
	private int turnsRemaining;

	public TriggeredEffectTemplate(String name) {
		this(name, null, "", "");
	}

	public TriggeredEffectTemplate(String name, Effect effect, String description, String imgPath, Event... activationTriggers) {
		this(name, effect, DEFAULT_DURATION, description, imgPath, activationTriggers);
	}

	public TriggeredEffectTemplate(String name, Effect effect, int numTurns, String description, String imgPath, Event... activationTriggers) {
		this(name, effect, numTurns, description, imgPath, Arrays.asList(activationTriggers));
	}

	public TriggeredEffectTemplate(String name, Effect effect, int numTurns, String description, String imgPath, Collection<Event> activationTriggers) {
		super(name, description, imgPath);
		this.effect = effect;
		this.activationTriggers = activationTriggers;
		this.duration = numTurns;
		this.turnsRemaining = duration;
	}

	public Effect getEffect() {
		return this.effect;
	}

	public TriggeredEffectTemplate setEffect(Effect effect) {
		this.effect = effect;
		return this;
	}

	public int getDuration() {
		return duration;
	}

	public TriggeredEffectTemplate setDuration(int duration) {
		this.duration = duration;
		return this;
	}

	public TriggeredEffectTemplate addActivationTriggers(Event... activationTriggers) {
		return addActivationTriggers(Arrays.asList(activationTriggers));
	}

	public TriggeredEffectTemplate addActivationTriggers(Collection<Event> activationTriggers) {
		this.activationTriggers.addAll(activationTriggers);
		return this;
	}

	@Override
    public TriggeredEffectTemplate copy() {
		return new TriggeredEffectTemplate(getName(), getEffect(), getDuration(), getDescription(), getImgPath(), getActivationTriggers());
	}

	@Override
    public int getRemainingTurns() {
		return turnsRemaining;
	}

	@Override
    public final void affect(UnitInstance unit, Event event, ImmutableGameState game) {
		if (getActivationTriggers().contains(event)) {
			effect.affect(unit, event, game);
		}
		if (event.equals(Event.TURN_END)) {
			turnsRemaining--;
		}
	}

	@Override
    public Collection<Event> getActivationTriggers() {
		return activationTriggers;
	}

	@Override
    public boolean isExpired() {
		return getRemainingTurns() <= 0;
	}

}

