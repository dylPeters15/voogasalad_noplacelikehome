package backend.util;

import backend.unit.UnitInstance;

import java.util.Collection;

/**
 * @author Created by th174 on 4/5/17.
 */
public interface TriggeredEffectInstance extends VoogaObject {
	int getRemainingTurns();

	void affect(UnitInstance unit, Event event, ImmutableGameState game);

	Collection<Event> getActivationTriggers();

	boolean isExpired();

	@FunctionalInterface
	interface Effect {
		void affect(UnitInstance unit, Event event, ImmutableGameState game);
	}
}
