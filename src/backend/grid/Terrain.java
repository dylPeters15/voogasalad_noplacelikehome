/**
 *
 */
package backend.grid;

import backend.Game;
import backend.GameObject;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Dylan Peters, Timmy Huang
 */
public class Terrain extends GameObject {
    public static final Terrain NONE = new Terrain("None", "Literally nothing", "black_void_of_the_abyss.png");
    public static final Terrain FLAT = new Terrain("Flat", "Open, flat, land that offers little defensive cover, but allows for easy movement.", "grassy_plain.png");
    public static final Terrain FOREST = new Terrain("Forest", "Thick forest that offers plenty of cover, but makes navigating difficult.", "forest.png");
    public static final Terrain WATER = new Terrain("Water", "Water that impedes movement for non-aquatic units", "splish_splash.png");
    public static final Terrain MOUNTAIN = new Terrain("Mountain", "Rugged mountains that are difficult to navigate through", "snowy_mountains.png");
    public static final Terrain FORTIFIED = new Terrain("Fortified", "A fortified defensive position", "castle.png");

    protected Terrain(String name, String description, String defaultImgPath) {
        this(name, description, Paths.get(defaultImgPath));
    }

    protected Terrain(String name, String description, Path defaultImgPath) {
        super(name, description, defaultImgPath, null);
    }

    protected Terrain(String name, String description, String defaultImgPath, Game game) {
        super(name, description, defaultImgPath, game);
    }

    protected Terrain(String name, String description, Path defaultImgPath, Game game) {
        super(name, description, defaultImgPath, game);
    }
}
