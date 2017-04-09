package backend.util;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Created by th174 on 3/30/2017.
 */
public abstract class VoogaCollection<T extends VoogaObject, U extends VoogaCollection<T, U>> extends VoogaTemplate<U> implements ImmutableVoogaCollection<T> {
	private final Map<String, T> gameObjects;

	@SafeVarargs
	public VoogaCollection(String name, String description, String imgPath, T... gameObjects) {
		this(name, description, imgPath, Arrays.asList(gameObjects));
	}

	public VoogaCollection(String name, String description, String imgPath, Collection<? extends T> gameObjects) {
		super(name, description, imgPath);
		this.gameObjects = new LinkedHashMap<>();
		addAll(gameObjects);
	}

	public U addAll(Collection<? extends T> elements) {
		gameObjects.putAll(elements.parallelStream().collect(Collectors.toMap(T::getName, e -> e)));
		return (U) this;
	}

	public U removeAll(Collection<? extends T> elements) {
		gameObjects.values().removeAll(elements);
		return (U) this;
	}

	@Override
	public abstract U copy();

	@Override
	public T get(String name) {
		return gameObjects.get(name);
	}

	@Override
	public final int size() {
		return gameObjects.size();
	}

	@Override
	public Collection<? extends T> getAll() {
		return Collections.unmodifiableCollection(gameObjects.values());
	}

	@Override
	public Iterator<T> iterator() {
		return gameObjects.values().iterator();
	}

	@SafeVarargs
	public final U addAll(T... elements) {
		return addAll(Arrays.asList(elements));
	}

	@SafeVarargs
	public final U removeAll(T... element) {
		return removeAll(Arrays.asList(element));
	}

	public final U removeAll(String... s) {
		return removeAll(Arrays.stream(s).map(this::get).collect(Collectors.toList()));
	}

	public final U removeIf(Predicate<T> removalCondition) {
		gameObjects.values().removeIf(removalCondition);
		return (U) this;
	}
}
