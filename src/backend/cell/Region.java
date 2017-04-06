package backend.cell;

import backend.util.VoogaCollection;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Region extends VoogaCollection<Terrain, Region> {
    //TODO ResourceBundlify this
    public Region DEFAULT_REGION = new Region("Default Region")
            .setDescription("The default region contains all the pre-defined terrains")
            .setImgPath("default_region.png")
            .addAll(Terrain.getPredefinedTerrain());

    public Region(String name) {
        super(name, "", "");
    }

    public Region(String name, String description, String imgPath, Terrain... gameObjects) {
        super(name, description, imgPath, gameObjects);
    }

    public Region(String name, String description, String imgPath, Collection<Terrain> gameObjects) {
        super(name, description, imgPath, gameObjects);
    }

    @Override
    public Region copy() {
        return new Region(getName(), getDescription(), getImgPath(), getAll());
    }

    public static Collection<Region> getPredefinedRegions() {
        return getPredefined(Region.class);
    }
}
