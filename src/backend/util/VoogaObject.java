package backend.util;

import backend.io.XMLsavable;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/31/2017.
 */
public abstract class VoogaObject implements XMLsavable {
    private final String name;
    private final String description;
    private final Path imgPath;

    public VoogaObject(String name, String description, String imgPath) {
        this(name, description, Paths.get(imgPath));
    }

    public VoogaObject(String name, String description, Path imgPath) {
        this.name = name;
        this.description = description;
        this.imgPath = imgPath;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Path getImgPath() {
        return imgPath;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public String toXml() {
        //TODO: Make Tavo XStream this shit
        throw new RuntimeException("Not implemented yet");
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
}
