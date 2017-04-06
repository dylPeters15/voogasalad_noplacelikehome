package backend.util;

import backend.unit.properties.InteractionModifier;
import backend.util.TriggeredEffectInstance.Effect;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class TriggeredEffectTemplate extends VoogaObject {
    //Unit passive abilities
    public static final TriggeredEffectTemplate REGENERATION = new TriggeredEffectTemplate("Regeneration", (unit, event, game) -> unit.takeDamage(-6), "This unit regenerates 6 HP at the run of each turn.", "Regenerate.png", Event.TURN_START);
    public static final TriggeredEffectTemplate HEALER = new TriggeredEffectTemplate("Healer", (unit, event, game) -> unit.getNeighboringUnits(game.getGrid()).values().forEach(c -> c.forEach(u -> u.takeDamage(-6))), "This unit heals nearby units for 6 HP at the run of each turn.", "Red_Cross.png", Event.TURN_START);
    public static final TriggeredEffectTemplate SHADOWSTALKER = new TriggeredEffectTemplate("Shadowstalker", (unit, event, game) -> unit.setVisible(event.equals(Event.UNIT_POST_ABILITY_USE) || game.getTurnNumber() % 6 != 4 || game.getTurnNumber() % 6 != 5), "This unit hides in the shadows, rendering it invisible in nighttime", "Ninja.png", Event.values());
    public static final TriggeredEffectTemplate POISONED = new TriggeredEffectTemplate("Poisoned", (unit, event, game) -> unit.getHitPoints().set(Math.min(1, unit.getHitPoints().getCurrentValue() - 4)), 6, "This unit is poisoned, and will lose 4 damage at the run and end of each turn, to a minimum of 1 HP. Lasts for 6 turns", "Drink_bleach.png", Event.TURN_START, Event.TURN_END);
    //Cell passive abilities
    public static final TriggeredEffectTemplate FULL_HEAL = new TriggeredEffectTemplate("Full Heal", (occupant, event, game) -> occupant.getHitPoints().resetValue(), "Units occupying this cell have their HP fully restored at the run of each turn.", "Red_Cross.png", Event.TURN_START);
    public static final TriggeredEffectTemplate POISON = new TriggeredEffectTemplate("Poison", (occupant, event, game) -> occupant.addTriggeredAbility(TriggeredEffectTemplate.POISONED.createInstance()), 3, "For the next three turns, units occupying this cell are poisoned at the end of their turn. Lasts for 3 turns.", "Drink_bleach.png", Event.TURN_END);
    public static final TriggeredEffectTemplate ON_FIRE = new TriggeredEffectTemplate("On Fire", (occupant, event, game) -> occupant.takeDamage(10), 2, "For the next two turns, units that move through this cell at the start of their turn take 8 damage.", "My_mixtape.png", Event.UNIT_POST_MOVEMENT);
    public static final TriggeredEffectTemplate STRONG_ATTACK = new TriggeredEffectTemplate("Strong Attack", (occupant, event, game) -> occupant.addOffensiveModifier(InteractionModifier.STRONG_ATTACK), 1, "For the next turn, units that move through this cell deal additional damage.", "Extra_damage.png", Event.UNIT_POST_MOVEMENT);


    protected static final int DEFAULT_DURATION = Integer.MAX_VALUE;
    private Collection<Event> activationTriggers;
    private Effect effect;
    private int duration;

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
    }

    public TriggeredEffectInstance createInstance() {
        return new TriggeredEffectInstance(this);
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Effect getEffect() {
        return this.effect;
    }

    public void setEffect(Effect effect) {
        this.effect = effect;
    }

    public Collection<Event> getActivationTriggers() {
        return activationTriggers;
    }
}

