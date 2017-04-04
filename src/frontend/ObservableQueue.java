package frontend;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.*;

//TODO Just replace this with ModifiableObservableListBase? It supports ListChangeListeners instead of just InvalidationListeners, and has all the list methods too
public class ObservableQueue<E> implements Queue<E>, Observable {

	Collection<InvalidationListener> listeners;
	List<E> elements;

	public ObservableQueue() {
		this(new ArrayList<E>());
	}

	public ObservableQueue(Collection<? extends E> elements) {
		this.elements = new ArrayList<E>(elements);
	}

	@Override
	public int size() {
		return elements.size();
	}

	@Override
	public boolean isEmpty() {
		return elements.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return elements.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return elements.iterator();
	}

	@Override
	public Object[] toArray() {
		return elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return elements.toArray(a);
	}

	@Override
	public boolean remove(Object o) {
		if (elements.remove(o)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return elements.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (elements.addAll(c)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		if (elements.removeAll(c)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		if (elements.retainAll(c)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		int sizeBeforeClear = elements.size();
		elements.clear();
		if (sizeBeforeClear > 0) {
			notifyListeners();
		}
	}

	@Override
	public boolean add(E e) {
		if (elements.add(e)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public boolean offer(E e) {
		if (elements.add(e)) {
			notifyListeners();
			return true;
		}
		return false;
	}

	@Override
	public E remove() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		return elements.remove(0);
	}

	@Override
	public E poll() {
		if (size() == 0) {
			return null;
		}
		return elements.remove(0);
	}

	@Override
	public E element() {
		if (size() == 0) {
			throw new NoSuchElementException();
		}
		return elements.get(0);
	}

	@Override
	public E peek() {
		if (size() == 0) {
			return null;
		}
		return elements.get(0);
	}

	@Override
	public void addListener(InvalidationListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(InvalidationListener listener) {
		listeners.remove(listener);
	}

	public void passTo(ObservableQueue<E> other) {
		addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				while (size() > 0) {
					other.add(poll());
				}
			}
		});
	}

	private void notifyListeners() {
		for (InvalidationListener listener : listeners) {
			listener.invalidated(this);
		}
	}
}