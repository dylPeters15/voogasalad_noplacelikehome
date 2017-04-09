package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class DefensiveModifierSet extends VoogaCollection<InteractionModifier<Double>, DefensiveModifierSet> {
	@SafeVarargs
	public DefensiveModifierSet(InteractionModifier<Double>... modifiers) {
		this(Arrays.asList(modifiers));
	}

	public DefensiveModifierSet(Collection<? extends InteractionModifier<Double>> modifiers) {
		super("Defensive modifiers", "Each unit has a set of defensive modifiers that can change the amount of damage the unit receives under different conditions.", "", modifiers);
	}

	@Override
	public DefensiveModifierSet copy() {
		return new DefensiveModifierSet(getAll().stream().map(InteractionModifier::copy).collect(Collectors.toList()));
	}

	@Override
	public List<InteractionModifier<Double>> getAll() {
		return Collections.unmodifiableList(new ArrayList<>(super.getAll()));
	}
}
