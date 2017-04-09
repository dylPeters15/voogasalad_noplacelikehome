package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.util.ModifiableVoogaObject;

import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class ModifiableTerrain extends ModifiableVoogaObject<ModifiableTerrain> implements Terrain {
	public static final int IMPASSABLE = Integer.MAX_VALUE;
	//TODO: ResourceBundlify this
	public static final Terrain EMPTY = new ModifiableTerrain("Empty")
			.setDefaultMoveCost(IMPASSABLE)
			.setDefaultDefenseModifier(InteractionModifier.NO_EFFECT)
			.setDescription("Literally nothing")
			.setImgPath("black_void_of_the_abyss.png");
	public static final Terrain FLAT = new ModifiableTerrain("Flat")
			.setDefaultMoveCost(1)
			.setDefaultDefenseModifier(new InteractionModifier<>("Default Flat Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .7 ? originalValue : 0, "Units have 30% evasion on Flat terrain by default."))
			.setDescription("Open, flat, land that offers little defensive cover, but allows for easy movement.")
			.setImgPath("grassy_plain.png");
	public static final Terrain FOREST = new ModifiableTerrain("Forest")
			.setDefaultMoveCost(2)
			.setDefaultDefenseModifier(new InteractionModifier<>("Default Forest Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .4 ? originalValue : 0, "Units have 60% evasion on Forest terrain by default."))
			.setDescription("Thick forest that offers plenty of cover, but makes navigating difficult.")
			.setImgPath("forest.png");
	public static final Terrain WATER = new ModifiableTerrain("Water")
			.setDefaultMoveCost(3)
			.setDefaultDefenseModifier(new InteractionModifier<>("Default Water Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .8 ? originalValue : 0, "Units have 20% evasion on Water terrain by default."))
			.setDescription("Water that impedes movement for non-aquatic units")
			.setImgPath("splish_splash.png");
	public static final Terrain MOUNTAIN = new ModifiableTerrain("Mountain")
			.setDefaultMoveCost(2)
			.setDefaultDefenseModifier(new InteractionModifier<>("Default Mountain Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .5 ? originalValue : 0, "Units have 50% evasion on Mountain terrain by default."))
			.setDescription("Rugged mountains that are difficult to navigate through")
			.setImgPath("snowy_mountains.png");
	public static final Terrain FORTIFIED = new ModifiableTerrain("Fortified")
			.setDefaultMoveCost(1)
			.setDefaultDefenseModifier(new InteractionModifier<>("Default Fortified Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .3 ? originalValue : 0, "Units have 70% evasion on Fortified terrain by default."))
			.setDescription("A fortified defensive position")
			.setImgPath("castle.png");

	private static final int DEFAULT_DEFAULT_MOVE_COST = 1;
	private int defaultMoveCost; //overridden by unit specific costs
	private InteractionModifier<Double> defaultDefenseModifier; //overridden by unit specific modifiers

	public ModifiableTerrain(String name) {
		this(name, DEFAULT_DEFAULT_MOVE_COST, (InteractionModifier<Double>) InteractionModifier.DUMMY, "", "");
	}

	public ModifiableTerrain(String name, int defaultMoveCost, InteractionModifier<Double> defaultDefenseModifier, String description, String defaultImgPath) {
		super(name, description, defaultImgPath);
		this.defaultMoveCost = defaultMoveCost;
		this.defaultDefenseModifier = defaultDefenseModifier;
	}

	@Override
	public Terrain copy() {
		return new ModifiableTerrain(getName(), getDefaultMoveCost(), getDefaultDefenseModifier(), getDescription(), getImgPath());
	}

	@Override
	public int getDefaultMoveCost() {
		return defaultMoveCost;
	}

	public ModifiableTerrain setDefaultMoveCost(int defaultMoveCost) {
		this.defaultMoveCost = defaultMoveCost;
		return this;
	}

	@Override
	public InteractionModifier<Double> getDefaultDefenseModifier() {
		return defaultDefenseModifier;
	}

	public ModifiableTerrain setDefaultDefenseModifier(InteractionModifier<Double> defaultDefenseModifier) {
		this.defaultDefenseModifier = defaultDefenseModifier;
		return this;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return (obj instanceof ModifiableTerrain) && ((Terrain) obj).getName().equals(this.getName());
	}

	public static Collection<ModifiableTerrain> getPredefinedTerrain() {
		return getPredefined(ModifiableTerrain.class);
	}
}
