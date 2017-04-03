package backend.util;

import javafx.util.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class VoogaCollection<T extends VoogaObject> extends VoogaObject implements Iterable<T> {
    private final Map<String, T> gameObjects;

    public VoogaCollection(String name, String description, String imgPath, T... gameObjects) {
        this(name, description, imgPath, Arrays.asList(gameObjects));
    }

    public VoogaCollection(String name, String description, String imgPath, Collection<T> gameObjects) {
        super(name, description, imgPath);
        this.gameObjects = gameObjects.stream().map(e -> new Pair<>(e.getName(), e)).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    public T get(String name) {
        return gameObjects.get(name);
    }

    public Collection<T> getAll() {
        return Collections.unmodifiableCollection(gameObjects.values());
    }

    public void add(T u) {
        gameObjects.put(u.getName(), u);
    }

    public void remove(T u) {
        remove(u.getName());
    }

    public void remove(String s) {
        gameObjects.remove(s);
    }

    public int size() {
        return gameObjects.size();
    }

    @Override
    public Iterator<T> iterator() {
        return gameObjects.values().iterator();
    }
}
