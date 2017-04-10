package backend.unit.properties;

import backend.unit.ModifiableUnit;
import backend.unit.Unit;
import backend.util.ModifiableVoogaCollection;

import java.util.Collection;

/**
 * Timmy
 *
 * @author Created by th174 on 3/29/2017.
 */
public class Faction extends ModifiableVoogaCollection<Unit, Faction> {
	public transient static final Faction UNDEAD = new Faction("Undead")
			.setDescription("The reanimated corpses of the dead want no more than to slake their thirst with the fresh blood of the living")
			.setImgPath("get_spooked.png")
			.addAll(ModifiableUnit.SKELETON_WARRIOR, ModifiableUnit.SKELETON_ARCHER);

	public Faction(String name) {
		this(name, "", "");
	}

	public Faction(String name, String description, String imgPath, Unit... unitTypes) {
		super(name, description, imgPath, unitTypes);
	}

	public Faction(String name, String description, String imgPath, Collection<? extends Unit> unitTypes) {
		super(name, description, imgPath, unitTypes);
	}

	@Override
	public Faction copy() {
		return new Faction(getName(), getDescription(), getImgPath(), getAll());
	}

	public static Collection<Faction> getPredefinedFactions() {
		return getPredefined(Faction.class);
	}
}
