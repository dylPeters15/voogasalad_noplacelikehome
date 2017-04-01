package backend.unit.properties;

import backend.util.GameState;
import backend.util.GameState.Event;
import backend.unit.UnitInstance;
import backend.util.VoogaObject;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Created by th174 on 3/31/2017.
 */
public abstract class TriggeredEffect extends VoogaObject {
    private final Collection<Event> activationTriggers;
    private final Effect effect;

    public TriggeredEffect(String name, Effect effect, String description, String imgPath, Event... activationTriggers) {
        this(name, effect, description, imgPath, Arrays.asList(activationTriggers));
    }

    public TriggeredEffect(String name, Effect effect, String description, String imgPath, Collection<Event> activationTriggers) {
        super(name, description, imgPath);
        this.effect = effect;
        this.activationTriggers = activationTriggers;
    }

    public void affect(UnitInstance unit, Event event, GameState game) {
        if (getActivationTriggers().contains(event)) {
            effect.affect(unit, event, game);
        }
    }

    public Collection<Event> getActivationTriggers() {
        return Collections.unmodifiableCollection(activationTriggers);
    }

    protected interface Effect {
        void affect(UnitInstance unit, Event event, GameState game);
    }
}

