package backend.unit.properties;

import backend.util.TriggeredEffect;
import backend.util.ModifiableVoogaCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class TriggeredAbilitySet extends ModifiableVoogaCollection<TriggeredEffect, TriggeredAbilitySet> {
	public TriggeredAbilitySet(TriggeredEffect... triggeredAbilities) {
		this(Arrays.asList(triggeredAbilities));
	}

	public TriggeredAbilitySet(Collection<? extends TriggeredEffect> triggeredAbilities) {
		super("Triggered abilities", "Each unit has passive abilities that automatically trigger in response to certain events in the game. Note that not all triggered abilities are positive.", "", triggeredAbilities);
	}

	@Override
	public TriggeredAbilitySet addAll(Collection<? extends TriggeredEffect> elements) {
		return super.addAll(elements.stream().map(TriggeredEffect::copy).map(TriggeredEffect.class::cast).collect(Collectors.toList()));
	}

	@Override
	public TriggeredAbilitySet copy() {
		return new TriggeredAbilitySet(getAll().stream().map(TriggeredEffect::copy).map(TriggeredEffect.class::cast).collect(Collectors.toList()));
	}
}
