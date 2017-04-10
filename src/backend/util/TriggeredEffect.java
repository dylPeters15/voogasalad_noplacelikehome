package backend.util;

import backend.unit.Unit;

import java.util.Collection;

/**
 * @author Created by th174 on 4/5/17.
 */
public interface TriggeredEffect extends VoogaEntity {
	int getRemainingTurns();

	void affect(Unit unit, Event event, ImmutableGameState game);

	Collection<Event> getActivationTriggers();

	boolean isExpired();

	@FunctionalInterface
	interface Effect {
		void affect(Unit unit, Event event, ImmutableGameState game);
	}
}
