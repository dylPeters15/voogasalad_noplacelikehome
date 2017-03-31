package backend.unit.properties;

import backend.game_engine.GameState;
import backend.unit.UnitInstance;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class TriggeredAbility extends Ability {
    //TODO: ResourceBundlify this shit
    public static final TriggeredAbility REGENERATOR = new TriggeredAbility("Regenerator", (user, event, game) -> user.takeDamage(-6), "This unit regenerates 6 HP at the start of each turn.", "Regenerate.png", TriggerEvent.ON_TURN_START);
    public static final TriggeredAbility HEALER = new TriggeredAbility("Healer", (user, event, game) -> user.getNeighboringUnits().values().forEach(c -> c.forEach(u -> u.takeDamage(-6))), "This unit heals nearby units for 6 HP at the start of each turn.", "Red_Cross.png", TriggerEvent.ON_TURN_START);
    public static final TriggeredAbility SHADOWSTALKER = new TriggeredAbility("Shadowstalker", (user, event, game) -> user.setVisible(event.equals(TriggerEvent.ON_ACTION) || game.getTurnNumber() % 6 == 4 || game.getTurnNumber() % 6 == 5), "This unit hides in the shadows, rendering it invisible in nighttime", "Ninja.png", TriggerEvent.ON_TURN_START, TriggerEvent.ON_TURN_END, TriggerEvent.ON_MOVE, TriggerEvent.ON_ACTION);

    private final Collection<TriggerEvent> activationTriggers;
    private final AbilityEffect effect;

    public TriggeredAbility(String name, AbilityEffect effect, String description, String imgPath, TriggerEvent... activationTriggers) {
        this(name, effect, description, imgPath, Arrays.asList(activationTriggers));
    }

    public TriggeredAbility(String name, AbilityEffect effect, String description, String imgPath, Collection<TriggerEvent> activationTriggers) {
        super(name, description, imgPath);
        this.effect = effect;
        this.activationTriggers = activationTriggers;
    }

    public void affect(UnitInstance user, TriggerEvent event, GameState game) {
        if (activationTriggers.contains(event)) {
            effect.activateAbility(user, event, game);
        }
    }

    public Collection<TriggerEvent> getActivationTriggers() {
        return Collections.unmodifiableCollection(activationTriggers);
    }

    public enum TriggerEvent {
        ON_TURN_START, ON_MOVE, ON_ACTION, ON_TURN_END
    }

    @FunctionalInterface
    public interface AbilityEffect {
        void activateAbility(UnitInstance user, TriggerEvent event, GameState game);
    }

    public static Collection<TriggeredAbility> getPredefinedTriggeredAbilities() {
        return getPredefined(TriggeredAbility.class);
    }
}
