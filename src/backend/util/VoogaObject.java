package backend.util;

import backend.io.XMLSerializable;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/31/2017.
 */
public abstract class VoogaObject implements XMLSerializable {
    private final String name;
    private final String description;
    private final String imgPath;

    public VoogaObject(String name, String description, String imgPath) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
    }

    protected static <T extends VoogaObject> Collection<T> getPredefined(Class<T> clazz) {
        return Arrays.stream(clazz.getFields()).map(e -> {
            try {
                return e.get(null);
            } catch (IllegalAccessException e1) {
                throw new RuntimeException("This will never happen");
            }
        }).filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImgPath() {
        return imgPath;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String toXML() {
        //TODO: Tavo pls
        throw new RuntimeException("Not implemented yet");
    }
}
