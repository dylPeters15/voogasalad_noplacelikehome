package backend.unit.properties;

import backend.util.ModifiableVoogaCollection;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 4/9/2017.
 */
public class UnitStats extends ModifiableVoogaCollection<UnitStat, UnitStats> {
	public UnitStats(UnitStat<?>... stats) {
		this(Arrays.asList(stats));
	}

	public UnitStats(Collection<? extends UnitStat> stats) {
		super("Unit stats", "Each unit has its own set of numerical stats, such as hitpoints or movement points.", "", stats);
	}

	@Override
	public UnitStats addAll(Collection<? extends UnitStat> elements) {
		return super.addAll(elements.stream().map(UnitStat::copy).map(UnitStat.class::cast).collect(Collectors.toList()));
	}

	@Override
	public UnitStats copy() {
		return new UnitStats(getAll().stream().map(UnitStat::copy).map(UnitStat.class::cast).collect(Collectors.toList()));
	}
}
