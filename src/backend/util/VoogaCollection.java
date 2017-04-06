package backend.util;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public abstract class VoogaCollection<T extends VoogaObject, U extends VoogaCollection<T, U>> extends VoogaTemplate<VoogaCollection<T, U>> implements Iterable<T> {
    private final Map<String, T> gameObjects;

    public VoogaCollection(String name, String description, String imgPath, T... gameObjects) {
        this(name, description, imgPath, Arrays.asList(gameObjects));
    }

    public VoogaCollection(String name, String description, String imgPath, Collection<T> gameObjects) {
        super(name, description, imgPath);
        this.gameObjects = gameObjects.stream().collect(Collectors.toMap(VoogaObject::getName, e -> e));
    }

    @Override
    public abstract U copy();

    public T get(String name) {
        return gameObjects.get(name);
    }

    public Collection<T> getAll() {
        return Collections.unmodifiableCollection(gameObjects.values());
    }

    public VoogaCollection<T, U> add(T u) {
        gameObjects.put(u.getName(), u);
        return this;
    }

    public U addAll(Collection<T> predefinedTerrain) {
        gameObjects.putAll(predefinedTerrain.stream().collect(Collectors.toMap(T::getName, e -> e)));
        return (U) this;
    }

    public U addAll(T... predefinedTerrains) {
        return addAll(Arrays.asList(predefinedTerrains));
    }

    public U remove(T u) {
        remove(u.getName());
        return (U) this;
    }

    public U remove(String s) {
        gameObjects.remove(s);
        return (U) this;
    }

    public int size() {
        return gameObjects.size();
    }

    @Override
    public Iterator<T> iterator() {
        return gameObjects.values().iterator();
    }

}
