package backend;

import java.util.*;

/**
 * @author Created by th174 on 3/30/2017.
 */
public class GameObjectSet<T extends GameObjectImpl> extends GameObjectImpl implements Iterable<T> {
    private Map<String, T> gameObjects;

    public GameObjectSet(String name, String description, String imgPath) {
        this(name, new HashSet<>(), description, imgPath);
    }

    public GameObjectSet(String name, Collection<T> gameObjects, String description, String imgPath) {
        super(name, description, imgPath);
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
