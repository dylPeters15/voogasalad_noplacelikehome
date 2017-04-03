package backend.cell;

import backend.unit.properties.InteractionModifier;
import backend.util.VoogaObject;

import java.util.Collection;

/**
 * @author Created by th174 on 3/31/2017.
 */
public class Terrain extends VoogaObject {
    public static final int IMPASSABLE = Integer.MAX_VALUE;
    //TODO: ResourceBundlify this shit
    public static final Terrain EMPTY = new Terrain("Empty", IMPASSABLE, (InteractionModifier<Double>) InteractionModifier.NO_EFFECT, "Literally nothing", "black_void_of_the_abyss.png");
    public static final Terrain FLAT = new Terrain("Flat", 1, new InteractionModifier<>("Default Flat Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .7 ? originalValue : 0, "Units have 30% evasion on Flat terrain by default."), "Open, flat, land that offers little defensive cover, but allows for easy movement.", "grassy_plain.png");
    public static final Terrain FOREST = new Terrain("Forest", 2, new InteractionModifier<>("Default Forest Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .4 ? originalValue : 0, "Units have 60% evasion on Forest terrain by default."), "Thick forest that offers plenty of cover, but makes navigating difficult.", "forest.png");
    public static final Terrain WATER = new Terrain("Water", 3, new InteractionModifier<>("Default Water Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .8 ? originalValue : 0, "Units have 20% evasion on Water terrain by default."), "Water that impedes movement for non-aquatic units", "splish_splash.png");
    public static final Terrain MOUNTAIN = new Terrain("Mountain", 2, new InteractionModifier<>("Default Mountain Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .5 ? originalValue : 0, "Units have 50% evasion on Mountain terrain by default."), "Rugged mountains that are difficult to navigate through", "snowy_mountains.png");
    public static final Terrain FORTIFIED = new Terrain("Fortified", 1, new InteractionModifier<>("Default Fortified Terrain Defense", (originalValue, agent, target, game) -> Math.random() < .3 ? originalValue : 0, "Units have 70% evasion on Fortified terrain by default."), "A fortified defensive position", "castle.png");

    private static final int DEFAULT_DEFAULT_MOVE_COST = 1;
    private final int defaultMoveCost; //overridden by unit specific costs
    private final InteractionModifier defaultDefenseModifier; //overridden by unit specific modifiers

    public Terrain(String name, String description, String defaultImgPath) {
        this(name, DEFAULT_DEFAULT_MOVE_COST, description, defaultImgPath);
    }

    public Terrain(String name, int defaultMoveCost, String description, String defaultImgPath) {
        this(name, defaultMoveCost, (InteractionModifier<Double>) InteractionModifier.DUMMY, description, defaultImgPath);
    }

    public Terrain(String name, InteractionModifier<Double> defaultDefenseModifier, String description, String defaultImgPath) {
        this(name, DEFAULT_DEFAULT_MOVE_COST, defaultDefenseModifier, description, defaultImgPath);
    }

    public Terrain(String name, int defaultMoveCost, InteractionModifier<Double> defaultDefenseModifier, String description, String defaultImgPath) {
        super(name, description, defaultImgPath);
        this.defaultMoveCost = defaultMoveCost;
        this.defaultDefenseModifier = defaultDefenseModifier;
    }

    public static Collection<Terrain> getPredefinedTerrain() {
        return getPredefined(Terrain.class);
    }

    public boolean equals(Object obj) {
        return (obj instanceof Terrain) && ((Terrain) obj).getName().equals(this.getName());
    }

    public int hashCode() {
        return getName().hashCode();
    }

    public int getDefaultMoveCost() {
        return defaultMoveCost;
    }

    public InteractionModifier getDefaultDefenseModifier() {
        return defaultDefenseModifier;
    }
}
