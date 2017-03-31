package backend.cell;

import backend.util.GameCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Region extends GameCollection<Terrain> {
    //TODO ResourceBundlify this
    public Region DEFAULT_REGION = new Region("Default Region", "The default region contains all the pre-defined terrains", "default_region.png", Terrain.getPredefinedTerrain());

    public Region(String name, String description, String imgPath, Terrain... gameObjects) {
        super(name, description, imgPath, gameObjects);
    }

    public Region(String name, String description, String imgPath, Collection<Terrain> gameObjects) {
        super(name, description, imgPath, gameObjects);
    }

    public static Collection<Region> getPredefinedRegions() {
        return getPredefined(Region.class);
    }
}
