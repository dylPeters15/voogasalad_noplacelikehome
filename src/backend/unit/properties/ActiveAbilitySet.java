package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class ActiveAbilitySet extends VoogaCollection<ActiveAbility, ActiveAbilitySet> {
	public ActiveAbilitySet(ActiveAbility... abilities) {
		this(Arrays.asList(abilities));
	}

	public ActiveAbilitySet(Collection<? extends ActiveAbility> abilities) {
		super("Active Abilities", "Each unit has a set of active abilities which the user can use on the users' own turn.", "", abilities);
	}

	@Override
	public ActiveAbilitySet copy() {
		return new ActiveAbilitySet(getAll().stream().map(ActiveAbility::copy).collect(Collectors.toList()));
	}
}
