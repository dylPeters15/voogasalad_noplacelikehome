package backend.unit.properties;

import backend.util.TriggeredEffectInstance;
import backend.util.VoogaCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class TriggeredAbilitySet extends VoogaCollection<TriggeredEffectInstance, TriggeredAbilitySet> {
	public TriggeredAbilitySet(TriggeredEffectInstance... triggeredAbilities) {
		this(Arrays.asList(triggeredAbilities));
	}

	public TriggeredAbilitySet(Collection<? extends TriggeredEffectInstance> triggeredAbilities) {
		super("Triggered abilities", "Each unit has passive abilities that automatically trigger in response to certain events in the game. Note that not all triggered abilities are positive.", "", triggeredAbilities);
	}

	@Override
	public TriggeredAbilitySet addAll(Collection<? extends TriggeredEffectInstance> elements) {
		return super.addAll(elements.stream().map(TriggeredEffectInstance::copy).map(TriggeredEffectInstance.class::cast).collect(Collectors.toList()));
	}

	@Override
	public TriggeredAbilitySet copy() {
		return new TriggeredAbilitySet(getAll().stream().map(TriggeredEffectInstance::copy).map(TriggeredEffectInstance.class::cast).collect(Collectors.toList()));
	}
}
