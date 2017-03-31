package backend.cell;

import backend.GameObjectSet;

import java.util.Collection;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class Region extends GameObjectSet<Terrain> {
    public Region(String name, String description, String imgPath) {
        super(name, description, imgPath);
    }

    public Region(String name, Collection<Terrain> terrains, String description, String imgPath) {
        super(name, terrains, description, imgPath);
    }
}
