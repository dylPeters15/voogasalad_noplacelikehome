/**
 *
 */
package backend.cell;

import backend.GameObject;
import backend.GameObjectImpl;
import backend.unit.properties.DefensiveModifier;
import backend.unit.properties.InteractionModifier;

/**
 * Dylan
 *
 * @author Dylan Peters, Timmy Huang
 */
public class Terrain extends GameObjectImpl implements GameObject {
    //TODO: Resource bundle this shit
    public static final Terrain NONE = new Terrain(
            "None",
            Integer.MAX_VALUE,
            (originalValue, agent, target, game) -> 0.0,
            "Literally nothing",
            "black_void_of_the_abyss.png");
    public static final Terrain FLAT = new Terrain(
            "Flat",
            1,
            (originalValue, agent, target, game) -> Math.random() < .7 ? originalValue : 0,
            "Open, flat, land that offers little defensive cover, but allows for easy movement.",
            "grassy_plain.png");
    public static final Terrain FOREST = new Terrain(
            "Forest",
            2,
            (originalValue, agent, target, game) -> Math.random() < .4 ? originalValue : 0,
            "Thick forest that offers plenty of cover, but makes navigating difficult.",
            "forest.png");
    public static final Terrain WATER = new Terrain(
            "Water",
            3,
            (originalValue, agent, target, game) -> Math.random() < .8 ? originalValue : 0,
            "Water that impedes movement for non-aquatic units", "splish_splash.png");
    public static final Terrain MOUNTAIN = new Terrain(
            "Mountain",
            2,
            (originalValue, agent, target, game) -> Math.random() < .5 ? originalValue : 0,
            "Rugged mountains that are difficult to navigate through",
            "snowy_mountains.png");
    public static final Terrain FORTIFIED = new Terrain(
            "Fortified",
            1,
            (originalValue, agent, target, game) -> Math.random() < .3 ? originalValue : 0,
            "A fortified defensive position",
            "castle.png");

    private static final int DEFAULT_DEFAULT_MOVE_COST = 1; //TODO: Better name?
    private final int defaultMoveCost; //overridden by unit specific costs
    private final InteractionModifier defaultDefenseModifier; //overridden by unit specific modifiers

    public Terrain(String name, String description, String defaultImgPath) {
        this(name, DEFAULT_DEFAULT_MOVE_COST, description, defaultImgPath);
    }

    public Terrain(String name, int defaultMoveCost, String description, String defaultImgPath) {
        this(name, defaultMoveCost, (DefensiveModifier) InteractionModifier.NO_EFFECT, description, defaultImgPath);
    }

    public Terrain(String name, DefensiveModifier defaultDefenseModifier, String description, String defaultImgPath) {
        this(name, DEFAULT_DEFAULT_MOVE_COST, defaultDefenseModifier, description, defaultImgPath);
    }

    public Terrain(String name, int defaultMoveCost, DefensiveModifier defaultDefenseModifier, String description, String defaultImgPath) {
        super(name, description, defaultImgPath);
        this.defaultMoveCost = defaultMoveCost;
        this.defaultDefenseModifier = defaultDefenseModifier;
    }

    public boolean equals(Object obj) {
        return (obj instanceof Terrain) && ((Terrain) obj).getName().equals(this.getName());
    }
    
    public int hashCode(){
        return getName().hashCode();
    }

    public int getDefaultMoveCost() {
        return defaultMoveCost;
    }

    public InteractionModifier getDefaultDefenseModifier() {
        return defaultDefenseModifier;
    }
}
