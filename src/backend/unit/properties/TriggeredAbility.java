package backend.unit.properties;

import backend.game_engine.GameState.Event;

import java.util.Arrays;
import java.util.Collection;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class TriggeredAbility extends TriggeredEffect implements Ability {
    //TODO: ResourceBundlify this shit
    public static final TriggeredAbility REGENERATOR = new TriggeredAbility("Regenerator", (unit, event, game) -> unit.takeDamage(-6), "This unit regenerates 6 HP at the start of each turn.", "Regenerate.png", Event.TURN_START);
    public static final TriggeredAbility HEALER = new TriggeredAbility("Healer", (unit, event, game) -> unit.getNeighboringUnits().values().forEach(c -> c.forEach(u -> u.takeDamage(-6))), "This unit heals nearby units for 6 HP at the start of each turn.", "Red_Cross.png", Event.TURN_START);
    public static final TriggeredAbility SHADOWSTALKER = new TriggeredAbility("Shadowstalker", (unit, event, game) -> unit.setVisible(event.equals(Event.UNIT_ABILITY_USE) || game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5), "This unit hides in the shadows, rendering it invisible in nighttime", "Ninja.png", Event.TURN_START, Event.TURN_END, Event.UNIT_MOVEMENT, Event.UNIT_ABILITY_USE);
    public static final TriggeredAbility POISONED = new TriggeredAbility("Poisoned", (unit, event, game) -> unit.getHitPoints().set(Math.min(1, unit.getHitPoints().getCurrentValue() - 4)), "This unit is poisoned, and will lose 4 damage at the start and end of each turn, to a minimum of 1 HP", "Drink_bleach.png", Event.TURN_START, Event.TURN_END);

    public TriggeredAbility(String name, Effect effect, String description, String imgPath, Event... activationTriggers) {
        super(name, effect, description, imgPath, Arrays.asList(activationTriggers));
    }

    public TriggeredAbility(String name, Effect effect, String description, String imgPath, Collection<Event> activationTriggers) {
        super(name, effect, description, imgPath, activationTriggers);
    }

    public static Collection<TriggeredAbility> getPredefinedTriggeredAbilities() {
        return getPredefined(TriggeredAbility.class);
    }
}
