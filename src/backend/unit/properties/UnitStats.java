package backend.unit.properties;

import backend.util.VoogaCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class UnitStats extends VoogaCollection<UnitStatInstance, UnitStats> {
	public UnitStats(UnitStatInstance<?>... stats) {
		this(Arrays.asList(stats));
	}

	public UnitStats(Collection<? extends UnitStatInstance> stats) {
		super("Unit stats", "Each unit has its own set of numerical stats, such as hitpoints or movement points.", "", stats);
	}

	@Override
	public UnitStats copy() {
		return new UnitStats(getAll().stream().map(UnitStatInstance::copy).map(UnitStatInstance.class::cast).collect(Collectors.toList()));
	}
}
