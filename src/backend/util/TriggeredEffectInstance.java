package backend.util;

import backend.unit.UnitInstance;

import java.util.Collection;
import java.util.Collections;

/**
 * @author Created by th174 on 4/5/17.
 */
public class TriggeredEffectInstance extends VoogaInstance<TriggeredEffectTemplate> {
    private final Collection<Event> activationTriggers;
    private final Effect effect;
    private final int duration;
    private int turnsRemaining;

    protected TriggeredEffectInstance(TriggeredEffectTemplate template) {
        super(template.getName(), template);
        this.effect = template.getEffect();
        this.duration = template.getDuration();
        this.activationTriggers = Collections.unmodifiableCollection(template.getActivationTriggers());
        this.turnsRemaining = duration;
    }

    public int getRemainingTurns() {
        return turnsRemaining;
    }

    public int getDuration() {
        return duration;
    }

    public final void affect(UnitInstance unit, Event event, ImmutableGameState game) {
        if (getActivationTriggers().contains(event)) {
            effect.affect(unit, event, game);
        }
        if (event.equals(Event.TURN_END)) {
            turnsRemaining--;
        }
    }

    public Collection<Event> getActivationTriggers() {
        return Collections.unmodifiableCollection(activationTriggers);
    }

    public boolean isExpired() {
        return getRemainingTurns() <= 0;
    }

    public interface Effect {
        void affect(UnitInstance unit, Event event, ImmutableGameState game);
    }
}
